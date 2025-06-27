package alingchasan.dp;

import java.util.Arrays;
import java.util.List;

/**
 * 01背包问题：
 *
 * 超级感谢灵神,只要问题有"从集合中取一些元素,使它们的总和与某个「定值」有关",那么可以考虑转换为背包问题.
 *
 * 写了几道课后题,现在觉得难点在于:
 *
 * 转换问题
 * 分析初始情况
 */
public class Bag01 {

    public static void main(String[] args) {

        Bag01 test=new Bag01();

        System.out.println(test.maxTotalReward(new int[]{1,1,3,3}));
    }

    /**
     * 2915. 和为目标值的最长子序列的长度
     * 方案：其实就是0-1背包问题，选择n个数 看看能否把背包装满,然后再加上记忆化搜搜
     * 时间复杂度就是 n*target
     * @param nums
     * @param target
     * @return
     */
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {

        int n=nums.size();
        int mem[][]=new int[n][target+1];

        for (int[] row:mem){
            Arrays.fill(row,-1);
        }

        int result=dpSearch(n-1,target,mem,nums);
        return result>0?result:-1;
    }


    private int dpSearch(int i,int target,int[][] mem,List<Integer> nums) {
        if (i < 0) {
            return target == 0 ? 0 : Integer.MIN_VALUE;
        }
        //是否已经计算过了
        if (mem[i][target] != -1) {
            return mem[i][target];
        }

        //不选择i这个数字
        int res = dpSearch(i - 1, target, mem, nums);
        if (target >= nums.get(i)) {
            //可以选择 第i个元素
            res = Math.max(res, dpSearch(i - 1, target - nums.get(i), mem, nums)+1);
        }

        return mem[i][target] = res;//记忆化
    }


    /**
     * 自底向上的计算：
     * 其实我们就是把target 当作 背包容量,然后使用这n个数字去填充
     *
     * @param nums
     * @param target
     * @return
     */
    public int lengthOfLongestSubsequence2(List<Integer> nums, int target) {
        int n = nums.size();

        //定义dp数组
        int[][] dp = new int[n + 1][target + 1];

        Arrays.fill(dp[0], Integer.MIN_VALUE);//第一行 0个元素组成 任意target 都是0

        dp[0][0] = 0;

        for (int i = 0; i < n; i++) {
            //当前物品的体积
            int x = nums.get(i);
            for (int j = 0; j <= target; j++) {
                //j 就是当前背包的容量
                if (x > j) {
                    //当前物体的体积 大于背包容量时，则装不下，需要继承上次结果
                    dp[i + 1][j] = dp[i][j];
                } else {
                    //可以选择本地结果，也可以不选择本地结果，然后取最大值
                    dp[i + 1][j] = Math.max(dp[i][j], dp[i][j - x] + 1);
                }
            }
        }
        //注意 还可以进行 容量压缩

        return dp[n][target] > 0 ? dp[n][target] : -1;
    }


    /**
     * 2787. 将一个数字表示成幂的和的方案数
     * 把n当做背包的容量，然后从 1^x 、2^x ....n,计算他们的和
     * @param n
     * @param x
     * @return
     */
    public int numberOfWays(int n, int x) {

        long [] dp=new long[n+1];//定义：装满容量为n的方案数
        dp[0]=1;//装包容量0的方案数有一种

        int s=0;
        for (int i=1;Math.pow(i,x)<=n;i++){

            int v= (int) Math.pow(i,x);//当前物品的容量

            s=Math.min(s+v,n);//获取加上当前物品 容量的最小值

            //倒序遍历：s则是前i个元素的x次方 的和的最大容量，这里使用倒序遍历，防止数据污染
            for (int c=s;c>=v;c--){

                //计算容量是c的情况下的方案数
                dp[c]=dp[c]+dp[c-v];
            }
        }

        return (int) (dp[n] % 1_000_000_007);

    }

    /**
     * 3180. 执行操作可获得的最大总奖励 I
     * 分析可知：优先选择小的奖励，然后再选择大的奖励，因此现把数组进行排序
     * f[i][j]：使用前i个元素 能否组成最大奖励j，第i个元素可以选择 也可以不选择 因此问题转化成了01背包问题
     * 其中j的值是 数组中最大值 2m-1
     * @param rewardValues
     * @return
     */
    public int maxTotalReward(int[] rewardValues) {

        int n = rewardValues.length;
        Arrays.sort(rewardValues);
        int nn = rewardValues[n - 1] * 2 - 1;
        boolean f[][] = new boolean[n + 1][nn + 1];
        f[0][0] = true;
        for (int i = 0; i < n; i++) {
            //第i个奖励值s
            int reward = rewardValues[i];
            for (int j = 0; j <= nn; j++) {
                //当前奖励小于已经获得的奖励，所以不能选择
                if (reward > j) {
                    //不需要选择
                    f[i + 1][j] = f[i][j];
                } else {
                    //可以选择 也可以不选择
                    f[i + 1][j] = f[i][j] || (j >= reward && j < 2 * reward && f[i][j - reward]);
                }
            }
        }

        //寻找最大的，所以使用倒序遍历最后一行
        for (int i = nn; i > 0; i--) {
            if (f[n][i]) {
                return i;
            }
        }
        return 0;
    }
}
