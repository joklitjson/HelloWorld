package sort;

/**
 * 基数排序
 * https://blog.csdn.net/qq_53414724/article/details/125015867
 */
public class RadixSort {
    public static void main(String[] args) {
        radixSort(new int[]{12,3,456,23,8,678,1345},4);
    }
    private static void radixSort(int [] arr,int maxLength) {
        int index = 0;
        int m = 0;
        int i = 1;
        int[][] tmpArr = new int[10][arr.length];//第一维表示的是余数的位，第二维表示的是数,比如[3][24,54]表示余数是3的有那些数字
        int[] count = new int[10];//余数下的数量，[5]=2表示余数是5的有两个
        for (; m < maxLength; m++) {
            //计算
            for (int value : arr) {
                int tmp = (value / i) % 10;//获取余数
                tmpArr[tmp][count[tmp]] = value;

                count[tmp]++;
            }

            //收集
            for (int j = 0; j < 10; j++) {
                if (count[j] > 0) {
//                    比如格式是这样的： [3][24,54]
                    for (int k = 0; k < count[j]; k++) {
                        arr[index++] = tmpArr[j][k];
                    }
                }
                count[j] = 0;
            }

            index = 0;//重置
            i *= 10;//进位处理
        }
        for (int value : arr) {
            System.out.print(value + " ");
        }
    }
}
