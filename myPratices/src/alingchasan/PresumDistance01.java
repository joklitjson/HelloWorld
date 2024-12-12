package alingchasan;

import java.util.*;

/**
 * 前缀和距离操作方便的：
 * 其实就是把数组进行排序，然后把每个数操作n次都等于目标值 taget,就是求蓝色、青色的面积。可以使用前缀和优化
 */
public class PresumDistance01 {

    public static void main(String[] args) {
//        System.out.println(new PresumDistance01().minOperations(new int[]{3,1,6,8},new int[]{1,5}));
//        System.out.println(new PresumDistance01().getSumAbsoluteDifferences3(new int[]{2,3,5}));
//        System.out.println(new PresumDistance01().distance(new int[]{1,3,1,1,2}));

//        System.out.println(new PresumDistance01().maxFrequencyScore(new int[]{1,4,4,2,4},0));

//        System.out.println(new PresumDistance01().largestMagicSquare(new int[][]{{7,1,4,5,6},{2,5,1,6,4},{1,5,4,3,2},{1,2,7,3,4}}));

//        System.out.println(new PresumDistance01().getBiggestThree(new int[][]{{3,4,5,1,3},{3,3,4,2,3},{20,30,200,40,10},{1,5,5,4,1},{4,3,2,2,5}}));

        new PresumDistance01().maxSumTwoNoOverlap(new int[]{3,8,1,3,2,1,8,9,0},3,2);
    }
    /**'
     * 2602. 使数组元素全部相等的最少操作次数
     * 排序数组，然后求前缀和，在循环query，在排序的num中二分查找小于等于target的索引(lowbound)，在求两者的面积
     * @param nums
     * @param queries
     * @return
     */
    public List<Long> minOperations(int[] nums, int[] queries) {
        Arrays.sort(nums);
        int n=nums.length;
        List<Long> result=new ArrayList<>();
        Long preSum[]=new Long[n+1];
        preSum[0]=0L;
        //求前缀和
        for (int i=1;i<=n;i++){
            preSum[i]=preSum[i-1]+nums[i-1];
        }

        for (int i=0;i<queries.length;i++){
            int value=queries[i];
            int idx=lowbound(nums,value);
            long leftOpert=(long)value*idx-preSum[idx];
            long rightOpert=preSum[n]-preSum[idx]-(long)(n-idx)*value;
            //蓝色和青色的和
            result.add(leftOpert+rightOpert);
        }
        return result;
    }

    /**
     * 二分求最左侧二分
     * lowbound:二分查找第一个大于等于num的数字下标         或可以理解成：元素中小于target的元素有几个
     * upbound:二分查找第一个大于num的数字
     * @param nums
     * @param target
     * @return
     */
    private int lowbound(int[] nums,int target){
        int l=0,r= nums.length;;
        while (l<r){
            int middle=l+(r-l)/2;
            if (nums[middle]==target){
                r=middle;
            }
            else if (nums[middle]>target){
                r=middle;
            }
            else {
                l=middle+1;
            }
        }
        return l;
    }

    /**
     * 1685. 有序数组中差绝对值之和
     * 方案一：距离前缀和:分两部分，第一份小于元素的距离绝对值之和，第二分部大于元素的距离绝对值之和的计算
     * 方案二：先求出后缀和，然后前缀合在遍历的过程中求解
     * 方案三：先求出后缀的全部元素和，然后在遍历的过程中 求前缀的所有元素的和，在使用加减法计算
     * @param nums
     * @return
     */
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int[] preSum = new int[n + 1];
        preSum[0] = 0;
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //求左右两部分面积
        for (int i = 0; i < n; i++) {
            int leftSum = nums[i] * (i ) - preSum[i];
            int rightSum = preSum[n] - preSum[i+1] - (n - 1 - i) * nums[i];
            result[i] = leftSum + rightSum;
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }

