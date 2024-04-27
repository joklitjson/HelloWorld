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
        dfs(nums,0,(-1)*target);
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
     * 设nums的元素和为sum, 可以列出方程：
     *
     * package_a - package_b = target;
     * package_a + package_b = sum;
     * 则 package_a = (target + sum)/2。
     * 所以根据题意给的target和sum，我们可以求出package_a的值。
     * 那这道题就可以转化为：给定一个大小为package_a的背包，有多少种组合方式能把背包装满？ 妥妥的0-1背包。
     */
    private void dp(){

    }
}
