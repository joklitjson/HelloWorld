package array;

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
}
