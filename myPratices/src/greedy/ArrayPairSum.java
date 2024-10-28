package greedy;

import java.util.Arrays;

public class ArrayPairSum {

    /**
     * 把数组分成n组，求 min(a,b) 和的最大值
     * 一句话，我们每次选择都想选个大的，但是最大的不能选（因为 min），所以每次选第二大的。
     * 因此需要先排序，然后选择每组中的第二大的数字
     * @param nums
     * @return
     */
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);

        int sum=0;
        for (int i=0;i<nums.length;i+=2){
            sum+=nums[i];
        }

        return sum;
    }
}
