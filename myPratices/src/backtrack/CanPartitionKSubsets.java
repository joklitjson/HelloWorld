package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 把
 */
public class CanPartitionKSubsets {

    public static void main(String[] args) {
        System.out.println(canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1},4));
    }

    //是否能把数组分成k组，每组的和都相等
    public static boolean canPartitionKSubsets(int[] nums, int k) {

        if (k > nums.length) {
            return false;
        }

        int sum = 0;
        for (int val : nums) {
            sum += val;
        }
        if (sum % k != 0) {
            return false;
        }

        int[] bucket = new int[k];
        int target = sum / k;

        Arrays.sort(nums);
        //
        int n=nums.length;
        //调换
        for (int i=0;i<(n/2);i++) {
            int tmp = nums[i];
            nums[i] = nums[n - 1 - i];
            nums[n - 1 - i] = tmp;
        }
        boolean [] used=new boolean[n];
//        return backtrackFromNum(nums, 0, bucket, target);
        return backtrackFromBucket(nums,k,0,0,used,target);
    }

    //从数字的角度 遍历(递归遍历数字)，每个数字选择是否放在桶k中
    //优化点：可以把数组进行降序排列，让不符合的情况提前进行剪枝
    private static boolean backtrackFromNum(int[] nums,int index,int [] bucket,int target) {

        //base case
        if (index == nums.length) {
            //查看桶内的数据是否都等于target
            for (int val : bucket) {
                if (val != target) {
                    return false;
                }
            }
            return true;
        }

        //num[index]依次选择桶i
        for (int i = 0; i < bucket.length; i++) {
            //剪枝 不符合的情况
            if (bucket[i]+nums[index]>target){
                continue;
            }
            // 将 nums[index] 装入 bucket[i]
            bucket[i] += nums[index];
            //        // 递归穷举下一个数字的选择
            boolean tmp = backtrackFromNum(nums, index + 1, bucket, target);
            if (tmp) {
                return true;
            }
            // 撤销选择
            bucket[i] -= nums[index];
        }

        // nums[index] 装入哪个桶都不行
        return false;
    }

    //从桶的角度来选择数字
    private static boolean backtrackFromBucket(int[] nums,int k,int start, int sum,  boolean[] used,int target){

        //桶用完了
        if (k==0){
            return true;
        }
        //填下一个桶
        if (sum==target){
            return backtrackFromBucket(nums,k-1,0,0,used,target);
        }

        for ( int i=start;i<nums.length;i++){
            //k号桶 是否选择第i个数
            if (used[i]){
                continue;
            }
            if (nums[i]+sum>target){
                continue;
            }
            //选择
            used[i]=true;
            sum+=nums[i];
            //
            if (backtrackFromBucket(nums,k,start+1,sum,used,target)){
                return true;
            }
            //撤销
            used[i]=false;
            sum-=nums[i];
        }
        return false;
    }

    /**
     * 分隔字符串数组，变成回文字符串
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> ans=new ArrayList<>();

        backTrace(s,ans,new ArrayList<>());

        return ans;
    }

    private void backTrace(String string, List<List<String>> ans,List<String> tmp){
        if (string.length()==0){
            //新创建一个集合
            ans.add(new ArrayList<>(tmp));
            return;
        }
        for (int i=0;i<string.length();i++){
            String subStr=string.substring(0,i+1);
            if (isPalindrome(subStr)){
                tmp.add(subStr);
                backTrace(string.substring(i+1),ans,tmp);//在判断剩余的子串是否能拆分
                tmp.remove(tmp.size()-1);
            }
        }
    }
    private boolean isPalindrome(String str) {
        if (str.length() == 0) {
            return true;
        }
        int left = 0, right = str.length() - 1;

        while (left <= right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
