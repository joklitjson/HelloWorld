package leetcode;

public class HuiShuo {

    public static void main(String[] args) {
        System.out.println( new HuiShuo().findTargetSumWays(new int[]{1,1,1,1,1},3));
    }


    /**
     * 目标和 149题
     * 使用回朔算法解决
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays(int[] nums, int target) {
//        sumWaysBackTracks(nums,0,(-1)*target);
//        dfs(nums,0,(-1)*target);
        dp(nums,target);
        return ans;
    }

    int ans=0;

    /**
     * 方案二：动态规划、DFS
     * @param nums
     * @param i
     * @param rest 剩余
     */
    private void dfs(int[] nums,  int i, int rest){

        if (i== nums.length){
            if (rest==0){
                ans++;
            }
            return;
        }
        //第i个元素选择+号或者选择-号
        dfs(nums,i+1,rest+nums[i]);
        dfs(nums,i+1,rest-nums[i]);
    }
    /**
     * 方案一：回溯算法
     * 方案二：动态规划、DFS
     * @param nums
     * @param i
     * @param rest 剩余
     */
    private void sumWaysBackTracks(int[] nums,  int i, int rest){
        //终结条件
        if (i==nums.length){
            if (rest==0){
                ans++;
            }
            return;
        }
//        opt==0 -，opt==1 +
        for(int opt=0;opt<2;opt++){
            if (opt==0){
                rest+=(-1)*nums[i];
                sumWaysBackTracks(nums,i+1,rest);
                //回撤
                rest+=(1)*nums[i];
            }
            else if (opt==1){
                //做加法
                rest+=nums[i];
                sumWaysBackTracks(nums,i+1,rest);
                //回撤
                rest+=(-1)*nums[i];
            }
        }
    }

    /**
     * 算法3 背包问题
     * 设nums的元素和为sum, 可以列出方程：
     *
     * package_a - package_b = target;
     * package_a + package_b = sum;
     * 则 package_a = (target + sum)/2。
     * 所以根据题意给的target和sum，我们可以求出package_a的值。
     * 那这道题就可以转化为：给定一个大小为package_a的背包，有多少种组合方式能把背包装满？ 妥妥的0-1背包。
     */
    private void dp(int[] nums,int target) {

        int sum = target;
        for (int value : nums) {
            sum += value;
        }
        //sum必须是偶数，非偶数直接返回
        if (((sum & 1) == 1) || sum < 0) {
            ans = 0;
            return;
        }
        int halfSum = target >> 1;

        int dp[][] = new int[nums.length][halfSum + 1];

//        4.初始化·dp，因为是自下而上计算，所以需要先给出最小规模问题的解//·
//        注意:dp[0][0]·表示为递归树中的叶节点，表示考虑完nums中所有数且target已经
        dp[0][0] = 1;
        if (nums[0] == 0) {
//            如果·nums[0]·=·0，则选、不选它都能构成·target·=·0·的解，所以解有两个;
            dp[0][0] = 2;
        } else {
            for (int j = 0; j <= halfSum; j++) {
                if (j == nums[0]) {
                    dp[0][j] = 1;
                }
            }
        }
        //自底向上搜索
        for (int i = 1; i < nums.length; i++) {//遍历物品
            for (int j = 0; j <= halfSum; j++) {//遍历背包容量
                if (j < nums[i]) {//容量小于 第i个物品的体积
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //放置物品
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i]];
                }
            }
        }
        //答案就是数组的最后一个元素
        ans = dp[nums.length - 1][halfSum];
    }
}
