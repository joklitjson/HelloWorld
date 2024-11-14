package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackTraceMain {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new BackTraceMain().restoreIpAddresses("25525511135").toArray()));

        System.out.println((new BackTraceMain().findTargetSumWays(new int[]{1,1,1,1,1},3)));
    }
    List<String> ans=new ArrayList<>();
    List<String> tmpAns=new ArrayList<>();
    /**
     *LCR 087. 复原 IP 地址
     * 使用回溯算法，尽量多进行剪枝，减少循环次数
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        backTrack(s,0);
        return ans;
    }

    private void backTrack(String string,int startIndex) {
        //
        if (tmpAns.size() > 4) {
            return;
        }
        if (startIndex == string.length()&&tmpAns.size()==4) {
            ans.add(String.join(".", tmpAns));
            return;
        }
        int mostLength = Math.min(startIndex + 3, string.length());
        for (int i = startIndex; i < mostLength; i++) {
            String sub = string.substring(startIndex, i + 1);
            if ((sub.length() > 1 && sub.startsWith("0")) || Integer.parseInt(sub) > 255 || tmpAns.size() >= 4) {
                return;
            }
            tmpAns.add(sub);
            backTrack(string, i + 1);
            tmpAns.remove(tmpAns.size() - 1);
        }
    }

    /**
     * LCR 102. 目标和
     * 使用回溯算法
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays(int[] nums, int target) {

        backtrack(0,nums,target,0);
        return count;
    }

    int count=0;
    private void backtrack(int idx, int[] nums, int target, int sum){
        if (idx==nums.length) {
            if (target == sum) {
                count++;
            }
            return;
        }

        sum+=nums[idx];
        backtrack(idx+1,nums,target,sum);
        sum-=nums[idx];

        sum-=nums[idx];
        backtrack(idx+1,nums,target,sum);
        sum+=nums[idx];
    }
}