    /**
     * 使用后缀和求解
     * @param nums
     * @return
     */
    public int[] getSumAbsoluteDifferences2(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int[] afterSum = new int[n+1];//后缀和
//        afterSum[afterSum.length - 1] = 0;
        for (int i = afterSum.length - 2; i >= 0; i--) {
            afterSum[i] = afterSum[i + 1] + nums[i ];//下标大于等于当前i的所有元素的和
        }

        int preSum = 0;
        for (int i = 0; i < n; i++) {
            preSum += nums[i];
            int left = nums[i] * (i+1) - preSum;
            int after = afterSum[i+1]  - (n - 1 - i) * nums[i];//后缀和直接减去后面几个元素
            result[i] = left + after;
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }
    /**
     * 1685. 有序数组中差绝对值之和
     * 方案一：距离前缀和:分两部分，第一份小于元素的距离绝对值之和，第二分部大于元素的距离绝对值之和的计算
     * 方案二：先求出后缀和，然后前缀合在遍历的过程中求解
     * 方案三：先求出后缀的全部元素和，然后在遍历的过程中 求前缀的所有元素的和，在使用加减法计算
     * @param nums
     * @return
     */
    public int[] getSumAbsoluteDifferences3(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        //求全部元素的和
        int preSum = 0, afterSum = 0;
        for (int value : nums) {
            afterSum += value;
        }
        for (int i = 0; i < n; i++) {
            preSum += nums[i];
            int left = nums[i] * (i + 1) - preSum;
            //使用面积法：求后面元素的和，然后在减去下面的面积
            int after = afterSum - preSum - (n - 1 - i) * nums[i];
            result[i] = left + after;
        }
        System.out.println(Arrays.toString(result));
        return result;
    }


    /**
     * 2615. 等值距离和
     * 解决方案：把元素按值进行分组，组内存着元素的下标，然后在遍历元素，进行求元素下标差的绝对值之和(前缀和距离)
     * @param nums
     * @return
     */
    public long[] distance(int[] nums) {

        Map<Integer, List<Integer>> valueToIdxMap = new HashMap<>();
        //优化点：直接求每组的下标和
        Map<Integer, List<Long>> preSumMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (!valueToIdxMap.containsKey(nums[i])) {
                valueToIdxMap.put(nums[i], new ArrayList<>());
                preSumMap.put(nums[i],new ArrayList<>() );
                preSumMap.get(nums[i]).add(0L);
            }
//            else {
                List<Long> pre= preSumMap.get(nums[i]);
                pre.add( pre.get(pre.size()-1)+i);//计算当前下标的前缀和
//            }
            //把相同的元素的下标放在一起
            valueToIdxMap.get(nums[i]).add(i);


        }
        long[] result = new long[nums.length];

        //进行求相同值的元素的下标的差
        for ( Map.Entry<Integer, List<Integer>> entry:valueToIdxMap.entrySet()){
            List<Integer> value= entry.getValue();
            List<Long> preSum= preSumMap.get(entry.getKey());
            //遍历元素
            int m=value.size();
            for (int i=0;i<m;i++){
                int target=value.get(i);
                long left=target*i-preSum.get(i);//蓝色面积
                long right=preSum.get(preSum.size()-1)-preSum.get(i)-(long)(m-i)*target;
                result[target]=left+right;
            }
        }
//       System.out.println(Arrays.toString(result));
        return result;
    }


    /**
     * 二分查找
     * @param list
     * @param j
     * @return
     */
    private int getTarget(List<Integer> list,int j) {
        int l = 0, right = list.size() - 1;

        while (l <= right) {
            int idx = l + (right - l) / 2;
            int middle = list.get(idx);
            if (middle == j) {
                return idx;
            } else if (j < middle) {
                right = middle - 1;
            } else {
                l = middle + 1;
            }
        }
        return l;
    }

