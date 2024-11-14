package dp;

public class CanPartition {

    /**
     * 一个数组，看看能否把数字评价分成两份，使两者的和一样，
     * 可以转换成背包问题：使用一半的数据，能否把背包的容量填满
     *
     * @param nums
     * @return
     */
    boolean canPartition(int[] nums) {
        int sum = 0, n = nums.length;
        for (int val : nums) {
            sum += val;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target=sum/2;
        //1、dp[i][j] 使用前i个商品 能否正好把容量j 装包
        boolean[][] dp = new boolean[n + 1][target + 1];

        //2\base case dp[i][0] 都是true，表示背包容量为0时 都能装满
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                //容量小于第i个物品的容量，因此装不下
                if (j < nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //dp[i][j] 依赖上一个物品的容下状态，当前物品可选 可不选
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        //计算是否满足
        return dp[n][target];
    }
}
