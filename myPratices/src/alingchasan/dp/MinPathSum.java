package alingchasan.dp;

/**
 * 1、定义dp方程：dp[i][j]表示 从左上角的顶点到i,j 点的最小距离，状态转移方程为 f(i,j)=min(f(i-1,j),f(i,j-1)),可以使用递归写法
 * 2、使用带备忘录的自顶向下的方式写
 * 3、递推方案：使用二维数组：自底向上的方式 计算答案
 * 4、使用状态压缩：一维数组，自底相上的方式写。空间是O(n)
 * 5.状态压缩：使用元数组，空间状态是O(1)
 */
public class MinPathSum {

    /**
     * 从左上角到右下角的最小路径和：
     * 定义dp[i][j]表示从左上角到i,j的最小路径，则dp[i][j] 只能从上、左节点达到，
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        int dp[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 0;
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

}
