package array;

import linkedList.ListNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoublePointInArray {

    /**
     * 移除重复元素，然后返回去重后的数组长度
     *
     * @param nums
     * @return
     */
    int removeDuplicates(int[] nums) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[slow] != nums[fast]) {
                slow++;//注意：先加了一，默认第一个元素已经放在了下表0的位置
                nums[slow] = nums[fast];
            }
            fast++;
        }
        //数组的长度要再索引的基础上加一
        return slow + 1;
    }


    /**
     * 移除指定元素
     *
     * @param nums
     * @param val
     * @return
     */
    int removeElement(int[] nums, int val) {
        int slow = 0, fast = 0;

        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }

    void moveZeroes(int[] nums) {
        int p = removeElement(nums, 0);
        while (p < nums.length) {
            nums[p++] = 0;
        }
    }

    int[] twoSum(int[] nums, int target) {
        return twoSum(nums,0,target);
    }
    //两数之和
    int[] twoSum(int[] nums,int start, int target) {
        int slow = start, fast = nums.length - 1;
        while (slow < fast) {
            int sum = nums[slow] + nums[fast];
            if (sum == target) {
                return new int[]{slow + 1, fast + 1};
            } else if (sum < target) {
                slow++;
            } else if (sum > target) {
                fast--;
            }
        }
        return new int[]{-1, -1};
    }

    List<List<Integer>> twoSum2(int[] nums, int start, int target) {
        int slow = start, fast = nums.length - 1;
        List<List<Integer>> res = new ArrayList<>();
        while (slow < fast) {
            int left = nums[slow];
            int right = nums[fast];
            int sum = nums[slow] + nums[fast];
            if (sum == target) {
                res.add(Arrays.asList(left,right));
                //防止有重复元素出现
                while (slow < fast && nums[slow] == left) {
                    slow++;
                }
                while (slow < fast && nums[fast] == right) {
                    fast--;
                }
            } else if (sum < target) {
                while (slow < fast && nums[slow] == left) {
                    slow++;
                }
            } else if (sum > target) {
                while (slow < fast && nums[fast] == right) {
                    fast--;
                }
            }
        }
        return res;
    }

    // 三数之和
    List<List<Integer>> threeSum(int[] nums) {
        int target = 0;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int nextSum = target - nums[i];
            List<List<Integer>> result = twoSum2(nums, i + 1, nextSum);
            if (!result.isEmpty()) {
                for (List<Integer> list : result) {
                    list.add(nums[i]);
                }
                res.addAll(result);
            }
            //跳过重复元素
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return res;
    }

    void reverseString(char[] s) {

        int slow = 0, fast = s.length;
        while (slow < fast) {
            char tm = s[slow];
            s[slow] = s[fast];
            s[fast] = tm;
            slow++;
            fast--;
        }
    }


}
