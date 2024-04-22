package leetcode;

public class MaxLastSeq {


    /**
     * 动态规划算法：设计状态转移方程：以i结尾的最大子序列和
     * max=(num[i],dp[i-1]+num[i]])
     * @param nums
     * @return
     */
    public int maxSubArray1(int[] nums) {
        int dp[]=new int[nums.length];// 以i结尾的最大子序列和
        //分情况讨论，以i结尾的最大和序列 需要判断他签名的数字是否是大于零，若是小于零则 他自己是最大，不需要在添加签名的值
        dp[0]=nums[0];
        for (int i=1;i<nums.length;i++){
            if (dp[i-1]>0){
                dp[i]=nums[i]+dp[i-1];
            }else {
                dp[i]=nums[i];
            }
        }

        //再次遍历一遍
        int max=dp[0];
        for (int i=1;i<dp.length;i++){
            if (dp[i]>max){
                max=dp[i];
            }
        }
        return max;
    }

    /**
     * 方案二：分治法：就是求 left、right、(left+middle+right) 中的最大值
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        return maxSubArraySum(nums, 0, len - 1);
    }

    /**
     *
     * @param nums
     * @return
     */
    private int maxSubArraySum(int[] nums, int left, int right) {
        if (left==right){
            return nums[left];
        }
        int middle = (left + right) / 2;
        int leftMax=maxSubArraySum(nums,left,middle);
        int rightMax=maxSubArraySum(nums,middle+1,right);
        int allMax=maxSubCrossingMiddle(nums,left,middle,right);
        return max(leftMax,rightMax,allMax);

    }
    private int maxSubCrossingMiddle(int[] nums, int left,  int middle,int right) {
        //计算以middle 结尾的最大值，就是从middle 向左遍历
        int leftMax=Integer.MIN_VALUE;
        int sumLeft=0;
        for (int i=middle;i>=left;i--){
            sumLeft+=nums[i];
            if (sumLeft>leftMax){
                leftMax=sumLeft;
            }
        }
        //2计算以right 结尾的最大值，就是从right 向右遍历
        int rightMax=Integer.MIN_VALUE;
        int sumRight=0;
        for (int i=middle+1;i<=right;i++){
            sumRight+=nums[i];
            if (sumRight>rightMax){
                rightMax=sumRight;
            }
        }
        return leftMax+rightMax;
    }
    private int max( int left, int middle, int right) {
        return Math.max(Math.max(left, middle), right);
    }

}
