package alingchasan;

import java.util.*;

/**
 * 前缀和距离操作方便的：
 * 其实就是把数组进行排序，然后把每个数操作n次都等于目标值 taget,就是求蓝色、青色的面积。可以使用前缀和优化
 */
public class PresumDistance01 {

    public static void main(String[] args) {
//        System.out.println(new PresumDistance01().minOperations(new int[]{3,1,6,8},new int[]{1,5}));
//        System.out.println(new PresumDistance01().getSumAbsoluteDifferences3(new int[]{2,3,5}));
//        System.out.println(new PresumDistance01().distance(new int[]{1,3,1,1,2}));

        System.out.println(new PresumDistance01().maxFrequencyScore(new int[]{1,4,4,2,4},0));


    }
    /**'
     * 2602. 使数组元素全部相等的最少操作次数
     * 排序数组，然后求前缀和，在循环query，在排序的num中二分查找小于等于target的索引(lowbound)，在求两者的面积
     * @param nums
     * @param queries
     * @return
     */
    public List<Long> minOperations(int[] nums, int[] queries) {
        Arrays.sort(nums);
        int n=nums.length;
        List<Long> result=new ArrayList<>();
        Long preSum[]=new Long[n+1];
        preSum[0]=0L;
        //求前缀和
        for (int i=1;i<=n;i++){
            preSum[i]=preSum[i-1]+nums[i-1];
        }

        for (int i=0;i<queries.length;i++){
            int value=queries[i];
            int idx=lowbound(nums,value);
            long leftOpert=(long)value*idx-preSum[idx];
            long rightOpert=preSum[n]-preSum[idx]-(long)(n-idx)*value;
            //蓝色和青色的和
            result.add(leftOpert+rightOpert);
        }
        return result;
    }

    /**
     * 二分求最左侧二分
     * lowbound:二分查找第一个大于等于num的数字下标         或可以理解成：元素中小于target的元素有几个
     * upbound:二分查找第一个大于num的数字
     * @param nums
     * @param target
     * @return
     */
    private int lowbound(int[] nums,int target){
        int l=0,r= nums.length;;
        while (l<r){
            int middle=l+(r-l)/2;
            if (nums[middle]==target){
                r=middle;
            }
            else if (nums[middle]>target){
                r=middle;
            }
            else {
                l=middle+1;
            }
        }
        return l;
    }

    /**
     * 1685. 有序数组中差绝对值之和
     * 方案一：距离前缀和:分两部分，第一份小于元素的距离绝对值之和，第二分部大于元素的距离绝对值之和的计算
     * 方案二：先求出后缀和，然后前缀合在遍历的过程中求解
     * 方案三：先求出后缀的全部元素和，然后在遍历的过程中 求前缀的所有元素的和，在使用加减法计算
     * @param nums
     * @return
     */
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int[] preSum = new int[n + 1];
        preSum[0] = 0;
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //求左右两部分面积
        for (int i = 0; i < n; i++) {
            int leftSum = nums[i] * (i ) - preSum[i];
            int rightSum = preSum[n] - preSum[i+1] - (n - 1 - i) * nums[i];
            result[i] = leftSum + rightSum;
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }

