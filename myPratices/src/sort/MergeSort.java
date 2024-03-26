package sort;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr=new int[]{1, 31, 4, 1, 2, 5, 7, 8,};
        mergeSort(arr,0,arr.length-1);

        for (int value : arr) {
            System.out.print(value + " ");
        }
    }
    private static void mergeSort(int [] arr,int left,int right){
        //结束条件
        if (left>=right){
            return;
        }
        int middle=(left+right)/2;
        mergeSort(arr,left,middle);
        mergeSort(arr,middle+1,right);
        merge(arr,left,middle,right);
    }

    /**
     * 合并两个数组
     * @param arr
     * @param left
     * @param middle
     * @param right
     */
    private static void merge(int [] arr,int left,int middle, int right){
        int tmp[]=new int[right-left+1];
        int i=left;
        int j=middle+1;
        int index=0;

        //合并两个数组
        while (i<=middle||j<=right){
            if (i<=middle&&j<=right){
                tmp[index++]=arr[i]<arr[j]?arr[i++]:arr[j++];
            }
            else {
                if (i <= middle) {
                    tmp[index++] = arr[i++];
                }
                if (j <= right) {
                    tmp[index++] = arr[j++];
                }
            }
        }

        index=left;
        //填充到原数组
        for (int value:tmp){
            arr[index++]=value;
        }
    }
}
