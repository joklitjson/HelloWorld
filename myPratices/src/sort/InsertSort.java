package sort;

public class InsertSort {
    public static void main(String[] args) {
        insertSort(new int[]{1, 31, 4, 1, 2, 5, 7, 8,});
    }

    private static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int insertValue = arr[i];
            int j = i - 1;
            //从右向左比较原始，然后进行交换
            for (; j >= 0 && insertValue < arr[j]; j--) {
                arr[j + 1] = arr[j];
            }
            // 插入到已排序区间中小于等于自己元素的后面，保证排序算法的稳定性
            arr[j + 1] = insertValue;
        }

        for (int value : arr) {
            System.out.print(value + " ");
        }
    }
}
