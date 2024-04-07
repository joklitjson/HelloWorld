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
        int pivotIndex = partition(arr, start, right);
        //pivotIndex 位置已经排序了，所以下面两个区间不需要包含他们
        quickSort(arr, start, pivotIndex-1);
        quickSort(arr, pivotIndex + 1, right);
    }
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
}
