package dp;

import java.util.Arrays;

public class CoinChange {

    public static void main(String[] args) {

//        System.out.println(coinChange(new int[]{1,5,10},11));

        System.out.println(coinChangeDP(new int[]{1,5,10},11));
    }

    /**
     * 类似 0-1背包问题
     * 若只使用coins中的前i个硬币的面值，若想凑出金额j，有dp[i][j]种凑法。
     * @param coins
     * @param amount
     * @return
     */
    // coins 中是可选硬币面值，amount 是目标金额
    static int  coinChange(int[] coins, int amount) {
        //i表示凑够的金额
        int[] dp = new int[amount + 1];
        Arrays.fill(dp,amount);

//        base case
        dp[0] = 0;
        for (int i = 0; i < dp.length; i++) {
            //凑够的金额是i 需要的硬币数
            int res = amount;
            //遍历使用以下硬币凑够
            for (int coin : coins) {
                int nextAmount = i - coin;
                if (nextAmount < 0) {
                    continue;
                } else {
//                    综上就是两种选择，而我们想求的dp[i][j]是「共有多少种凑法」，
//                    所以dp[i][j]的值应该是以上两种选择的结果之和：for (int i = 1; i <= n; i++) {
                    //    for (int j = 1; j <= amount; j++) {
                    //        if (j - coins[i-1] >= 0)
                    //            dp[i][j] = dp[i - 1][j] 
                    //                     + dp[i][j-coins[i-1]];
                    //                            return dp[N][W]
                    dp[i] = Math.min(dp[i], dp[nextAmount] + 1);
                }
            }
        }
        return dp[amount] == amount ? -1 : dp[amount];
    }

    //递归写法
    static int  coinChangeDP(int[] coins, int amount) {
        if (amount <= 0) {
            return 0;
        }
        //递归
        int res = amount;
        for (int coin : coins) {
            if (amount - coin < 0) {
                continue;
            }
            //+1，表示使用当前的硬币
            res = Math.min(res, coinChangeDP(coins, amount - coin)+1);
        }

        return res;
    }
}
