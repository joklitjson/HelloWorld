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

        //2、记录之前的位移量
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
}
