package dp;

import java.util.Arrays;

/**
 * 最大递增长度
 */
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {

        System.out.println(most(new int[]{10,9,2,5,3,7,101,18}));
    }
    public static int most(int [] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);//每个字符都是以自己结尾的最大增长长度
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                //在(0,j-1)中找比自己小的数的最大的递增长度，，然后在加上他自己的长度
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = 0;
        for (int val : dp) {
            max = Math.max(max, val);
        }
        return max;
    }


    /**
     * LCR 097. 不同的子序列
     * 求字符串s和字符串t的不同子序列
     * 使用动态规划，定义dp[i][j] 表示s中的前i个字符和t中的前j个字符的不同子序列
     * @param s
     * @param t
     * @return
     */
    public int numDistinct(String s, String t) {

        int m=s.length(),n=t.length();
        if (m<n){
            return 0;
        }
        int [][]dp=new int[m+1][n+1];

//        base case
        for (int i=0;i<=m;i++){
            dp[i][0]=1;
//            表示使用s的前n个字符串 和空字符串的子序列 有1个
        }
        for (int i=1;i<=m;i++){
            for (int j=1;j<=n;j++){
                if (s.charAt(i-1)==t.charAt(j-1)){
                    //表示第i个字符相等，i-1的意思是这里的索引从1开始的，因此使用i-1
//                    可以使用第i个字符和第j个字符尽心匹配，也可以不使用i和j匹配，则使用i-1个字符和j个字符进行匹配
                        dp[i][j]=dp[i-1][j-1]+dp[i-1][j];
                }
                else {
//                   需要考虑 s 的前 i−1 个字符的子序列中等于 t 的前 j 个字符的不同子序列的个数。因此 dp[i][j]=dp[i−1][j]。
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }
}
