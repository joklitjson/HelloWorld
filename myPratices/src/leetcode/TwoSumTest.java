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

        Arrays.sort(numbers);

        int left=0,right=numbers.length-1;
        while (left<right){
            int sum=numbers[left]+numbers[right];
            if (sum==target){
                return new int[]{left,right};
            }
            else if (sum>target){
                int rightValue=numbers[right];
                //可以去除重复元素
                while (numbers[right]==rightValue) {
                    right--;
                }

            }
            else {
                //消除重复元素
                int leftValue=numbers[left];
                while (leftValue==numbers[left]){
                    left++;
                }

            }
        }
        return new int[]{};
    }
}
