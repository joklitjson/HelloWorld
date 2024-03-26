package sort;

public class SelectionSort {
    public static void main(String[] args) {
        selectionSort(new int[]{1, 31, 4, 1, 2, 5, 7, 8,});
    }

    /**
     * 选择排序:在未排序区间选择最小值，然后在和未排序区间的第一个值进行交换
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
