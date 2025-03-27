package alingchasan.dp;

/**
 * 动态规划
 * 1、爬楼梯 当前问题：只和前面的两个记录相关。
 * 类似的题目 还有菲薄切那数列
 */
public class DynamicProgram {

    public static void main(String[] args) {
        DynamicProgram dynamicProgram=new DynamicProgram();

        System.out.println("deleteAndEarn===="+dynamicProgram.deleteAndEarn(new int[]{1}));
    }

    /**
     * 740. 删除并获得点数
     * 分析：删除一个数后：其前面一个和后面一个都需要删除，可总结成相邻的元素不能同时被选择，问题转化成打家劫舍问题
     * 在【值域】上 进行打家劫舍（如果值域 比较大，不能创建数组怎么办呢？ ）
     * @param nums
     * @return
     */
    public int deleteAndEarn(int[] nums) {
        // 获取最大值：把元素的值 映射到对应的index

        int max = 0;
        for (int value : nums) {
            max = Math.max(max, value);
        }
        int[] values = new int[max + 1];
        //然后在进行累加：表示表示下标=x的元素 都能被获得点数
        for (int value : nums) {
            values[value] += value;
        }
        return dp(values);
    }

    /**
     * 进行 dp 搜索
     * @param values
     * @return
     */
    private int dp(int[] values ) {
        int n = values.length;

        //为了下面的错位相加（所以这里的长度+2了）
        int[] dpValue = new int[n  + 2];

        for (int i = 0; i <n; i++) {
            //计算当前最大值：当前元素 不删除删除、删除状态 下的最大值
            dpValue[i+2] = Math.max(dpValue[i + 1], dpValue[i] + values[i]);
        }
        //获取最后一个值
        return dpValue[n+1];
    }
}