    /**
     * 2968. 执行操作使频率分数最大
     * 排序+贪心算法+滑动窗口:中位数贪心，最优做法是把子数组内的元素都变成子数组的中位数，操作次数如果超过 k，就必须移动左端点。
     * @param nums
     * @param k
     * @return
     */
    public int maxFrequencyScore(int[] nums, long k) {
        Arrays.sort(nums);
        int n = nums.length;
        long preSum[] = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        int left = 0, right = 0;
        int ans = 0;
        //滑动窗口
        for (; right < n; right++) {
            //操作次数大于k的话 则缩短左边界
            while (distanceSum(preSum, nums, left, (right + left) / 2, right) > k) {
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


    /**
     * 子数组内的元素都变成中心元素的操作次数
     * @return
     */
    private long distanceSum( long preSum[],int[] nums,int l,int m,int r) {
        int value = nums[m];
        if (l==6||r==6){
            System.out.println("");
        }
        long left = (long) value * (m - l) - (preSum[m] - preSum[l]);//蓝色距离
        long right = preSum[r + 1] - preSum[m + 1] - (long) nums[m] * (r - m);//青色距离
        return left + right;
    }


    /**
     * 1895. 最大的幻方
     * 解决方案：先预处理每行、每列的前缀和，然后在遍历最大的边，在遍历二维数组的点，作为左上角，在使用边长作为计算右下角的订单，在check这个正方形
     * 中的每行和每列、对角线的和是否相同
     * @param grid
     * @return
     */
    public int largestMagicSquare(int[][] grid) {

        int m = grid.length, n = grid[0].length;
        long[][] preRowSum = new long[m][n + 1];
        for (int i = 0; i < m; i++) {
            int[] row = grid[i];
            long sum = 0;
            for (int j = 0; j < n; j++) {
                //求前缀和:第一种写法
                preRowSum[i][j + 1] = preRowSum[i][j] + row[j];
                //第二种写法
//                sum+=row[j];
//                preRowSum[i][j+1]=sum;
            }
        }

        long[][] preColomnSum = new long[n][m + 1];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < m; j++) {
                sum += grid[j][i];
                preColomnSum[i][j + 1] = sum;
            }
        }

        for (int edge = Math.min(m, n); edge >= 1; edge--) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {

                    if (i==1&&j==1&&edge==2){
                        System.out.println(11);
                    }

                    //求另一个点
                    if (i + edge > m || j + edge > n) {
//                        判断是否越界
                        continue;
                    }

                    //因为边长是edge，但是i、j这个点已经占用了一个边长，因此目标的点位就是i+edage-1了
                    int xx = i + edge-1, yy = j + edge-1;
                    //check

                    //求第一行的和
                    long sum = preRowSum[i][yy + 1] - preRowSum[i][j];
                    boolean ok=true;
                    //遍历每一行
                    for (int k = i + 1; k <= xx; k++) {
                        long subRowSum = preRowSum[k][yy + 1] - preRowSum[k][j];
                        if (subRowSum != sum) {
                            ok=false;
                            break;
                        }
                    }
                    if (!ok){
                        continue;
                    }

                    //遍历每一列
                    for (int p = j; p <= yy; p++) {
                        long subColumnSum = preColomnSum[p][xx + 1] - preColomnSum[p][i];
                        if (subColumnSum != sum) {
                            ok=false;
                            break;
                        }
                    }
                    if (!ok){
                        continue;
                    }

                    //判断对角线
                    long d1 = 0, d2 = 0;
                    for (int q = 0; q < edge; q++) {
                        d1 += grid[i + q][j + q];//左上角到右下角的对角线
                        d2 += grid[xx - q][j + q];//左下角到右上角的对角线
                    }
                    if (d1 != sum || d2 != sum) {
                        continue;
                    }
                    return edge;
                }
            }
        }
        return 1;
    }

    /**
     * 1878. 矩阵中最大的三个菱形和
     * 解决方案：先求出 正斜向前缀和，和反斜向前缀和，然后在遍历矩形中的点，以当前点为矩形的中心点，在遍历长度k，求四个点的坐标点，然后在利用前缀和快速求出每个
     * 菱形的最大面积，然后在保持下来
     * @param grid
     * @return
     */

    private int x, y, z; // 最大，严格次大，严格第三大
    public int[] getBiggestThree(int[][] grid) {
        x=y=z=0;
        int m = grid.length;
        int n = grid[0].length;

        //求左上角到右下角的前缀和
        int[][] sum1 = new int[m + 1][n + 1];
        int[][] sum2 = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum1[i + 1][j + 1] = sum1[i][j] + grid[i][j];
                //右上到左下角
                sum2[i + 1][j] = sum2[i][j + 1] + grid[i][j]; // ↙
            }
        }

        for (int [] row:sum2) {
            System.out.println(Arrays.toString(row));
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                //以[i,j] 为中心，计算菱形的四个顶点
                update(grid[i][j]);//把当前点作为一个菱形
                for (int k = 0; i - k >= 0 && i + k < m && j - k >= 0 && j + k < n; k++) {
                    if (i==2&&j==1&&k==1){
                        System.out.println(";;;;;;;;;;");
                    }
                    int a = getDiagonal(sum1, i - k, j, k);//右上的边
                    int b = getDiagonal(sum1, i, j - k, k);//左下的边
                    int c = getAntDiagonal(sum2, i-k+1, j - 1, k-1);
                    int d = getAntDiagonal(sum2, i , j+k, k+1);
                    update(a + b + c + d);
                }
            }
        }
        //判断是否小于三个
        if (z == 0) return y == 0 ? new int[]{x} : new int[]{x, y};

        return new int[]{x, y, z};
    }
    //从左上到右下边长为k的点的和
    private int getDiagonal(int[][] sum,int x,int y,int k){
        return sum[x+k][y+k]-sum[x][y];
    }

    // 从 (x,y) 开始，向 ↙，连续的 k 个数的和
    private int getAntDiagonal(int[][] sum,int x,int y,int k){
        return sum[x + k][y + 1 - k] - sum[x][y + 1];
    }

    /**
     * 更新 前三个最大值:就是逐个和前三大元素进行比较，看看能顶替哪个位置
     * @param val
     */
    private void update(int val){
        if (val>x){
            z=y;
            y=x;
            x=val;
        }
        else if (val>y&&val<x){
            z=y;
            y=val;
        }
        else if (val>z&&val<y){
            z=val;
        }
    }

    /**
     * 1031. 两个非重叠子数组的最大和
     * 解决方案：对于两个变量的题目 通常都是枚举一个变量，然后求左侧 已经枚举元素的最大或者最小值。这和我们的两数之和==target 很类似，左边使用一个map维护 需要找的元素
     * 此题：我们使用 maxA 记录左边子数组的最大值，然后在加上当前子数组进行比较
     * 总共有两种情况 左a右b、左b右a 分别求最大值，在进行比较
     * @param nums
     * @param firstLen
     * @param secondLen
     * @return
     */
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int n=nums.length,sum=0;
        int [] preSum=new int[n+1];
        for (int i=0;i<n;i++){
            //求前缀和的第二种写法
            sum+=nums[i];
            preSum[i+1]=sum;
        }
