package sort;

import java.util.Arrays;
/**
 * 基于插入排序进行的，先进行粗粒度的插入排序，然后再进行细粒度的排序
 * 也是不稳定排序，相同元素 会移动位置
 * 尔排序目的为了加快速度改进了插入排序，交换不相邻的元素对数组的局部进行排序，并最终用插入排序将局部有序的数组排序。
 *
 * 在此我们选择增量 gap=length/2，缩小增量以 gap = gap/2 的方式，
 */
public class ShellSortText {

    public static void main(String[] args){
        int [] arr={1,33,44,12,4,6,8,10,111};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    private static void shellSort(int [] arr){
        int d=arr.length;

        while (d>1) {
            d = d / 2;
            for (int x = 0; x < d; x++) {
                //内部希尔排序
                for (int i = x + d; i < arr.length; i += d) {
                    int tmp = arr[i];
                    int j = i - d;//第一个原始
                    for (; j >= 0 && arr[j] > tmp; j = j - d) {
                        arr[j + d] = arr[j];
                    }
                    //最后一个赋值
                    arr[j + d] = tmp;
                }
            }
        }
    }
}
