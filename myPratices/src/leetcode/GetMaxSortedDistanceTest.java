package leetcode;

public class GetMaxSortedDistanceTest {


    public static void main(String[] args) {
        int[]array=new int[]{2,6,3,4,5,10,9};
        System.out.println(getMaxSortedDistance(array));
    }
    /**
     * 无序数组排序后获取最大距离值
     * 方案一：使用log(n) 的排序算法 排好序，然后在遍历一遍即可
     * 方案二：使用桶排序，记录每个桶的最大和最小值，然后在计算相邻桶之间的差值(右桶最小值-左桶最大值)
     * @param arr
     * @return
     */
    public static int getMaxSortedDistance(int [] arr) {
        //1计算最大和最小值

        int max = arr[0];
        int min = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
            if (arr[i] < min) {
                min = arr[i];
            }
        }

        int d = max - min;
        //说明数组中的数都相同
        if (d == 0) {
            return 0;
        }

        Bucket[] buckets = new Bucket[arr.length];
        int bucketNum = buckets.length;
        //2、入桶
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            //计算桶下表
//            我们使用一个计算公式来计算所属哪个桶：f(x) = (int)((x - min) / (max - min + 1.0) * 桶个数)。
            int index = ((num - min)* bucketNum) / (d+1);
            if (buckets[index] == null) {
                buckets[index] = new Bucket();
            }
            //计算当前桶的最小值
            if (buckets[index].min == null || num < buckets[index].min) {
                buckets[index].min = num;
            }

            if (buckets[index].max == null || num > buckets[index].max) {
                buckets[index].max = num;
            }
        }

        //3、比较
        int maxDistance = 0;
        Bucket leftBucket = null;
        for (Bucket bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            if (leftBucket == null) {
                leftBucket = bucket;
                continue;
            }
            if (bucket.min - leftBucket.max > maxDistance) {
                maxDistance = bucket.min - leftBucket.max;
            }
            leftBucket = bucket;
        }

        return maxDistance;
    }

    static class Bucket{
        Integer min;
        Integer max;
    }
}
