package skill;

import java.util.Arrays;

public class FindErrorNums {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(findErrorNums(new int[]{1,2,2,4})));
    }
    /**
     * 把元素对应的索引位置的数字设置成负数，如果有一个元素 已经被设置成了负数，则表示这个元素就是重复的数组
     * 然后在遍历一次数组，找到大于0的元素的下标，就是缺失的数字
     * @param nums
     * @return
     */
    public static int[] findErrorNums(int[] nums) {

        int dummp=0;
        int [] copy=new int[nums.length+1];
        for (int i=0;i<nums.length;i++){
            copy[i+1]=nums[i];
        }
        for (int i=1;i<copy.length;i++){
            // 求num[i] 对应的索引的值是否小于0
            int index=Math.abs(copy[i]);
            if (copy[index]<0){
                dummp=Math.abs(copy[i]);
            }
            else {
                copy[index]*=(-1);
            }
        }

        int missing=0;
        for ( int i=1;i<copy.length;i++){
            if (copy[i]>0){
                missing=i;
            }
        }
        return new int[]{dummp,missing};
    }
}
