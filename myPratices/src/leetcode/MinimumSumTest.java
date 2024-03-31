package leetcode;

public class MinimumSumTest {
    public static void main(String[] args) {
        System.out.println(minimumSum(new int[]{5,4,8,7,10,2}));
    }
    //**
    public  static int minimumSum(int[] nums) {

        int [] preNums=new int[nums.length];
        int []sufNums=new int[nums.length];
        preNums[0]=nums[0];
        sufNums[nums.length-1]=nums[nums.length-1];

        //1、构建 前后缀 数组
        for (int i = 1; i < nums.length ; i++) {
            //当前元素和其上一个元素的最小前追比较
            preNums[i]=Math.min(nums[i],preNums[i-1]);
        }

        for (int j= nums.length-2;j>=0;j--){
            sufNums[j]=Math.min(nums[j],sufNums[j+1]);
        }


        //遍历判断
        int anx=Integer.MAX_VALUE;
        for (int i=1;i< nums.length-1;i++){
            if(nums[i] > Math.max(preNums[i-1],sufNums[i+1])) {
                anx = Math.min(anx, preNums[i-1] + nums[i] + sufNums[i+1]);
            }
        }
        return anx==Integer.MAX_VALUE?-1:anx;
    }
}
