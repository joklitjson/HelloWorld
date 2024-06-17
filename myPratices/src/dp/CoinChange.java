package dp;

import java.util.Arrays;

public class CoinChange {

    public static void main(String[] args) {

//        System.out.println(coinChange(new int[]{1,5,10},11));

        System.out.println(coinChangeDP(new int[]{1,5,10},11));
    }
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
