package leetcode;

public class GetBestGoldMiningTest {

    public static void main(String[] args) {
        int w=10;
        int[]p={5,5,3,4,3};
        int[]g={400,500,200,300,350};
        System.out.println("最优收益："+getBestGoldMiningV1(w,g.length,p,g));
        System.out.println("最优收益："+getBestGoldMiningV2(w,g.length,p,g));

    }

    /**
     * 动态规划问题，每一个金矿都是可选或者可不选择的，因此归结为0-1背包问题
     * 因此可以构造出二叉树的递归关系
     * 递归二叉树中 存在着重复计算，因此效率不高，最快的方式是 从底向上计算
     * @param w 可用工人
     * @param n 可选金矿数量
     * @param p 金矿需要的人数
     * @param g 金矿的价值
     * @return
     */
    public static int getBestGoldMiningV1(int w, int n,int[]p,int[]g) {
        if (w == 0 || n == 0) {
            return 0;
        }
        //人数不足以选择第p个金矿
        if (w < p[n - 1]) {
            return getBestGoldMiningV1(w, n - 1, p, g);
        }

        //取这两则的最大值，
        return Math.max(getBestGoldMiningV1(w, n - 1, p, g),
                getBestGoldMiningV1(w - p[n - 1], n - 1, p, g) + g[n - 1]);
    }

    /**
     * 自底向上计算，先计算1个工人的值，在计算2个工人的值。。。。
     * @param w 工人数
     * @param n 金矿数量
     * @param p 金矿i所需要的人力
     * @param g 金矿的价值
     * @return
     */
    public static int getBestGoldMiningV2(int w, int n,int[]p,int[]g) {
        //创建表格
        int[][] dp = new int[n+1][w+1];
        for (int i = 1; i <= n; i++) {//金矿个数
            for (int j = 1; j <= w; j++) {//人力
                //填充表格
                if (j < p[i-1]) {
                    dp[i][j] =  dp[i-1][j];
                } else {
                    //max(不选择此金矿，选择此金矿())
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j - p[i-1]] + g[i-1]);
                }
            }
        }
        return dp[n][w ];
    }
}
