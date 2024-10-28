package greedy;

import java.util.Arrays;

public class CanPlaceFlowers {

    /**
     * 从左向右遍历花坛，在可以种花的地方就种一朵，能种就种（因为在任一种花时候，不种都不会得到更优解），就是一种贪心的思想
     * 这里可以种花的条件是：
     * <p>
     * 自己为空
     * 左边为空 或者 自己是最左
     * 右边为空 或者 自己是最右
     * 最后判断n朵花是否有剩余，为了效率起见，可以在种花的过程中做判断，一旦花被种完就返回true
     *
     * @param flowerbed
     * @param n
     * @return
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                n--;
                if (n <= 0) return true;
                flowerbed[i] = 1;
            }
        }

        return n <= 0;

    }

    /**
     * 验证字符串是否是回文字符，同时最多可以删除一个字符
     * 方案：验证回文串时 是比较左右两个指向，如果相同 进行下一个比较，
     * 如果不同则 要么删除左边节点 要么删除右边字符，然后在判断是否是回文字符
     *
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {

        int low = 0, hight = s.length() - 1;

        while (low <= hight) {
            if (s.charAt(low) == s.charAt(hight)) {
                low++;
                hight--;
            } else {
                return validPalindromeWithIndex(s, low + 1, hight) ||
                        validPalindromeWithIndex(s, low, hight - 1);
            }
        }
        return true;
    }

    public boolean validPalindromeWithIndex(String s, int low, int hight) {
        while (low <= hight) {
            if (s.charAt(low) == s.charAt(hight)) {
                low++;
                hight--;
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * 1005. K 次取反后最大化的数组和
     * 贪心算法：优先取反 负数的值，其次在看看是否还有剩余，如果有 剩余且是奇数个，则在减去最小的数
     *
     * @param nums
     * @param k
     * @return
     */
    public int largestSumAfterKNegations(int[] nums, int k) {

        Arrays.sort(nums);

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (k > 0 && nums[i] <= 0) {
                nums[i] = -nums[i];
                k--;
            }
            sum += nums[i];
        }

        Arrays.sort(nums);
        // 如果k没剩，那说明能转的负数都转正了，已经是最大和，返回sum
        // 如果k有剩，说明负数已经全部转正，所以如果k还剩偶数个就自己抵消掉，不用删减，如果k还剩奇数个就减掉2倍最小正数。
        return sum - (k % 2 == 0 ? 0 : 2 * nums[0]);
    }

    /**
     * 数组能否被三等分：
     * 解法：遍历求和，看看数组和能否是3的倍数，然后在遍历数组找到下表i,j 使，sum(0,i)=sum/3,sum(0,j)=sum/3*2，
     * 则i、j就把数组三等分了
     * @param arr
     * @return
     */
    public boolean canThreePartsEqualSum(int[] arr) {

        return true;
    }

    /**
     *
     * 是否存在递增的三元组序列
     * 上述做法的贪心思想是：为了找到递增的三元子序列，first 和 second 应该尽可能地小，此时找到递增的三元子序列的可能性更大。
     * 方案一：定义一个左边最小值集合，右边最大值集合，然后再次遍历每个元素，看看是否存在 左边小于自己的值，并且右边大于自己的值
     * 方案二：定义 first、second，作为左边最小、第二小的值，然后遍历数组，看看是否存在大于他俩的值，如果不存在则更新first或second额
     * @param nums
     * @return
     */
    public boolean increasingTriplet(int[] nums) {
        int first=nums[0],second=Integer.MAX_VALUE;

        /**
         * 方案就是尽可能的让 第一个和第二个元素小
         */
        for (int value:nums){
            if (value>second){
                return true;
            }
            else if (value>first){
                second=value;
            }
            else {
                first=value;
            }
        }

        return false;
    }
}
