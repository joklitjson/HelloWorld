package sort;

import java.util.Arrays;

/**
 * 三路快拍
 */
public class ThreeWayQuickSorter {
    public static void main(String[] args) {
        int[] data = new int[]{34,33,12,78,21,1,98,36};
        new ThreeWayQuickSorter().sort(data);
        System.out.println(Arrays.toString(data));
    }

    public static void sort(int [] arr){
        sortHelper(arr,0,arr.length-1);
    }

    private static void sortHelper(int []arr,int lo,int hi){
        if (lo>=hi){
            return;
        }
        int privote=arr[(hi+lo)/2];
        int less=lo,greater=hi;//小于privote的值索引
        int i=lo;
        while (i<=greater){
            if (arr[i]<privote){
//                把当前值 交换放在最左边区域
                swap(arr,i,less);
                less++;
                i++;
            }
            else if (arr[i]>privote){
                //把当前值放在大于他的区域[右侧区域]
                swap(arr,i,greater);
                greater--;
            }
            else {
                i++;
            }
        }

        sortHelper(arr,lo,less-1);
        sortHelper(arr,greater+1,hi);
    }

    private static void swap(int arr[],int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
}
