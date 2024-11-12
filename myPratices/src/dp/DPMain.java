package dp;

import java.util.Arrays;

public class DPMain {


    /**
     * 爬楼梯的最小成本，
     * 解决方案：定义动态规划，：爬到楼梯i的选择有两种，1、从i-1层向上爬一层，2、从i-2层往上爬两层，在比较两则的成本
     * 他是在爬楼梯的基础上的变种，
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {

        int n=cost.length;
        int [] dp=new int[n+1];
        dp[0]=dp[1]=0;//爬到第一层楼梯的成本是0

        for (int i=2;i<=n;i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[n];
    }



    /**
     *  注意到当 i≥2 时，dp[i] 只和 dp[i−1] 与 dp[i−2] 有关，因此可以使用滚动数组的思想，将空间复杂度优化到 O(1)。
     * @param cost
     * @return
     */
    public int minCostClimbingStairs2(int[] cost) {

        int n = cost.length;

        int pre = 0, current = 0;
        for (int i = 2; i <= n; i++) {
            int next = Math.min(current + cost[i - 1], pre + cost[i - 2]);
            pre = current;
            current = next;
        }
        return current;
    }


    /**
     * 377. 组合总和 Ⅳ
     * 递归+记忆化搜搜：从顶向下执行,如何理解呢？比如我们需要求dp(40)的种类数，如果是数组含有 【1,2,3】 三个数
     * 当数组选择了元素1，那么我们就求子问题dp(40-1)的解
     * 当数组选择了元素2，那么我们就求子问题dp(40-2)的解
     * 当数组选择了元素3，那么我们就求子问题dp(40-3)的解
     * 因此dp[40]=dp(40-1)+dp(40-2)+dp(40-3)了，然后在向下求解子问题
     * https://leetcode.cn/problems/combination-sum-iv/solutions/740877/fu-xue-ming-zhu-cong-ji-yi-hua-di-gui-tu-rqwy/
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4(int[] nums, int target) {
        int n = nums.length;
        int[] memo = new int[target + 1];

        Arrays.fill(memo,-1);
        memo[0]=1;
        return dfs(nums, target, memo);
    }

    private int dfs(int[] nums, int target,int [] memo) {
        if (target < 0) {
            return 0;
        }
        if (target == 0) {
            return 1;
        }

        if (memo[target]!=-1){
            return memo[target];
        }
        int res = 0;//表示组成target的种类数，u由于有重复计算问题，因此我们使用了记忆数组
        for (int num : nums) {
            //如果当前num大于了target，则不能由他组成 target
            if (target >= num) {
                res += dfs(nums, target - num,memo);
            }
        }
        memo[target]=res;
        return res;
    }

    /**
     * 动态规划版本
     * @param nums
     * @param target
     * @return
     */

    public int combinationSum42(int[] nums, int target) {

        int[] dp = new int[target + 1];
        dp[0] = 1;
        //
        for (int tar = 0; tar <= target; tar++) {
            for (int num : nums) {
                //表示凑成tar的方法和
                if (tar >= num) {
                    dp[tar] += dp[tar - num];
                }
            }
        }
        return dp[target];
    }
}
