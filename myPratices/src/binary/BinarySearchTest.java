package binary;

import java.util.HashMap;
import java.util.Map;

public class BinarySearchTest {

    public static void main(String[] args) {
//        System.out.println(new BinarySearchTest().maximumCandies(new int[]{1,2,3,4,10},5));

//        System.out.println(new BinarySearchTest().minimumTime(new int[]{1,2,3},5));
        System.out.println(new BinarySearchTest().minimizeArrayValue(new int[]{3,7,1,6}));
    }
    /**
     * 查找元素的位置：如果不存在则返回应该插入的位置
     * 转换成：在一个有序数组中找第一个大于等于 target 的下标
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {

        int low = 0, hight = nums.length - 1;
        int ans = nums.length;
        while (low <= hight) {
            int middle = (hight - low) / 2 + low;
            //其实就是找low_bound
//            if (target<=nums[middle]){
//                ans=middle;
//                hight=middle-1;
//            }
            if (target == nums[middle]) {
                ans = middle;
                return ans;
            } else if (target < nums[middle]) {
                ans=middle;//如果元素找不到则 这里可能是插入的位置，因此可能是答案
                hight = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return ans;
    }


    /**
     * 寻找山峰 ，直接遍历
     * @param arr
     * @return
     */

    public int peakIndexInMountainArray(int[] arr) {

        for (int i=0;i<arr.length;i++){
            //判断 i+1如果是山峰的右侧，则返回峰顶
            if (arr[i]>arr[i+1]){
                return i;
            }
        }

        return 0;
    }

    /**
     * 寻找峰值，可能存在多个，只需要返回一个即可
     * 设当前分割点 mid 满足关系 num[mid]>nums[mid+1] 的话，一个很简单的想法是 num[mid] 可能为峰值，而 nums[mid+1] 必然不为峰值，
     * 于是让 r=mid，从左半部分继续找峰值。

     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int l=0,h=nums.length-1;
        while (l<h){
            int middl=(h-l)/2+l;
            if (nums[middl]>nums[middl+1]){
                h=middl;
            }
            else {
                l=middl+1;
            }
        }
        return h;
    }


    /**
     * LCR 179. 查找总价格为目标值的两个商品
     * 数组整体升序排列，使用双指针进行查询
     * @param price
     * @param target
     * @return
     */
    public int[] twoSum(int[] price, int target) {

        int left = 0, right = price.length - 1;
        while (left < right) {
            int sum = price[left] + price[right];
            if (sum > target) {
                right--;
            } else if (sum == target) {
                return new int[]{price[right], price[left]};
            } else {
                left++;
            }
        }

        //方案二：也可以使用hash，记录过去的值
        Map<Integer, Integer> map = new HashMap<>();
        for (int value : price) {

            if (map.containsKey(target - value)) {
                return new int[]{value, map.get(target - value)};
            }
            map.put(value, value);
        }
        return new int[0];
    }


    /**
     * 2226. 每个小孩最多能分到多少糖果
     * 方案：先计算糖果的总数量，如果sum()<k 则不能分给k个小朋友
     * 然后在使用二分法进行 试探：，此类问题的模板就是定义一个check方法，判断当前的target是否满足，然后在进行 +1、-1缩进
     * @param candies
     * @param k
     * @return
     */
    public int maximumCandies(int[] candies, long k) {
        long sum = 0;
        for (int value : candies) {
            sum += value;
        }
        if (sum < k) {
            return 0;
        }

        //定义上下边界，然后在使用二分去check
        long left = 1, right =  (sum );

        long ans = 0;

        while (left <= right) {
            long middle = left + (right - left) / 2;
            if (check(candies, k, middle)) {
                //表示可以分成middle堆，因此我们在试探这 看看能否在进行分更大的堆
                ans = middle;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return (int) ans;
    }

    /**
     * 判断这堆糖果 是否能分给k个孩子，每人num个糖果
     * @param candies
     * @param k
     * @param limit
     * @return
     */
    private boolean check(int[] candies, long k ,long limit) {
        for (int value : candies) {
            if (value < limit) {
                //当前糖果不能分成一堆
                continue;
            } else if (value == limit) {
                k--;
            } else {
                //value/num 表示可以分的堆数
                k -= value / limit;
            }
        }
        return k <= 0;
    }


    /**
     * 2187. 完成旅途的最少时间
     * @param time
     * @param totalTrips
     * @return
     */
    public long minimumTime(int[] time, int totalTrips) {

        int max = time[0], min = time[0];
        for (int val : time) {
            max = Math.max(max, val);
            min = Math.min(min, val);
        }

//        long left = 1, right =Math.max(Long.MAX_VALUE,max * totalTrips) ;


        //大大数的情况下可以通过
        int avg = (totalTrips - 1) / time.length + 1;
        // 循环不变量：check(left) 恒为 false
        long left = (long) min * avg - 1;
        // 循环不变量：check(right) 恒为 true
        long right = Math.min((long) max * avg, (long) min * totalTrips);

        long ans = 0;
        while (left <= right) {
            long middleTime = left + (right - left) / 2;
            if (checkComplete(time, totalTrips, middleTime)) {
                //这个时间可以完成，在求更小的时间
                ans = middleTime;
                right = middleTime - 1;
            } else {
                left = middleTime + 1;
            }
        }
        return ans;
    }
    private boolean checkComplete(int[] time, int totalTrips,long totalTime){
        //判断在totalTime 时间内，每个车子完成了多少次旅行
        long cnt = 0;
        for (int val:time){
            cnt+=totalTime/val;
        }
        return cnt>=totalTrips;
    }

    /**
     * 2439. 最小化数组中的最大值
     * 二分查找
     * @param nums
     * @return
     */
    public int minimizeArrayValue(int[] nums) {
        int max=nums[0],min=nums[0];
        for(int val:nums){
            max=Math.max(max,val);
            min=Math.min(min,val);
        }
// 初始化二分查找的左右边界。
        // left 初始化为 -1 表示还没有任何有效值。
        // right 初始化为数组中最大的元素值。
        int left=min,right=max;
        int ans=0;
        while (left<=right){
            int middle=left+(right-left)/2;
            //判断调整后的数组的值是否都不超过 middle
            if (check(nums,middle)){
                ans=middle;
                right=middle-1;
            }
            else {
                left=middle+1;
            }
        }
        return ans;
    }

    /**
     *  // 辅助函数 check 检查是否可以通过调整使数组的最大值不超过 limit。
     * @param nums
     * @param limit
     * @return
     */
    private boolean check(int nums[],int limit){
        long exact=0;//倒序遍历，表示可以提供给前面的元素数量，不包含第一个(使用long 是为了防止数很大 溢出了)
        for (int i= nums.length-1;i>0;i--){
            //表示当前元素使用了之前元素贡献的数 还是超过了limit，为了不超过limit，则把超过的值贡献给下一个元素
            if (nums[i]+exact>limit){
                exact=nums[i]+exact-limit;
            }
            else {
                //当前元素加上之前元素贡献的exact 都小于 limit，那么之前的exact都给当前元素使用了，索引把exact设置成0
                exact=0;
            }
        }

        //判断第一个数是否不超过limit
        return nums[0]+exact<=limit;
    }
}
