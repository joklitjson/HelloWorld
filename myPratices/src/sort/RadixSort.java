package sort;

import java.util.Arrays;

/**
 * 基数排序
 * https://blog.csdn.net/qq_53414724/article/details/125015867
 */
public class RadixSort {
    public static void main(String[] args) {
        radixSort(new int[]{12,3,456,23,8,678,1345},4);
        System.out.println();

        System.out.println(Arrays.toString(radixSortStr(new String[]{"abc","ab","ff","ha","jukf","aaa","bbb","ccc","wsdfg"},5)));
    }

    /**
     * 1、总体分成两个步骤：计算、收集
     * 计算 就是计算每个数的余数，然后在放在指定的余数列表中，
     * 收集：就是把List<list> 中的数据再次放在数组中，然后在把基数增加*10,直到达到最大长度
     * @param arr
     * @param maxLength
     */
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

    /**
     * 基于字符串的基数排序
     * @param arr
     * @param maxLength
     */
    private static String []  radixSortStr(String [] arr,int maxLength){

        //排序结果数组
        String sorted[]=new String[arr.length];

        //从低位开始遍历
        for (int k=maxLength-1;k>=0;k--){
            int [] count=new int[128];
            //计数排序的过程，分3步走
            //1、遍历每个字符的第k位
            for (int j=0;j<arr.length;j++){
               int index= getCharIndex(arr[j],k);
               count[index]++;
            }
            //2、做变形处理,计算排名
            for (int i=1;i<count.length;i++){
                count[i]=count[i]+count[i-1];
            }
            //3、倒序遍历数组，然后
            for (int i=arr.length-1;i>=0;i--){
                int index= getCharIndex(arr[i],k);
                int sortIndex=count[index]-1;//-1的目的是sorted是从0开始排序的
                sorted[sortIndex]=arr[i];
                count[index]=count[index]-1;
            }
            //下一轮的排序需要再上一轮的基础上 进行，因此这里直接使用已经排序的数组处理
            arr=sorted.clone();
        }
        return sorted;
    }

    /**
     * 获取字符串str的第k位对应的字符
     * @param str
     * @param k
     * @return
     */
    private static int getCharIndex(String str,int k){
        //如果字符串长度小于k，则补0处理
        if (str.length()<k+1){
            return 0;
        }

        return  str.charAt(k);
    }
}
