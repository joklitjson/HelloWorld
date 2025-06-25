package alingchasan.dp;

import java.util.Arrays;
import java.util.List;

/**
 * 动态规划
 * 1、爬楼梯 当前问题：只和前面的两个记录相关。
 * 类似的题目 还有菲薄切那数列
 *
 * 超级感谢灵神,只要问题有"从集合中取一些元素,使它们的总和与某个「定值」有关",那么可以考虑转换为背包问题.
 *
 * 写了几道课后题,现在觉得难点在于:
 *
 * 转换问题
 * 分析初始情况
 *
 */
public class DynamicProgram {

    public static void main(String[] args) {
        DynamicProgram dynamicProgram=new DynamicProgram();

        System.out.println("deleteAndEarn===="+dynamicProgram.deleteAndEarn(new int[]{1}));
    }

    /**
     * 740. 删除并获得点数
     * 分析：删除一个数后：其前面一个和后面一个都需要删除，可总结成相邻的元素不能同时被选择，问题转化成打家劫舍问题
     * 在【值域】上 进行打家劫舍（如果值域 比较大，不能创建数组怎么办呢？ ）
     * @param nums
     * @return
     */
    public int deleteAndEarn(int[] nums) {
        // 获取最大值：把元素的值 映射到对应的index

        int max = 0;
        for (int value : nums) {
            max = Math.max(max, value);
        }
        int[] values = new int[max + 1];
        //然后在进行累加：表示表示下标=x的元素 都能被获得点数
        for (int value : nums) {
            values[value] += value;
        }
        return dp(values);
    }

    /**
     * 进行 dp 搜索
     * @param values
     * @return
     */
    private int dp(int[] values ) {
        int n = values.length;

        //为了下面的错位相加（所以这里的长度+2了）
        int[] dpValue = new int[n  + 2];

        for (int i = 0; i <n; i++) {
            //计算当前最大值：当前元素 不删除删除、删除状态 下的最大值
            dpValue[i+2] = Math.max(dpValue[i + 1], dpValue[i] + values[i]);
        }
        //获取最后一个值
        return dpValue[n+1];
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

}
