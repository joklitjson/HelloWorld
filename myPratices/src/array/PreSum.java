package array;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀和问题
 */
public class PreSum {


    int [] preSum;

//    public NumArray(int[] nums) {
//        //长度是n+1，因为后面的sumRange都是从1开始的
//        preSum=new int[nums.length+1];
//        preSum[0]=0;
//        for (int i=1;i< preSum.length;i++){
//            preSum[i]=preSum[i-1]+nums[i-1];//前面的和在加上他自己
//        }
//    }

    public int sumRange(int left, int right) {
        // nums[i]的值被加在了preSum[i+1]数组上了，因此计算[left,right] 两个闭区间时，要都包含在内
        //num[right]-->preNum[right+1]
//        num[left]-->preSum[left+1]包含num[left]的值的，如果直接相加则失去了num[left]的值，因此需要减去preSUm[left]
        return preSum[right+1]-preSum[left];
    }

    //二位数组的前缀和问题
    int[][] preMatrixSum;
//    public NumMatrix(int[][] matrix) {
//        int m=matrix.length,n=matrix[0].length;
//        preMatrixSum=new int[m+1][n+1];
//
//        for (int i=1;i<=m;i++){
//            for (int j=1;j<=n;j++){
//                //统计前缀和
//                preMatrixSum[i][j]=preMatrixSum[i-1][j]+preMatrixSum[i][j-1]-
//                        preMatrixSum[i-1][j-1]+matrix[i-1][j-1];
//            }
//        }
//    }

    public int sumRegion(int x1, int y1, int x2, int y2) {
        //类比推理，都是x2、y2的顶点+1
        int sum=preMatrixSum[x2+1][y2+1]-preMatrixSum[x1][y2+1]-
                preMatrixSum[x2+1][y1]+preMatrixSum[x1][y1];
        return  sum;
    }


    /**
     * 和为k的子数组
     * 方案：使用hash化前缀和，hash+前缀和
     * 核心方案就是 sumi -sumj=k（i>j）,
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        map.put(0, 1);
        int sum = 0;
        for (int value : nums) {
            sum += value;
            if (map.containsKey(sum - k)) {
                ans += map.get(sum - k);
            }
            //增加和为sum的元素的个数
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }


    /**
     * LCR 012. 寻找数组的中心下标
     * 寻找中心数组
     * @param nums
     * @return
     */
    public static int pivotIndex(int[] nums) {
        int n = nums.length;
        int[] preSum = new int[n + 1];
        preSum[0] = 0;//估计多一个长度，方便存储第一个数字
        for (int i = 1; i <= nums.length; i++) {
//            pre[i]代表0~i-1元素之和
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        for (int i = 0; i < nums.length; i++) {
            int pre = preSum[i];
            int afterSum = preSum[n] - preSum[i+1];
            if (pre == afterSum) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(pivotIndex(new int[]{1,7,3,6,5,6}));
    }


    /**
     * LCR 011. 连续数组
     * 思想转换：把数组中的0想象成-1,0和1的数量相等，则等价为 子数组中的元素的和是0，因此转变了了前缀和数组+hash的方式
     * 则原问题转换成「求最长的连续子数组，其元素和为 0」。
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        int maxLength = 0;
        Map<Integer, Integer> numIndex = new HashMap<>();
        int count = 0;//当前索引下的和
        numIndex.put(count, -1);

        //核心思想就是在过去的区间中查找和是否 存在和为count的下标
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                count--;
            }
            //过去遍历的区间k中 有和为count的数字，sum(0,k)=sum(0,i)=count，因此i-k 区间可以算是一个连续子数组,他们的和是0
            if (numIndex.containsKey(count)) {
                int preIndex = numIndex.get(count);
                maxLength = Math.max(maxLength, (i - preIndex));
            } else {
                //记录这个和的索引
                numIndex.put(count, i);
            }
        }
        return maxLength;
    }
}
