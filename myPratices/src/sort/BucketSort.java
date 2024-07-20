package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://blog.csdn.net/qq_36256590/article/details/126663121
 * 将数组分到有限数量的桶子里。每个桶子再个别排序（有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排序）。
 * 简单概括就是：划分多个范围相同的区间，每个子区间自排序，最后合并（分治思想的体现）。
 * 补充： 映射函数一般是 f = array[i] / k; k^2 = n; n是所有元素个数.
 * 桶排序的步骤：
 * 1、计算数组内的最大和 最小值
 * 2、确定桶的个数
 * 3、入桶
 * 4、桶内再次排序
 * 5、出桶
 */
public class BucketSort {

    public static void main(String[] args) {
        bucketSort(new int[]{1,5,7,33,4,22,98,102,55});
    }
    public static void bucketSort(int[] arr) {

        //1、计算最值
        int min=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;
        for (int val:arr){
            min=Math.min(min,val);
            max=Math.max(max,val);
        }

        //2、计算桶的数量
        int bucketCount=(max-min)/arr.length+1;
        List<List<Integer>> bucket=new ArrayList<>();
        for (int i=0;i<bucketCount;i++){
            bucket.add(new ArrayList<>());
        }

        //3、入桶
        for (int val:arr){
            int bktIndex=(val-min)/bucketCount;
            bucket.get(bktIndex).add(val);
        }

        //4、桶内排序
        for (List<Integer> list:bucket){
            Collections.sort(list);
        }

        //5输出

        for (List<Integer> list:bucket){
          for (int i:list){
              System.out.print(i+" ");
          }
        }
        System.out.println();
    }
}
