package sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr=new int[]{1, 31, 4, 1, 2, 5, 7, 8,};
//        quickSort(arr,0,arr.length-1);
        quickSortWithStack(arr,0,arr.length-1);
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
}
