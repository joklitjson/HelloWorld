package sort;

public class JishuSort {
    public static void main(String[] args) {
//        countSort(new int [] {1,2,3,1,5,8,6,5});

        countSort2(new int [] {1,2,3,1,5,8,6,5});

//        countSort2(new int [] {1,2,2,4});
    }

    /**
     * 1、基本计数排序算法，把值当做下标，一个下标中可能会有多个值，
     * 分为计算和收集阶段
     * 缺点：基础版能够解决一般的情况，但是它有一个缺陷，那就是存在空间浪费的问题。
     * @param arr
     */
    private static  void countSort(int [] arr){
        Integer max=arr[0];
        for (int i:arr){
            if (i>max){
                max=i;
            }
        }
        int [] sortArr=new int[max+1];
        for (int i=0;i<arr.length;i++){
            //直接把他的值当做索引下表
            sortArr[arr[i]]++;
        }

        //收集数据
        int [] finalSortArr=new int[arr.length];
        int index=0;
        for (int i=0; i<sortArr.length;i++){
            //有可能有相同的数在同一个下标中，因此这里是再次循环
            for (int count=0;count<sortArr[i];count++){
                finalSortArr[index++]=i;
            }
        }
        //打印数据
       for (int value:finalSortArr){
           System.out.println(value);
       }
    }

    /**
     * 如果我们想要原始数组中的相同元素按照本来的顺序的排列，那该怎么处理呢？
     * 方案：倒序遍历原始元素，然后再填充到新数组中
     * @param arr
     */
    private static void  countSort2(int [] arr){
        int max=arr[0],min=arr[0];
        for (int value:arr){
            if (value<min){
                min=value;
            }
            if (value>max){
                max=value;
            }
        }
        //压缩数组
        int[] sortArr=new int[max-min+1];
        for (int value:arr){
            sortArr[value-min]++;
        }

        //2、记录之前的位移量(表示他的位置)
        //.统计数组做变形，后面的元素等于前面的元素之和
        for (int i=1;i< sortArr.length;i++){
            sortArr[i]+=sortArr[i-1];
        }
        int[] finallySort=new int[arr.length];
        //3、收集
        for (int i=arr.length-1;i>=0;i--) {
//             arr[i]-min//表示位移量
            finallySort[sortArr[arr[i] - min] - 1] = arr[i];
            sortArr[arr[i] - min]--;//表示已经取出一个数字了

        }

        for (int value:finallySort){
            System.out.print(value+" ");
        }
    }

    /**
     * 排序数组，相对位置不变
     * 类似计数排序
     * @param arr1
     * @param arr2
     * @return
     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {

        int max=Integer.MIN_VALUE;
        for(int val:arr1){
            max=Integer.max(max,val);
        }

        int count[]=new int[max+1];
        //统计元素个数
        for (int val:arr1){
            count[val]++;
        }
        int index=0;
        int [] sorted=new int[arr1.length];

        //遍历数组2，填充排序数组
        for (int val:arr2){
            while (count[val]>0){
                sorted[index++]=val;
                count[val]--;
            }
        }

        //count 数组 只剩下不在arr2中的元素了,从小数开始在遍历一遍，填充到排序集合中
        for (int i=0;i<=max;i++){
            while (count[i]>0){
                sorted[index++]=i;
                count[i]--;
            }
        }
        return sorted;
    }
}
