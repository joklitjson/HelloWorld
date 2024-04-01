package leetcode;

import java.util.ArrayList;
import java.util.List;

public class GoodIndex {
    public static void main(String[] args) {
        System.out.println(goodIndices(new int[]{2,1,1,1,3,4,1},2));
    }

    /**
     * 前后缀分解，我们考虑下标
     * 是否满足条件，如果其满足条件，则说明
     * 左边前
     * 个元素是非递增的，右边前
     * 个元素是非递减的，我们可以定义两个数组
     * 表示以
     * 结尾的最长递减子数组的长度，
     * 表示以
     * 开头的最长递增子数组的长度，则必须要有
     * 预处理一下数组即可。
     * @param nums
     * @param k
     * @return
     */
    public  static List<Integer> goodIndices(int[] nums, int k) {
        List<Integer> decList=new ArrayList<>();
        List<Integer> incList=new ArrayList<>();
        List<Integer> ans=new ArrayList<>();
        int n= nums.length;;
        //1、预处理数组
        for (int value:nums){
            decList.add(1);
            incList.add(1);
        }

        //2、遍历元素，从左到右设置递减的最大长度

        for (int i=1;i<n;i++){
            if (nums[i]<=nums[i-1]){
                //设置递增的 数量
                decList.set(i,decList.get(i-1)+1);
            }
        }

        //设置
        for (int i=n-2;i>=0;i--) {
            if (nums[i] <= nums[i + 1]) {
                incList.set(i, incList.get(i + 1) + 1);
            }
        }
        for (int i=k;i<n-k;i++){
            //判断下表1左右两边的递增或递减 个数是否大于k
            if (incList.get(i+1)>=k&&decList.get(i-1)>=k){
                ans.add(i);
            }
        }
        return ans;
    }
}