    /**
     * 使用后缀和求解
     * @param nums
     * @return
     */
    public int[] getSumAbsoluteDifferences2(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int[] afterSum = new int[n+1];//后缀和
//        afterSum[afterSum.length - 1] = 0;
        for (int i = afterSum.length - 2; i >= 0; i--) {
            afterSum[i] = afterSum[i + 1] + nums[i ];//下标大于等于当前i的所有元素的和
        }

        int preSum = 0;
        for (int i = 0; i < n; i++) {
            preSum += nums[i];
            int left = nums[i] * (i+1) - preSum;
            int after = afterSum[i+1]  - (n - 1 - i) * nums[i];//后缀和直接减去后面几个元素
            result[i] = left + after;
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }
    /**
     * 1685. 有序数组中差绝对值之和
     * 方案一：距离前缀和:分两部分，第一份小于元素的距离绝对值之和，第二分部大于元素的距离绝对值之和的计算
     * 方案二：先求出后缀和，然后前缀合在遍历的过程中求解
     * 方案三：先求出后缀的全部元素和，然后在遍历的过程中 求前缀的所有元素的和，在使用加减法计算
     * @param nums
     * @return
     */
    public int[] getSumAbsoluteDifferences3(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        //求全部元素的和
        int preSum = 0, afterSum = 0;
        for (int value : nums) {
            afterSum += value;
        }
        for (int i = 0; i < n; i++) {
            preSum += nums[i];
            int left = nums[i] * (i + 1) - preSum;
            //使用面积法：求后面元素的和，然后在减去下面的面积
            int after = afterSum - preSum - (n - 1 - i) * nums[i];
            result[i] = left + after;
        }
        System.out.println(Arrays.toString(result));
        return result;
    }


    /**
     * 2615. 等值距离和
     * 解决方案：把元素按值进行分组，组内存着元素的下标，然后在遍历元素，进行求元素下标差的绝对值之和(前缀和距离)
     * @param nums
     * @return
     */
    public long[] distance(int[] nums) {

        Map<Integer, List<Integer>> valueToIdxMap = new HashMap<>();
        //优化点：直接求每组的下标和
        Map<Integer, List<Long>> preSumMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (!valueToIdxMap.containsKey(nums[i])) {
                valueToIdxMap.put(nums[i], new ArrayList<>());
                preSumMap.put(nums[i],new ArrayList<>() );
                preSumMap.get(nums[i]).add(0L);
            }
//            else {
                List<Long> pre= preSumMap.get(nums[i]);
                pre.add( pre.get(pre.size()-1)+i);//计算当前下标的前缀和
//            }
            //把相同的元素的下标放在一起
            valueToIdxMap.get(nums[i]).add(i);


        }
        long[] result = new long[nums.length];

        //进行求相同值的元素的下标的差
        for ( Map.Entry<Integer, List<Integer>> entry:valueToIdxMap.entrySet()){
            List<Integer> value= entry.getValue();
            List<Long> preSum= preSumMap.get(entry.getKey());
            //遍历元素
            int m=value.size();
            for (int i=0;i<m;i++){
                int target=value.get(i);
                long left=target*i-preSum.get(i);//蓝色面积
                long right=preSum.get(preSum.size()-1)-preSum.get(i)-(long)(m-i)*target;
                result[target]=left+right;
            }
        }
//       System.out.println(Arrays.toString(result));
        return result;
    }


    /**
     * 二分查找
     * @param list
     * @param j
     * @return
     */
    private int getTarget(List<Integer> list,int j) {
        int l = 0, right = list.size() - 1;

        while (l <= right) {
            int idx = l + (right - l) / 2;
            int middle = list.get(idx);
            if (middle == j) {
                return idx;
            } else if (j < middle) {
                right = middle - 1;
            } else {
                l = middle + 1;
            }
        }
        return l;
    }

    /**
     * 2968. 执行操作使频率分数最大
     * 排序+贪心算法+滑动窗口:中位数贪心，最优做法是把子数组内的元素都变成子数组的中位数，操作次数如果超过 k，就必须移动左端点。
     * @param nums
     * @param k
     * @return
     */
    public int maxFrequencyScore(int[] nums, long k) {
        Arrays.sort(nums);
        int n = nums.length;
        long preSum[] = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        int left = 0, right = 0;
        int ans = 0;
        //滑动窗口
        for (; right < n; right++) {
            //操作次数大于k的话 则缩短左边界
            while (distanceSum(preSum, nums, left, (right + left) / 2, right) > k) {
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


    /**
     * 子数组内的元素都变成中心元素的操作次数
     * @return
     */
    private long distanceSum( long preSum[],int[] nums,int l,int m,int r) {
        int value = nums[m];
        if (l==6||r==6){
            System.out.println("");
        }
        long left = (long) value * (m - l) - (preSum[m] - preSum[l]);//蓝色距离
        long right = preSum[r + 1] - preSum[m + 1] - (long) nums[m] * (r - m);//青色距离
        return left + right;
    }

}
