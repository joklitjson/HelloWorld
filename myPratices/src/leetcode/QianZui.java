package leetcode;

import java.util.HashMap;
import java.util.Map;

public class QianZui {

    public static void main(String[] args) {
        System.out.println(
                new QianZui().subarraySum(new int[]{1, 1, 1}, 2)
        );
    }
    /**
     * 方案一、暴力破解：固定左右边界，在求和，然后计算
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum1(int[] nums, int k) {
        int ans = 0;
        for (int left = 0; left < nums.length; left++) {
            int sum = 0;
            for (int right = left; right < nums.length; right++) {
                sum += nums[right];
                if (sum == k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * 方案二、使用前缀和的方式 计算sum(j)-sum(i)==k：固定左右边界，在求和，然后计算
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum11(int[] nums, int k) {
        int ans = 0;
        int[] preSum = new int[nums.length];
        preSum[0] = nums[0];
        for (int left = 1; left < nums.length; left++) {
            preSum[left] =preSum[left-1]+ nums[left];
        }
        for (int left = 0; left < nums.length; left++) {
            for (int right = left; right < nums.length; right++) {
                if (preSum[right] - preSum[left] == k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public int subarraySum(int[] nums, int k) {
        int len = nums.length;
        // 计算前缀和数组
        int[] preSum = new int[len];
        preSum[0] = nums[0];
        for (int i = 1; i < len; i++) {
            preSum[i ] = preSum[i-1] + nums[i];
        }

        int count = 0;
        for (int left = 0; left < len; left++) {
            for (int right = left; right < len; right++) {
                // 区间和 [left..right]，注意下标偏移
                if (preSum[right ] - preSum[left] == k) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * 使用前缀和  hash表
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum3(int[] nums, int k) {
        int ans = 0;
        int preSum = 0;
        Map<Integer, Integer> sumCount = new HashMap<>();
        sumCount.put(0, 1);
        for (int left = 0; left < nums.length; left++) {
            preSum += nums[left];
            // 先获得前缀和为 preSum - k 的个数，加到计数变量里
            if (sumCount.containsKey(preSum - k)) {
                ans += sumCount.get(preSum - k);
            }
            //更新数量
            sumCount.put(preSum, sumCount.getOrDefault(preSum, 0) + 1);
        }
        return ans;
    }
}
