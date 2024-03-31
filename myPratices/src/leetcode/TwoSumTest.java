package leetcode;

import java.util.Arrays;

public class TwoSumTest {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum1(new int []{2,3,4},6)));
    }
//    public int[] twoSum(int[] nums, int target) {
//
//    }

    /**
     * 双指针不行，除非数组是有序数组
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] numbers, int target) {
        int left=0,right=numbers.length-1;
        while (left<right){
            int sum=numbers[left]+numbers[right];
            if (sum==target){
                return new int[]{left,right};
            }
            else if (sum>target){
                right--;
            }
            else {
                left++;
            }
        }
        return new int[]{};
    }
}
