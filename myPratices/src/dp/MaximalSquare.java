package dp;

/**
 * 最大的正方形面积:
 * 1、暴力法：遍历每个点，
 * 2、动态规划：设dp【i,j】未右下角的正方形，求周边的三个点的边长的最小值
 */
public class MaximalSquare {

    public int maximalSquare(char[][] matrix) {
        int maxSide = 0;
        if (matrix==null||matrix.length==0){
            return maxSide;
        }
        int m = matrix.length, n = matrix[0].length;

        int dp[][] = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; i < n; j++) {
                if (matrix[i][j] == '1') {
                    //边上的1，所以最长都是1
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        //周边的三个点的最大边长，在加上他自己本身
                        dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i - 1][j - 1], dp[i][j - 1])) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }

        return maxSide * maxSide;
    }
}
