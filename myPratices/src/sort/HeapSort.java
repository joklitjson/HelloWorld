package sort;

import java.util.Arrays;

/**
 * 堆排序:
 * 方案：1、先把堆调整成大根堆，
 * 2、然后在循环把顶部最大的数和 最后一个数字进行交换，然后在进行大根堆的调整
 */
public class HeapSort {

    public static void main(String[] args) {
        int [] arr=new int[]{5,3,7,12,33,1,4,8};
        new HeapSort().sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int arr[]) {
        //进行大根堆的调整
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        //依次取出堆顶，然后在和最后一个元素进行交换，依此 达到升序排列的过程

        for (int i=n-1;i>0;i--){
            //进行交换
            int temp=arr[0];
            arr[0]=arr[i];
            arr[i]=temp;

            //check顶部元素
            heapify(arr,i,0);

        }
    }

    /**
     * 进行大根堆的调整
     *
     * @param arr
     * @param n
     * @param i
     */
    void heapify(int arr[], int n, int i) {
        int larger = i;//默认最大值是跟节点
        int left = 2 * i + 1;//左孩子
        int right = 2 * i + 2;//右孩子

        //左孩子大于根，则 把左孩子当做最大
        if (left < n && arr[left] > arr[larger]) {
            larger = left;
        }
        //右孩子 大于最大的值，因此把右孩子当做最大值
        if (right < n && arr[right] > arr[larger]) {
            larger = right;
        }

        //进行交换
        if (larger != i) {
            //进行交换
            int tmp = arr[i];
            arr[i] = arr[larger];
            arr[larger] = tmp;
            //继续调整下一个最大值
            heapify(arr, n, larger);
        }
    }
}
