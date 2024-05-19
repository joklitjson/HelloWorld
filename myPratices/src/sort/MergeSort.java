package sort;

public class MergeSort {

    static int []tmp=null;
    public static void main(String[] args) {
        int[] arr=new int[]{1, 31, 4, 1, 2, 5, 7, 8,};
        tmp=new int[arr.length];
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
     * 合并两个数组
     * @param arr
     * @param left
     * @param middle
     * @param right
     */
    private static void merge(int [] arr,int left,int middle, int right){

        //copy排好的数组
        for(int i=left;i<=right;i++){
            tmp[i]=arr[i];
        }
        int i=left,p=left,j=middle+1;
        while (p<=right) {
            if (i == middle + 1) {
                //左半部分的数据已经用完
                arr[p++] = tmp[j++];
            } else if (j == right + 1) {
                arr[p++] = tmp[i++];
            } else if (tmp[i] < tmp[j]) {
                arr[p++] = tmp[i++];
            } else {
                arr[p++] = tmp[j++];
            }
        }
//        int tmp[]=new int[right-left+1];
//        int i=left;
//        int j=middle+1;
//        int index=0;
//
//        //合并两个数组
//        while (i<=middle||j<=right){
//            if (i<=middle&&j<=right){
//                tmp[index++]=arr[i]<arr[j]?arr[i++]:arr[j++];
//            }
//            else {
//                if (i <= middle) {
//                    tmp[index++] = arr[i++];
//                }
//                if (j <= right) {
//                    tmp[index++] = arr[j++];
//                }
//            }
//        }
//
//        index=left;
//        //填充到原数组
//        for (int value:tmp){
//            arr[index++]=value;
//        }
    }
}
