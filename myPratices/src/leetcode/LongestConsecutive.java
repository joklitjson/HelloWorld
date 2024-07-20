package leetcode;

import java.util.*;

/**
 * 数组中的最长连续子序列
 * https://leetcode.cn/problems/WhsWhI/
 */
public class LongestConsecutive {

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1}));
//        System.out.println(longestConsecutive(new int[]{1, 2, 0, 1}));
    }

    /**
     * 方案一：先排序，在查找最长的连续子序列
     *
     * @param nums
     * @return
     */
    public static int longestConsecutive1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int count = 1;//目前有序序列的长度
        int longst = 1;//默认第一个元素就是最长的
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue;
            }
            //比前一个大1，可以构成连续的序列，count++
            if ((nums[i] - nums[i - 1]) == 1) {
                count++;
            } else {
                //没有比前一个大1，不可能构成连续的，
                //count重置为1
                count = 1;
            }
            //记录最长的序列长度
            longst = Math.max(longst, count);
        }
        return longst;
    }

    //借助set集合，用于去重，然后在遍历元素，假设每个元素都是连续序列的最小值(在排除掉最小的)，然后在递增的在set中找比他大的元素
    public static int longestConsecutive2(int[] nums) {

        Set<Integer> set = new HashSet<>(nums.length);
        for (int val : nums) {
            set.add(val);
        }

        int longst = 0;
        for (int val : nums) {

            //说明当前元素还有比他小的值，因此不是最小值
            if (set.contains(val - 1)) {
                continue;
            }

            //找比他大的元素
            int count = 1;
            int currNum = val;
            while (set.contains(currNum + 1)) {
                count++;
                currNum++;
            }
            longst = Math.max(count, longst);
        }
        return longst;
    }

    /**
     * 借助hash表结构：hash表中的key存放的是数字，value存放的是左右两边的宽度，遇到连续的数字，在把两个宽度连接起来，然后更新hash表
     * @param nums
     * @return
     */
    public static int longestConsecutive(int[] nums) {

        int lonest = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int val : nums) {
            //防止重复元素
            if (!map.containsKey(val)) {

                int leftLen = map.get(val - 1) == null ? 0 : map.get(val - 1);
                int rightLen = map.get(val + 1) == null ? 0 : map.get(val + 1);
                int currnetLen = leftLen + rightLen + 1;
                map.put(val, currnetLen);
                //更新左右两个端点涉及的连续最长值
                map.put(val-leftLen, currnetLen);
                map.put(val+rightLen, currnetLen);
                lonest = Math.max(lonest, currnetLen);
            }
        }

        return lonest;
    }

    /**
     * 借助并查集，把连续的数字进行联通，然后比较联通中的元素的最大数量
     * @param nums
     * @return
     */
    public static int longestConsecutive4(int[] nums) {

        return 0;
    }
}
