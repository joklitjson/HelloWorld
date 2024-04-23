package sort;

public class SelectionSort {
    public static void main(String[] args) {
        selectionSort(new int[]{1, 31, 4, 1, 2, 5, 7, 8,});
    }

    /**
     * 选择排序:在未排序区间选择最小值，然后在和未排序区间的第一个值进行交换
     * 优点：和冒泡排序相比，交换次数少，。
     * 缺点：是不稳定排序，相同分数的数据，位置会发生变化
     * @param arr
     */
    private static void selectionSort(int [] arr){
        for (int i=0;i< arr.length;i++){
            int minIndex=i;
            //
            for (int j=i+1;j< arr.length;j++){
                if (arr[j]<arr[minIndex]){
                    minIndex=j;
                }
            }
            //交换位置
            if (minIndex!=i) {
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }

        for (int value : arr) {
            System.out.print(value + " ");
        }
    }
}
