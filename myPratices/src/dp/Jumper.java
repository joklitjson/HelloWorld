package dp;

/**
 *调到终点的最小次数
 */
public class Jumper {

    int[] dp;

    /**
     * 跳到终点需要的最少次数
     * 动态规划写法：定义dp[i] ：表示i节点 跳到终点的 需要最少次数
     *
     * @param nums
     * @return
     */
    int canJump(int[] nums) {
        dp = new int[nums.length];

        return dp(nums, 0);
    }

    private int dp(int[] nums, int p) {

        if (p >= nums.length - 1) {
            return 0;
        }
        int step = nums[p];

        for (int i = 1; i <= step; i++) {
            int subProblem = dp(nums, p + i);// 查看子问题的解，当前问题都是在子问题的基础上 求解的
            // 取其中最小的作为最终结果
            dp[p] = Math.min(dp[p], subProblem + 1);
        }
        return dp[p];
    }

    public boolean canJump2(int[] nums) {
        return dfs(nums,0);
    }

    private Boolean dfs(int[] nums, int pos) {
        //当前位置如果 已经超过了 数组小标，说明 已经跳过了这个位置
        if (pos >= nums.length-1) {
            return true;
        }

        //循环pos 所在的位置 能跳跃的次数，然后在跟进这个范围计算下一次能跳跃的最远距离
        for (int i = 1; i <= nums[pos]; i++) {
            if (dfs(nums, pos + i)) {
                return true;
            }
        }
        return false;
    }
}
