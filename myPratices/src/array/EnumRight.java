package array;

import java.awt.image.AffineTransformOp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举右边，维护左边的值
 */
public class EnumRight {

    /**
     * 1512. 好数对的数目
     * @param nums
     * @return
     */
    public int numIdenticalPairs(int[] nums) {

        int ans = 0;
        //维护左边信息的集合:记录元素出现的次数
        Map<Integer, Integer> numOfCount = new HashMap<>();

        for (int value : nums) {
            int count = numOfCount.getOrDefault(value, 0);
            ans += count;
            numOfCount.put(value, count + 1);
        }
        return ans;
    }

    /**
     * 121. 买卖股票的最佳时机
     * 寻找左侧最小值
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {

        int max = 0;
        int preMin = prices[0];//寻找左侧的最小值
        for (int value : prices) {
            max = Math.max(max, value - preMin);
            preMin = Math.min(preMin, value);
        }
        return max;
    }


    /**
     * 2815. 数组中的最大数对和
     * 定义数组numIdx[] 表示最大位的所有数中的最大值,枚举右边界的时候 再去数组中查收对应的最大位的最大值，然后再把他俩相加
     * @param nums
     * @return
     */
    public static int maxSum(int[] nums) {

        int[] maxNumValue = new int[10];//最大位对应的最大值
        int maxSum = 0;
        for (int value : nums) {
            //求最大位
            int tmp=value;
            int maxNum = tmp % 10;
            while (tmp > 0) {
                maxNum = Math.max(maxNum, tmp % 10);
                tmp = tmp / 10;
            }
            //当前的位置上有值 才进行计算
            if (maxNumValue[maxNum] != 0) {
                maxSum = Math.max(maxSum, maxNumValue[maxNum] + value);
            }
            maxNumValue[maxNum] = Math.max(maxNumValue[maxNum], value);
        }
        return maxSum!=0?maxSum: -1;
    }


    /**
     * 2342. 数位和相等数对的最大和
     * 方案：使用map 维护一个 数位和 对应的最大值
     * @param nums
     * @return
     */
    public static int maximumSum(int[] nums) {
        int ans = -1;
        Map<Integer, Integer> map = new HashMap<>();

        for (int value : nums) {
            //求数位和
            int tmp = value;
            int bitSum = 0;
            while (tmp > 0) {
                bitSum += tmp % 10;
                tmp = tmp / 10;
            }
            if (map.containsKey(bitSum)) {
                ans = Math.max(ans, map.get(bitSum) + value);
                map.put(bitSum, Math.max(value, map.get(bitSum)));
            } else {
                map.put(bitSum, value);
            }
        }
        return ans;
    }

    /**
     * 1679. K 和数对的最大数目
     * 解决方案：维护左边元素的个数,枚举j时，判断是否有k-j的元素，如果有则进行去除操作
     * @param nums
     * @param k
     * @return
     */
    public int maxOperations(int[] nums, int k) {
        Map<Integer, Integer> numCountMap = new HashMap<>();
        int ans = 0;
        for (int val : nums) {
            if (numCountMap.containsKey(k - val)) {
                ans++;
                int count = numCountMap.get(k - val) - 1;
                if (count == 0) {
                    numCountMap.remove(k - val);
                } else {
                    numCountMap.put(k - val, count);
                }
            } else {
                //放入统计数据中
                numCountMap.put(val, numCountMap.getOrDefault(val, 0) + 1);
            }
        }
        return ans;
    }

    /**
     * 2260. 必须拿起的最小连续卡牌数
     * 使用map 记录 值到索引的映射，每次遍历时到数组中查找相同值的卡牌
     * @param cards
     * @return
     */
    public int minimumCardPickup(int[] cards) {
        int ans = Integer.MAX_VALUE;
        Map<Integer, Integer> valuMap = new HashMap<>();

        for (int i = 0; i < cards.length; i++) {
            int val = cards[i];
            if (valuMap.containsKey(val)) {
                ans= Math.min(ans, i - valuMap.get(val)+1);
            }
            valuMap.put(val, i);
        }
        return ans==Integer.MAX_VALUE?-1:ans;
    }

    /**
     * 1010. 总持续时间可被 60 整除的歌曲
     * @param time
     * @return
     */
    public  static int numPairsDivisibleBy60(int[] time) {

        int[] numCount = new int[60];
        int ans = 0;
        for (int value : time) {

            if (numCount[(60 - value % 60)%60] > 0) {
                ans += numCount[(60 - value % 60)%60];
            }
            numCount[value % 60]++;
        }
        return ans;
    }

