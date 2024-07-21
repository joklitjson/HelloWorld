package sort;

/**
 * 归并合并的 两种方案：
 * 1、自顶向下
 * 2、自底向上
 */
public class MergeSort {

    static int []tmp=null;
    public static void main(String[] args) {
        int[] arr=new int[]{1, 31, 4, 1, 2, 5, 7, 8,};
        tmp=new int[arr.length];
//        mergeSort(arr,0,arr.length-1);
        mergeSortBU(arr);

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

    public  static  int[] mergeSortBU(int[] arr) {
        if (arr.length < 2) return arr;
        int[] tmpArr = new int[arr.length];
        // 间隔，注意不能写成gap < arr.length / 2 + 1，此种写法只适用于元素个数为2的n次幂时
        for(int gap = 1; gap < arr.length; gap *= 2) {
            // 基本分区合并(随着间隔的成倍增长，一一合并，二二合并，四四合并...)
            for(int left = 0; left < arr.length - gap; left += 2 * gap) {
                // 调用非原地合并方法。leftEnd = left+gap-1; rightEnd = left+2*gap-1;
//                System.out.println("grap="+gap+"; left="+left+" leftEnd="+(left + gap - 1)+"; rightEnd="+(left + gap )+"right="+Math.min(left + 2 * gap - 1, arr.length - 1));

                System.out.println("grap="+gap+"; left="+left+"right="+Math.min(left + 2 * gap - 1, arr.length - 1));
                merge(arr, tmpArr, left, left + gap - 1, Math.min(left + 2 * gap - 1, arr.length - 1));
            }
        }
        return arr;
    }

    // 非原地合并方法
    private static void merge(int[] arr, int[] tmpArr, int leftPos, int leftEnd, int rightEnd) {
        int rightPos = leftEnd + 1;
        int startIdx = leftPos;
        int tmpPos = leftPos;
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (arr[leftPos] <= arr[rightPos]) {
                tmpArr[tmpPos++] = arr[leftPos++];
            }
            else {
                tmpArr[tmpPos++] = arr[rightPos++];
            }
        }
        // 比较完成后若左数组还有剩余，则将其添加到tmpArr剩余空间
        while (leftPos <= leftEnd) {
            tmpArr[tmpPos++] = arr[leftPos++];
        }
        // 比较完成后若右数组还有剩余，则将其添加到tmpArr剩余空间
        while (rightPos <= rightEnd) {
            tmpArr[tmpPos++] = arr[rightPos++];
        }
        // 容易遗漏的步骤，将tmpArr拷回arr中
        // 从小区间排序到大区间排序，大区间包含原来的小区间，需要从arr再对应比较排序到tmpArr中，
        // 所以arr也需要动态更新为排序状态，即随时将tmpArr拷回到arr中
        for(int i = startIdx; i <= rightEnd; i++) {
            arr[i] = tmpArr[i];
        }
    }
}
