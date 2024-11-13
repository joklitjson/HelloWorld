package dp;

import java.util.Map;

public class SeqOrSub {

    /**
     *516. 最长回文子序列
     * 解决方案：动态规划方法：定义dp[i,j] 表示i,j之间的字符串 表示的最长回文子序列
     * 前提条件 i<j,分两种情况
     * 1、num[i]==num[j],则只需要考虑子序列dp[i+1][j-1]的最长回文子序列就可以
     * 2、如果num[i]！=num[j]，则比较dp[i+1][j]、dp[i][j-1]的最大长度
     * 遍历的方向 我们现在反向遍历
     *
     * @param s
     * @return
     */
    public int longestPalindromeSubseq(String s) {

        char[] charts = s.toCharArray();
        int n = s.length();
        int dp[][] = new int[n][n];
        // base case
        for (int i=0;i<n;i++){
            dp[i][i]=1;
        }
        // 反着遍历保证正确的状态转移
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (charts[i] == charts[j]) {
                    //+2是在子问题的基础上，加上他们俩的长度
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        //结果
        return dp[0][n - 1];
    }

    /**
     * LCR 095. 最长公共子序列
     * 使用动态规划解决
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {

        char [] str1=text1.toCharArray();
        char [] str2=text2.toCharArray();
        int m=text1.length(),n=text2.length();

        //base case 都初始化成0
        // 为啥使用m+1或n+1 我们把索引0定义成 ""空字符串
        int [][]dp=new int[m+1][n+1];

        for (int i=1;i<=m;i++){
            for (int j=1;j<=n;j++){
                if (str1[i-1]==str2[j-1]){
                    dp[i][j]=dp[i-1][j-1]+1;
                }
                else {
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }

        //返回这两个长度的最大子序列
        return dp[m][n];
    }
}
