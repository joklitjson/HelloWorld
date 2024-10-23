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
        int [] arr2={1,33,44,12,4,6,8,10,111};
        shellSort2(arr2);
        System.out.println(Arrays.toString(arr2));
    }

    private static void shellSort2(int [] arr){

        int grap=arr.length;

        while (grap>1){
            grap=grap/2;//增量缩小

            for (int i=grap;i<arr.length;i++){
                int tmp=arr[i];
                int j=i;
                //内部插入排序
                for (;j>=grap&&arr[j-grap]>tmp;j-=grap){
                    //把元素向后移动
                    arr[j]=arr[j-grap];
                }
                //把tmp 放在j这个位置
                arr[j]=tmp;
            }
        }
    }
}
