package alingchasan;

import java.util.HashMap;
import java.util.Map;

public class APreSum {

    public static void main(String[] args) {
        System.out.println(numberOfSubarrays(new int[]{1,1,2,1,1},3));
    }
    /**
     * 1248. 统计「优美子数组」
     * 个连续子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     *
     * 请返回这个数组中 「优美子数组」 的数目。
     * 解决方案：定义一个前缀奇数和的数组，然后向右遍历，维护左边的索引
     * @param nums
     * @param k
     * @return
     */
    public static int numberOfSubarrays(int[] nums, int k) {

        // 数组 prefixCnt 的下标是前缀和（即当前奇数的个数），值是前缀和的个数。
        int[] prefixCnt = new int[nums.length + 1];
        prefixCnt[0]=1;
        int evenCnt=0;
        int ans=0;
        for (int i=0;i<nums.length;i++){
            if (nums[i]%2==1){
                evenCnt++;
            }
            prefixCnt[evenCnt]++;//记录左侧前缀和等于 enentCnt的子数组的个数
            if (evenCnt >= k) {
                ans += prefixCnt[evenCnt - k];
            }
        }
        return ans;
    }
}
