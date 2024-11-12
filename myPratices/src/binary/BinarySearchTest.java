package binary;

public class BinarySearchTest {

    /**
     * 查找元素的位置：如果不存在则返回应该插入的位置
     * 转换成：在一个有序数组中找第一个大于等于 target 的下标
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {

        int low=0,hight=nums.length-1;
        int ans=nums.length;
        while (low<hight){
            int middle=(hight-low)/2+low;
            //其实就是找low_bound
            if (target<=nums[middle]){
                ans=middle;
                hight=middle-1;
            }
            else {
                low=middle+1;
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
}