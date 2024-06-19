package dp;

public class MaxSubArray {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-3,1,3,-1,2,-4,2}));
    }
    /**
     * 动态规划：最大子数组的和
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums){

        int [] dp=new int[nums.length];//dp[i]表示的是以i结尾的数的最大的子数组的和

//        base case
        dp[0]=nums[0];//第一个元素的最大子数组的和是他自己
//        dp[i] 的最大和：要么是他自己组成的最大和，要么是他连接前面的数组组成的最大和
//        dp[i]= max(num[i],dp[i-1]+nums[i]);

        for (int i=1;i<nums.length;i++){
            dp[i]=Math.max(nums[i],nums[i]+dp[i-1]);
        }

        int max=0;
        for (int val:dp){
            max=Math.max(val,max);
        }

        return max;

    }
}
