package sort;

/**
 * 从左到右，假设左侧是已排序区间，每次排序 把右侧未排序区间的第一个数字插入到左侧区间的合理位置，使左侧区间变得有序
 * 每次右侧第一个元素是要插入的元素，插入排序是稳定性排序
 */
public class InsertSort {
    public static void main(String[] args) {
        insertSort(new int[]{1, 31, 4, 1, 2, 5, 7, 8,});
    }

    private static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int insertValue = arr[i];
            int j = i - 1;
            //从右向左比较有序位置元素
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
