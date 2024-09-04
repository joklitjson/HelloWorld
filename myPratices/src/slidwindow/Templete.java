package slidwindow;

/**
 * 滑动窗口分最长、定长、最短窗口
 */
public class Templete {


    /**
     * 最长窗口的代码模板
     * 给定一个条件
     * 求最长区间/最长子串
     * 题目给出的区间需要具备单调性
     * @param arr
     * @return
     */
    public int maxLength(int [] arr){

        int left=-1,right=0,n=arr.length;
        int ans=0;

        while (right<n){
            // assert 在加入A[i]之前，(left, i-1]是一个合法有效的区间
            // step 1. 直接将A[i]加到区间中，形成(left, i]
            // step 2. 将A[i]加入之后，惰性原则
            //不满足条件，则缩短左窗口
            while (check(right)){
                left++;
                // TODO 修改区间的状态
            }
            //更新最大值
            ans=Math.max(ans,right-left);
            right++;
        }
        return ans;
    }
    private boolean check(int left){
        return false;
    }

    /**
     * 最长的无重复字符
     * @param s
     * @return
     */
    int lengthOfLongestSubstring(String s) {

        int left = -1, right = 0, n = s.length();
        int ans = 0;
        int[] pos = new int[256];//下标对应的位置

        while (right < n) {
            int idx = (int) s.charAt(right);

            // 那么看一下是否会破坏区间的？
            while (left < right && pos[idx] > left) {
                left = pos[idx];//yidong
            }
            pos[idx] = right;//设置其位置
            ans = Math.max(ans, right - left);
            right++;
        }
        return ans;
    }

    /**
     * 动态规划解法：f(i)=max(f(i-1)+num[i],num[i])
     * 要么自己成为子数组，要么加上前面的和成为子数组
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {

        int maxArr[]=new int[nums.length];
        maxArr[0]=nums[0];

        //求出每个位置的最大值
        for (int i=1;i<nums.length;i++){
            maxArr[i]=Math.max(nums[i],maxArr[i-1]+nums[i]);
        }
        //在进行一次遍历，求最大值
        int max=Integer.MIN_VALUE;
        for (int val:maxArr){
            max=Math.max(max,val);
        }
        //f(i)=max(f(i-1)+num[i],num[i])还可以进行状态压缩：设置一个变量，前面一个的最大值
        return max;
    }

    public int maxSubArray2(int[] nums) {

        int currMax=0;//当前元素的最大值，要么自己成为最大值，要么连上前面一个元素的最大值 成为最大值
        int max=0;//全局元素的最大值

        for (int val:nums){
            currMax=Math.max(currMax,currMax+val);
            max=Math.max(currMax,max);//全局元素的最大值
        }

        return max;
    }

        /**
         * 环形数组最大子数组
         * max(subArr)=Max(subarr,sum-miSubarr)
         * @param nums
         * @return
         */
    public int maxSubarraySumCircular(int[] nums) {
//        统一注释，total为数组的总和，
//        maxSum为最大子数组和，minSum为最小子数组和，
//        curMax为包含当前元素的最大子数组和，curMin为包含当前元素的最小子数组和
        Integer curMax = 0, maxSum = nums[0];
        Integer curMin = 0, minSum = nums[0], sum = 0;

        for (int val : nums) {
            // 以 nums[i-1] 结尾的子数组选或不选（取 max）+ x = 以 x 结尾的最大子数组和
            curMax = Math.max(curMax + val, val);
            maxSum = Math.max(maxSum,curMax);

            // 以 nums[i-1] 结尾的子数组选或不选（取 min）+ x = 以 x 结尾的最小子数组和
            curMin = Math.min(curMin + val, val);
            minSum = Math.min(minSum,curMin);

            sum += val;
        }

        // sum == minSum  全是负数
        return sum == minSum ? maxSum : Math.max(maxSum, sum - minSum);
    }
    }
