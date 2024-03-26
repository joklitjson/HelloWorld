package sort;

public class JishuSort {
    public static void main(String[] args) {
        jishuSor(new int [] {1,2,3,1,5,8,6,5});
    }

    private static  void  jishuSor(int [] arr){

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
}
