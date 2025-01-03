package leetcode;

public class BinaySearch {

    public static void main(String[] args) {
        System.out.println(search(new int[]{3,1},1));
    }


    public int  binaySearch(int [] nums,int target) {
        int l = 0, h = nums.length - 1;

        while (l < h) {
            int middle = l + (h - l) / 2;

            if (target == nums[middle]) {
                return middle;
            } else if (nums[middle] > target) {
                h = middle - 1;
            } else {
                l = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 旋转数组的二分搜索
     *
     * 1、将数组一分为二。（其中有一个一定是有序的；另一个则是有序或部分有序的）
     * 2、此时如果target在有序部分里，则用二分法查找。
     * 3、否则进入无序部分查找，返回1。
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
        if (nums.length==0){
            return -1;
        }
        if (nums.length==1){
            return nums[0]==target?0:-1;
        }
        int left=0,right= nums.length-1;

        while (left<=right){
            int middle=(left+right)/2;
            if (nums[middle]==target){
                return middle;
            }
            if (nums[left]<=nums[middle]){
                //左边是有序区间
                if (target>=nums[left]&&target<nums[middle]){
                    right=middle-1;
                }
                else {
                    left = middle + 1;
                }
            }
            else {
                //右边是有序区间
                if (target>nums[middle]&&target<=nums[right]){
                    left=middle+1;
                }
                else {
                    right=middle-1;
                }
            }
        }

        return -1;


//        int left = 0, right = nums.size() - 1;
//        while (left <= right) {
//            int mid = (left + right) >> 1;
//            if (nums[mid] == target) return mid;
//            if (nums[left] <= nums[mid]) {
//                // left 到 mid 是顺序区间
//               if (target >= nums[left] && target < nums[mid]) {
//                   right = mid - 1;
//               } else {
//                   left = mid + 1;
//               }
//            }
//            else {
//                // mid 到 right 是顺序区间
//                if ((target > nums[mid] && target <= nums[right])){
//                    left = mid + 1;
//                } else {
//                    right = mid - 1;
//                }
//            }
//        }
//        return -1;
    }


    /**
     * LCR 128. 库存管理 I
     * 其实就是旋转数组：找最小值问题：旋转数组会形成两个有序数组，最小值一定在右侧的数组中
     * 使用二分法 逐渐逼近右侧数组
     * 如下图所示，寻找旋转数组的最小元素即为寻找 右排序数组 的首个元素 stock[x] ，称 x 为 旋转点 。
     * @param stock
     * @return
     */
    public int inventoryManagement(int[] stock) {
        int left=0,right=stock.length-1;

        while (left<right) {
            int middle = (right - left) / 2 + left;

            if (stock[middle] < stock[right]) {
                //middle落在了右侧数组中, 旋转点则在右侧数组中，搜索右侧边界
                right = middle;
            } else if (stock[middle] > stock[right]) {
//                stock[middle]>stock[right]
                left = middle + 1;
//                即旋转点 x 一定在 [m+1,j] 闭区间内，因此执行 i=m+1；
            } else {
                right--;
            }
        }

        return  stock[left];
    }
}
