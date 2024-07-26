package slidwindow;

import java.util.*;

public class SliceWindowTest {
    public static void main(String[] args) {

        System.out.println("Hello world!");

//        System.out.println("第k大的值"+findKthLargest(new int[]{3,2,3,1,2,4,5,5,6},4));

//        System.out.println("第k大的值"+findKthLargest2(new int[]{3,2,1,5,6,4},2));

//        System.out.println("最长无重复字符=====" + lengthOfLongestSubstring("abba"));
//        System.out.println(minSubArrayLen(7,new int[]{2,3,1,2,4,3}));

//        System.out.println(minimumRecolors("WBBWWBBWBW",7));
//        System.out.println(minimumDifference(new int[]{9,4,1,7},2));
        System.out.println(maximumLengthSubstring("bcbbbcba"));
        System.out.println(findMaxAverage(new int[]{-1},1));
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

    /**
     * 无重复元素的最大子字符
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, right = 0;
        int maxLongst = 0;
        for (; right < s.length(); right++) {
            //必须要大于左指针,优先排除无效指针数据
            if (map.containsKey(s.charAt(right))&&map.get(s.charAt(right))>=left) {
//原则上 map.getOrDefault(s.charAt(right),0)+1是最大值，但是也有可能历史中有此数据，
// 因此会有误判的情况，所以需要比较下left和当前left值的最大值。主要原因还是由于map未清空历史数据的问题
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

    /**
     * 先固定左端点的值，然后右端点向右扩展
     * 最长的奇偶交替的数组最长奇偶子数组
     * @param nums
     * @param threshold
     * @return
     */
    public int longestAlternatingSubarray(int[] nums, int threshold) {

        int i = 0, j = 0, n = nums.length, ans = 0;
        while (i < n) {
            //左边界一定是偶数开头的
            if (nums[i] % 2 == 1 || nums[i] > threshold) {
                i++;
                continue;
            }
            //寻找右边界
            int pre = nums[i] % 2;
            j = i + 1;
            while (j<n&&nums[j] <= threshold && nums[j] % 2 != pre) {
                pre = nums[j] % 2;
                j++;
            }
            ans = Math.min(ans, j - i );
            i = j;
        }
        return ans;
    }

    /**
     * 2379. 得到 K 个黑块的最少涂色次数
     * @param blocks
     * @param k
     * @return
     */
    public static int minimumRecolors(String blocks, int k) {

//        思路:计算窗口内的小的白色数量
        int n = blocks.length();
        if (k > n) {
            return -1;
        }
        int maxblack = 0;
        int ans=Integer.MAX_VALUE;
        for (int i = 0; i < blocks.length(); i++) {
            char c = blocks.charAt(i);
            //窗口内的元素
            if (c == 'B') {
                maxblack += 1;
            }
            //移除窗口的左侧元素
            if (i >= k) {
                //移除窗口左侧的B
                if (blocks.charAt(i - k) == 'B') {
                    maxblack -= 1;
                }
            }
            //和上一次的比较
            ans = Math.min(k - maxblack, ans);
        }

        return ans;
    }

    /**
     * 1984. 学生分数的最小差值
     * @param nums
     * @param k
     * @return
     */
    public static int minimumDifference(int[] nums, int k) {
        if (nums.length==1||nums.length<k){
            return 0;
        }
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        //直接初始化从k-1个开始
        for (int i = k-1; i < nums.length; i++) {
            min = Math.min(min, (nums[i] - nums[i - k+1]));
        }
        return min;
    }

    /**
     * 3090. 每个字符最多出现两次的最长子字符串
     * @param s
     * @return
     */
    public static int maximumLengthSubstring(String s) {

        int cnt[] = new int[26];
        int n = s.length();
        int l = 0, r = 0;
        int ans = -1;
        while (r < n) {
            int idx = s.charAt(r) - 'a';
            cnt[idx]++;
            //加入之后的值超过了
            while (cnt[idx] > 2) {
                //收缩左边界
                cnt[s.charAt(l) - 'a']--;
                l++;
            }
            ans = Math.max(ans, r - l + 1);
            r++;
        }

        return ans;
    }

    /**
     * 1876. 长度为三且各字符不同的子字符串
     * @param s
     * @return
     */
    public static int countGoodSubstrings(String s) {
        if (s == null || s.length() < 3) {
            return 0;
        }
        int[] cnt = new int[26];
        int count = 0, n = s.length();
        int l = 0, r = 0;
        //定义左右边界，不断的加大右边界
        while (r < n) {
            int idx = s.charAt(r) - 'a';
            cnt[idx]++;
            r++;
            //右重复字符，收缩左边界
            while (cnt[idx] > 1) {
                //
                cnt[s.charAt(l) - 'a']--;
                l++;
            }
            //长度满足窗口的长度
            if (r - l == 3) {
                count += 1;
                cnt[s.charAt(l) - 'a']--;
                l++;
            }
        }
        return count;
    }


    /**
     * 643. 子数组最大平均数 I
     * @param nums
     * @param k
     * @return
     */
    public static double findMaxAverage(int[] nums, int k) {

        if (nums.length < k) {
            return 0;
        }
        int n = nums.length;
        Double maxAvg = null;
        double sum = 0;

        int l = 0, r = 0;
        while (r < n) {
            sum += nums[r];
            //达到了窗口的大小
            if (r - l + 1 == k) {
                maxAvg = maxAvg == null ? sum / k : Math.max(maxAvg, sum / k);

                //移除最左侧的元素
                sum -= nums[l];
                l++;
            }
            r++;
        }
        return maxAvg;
    }
}