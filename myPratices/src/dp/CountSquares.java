package dp;

public class CountSquares {

    /**
     * 全是1的正方形子矩阵：计算子矩阵的数量
     * 定义dp[i,j] 是以dp[i,j]为右下角的正方形的最大变成的个数
     * @param matrix
     * @return
     */
    public int countSquares(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int dp[][] = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //靠边的看他自己是否是0
                if (i == 0 || j == 0) {
                    dp[i][j] = matrix[i][j];
                } else if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    //k看他的周边最小值
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i - 1][j - 1], dp[i][j - 1])) + 1;
                }
                //以为正方形的累加起来
                ans += dp[i][j];
            }
        }

        return ans;
    }
}