//        int maxSum=Math.max(getMaxSum(preSum,firstLen,secondLen),getMaxSum(preSum,secondLen,firstLen));
//        return maxSum;
        //方案二：优化可以使用两个变量：同时维护 左侧长度为first、second的最大值
        int maxA=0,maxB=0,maxSum=0;
        for (int i=firstLen+secondLen;i< preSum.length;i++){
            maxA=Math.max(maxA,preSum[i-secondLen]-preSum[i-secondLen-firstLen]);
            maxB=Math.max(maxB,preSum[i-firstLen]-preSum[i-secondLen-firstLen]);//左侧长度为second的最大和
            //在进行比较大小
            maxSum=Math.max(maxSum,Math.max(maxA+preSum[i]-preSum[i-secondLen],maxB+preSum[i]-preSum[i-firstLen]));
        }
        return maxSum;
    }
    private int getMaxSum(  int [] preSum,int firstLen, int secondLen){
        //假设firstLen在左边，记录左边的最大和
        int maxA=0;
        int maxSum=0;//记录总体最大和
        for (int j=firstLen+secondLen;j< preSum.length;j++){
            maxA=Math.max(maxA,preSum[j-secondLen]-preSum[j-firstLen-secondLen]);
            //判断上一个最大值 和当前a的最大值 和
            maxSum=Math.max(maxSum,maxA+preSum[j]-preSum[j-secondLen]);
        }
        return maxSum;
    }
}
