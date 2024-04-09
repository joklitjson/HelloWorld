package leetcode;

import java.util.*;

public class SliceWindowTest {
    public static void main(String[] args) {

        System.out.println("Hello world!");

//        System.out.println("第k大的值"+findKthLargest(new int[]{3,2,3,1,2,4,5,5,6},4));

//        System.out.println("第k大的值"+findKthLargest2(new int[]{3,2,1,5,6,4},2));

        System.out.println("最长无重复字符=====" + lengthOfLongestSubstring("abba"));
        System.out.println(minSubArrayLen(7,new int[]{2,3,1,2,4,3}));
    }

    /**
     * 都有一个胃口值 g[i]
     * 并且每块饼干 j，都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i
     *
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        int i = g.length - 1;
        //遍历饼干
        for (int j = s.length - 1; j >= 0; j--) {
            //遍历当前最大的饼干是否能满足当前最大需求孩子，不满足则
            for (; i >= 0; i--) {
                if (s[j] >= g[i]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean lemonadeChange(int[] bills) {
        if (bills[0] != 5) {
            return false;
        }

        int five = 0, ten = 0;
        for (int value : bills) {
            if (value == 5) {
                five++;
            } else if (value == 10) {
                ten++;
                five--;
            } else if (value == 20) {
                //找零,优先找10元的零钱,因为5元的是万能的
                if (ten > 0) {
                    ten--;
                    five--;
                } else {
                    five = five - 3;
                }
            }
            if (five < 0 || ten < 0) {
                return false;
            }
        }
        //判断最后是否大大于零
        return five >= 0 && ten >= 0;
    }

    public int candy(int[] ratings) {

        int[] candys = new int[ratings.length];
        candys[0] = 1;
        //分糖果
        for (int i = 1; i < ratings.length; i++) {
            //相邻的元素，如果后面的大于前面的则糖果要多余前面
            if (ratings[i] > ratings[i - 1]) {
                candys[i] = candys[i - 1] + 1;
            } else {
                candys[i] = 1;
            }
        }
        int count = candys[ratings.length - 1];
        for (int i = ratings.length - 2; i >= 0; i--) {
            //在从右向左比遍历一遍
            if (ratings[i] > ratings[i + 1]) {
                candys[i] = Math.max(candys[i], candys[i + 1] + 1);
            }
            count += candys[i];
        }

        return count;
    }

    /**
     * 方案二：使用小跟堆，只保留k个数，遍历每次都去除根部的最小值，由于是小跟堆，因此根元素就是第k个最大值
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest2(int[] nums, int k) {

        PriorityQueue<Integer> heap = new PriorityQueue<>(k);
        for (int num : nums) {
            if (heap.size() == k) {
                //移除小于优先级队列的最小值，只保存k个大值
                if (num > heap.peek()) {
                    heap.poll();
                    heap.offer(num);
                }
            } else {
                heap.offer(num);
            }
        }
        return heap.peek();
    }

    public static int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private static int quickSelect(int[] nums, int l, int r, int k) {
        int partionIndex = partion(nums, l, r, k);
        System.out.println("partionIndex=" + partionIndex + "partionValue=" + nums[partionIndex] + Arrays.toString(nums));
        if (partionIndex == k) {
            return nums[partionIndex];
        }
        if (k > partionIndex) {
            return quickSelect(nums, partionIndex + 1, r, k);
        } else {
            return quickSelect(nums, l, partionIndex - 1, k);
        }
    }

    //此时的排序是降序，从大到小
    private static int partion(int[] nums, int l, int r, int k) {

        if (l >= r) {
            return l;
        }
        int partionValue = nums[l];
        int left = l, right = r;
        while (left != right) {
            while (right > left && nums[right] > partionValue) {
                right--;
            }
            while (left < right && nums[left] <= partionValue) {
                left++;
            }

            if (left != right) {
                int tmp = nums[right];
                nums[right] = nums[left];
                nums[left] = tmp;
            }
        }

        int tmp = nums[left];
        nums[left] = partionValue;
        nums[l] = tmp;
        return left;
    }

    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0;
        int maxLongst = 0;
        for (; right < s.length(); right++) {
            //必须要大于左指针,优先排除无效指针数据
            if (map.containsKey(s.charAt(right))&&map.get(s.charAt(right))>=left) {
//原则上 map.getOrDefault(s.charAt(right),0)+1是最大值，但是也有可能历史中有此数据，
// 因此会有误判的情况，所以需要比较下left和当前left值的最大值。主要原因还是由于map清空历史数据的问题
                left = Math.max(left, map.getOrDefault(s.charAt(right), 0) + 1);
            }
            map.put(s.charAt(right), right);
            maxLongst = Math.max(maxLongst, right - left + 1);
            if (maxLongst == 3) {
                System.out.println(right + "" + s.charAt(right));
            }
        }
        return maxLongst;
    }

    /**
     * 滑动窗口—至多包含两个不同字符的最长子串（leetcode 159）
     * <p>
     * 给定一个字符串 s ，找出 至多 包含两个不同字符的最长子串 t ，并返回该子串的长度。
     * 该题目的主要关键点在于 ：
     * 1、如何统计包含字符个数？使用hashMap统计
     * 2、当包含3个字符时，需要删除哪个字符？ 需要计算最长子串，因此需要移除最左边的元素
     * 右以上分析可以构建一个map key=字符 value=下表
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() < 3) {
            return s.length();
        }
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int leftIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.size() < 3) {
                map.put(s.charAt(i), i);
            }
            //剔除多余字符
            if (map.size() == 3) {
                int minIndex = Collections.min(map.values());
                map.remove(s.charAt(minIndex));
                //左指针向右移动
                leftIndex = minIndex + 1;
            }
            maxLength = Math.max(maxLength, i - leftIndex);
        }
        return maxLength;
    }

    /**
     * [LeetCode] 340、至多包含 K 个不同字符的最长子串
     * 给定一个字符串 s ，找出 至多 包含 k 个不同字符的最长子串 T。
     * <p>
     * 分析：上一题的变化：就是把2变成参数k了
     * @param k
     * @return
     */
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {

        int length=s.length();
        if (k <= 0 || length == 0)
            return 0;

        if (length <= k) {
            return s.length();
        }
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int leftIndex = 0,right=0;

        //
        while (right<length) {
            if (map.size() < k + 1) {
                map.put(s.charAt(right), right++);
            }
            //剔除多余字符
            if (map.size() == k + 1) {
                int minIndex = Collections.min(map.values());
                map.remove(s.charAt(minIndex));
                //左指针向右移动
                leftIndex = minIndex + 1;
            }
            maxLength = Math.max(maxLength, right - leftIndex);
        }
        return maxLength;
    }

    /**
     * 209. 长度最小的子数组
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     *
     * 找出该数组中满足其总和大于等于 target 的长度最小的 连续
     * 子数组
     *  [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0
     * @param target
     * @param nums
     * @return
     */
    public static int minSubArrayLen(int target, int[] nums) {
        int ans = Integer.MAX_VALUE;
        //滑动窗口算法，让指针向右滑动，直到和大于target，然后在慢慢收紧left边界，找到最小长度数组
        int left = 0, right = 0, sum = 0;

        while (right < nums.length) {
            sum += nums[right++];
            while (sum >= target) {
                ans = Math.min(ans, right - left + 1);
                sum -= nums[left++];
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}