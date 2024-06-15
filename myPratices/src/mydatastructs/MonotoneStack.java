package mydatastructs;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//单调栈
public class MonotoneStack {

    //返回数组中的元素右侧的第一个最大值
    public int[] nextGreaterElement(int[] nums) {
        // 见上文
        Stack<Integer> stack = new Stack<Integer>();
        int[] res = new int[nums.length];

        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            //把当前元素入栈
            stack.push(nums[i]);
        }
        return res;
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        //先求num2的右侧的最大元素
        int[] nextGreater = nextGreaterElement(nums2);
        //然后再计算他的映射
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], nextGreater[i]);
        }

        int[] res = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(res[i]);
        }
        return res;
    }

    /**
     * 再过几天会有更高的温度，
     *
     * @param temperatures
     * @return
     */
    int[] dailyTemperatures(int[] temperatures) {

        int[] res = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();//存储的是下一天高温度的索引
        for (int i = temperatures.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return res;
    }

    /**
     * 环形数组的下一个更大的元素
     *
     * @param nums
     * @return
     */
    int[] nextGreaterElements2(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = 2 * n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() < nums[i % n]) {
                stack.pop();
            }
            res[i % n] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i % n]);
        }
        return res;
    }
}
