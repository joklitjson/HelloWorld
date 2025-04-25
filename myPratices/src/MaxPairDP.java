public class MaxPairDP {
    // 判断是否可以配对
    private static boolean canPair(char a, char b) {
        return (a == 'A' && b == 'U') ||
               (a == 'U' && b == 'A') ||
               (a == 'C' && b == 'G') ||
               (a == 'G' && b == 'C');
    }

    // 动态规划求最大字符对数
    public static int maxPairs(String seq) {
        int n = seq.length();
        int[][] dp = new int[n][n];

        // 长度从2到n遍历区间
        for (int len = 5; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // 情况1：i不参与配对
                dp[i][j] = dp[i + 1][j];
                // 情况2：i与k配对（i < k <= j）
                for (int k = i + 1; k <= j; k++) {
                    if (canPair(seq.charAt(i), seq.charAt(k))) {
                        int left = (i + 1 <= k - 1) ? dp[i + 1][k - 1] : 0;
                        int right = (k + 1 <= j) ? dp[k + 1][j] : 0;
                        dp[i][j] = Math.max(dp[i][j], left + right + 1);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        String seq = "ACCGGUAGU";
        System.out.println("最大字符对数: " + maxPairs(seq));
    }
}