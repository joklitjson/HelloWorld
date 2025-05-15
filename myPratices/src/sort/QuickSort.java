package sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 *
 * 不同的基准值方法，对排序的时间性能有决定性影响。下面介绍两种常用的分区法。
 *Lomuto 分区法：此方案选择最后一个元素作为基数，由于此方案代码更 紧凑 且 易于理解，常被用于教程。但整体效率低于 Hoare 的原始方案。特别是在整个数组已经有序时，效率下降到O(n^2)O(n
 *
 * Hoare 分区法 :使用数组的中间元素作为基准值，Hoare 的方案比 Lomuto 的分区方案更有效，使用双向指针，分别查找一个 大于基准索引和一个小于基准索引，然后在交换
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr=new int[]{1, 31, 4, 6, 2, 5, 7, 8,};
//        quickSort(arr,0,arr.length-1);
//        quickSortWithStack(arr,0,arr.length-1);
        randomizedQuicksort(arr,0,arr.length-1);;
        for (int value : arr) {
            System.out.print(value + " ");
        }
    }

    static  String beginKey="begin";
    static  String endKey="end";

    /**
     * 非递归调用
     * @param arr
     * @param start
     * @param right
     */
    private static void quickSortWithStack(int arr[],int start,int right){
        Stack<Map<String,Integer>> sortStack=new Stack<>();
        Map<String,Integer> map=new HashMap<>();
        sortStack.push(map);
        map.put(beginKey,start);
        map.put(endKey,right);
        //使用堆模拟递归
        while (!sortStack.isEmpty()){
            Map<String,Integer> current=  sortStack.pop();
            int tmpBegin=current.get(beginKey);
            int tmpEnd=current.get(endKey);
            int pivotIndex= partition(arr,tmpBegin,tmpEnd);
            //把左边的循环压入战中
            if (tmpBegin<pivotIndex-1){
                Map<String,Integer> leftMap=new HashMap<>();
                sortStack.push(leftMap);
                leftMap.put(beginKey,tmpBegin);
                leftMap.put(endKey,pivotIndex-1);
            }
            //把右边的区间 压入栈中
            if (pivotIndex+1<tmpEnd) {
                Map<String, Integer> rightMap = new HashMap<>();
                sortStack.push(rightMap);
                rightMap.put(beginKey, pivotIndex + 1);
                rightMap.put(endKey, tmpEnd);
            }
        }
    }
    /***
     * 递归方式调用
     * @param arr
     * @param start
     * @param right
     */
    private static void quickSort(int arr[],int start,int right) {
        if (start >= right) {
            return;
        }
        int pivotIndex = partition2(arr, start, right);
//        int pivotIndex = partition(arr, start, right);
        //pivotIndex 位置已经排序了，所以下面两个区间不需要包含他们
        quickSort(arr, start, pivotIndex-1);
        quickSort(arr, pivotIndex + 1, right);
    }

    /**
     * 交换法：顾名思义：就是交换左右两边的值，因此需要再两边都找到元素才进行交换。
     * @param arr
     * @param low
     * @param hight
     * @return
     */
    private  static int partition(int [] arr,int low,int hight){
        int left=low,right=hight;
        int pivotValue=arr[low];
        //左边不等于右边
        while (left!=right){
            while (left<right&& arr[right]>pivotValue){
                right--;
            }
            while (left<right&& arr[left]<=pivotValue){
                left++;
            }
            //左指针小于右指针 才进行交换，left==right就不需要进行交换值了
            if (left<right){
                //交换left、right的值
                int tmp=arr[right];
                arr[right]=arr[left];
                arr[left]=tmp;
            }
        }
        //pivotValue 放在合适的位置上，同时返回pivotValue所在的下表
        int tmp=arr[left];
        arr[left]=pivotValue;
        arr[low]=tmp;
        return left;
    }

    /**
     * 填坑法，就是每次循环中 在交换元素，不是等到双指针找到左右元素在交换
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private  static int partition2(int [] arr,int left,int right){

        //1\定标准元素
        int partionValue=arr[left];

        while (left<right){
            //2、找右边小于标准的元素，然后把他移动到左边的坑位
            while (left<right&&arr[right]>partionValue){
                right--;
            }
            //3、填充左边的坑位
            arr[left]=arr[right];//直接先填充

            //4、找左边大于标准元素的值，然后在把这个值 填充到右边的坑位
            while (left<right&&arr[left]<partionValue){
                left++;
            }
            arr[right]=arr[left];
        }

        //5、最后把比较值 放在正确的位置上，此时的索引就是他的位置，他把左右两边的元素都正确分开了
        // partionValue放在合适的位置
        arr[left]=partionValue;
        return left;
    }


    // 随机快速排序主函数
    public static void randomizedQuicksort(int[] arr, int low, int high) {
        if (low < high) {
            // 随机选择基准元素并分区
            int pivotIndex = randomizedPartition(arr, low, high);
            // 递归排序左右两部分
            randomizedQuicksort(arr, low, pivotIndex - 1);
            randomizedQuicksort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * 随机快速排序：就是随机选择基准值，为了更方便的把数组均分
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private  static int randomizedPartition(int [] arr,int left,int right){

        Random random=new Random();
        int randomIdx=random.nextInt(right-left+1)+left;
        //交换元素：把随机选择的元素交换到数组末尾或者开头，然后在使用 交换、填坑的方式进行计算
        swap(arr,right,randomIdx);

        // 使用标准分区方法
        return partition3(arr,left,right);
    }

    /**
     * Lomuto分区方案 核心思想就是把小于 pivot的值都交换到他左边，然后交换完成后，左边的值都小于他了，那么他就放在 左边指针的下一个位置(i+1)
     * @param arr
     * @param low
     * @param high
     * @return
     */
    // 标准分区方法（Lomuto分区方案）
    private static int partition3(int[] arr, int low, int high) {
        // 基准元素是数组末尾的元素
        int pivot = arr[high];
        int i = low - 1;

        // 将小于等于基准的元素交换到左边
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // 将基准元素放到正确的位置
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int [] arr,int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
}
