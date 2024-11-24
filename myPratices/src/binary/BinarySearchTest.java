package binary;

import java.util.HashMap;
import java.util.Map;

public class BinarySearchTest {

    public static void main(String[] args) {

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
}
