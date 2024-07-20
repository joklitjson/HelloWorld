package sort;


/**
 * 泡泡排序：比较相邻元素大小，如果逆序则交换位置，因此一次冒泡之后会把最大值交换到数组的尾部，因此称为冒泡排序
 */
public class BubbleSortCalc {
    public static void main(String[] args) {
        bubbleSort(new int[]{1,31,4,1,2,5,7,8,});
    }
    private static void bubbleSort(int [] arr){
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr.length-i-1;j++){
                //左边的大于右边的，则 交换
                if (arr[j]>arr[j+1]){
                    int tmp=arr[j+1];
                    arr[j+1]=arr[j];;
                    arr[j]=tmp;
                }
            }
        }

        for (int value:arr){
            System.out.print(value+" ");
        }
    }
}
