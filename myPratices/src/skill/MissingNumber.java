package skill;

public class MissingNumber {

    public static void main(String[] args) {
        System.out.println(new MissingNumber().missingNumber(new int[]{3,0,1}));
        System.out.println(new MissingNumber().missingNumber2(new int[]{3,0,1}));
        System.out.println(new MissingNumber().missingNumber3(new int[]{3,0,1}));
    }

    int missingNumber(int[] nums) {
        int n = nums.length;
        int res = 0;
        //先和索引n进行亦或
        res ^= n;

        // 和其他的元素、索引做异或
        for (int i = 0; i < n; i++) {
            res ^= i ^ nums[i];
        }
        return res;
    }

    int missingNumber2(int[] nums) {
        int n = nums.length;
        // 公式：(首项 + 末项) * 项数 / 2 （可能存在整型溢出）
        int espect=(0+n)*(n+1)/2;

        int miss=espect;
        for (int i = 0; i < n; i++) {
            //减去已经在的
            miss-= nums[i];
        }
        return miss;
    }

    /**
     * 由于 等差数列 先计算和 可能存在溢出的情况，因此我们可以在循环中 一边加索引在一边减去值
     * @param nums
     * @return
     */
    int missingNumber3(int[] nums) {
        int n = nums.length;
        int res = 0;
        res = n - 0;//补一位，先把索引n加进去

        //其实求的就是索引的和减去值的和，缺少的就是元素
        for (int i = 0; i < nums.length; i++) {
            res += i - nums[i];
        }
        return res;
    }

}
