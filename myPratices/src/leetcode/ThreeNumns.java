package leetcode;

import java.util.*;

public class ThreeNumns {
    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{0,0,0,0}));
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);//先排序
        int k = nums.length;

        for (int i = 0; i < nums.length - 2; i++) {
            int target = 0 - nums[i];
            int l = i + 1, h = nums.length - 1;
            //防止重复元素再次遍历
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            //优化点1：最小的值，都大于目标值了，因此提前结束
            if (l + 1 < k && nums[l] + nums[l + 1] > target) {
                continue;
            }
            //优化点二：最大值的和都小于目标值
            if (h - 1 >= 0 && nums[h] + nums[h - 1] < target) {
                continue;
            }
            //使用双指针法，此处也可以使用 二分法
            while (l < h) {
                //防止重复元素再次遍历
                if (l > i + 1 && nums[l] == nums[l - 1]) {
                    l++;
                    continue;
                }
                int sum = nums[l] + nums[h];
                if (sum == target) {
                    String key = nums[i] + "," + nums[l] + "," + nums[h];
                    result.add(Arrays.asList(nums[i], nums[l], nums[h]));
                    l++;
                } else if (sum > target) {
                    h--;
                } else {
                    l++;
                }
            }
        }
        return result;
    }
}
