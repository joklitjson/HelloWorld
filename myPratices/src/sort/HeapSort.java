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
//        new HeapSort().sort(arr);
        new HeapSort().sort2(arr);
        System.out.println(Arrays.toString(arr));
    }


    public void  sort2(int arr[]){
        insertHeap(arr);

        int n=arr.length;
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
     * //构造大根堆（通过新插入的数上升）
     * @param arr
     */
    private void  insertHeap(int arr[]){

        int n=arr.length;
        for (int i=0;i<n;i++) {
            int current = i;
            int fatherIndex = (current - 1) / 2;
            //如果大于father，则上升
            while (arr[current] > arr[fatherIndex]) {
                int tm = arr[fatherIndex];
                arr[fatherIndex] = arr[current];
                arr[current] = tm;
                current = fatherIndex;
                fatherIndex = (current - 1) / 2;
            }
        }
    }
    public void sort(int arr[]) {
        //进行大根堆的调整
        int n = arr.length;
        //注意：这里为啥值取了一半的长度？这里取的每一个元素都是当前根节点的索引的，因此他们得左右子孩子 在数组的另一部分都能被渠道，
        // 因此这里只从中间 进行倒序遍历,同样n是指数组长度，防止越界
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        //遍历后 顶部就是最大值了，然后在逐个和后面的n-1、n-2个元素进行交换
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
     *比较 i和他的左右孩子，然后把最大的调整上去，把小的调整下来（把顶端的数进行下降）
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