    /**
     * 3185. 构成整天的下标对数目 II
     * @param hours
     * @return
     */
    public long countCompleteDayPairs(int[] hours) {
        long[] numCount = new long[24];
        long ans = 0;
        for (int value : hours) {

            if (numCount[(24 - value % 24)%24] > 0) {
                ans += numCount[(24 - value % 24)%24];
            }
            numCount[value % 24]++;
        }
        return ans;
    }

    /**
     * 2748. 美丽下标对的数目
     * 解决方案：统计最高位出现的次数：然后枚举 j，循环判断0~9中和j的最低位 是否互质，然后在加入答案
     * @param nums
     * @return
     */
    public int countBeautifulPairs(int[] nums) {
        int ans=0;
        int cnt[]=new int[10];

        for (int value:nums){

            int tail=value%10;

            for (int i=1;i<cnt.length;i++){
                if (cnt[i]>0&&gcd(i,tail)==1){
                    ans+=cnt[i];
                }
            }

            //首位数字
            int tmp=value;
            while (tmp>=0){
                tmp=tmp/10;
            }
            cnt[tmp]++; // 统计最高位的出现次数
        }
        return ans;
    }

    /**
     * 求 最大公因数
     * @param a
     * @param b
     * @return
     */
    int gcd(int a,int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    /**
     * 2874. 有序三元组中的最大值 II
     * 下标三元组 (i, j, k) 的值等于 (nums[i] - nums[j]) * nums[k] 。
     * 固定j的位置，其实就是求j左边的最大值和j右边的最大值
     * 可以先求出j后缀的最大值，j前缀的最大值可以在枚举j的过程中计算
     * @param nums
     * @return
     */
    public static long maximumTripletValue(int[] nums) {
        int n=nums.length;
        //计算后缀最大值
        int[] sufMax = new int[n+1];
        for (int i = nums.length - 1; i >= 0; i--) {
            sufMax[i] = Math.max( sufMax[i+1], nums[i]);//也包含他自己
        }

        long preMax = nums[0];//记录左边的最大值
        long maxValue = 0;//最大结果
        for (int i = 1; i < nums.length - 1; i++) {
            maxValue = Math.max(maxValue, (long)(preMax - nums[i]) * sufMax[i+1]);
            preMax = Math.max(preMax, nums[i]);
        }
        return maxValue;
    }


    /**
     * 454. 四数相加 II
     * 把数组分成两部分：前面两个是一部分，计算他俩每个元素相加后的结果的个数，然后在使用两层循环遍历后面的两个数组，到第一个集合中
     * 查找有几个result=0-i-j的值，进行累加
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int value : nums1) {
            for (int value2 : nums2) {
                int key = value + value2;
                int count = countMap.getOrDefault(value + value2, 0);
                countMap.put(key, count + 1);
            }
        }

        int ans = 0;

        for (int value : nums3) {
            for (int value2 : nums4) {
                int key = -value - value2;//判断是否有
                if (countMap.containsKey(key)) {
                    ans += countMap.get(key);
                }
            }
        }
        return ans;
    }

    /**
     * 1014. 最佳观光组合
     *  values[i] + values[j] + i - j --->(values[i] + i)+（values[j]-j）
     * 遍历j的同时，求之前的preMax
     * @param values
     * @return
     */
    public int maxScoreSightseeingPair(int[] values) {

        int n = values.length;
        int preMax = values[0];
        int ans = 0;
        for (int i = 1; i < n; i++) {
            ans = Math.max(ans, preMax + values[i] - i);
            preMax = Math.max(preMax, values[i] + i);
        }
        return ans;
    }


    /**
     * 930. 和相同的二元子数组
     * @param nums
     * @param goal
     * @return
     */
    public int numSubarraysWithSum(int[] nums, int goal) {

        Map<Integer, Integer> sumCount = new HashMap<>();
        sumCount.put(0,1);//表示和为0的右一个
        int sum = 0, ans = 0;
        for (int value : nums) {
            sum += value;
            if (sumCount.containsKey(sum - goal)) {
                ans += sumCount.get(sum - goal);
            }
            //递增一个
            sumCount.put(sum, sumCount.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
//        System.out.println(maxSum(new int[]{51,71,17,24,42}));
//        System.out.println(maximumSum(new int[]{18,43,36,13,7}));
//        System.out.println(numPairsDivisibleBy60(new int[]{30,20,150,100,40}));
        System.out.println(maximumTripletValue(new int[]{12,6,1,2,7}));
    }


}
