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
}
