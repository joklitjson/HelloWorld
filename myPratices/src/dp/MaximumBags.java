package dp;

/**
 * 背包问题
 */
public class MaximumBags {

    public static void main(String[] args) {

    }
    /**
     * 0-1背包问题，每个元素只能选择一次，
     *我们定义数组dp[i][j] 表示 前i个商品，在背包容量为j的情况下  所能容纳的最大价值
     * 因此 每一个物品 都可以选择装入或者不装入背包，所以我们dp[i][j] 应该选择这两者中的最大值
     * 不装入：dp[i][j]=dp[i-1][j],
     * 装 入：dp[i][j]= value[i]+dp[i-1][j-weight[i]]
     * base case: dp[i][0]=dp[0][j]=0;
     *
     * 注意，当 weight[i-1] 大于 j 的时候，因为无法放进去，所以只有第一种选择，没有第二种选择。
     * @param weight
     * @param value
     * @param capacity
     * @return
     */
    public static int maxValue(int[] weight, int[] value, int capacity) {
        int m = weight.length;
        int[][] dp = new int[m + 1][capacity + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= capacity; j++) {

                //第i个物品需要的容量大于背包容量
                if (weight[i] > j) {
//                    你不装嘛，那就继承之前的结果。
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //选择或者不选择，取最大值
                    dp[i][j] = Math.max(dp[i - 1][j], value[i] + dp[i - 1][j - weight[i]]);
                }
            }
        }
        return dp[m][capacity];
    }

    /**
     * 01背包压缩
     * @param weight
     * @param value
     * @param capacity
     * @return
     */
    public static int maxValue2(int[] weight, int[] value, int capacity) {
        int m = weight.length;
        int[]dp = new int[capacity + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= capacity; j++) {

                //第i个物品需要的容量大于背包容量
                if (weight[i] > j) {
//                    你不装嘛，那就继承之前的结果。
                    dp[j] = dp[j];
                } else {
                    //选择或者不选择，取最大值
                    dp[j] = Math.max(dp[j], value[i] + dp[j - weight[i]]);
                }
            }
        }
        return dp[capacity];
    }

    /**
     * 完全背包问题与01背包问题基本相似,唯一的区别就是多重背包问题的物品数量是无限的
     *
     */
    public static int maxValueComplete(int[] weight, int[] value, int capacity) {
        int m = weight.length;
        int[][] dp = new int[m + 1][capacity + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= capacity; j++) {

                //第i个物品需要的容量大于背包容量
                if (weight[i] > j) {
//                    你不装嘛，那就继承之前的结果。
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //选择或者不选择，取最大值
//                    选择的情况下 和01背包不同dp[i][j - weight[i]]
                    dp[i][j] = Math.max(dp[i - 1][j], value[i] + dp[i][j - weight[i]]);
                }
            }
        }
        return dp[m][capacity];
    }

}
