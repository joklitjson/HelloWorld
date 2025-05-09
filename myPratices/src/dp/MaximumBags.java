package dp;

/**
 * 背包问题
 */
public class MaximumBags {

    public static void main(String[] args) {
        int[] weights = {2, 3, 1};
        int[] values = {3, 4, 2};
        int[] counts = {2, 3, 1};
        int capacity = 5;

        int maxVal = maxValue(weights, values, counts, capacity);
        System.out.println("背包能装入的最大价值为: " + maxVal); // 输出应为10
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
     * 注意到dp[i][j]都是通过上一行dp[i-1][..]转移过来的，之前的数据都不会再使用了。
     *  ：唯一需要注意的是j应该从后往前反向遍历，因为每个物品（或者说数字）只能用一次，以免之前的结果影响其他的结果。
     * @param weight
     * @param value
     * @param capacity
     * @return
     */
    public static int maxValue2(int[] weight, int[] value, int capacity) {
        int m = weight.length;
        int[]dp = new int[capacity + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = capacity; j>=1; j++) {//注意：状态压缩 此处需要逆序遍历，防止覆盖上一次的数据

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

    /**
     * 多重背包问题--每个物品可以选择的数量有限制
     */
    public static int maxValue(int[] weights, int[] values, int[] counts, int capacity) {
        int n = weights.length;
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < n; i++) {
            int weight = weights[i];
            int value = values[i];
            int count = counts[i];

            // 二进制优化，将第i个物品拆分成多个不同数量的物品
            for (int k = 1; k <= count; k *= 2) {
                int curWeight = k * weight;
                int curValue = k * value;
                // 0-1背包处理
                for (int j = capacity; j >= curWeight; j--) {
                    dp[j] = Math.max(dp[j], dp[j - curWeight] + curValue);
                }
                count -= k;
            }
            // 处理剩余的物品数量
            if (count > 0) {
                int curWeight = count * weight;
                int curValue = count * value;
                for (int j = capacity; j >= curWeight; j--) {
                    dp[j] = Math.max(dp[j], dp[j - curWeight] + curValue);
                }
            }
        }

        return dp[capacity];
    }

}
