package dp;

public class Jumper {

    int [] dp;
    /**
     * 跳到终点需要的最少次数
     * 动态规划写法：定义dp[i] ：表示i节点 跳到终点的 需要最少次数
     * @param nums
     * @return
     */
    int  canJump(int [] nums) {
        dp=new int[nums.length];

        return dp(nums,0);
    }

    private int dp(int [] nums,int p) {

        if (p >= nums.length - 1) {
            return 0;
        }
        int step = nums[p];

        for (int i = 1; i <= step; i++) {
            int subProblem = dp(nums, p + 1);// 查看子问题的解，当前问题都是在子问题的基础上 求解的
            // 取其中最小的作为最终结果
            dp[p] = Math.min(dp[p], subProblem + 1);
        }

        return dp[p];
    }
}
