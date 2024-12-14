package alingchasan;

import java.util.*;

/**
 *
 * 性质 1：从左到右累加 d 中的元素，可以得到数组 a。
 *
 * 性质 2：如下两个操作是等价的。
 *
 * 把 a 的子数组 a[i],a[i+1],…,a[j] 都加上 x。
 * 把 d[i] 增加 x，把 d[j+1] 减少 x。
 * 利用性质 2，我们只需要 O(1) 的时间就可以完成对 a 的子数组的操作。最后利用性质 1 从差分数组复原出数组 a。
 *
 * 注：也可以这样理解，d[i] 表示把下标 ≥i 的数都加上 d[i]。
 */
public class APreSum {

    public static void main(String[] args) {
//        System.out.println(numberOfSubarrays(new int[]{1,1,2,1,1},3));
//        System.out.println(new APreSum().maxPower(new int[]{1,2,4,5,0},1,2));
        System.out.println(new APreSum().maxPower2(new int[]{1,2,4,5,0},1,2));

        int [] arrr=new int[]{0,1,2,3,4,5,6};
        int [] dd=new int[7];
        //1111、对 0~3+1
        dd[0]+=1;
        dd[4]-=1;

        //1111、对 2~5 +1
        dd[2]+=1;
        dd[6]-=1;

        int [] rawArr=new int[7];//还原数组
        //******************方案一
        int sum=0;//设置一个变量 进行累加
        for (int i=0;i<arrr.length;i++){
            sum+=dd[i];
            rawArr[i]=sum;
        }
        System.out.println(Arrays.toString(rawArr));

        //******************方案二
        rawArr[0]=dd[0];//设置第一个
        for (int i=1;i<arrr.length;i++){
            //当前差分+前一个被还原的数字
            rawArr[i]=dd[i]+rawArr[i-1];
        }
//        System.out.println(Arrays.toString(rawArr));

//        System.out.println("waysToSplit==="+waysToSplit(new int[]{1,2,2,2,5,0}));
//        System.out.println("waysToSplit==="+waysToSplit2(new int[]{1,2,2,2,5,0}));
        System.out.println("sumOfFlooredPairs==="+sumOfFlooredPairs(new int[]{2,5,9}));
        System.out.println("maxTrailingZeros"+maxTrailingZeros(new int[][]{{23,17,15,3,20},{8,1,20,27,11},{9,4,6,2,21},{40,9,1,10,6},{22,7,4,5,3}}));
        System.out.println("maxTrailingZeros"+maxTrailingZeros2(new int[][]{{23,17,15,3,20},{8,1,20,27,11},{9,4,6,2,21},{40,9,1,10,6},{22,7,4,5,3}}));

        System.out.println("maxSumSubmatrix==========="+maxSumSubmatrix(new int[][]{{2,2,-1}},2));

//        System.out.println("matrixBlockSu======="+matrixBlockSum(new int[][]{{1,2,3},{4,5,6},{7,8,9}},1));
        System.out.println("countSubmatrices==="+countSubmatrices(new int[][]{{7,6,3},{6,6,1}},18));

//        kthLargestValue(new int[][]{{5,2},{1,6}},1);
        System.out.println("maxSideLength========="+maxSideLength(new int[][]{{1,1,3,2,4,3,2},{1,1,3,2,4,3,2},{1,1,3,2,4,3,2}},4));

        System.out.println("countSquares============"+countSquares(new int[][]{
                {0,1,1,1},
                {1,1,1,1},
                {0,1,1,1}
        }));

        System.out.println("numSubmat==="+numSubmat(new int[][]{{1,0,1},{1,1,0},{1,1,0}}));
//        System.out.println(lowerBound(new int[]{1,4,5,5,9},0,5,5));
//        System.out.println(upperBound(new int[]{1,4,5,5,9},0,5,5));
        // 2~5+1
    }
    /**
     * 1248. 统计「优美子数组」
     * 个连续子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     *
     * 请返回这个数组中 「优美子数组」 的数目。
     * 解决方案：定义一个前缀奇数和的数组，然后向右遍历，维护左边的索引
     * @param nums
     * @param k
     * @return
     */
    public static int numberOfSubarrays(int[] nums, int k) {

        // 数组 prefixCnt 的下标是前缀和（即当前奇数的个数），值是前缀和的个数。
        int[] prefixCnt = new int[nums.length + 1];
        prefixCnt[0]=1;//
        int evenCnt=0;
        int ans=0;
        for (int i=0;i<nums.length;i++){
            if (nums[i]%2==1){
                evenCnt++;
            }
            prefixCnt[evenCnt]++;//记录左侧前缀和等于 enentCnt的子数组的个数
            if (evenCnt >= k) {
                ans += prefixCnt[evenCnt - k];
            }
        }
        return ans;
    }


    /**
     * 2528. 最大化城市的最小电量
     * 就是求创建n个发电站后 最小的发电量：使用二分法，
     * 给定每个城市的最小电量 limit，求最小的新建发电站的数目，看看是否能满足k个发电站的大小，如果limit 能满足，则继续增大limit
     * @param stations
     * @param r
     * @param k
     * @return
     */
    public long maxPower(int[] stations, int r, int k) {

        long left = 1, right = Integer.MAX_VALUE;
        long ans = 0;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (checkLimit(stations, r, k, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 优化：先计算出来每个城市的电量：使用前缀和，然后在使用二分法进行试探
     * check 函数中使用差分数组计算 对每个数组的增减量
     * @param stations
     * @param r
     * @param k
     * @return
     */
    public long maxPower2(int[] stations, int r, int k) {
        int n = stations.length;
        long sum[] = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + stations[i];
        }

        //存储每个发电站的电量
        long[] power = new long[n];
        long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            //每个发电站的i的发电量是他的 从[i-r,i+r] 发电站的所有的和，因此问题转换成子数组的和：可以使用前缀和求解
            power[i] = sum[Math.min(i + r + 1, n)] - sum[Math.max(i - r, 0)];//这就是右边界 -左边界
            min = Math.min(min, power[i]);
        }
        System.out.println(Arrays.toString(power));
        //最大值不会超过min+k+1的
        long left = min, right = min + k + 1;
        long ans = left;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (checkPowerLimit(power, r, k, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 总结：当前方法的特点是 需求是需要一遍遍历一遍更新数组中部分子数组的值，因此创建了一个临时差分数组differ
     *同时还创建了一个sum ，用于还原差分数组的值的，然后在加上原数组的值 就计算出了正确的当前值
     *  long differ[]=new long[n+1];//使用临时数组计算对数组power的差分操作
     *     long sum=0;//前面增加的电站:滚动的统计前面对差分数组的增减和
     *         for (int i=0;i<n;i++){
     *             sum+=differ[i];//计算当前电站的影响电量
     *              if (limit>sum+power[i]){
     *                 //更新这一段的下一个差分值
     *                 if (i+2*r+1<n){
     *                     differ[i+2*r+1]-=m;
     *                 }
     *             }
     *         }
     *         return true;
     * @param power
     * @param r
     * @param k
     * @param limit
     * @return
     */
    private boolean checkPowerLimit(long[] power, int r, int k,long limit) {
        int n=power.length;
        long differ[]=new long[n+1];//使用临时数组计算对数组power的差分操作
        int need=0;//使用的发电站的量
        long sum=0;//前面增加的电站:滚动的统计前面对差分数组的增减和
        for (int i=0;i<n;i++){

            sum+=differ[i];//计算当前电站的影响电量
//            sum+power[i]:表示当前电站的实际电量
            //当前电站的电量不够
            if (limit>sum+power[i]){
                long m=limit-sum-power[i];
                need+=m;
                //提前判断 需要的发电站超过了 可以建造的
                if (need>k){
                    return false;
                }

                //因此需要建立发电站：建立的位置就在 i+r 位置处，因此可以增加发电的区间是[i,i+2*r],所以需要再 差分数组：i+2*r+1处减去m
                sum+=m;//增加发电的影响
                //更新这一段的下一个差分值
                if (i+2*r+1<n){
                    differ[i+2*r+1]-=m;
                }
            }
        }
        return true;
    }

    /**
     * 判断增加k个充电站的情况下最大供电量是否都大于limit
     * @param raw
     * @param r
     * @param k
     * @param limit
     * @return
     */
    private boolean checkLimit(int[] raw, int r, int k,long limit) {
        int[] stations = raw.clone();
        long cnt = 0;//记录已经使用的发电站数量
        long sum = 0;//记录每个发电站的发电量
        for (int i = 0, j = 0; i < stations.length; i++) {

            //统计区间内的发电量
            while (j < stations.length && j - i <= r) {
                sum += stations[j];
                j++;
            }
            //当i向右滚动时，移除左侧的那个发电站量
            if (i - r - 1 >= 0) {
                sum -= stations[i - r - 1];
            }

            //当前发电站的发电量小于目标值
            if (sum < limit) {
                // j - 1 就是最右侧可以新建变电站且能补充当前城市电量的位置？为啥需要再右侧？因为左侧都满足了，所以建立在右侧
                stations[j - 1] += limit - sum;//

                cnt += limit - sum;//增加使用的发电站

                //已经使用的发电站 超过了最大发电站
                if (cnt > k) {
                    return false;
                }
                // 新建了变电站，区间和 sum = val,即当前区间满足 limit了
                sum= (int) limit;
            }
        }
        return true;
    }


    /**
     * 1712. 将数组分成三个子数组的方案数
     * 第一刀的起始位置可以是0，但是其最终的位置 不能大于sum(arr)/3，否则后面的两个数字就不可能都大于他了
     * 第二刀的位置：起始位置是当mid的和等于left的和，终止位置是mid、right的中间位置，不能超过left右边的所有元素和的一半的位置
     * 因此我们使用前缀和+二分法
     * @param nums
     * @return
     */
    public static int waysToSplit(int[] nums) {

        int n = nums.length;
        int[] preSum = new int[n + 1];
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += nums[i];
            preSum[i + 1] = sum;
        }
        final int MOD = 1000000000 + 7;

        if(sum == 0){  // 特判, nums中全为0的情况
            return (int)((long)(n-1) *(n-2) / 2 % MOD);
        }

        long  ans = 0;
        int i = 1;
        int t=sum/3;
        for (; i <= n && preSum[i] <= t; i++) {

            //查找最左侧下标
            int l = lowerBound(preSum, i+1, n + 1, 2 * preSum[i]);

            //查找最右侧下标
            int r = upperBound(preSum, i+1, n + 1, preSum[i]+(preSum[n] - preSum[i]) / 2);
            //统计这个区间的个数:上面都是使用的左闭右开区间的写法，因此这里直接相减就行了
            if (r>=l){
                ans += r - l;
            }

        }

        return (int) (ans%MOD);
    }

    /**
     * 可以使用三指针
     *
     * 三指针的可行性：
     * 三指针i,l,r定义：i 表示第一刀的位置，枚举第一刀的位置，计算第二刀的可选位置数j属于[l,r），这样三个子数组:nums left=[0,i] mid=[i,j] right=[j,n]
     * 三指针要求：
     * 右边大于中间 Sum(right) >= Sum(mid)， 即preSum[n] - preSum[r] >= preSum[r] - preSum[i]
     * 中间大于左边 Sum(mid) >= Sum(left)，即preSum[l] - preSum[i] < preSum[i]
     * 三指针单调性：根据三指针要求，我们可以看出，随着i变大，l与r一定只变大不变小，所以每次迭代i，可以从i-1对应的l,r向右查找本次的l,r，将定位l,r的时间从O(n)变成O(1)
     * 时间复杂度分析：三指针对于preSum前缀和数组每个指针只遍历一次，所以总时间复杂度为O(n)
     * 备注：也可以外层i,内层二分查找前缀和数组确定l,r，时间复杂度为O(nlgn)
     s
     * 因为整个前缀和数组是单调性的
     * @param nums
     * @return
     */
    public  static int waysToSplit2(int[] nums) {
        int n = nums.length;
        int[] preSum = new int[n + 1];
        int sum = 0;
        long  ans = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            preSum[i + 1] = sum;
        }
        final int MOD = 1000000000 + 7;
        // |______|________|_______|________|
        // 1      i        l       r        n
        // i 表示第一刀的位置，枚举第一刀的位置，计算第二刀的可选位置数
        for (int i=1,l=2,r=2;i<n;i++){
            l=Math.max(l,i+1);
            r=Math.max(r,i+1);

            //为啥要先遍历r？原因就是遍历r的同时 不需要考虑l的位置在哪里，其实就是在找r的最右边的位置，类似二分upperBound
            // sum(right) >= sum(mid)，r最大为n-1，right保证要有一个数
            while (r<n&&preSum[n]-preSum[r]>=preSum[r]-preSum[i]){
                r++;
            }

            // // sum(mid) >= sum(left)
            while (l<n&&preSum[l]-preSum[i]<preSum[i]){
                l++;
            }
            if (l<r){
                ans+=r-l;
            }
        }

        return (int) (ans%MOD);
    }

    private static int lowerBound( int [] arr,int left,int right,int target) {
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (arr[middle] == target) {
                right = middle;
            } else if (arr[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    private static int upperBound( int [] arr,int left,int right,int target) {
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (arr[middle] == target) {
                left = middle+1;
            } else if (arr[middle] < target) {
                left = middle+1;
            } else {
                right = middle;
            }
        }
        return left;
    }


    /**
     * 1862. 向下取整数对和
     * 解决方案：创建一个 统计元素个数的 cnt，然后求最大值下标就是元素的值，value就是元素的个数
     * @param nums
     * @return
     */
    public  static int sumOfFlooredPairs(int[] nums) {
        int mod = 1000000007;
        int max = 0;
        for (int value : nums) {
            max = Math.max(max, value);
        }

        int[] cnt = new int[max+1];

        for (int value : nums) {
            cnt[value]++;
            max = Math.max(max, value);
        }

        //计算其前缀和
        int[] preSum = new int[max+1];//长度和数组长度一致的前缀和
        int count = 0;//统计元素个数的前缀和
        for (int i = 1; i < cnt.length; i++) {
            count += cnt[i];
            preSum[i] = preSum[i-1] +cnt[i];
        }
        long ans = 0;
        //遍历元素

        for (int num = 1; num <=max; num++) {
            if (cnt[num] != 0) {
                //表示 数字num的个数不为0，就是数组中存在这样的数字
                //[i,i*2-1]、[i*2,i*3-1]、[i*3,i*4-1]，区间内的floor函数值都一样
                int d = 1;//i是他的倍数，比如我们求一倍区间的元素个数idx[num,2*num] 求这个区间的元素个数，可以使用前面计算的前缀和
                while (d*num <=max) {
                    int left = d*num-1;//注意：前面前缀和的长度是n，因此 presum[i] 是包含第i个数字的
                    int right = Math.min((d+1)*num-1, max);
                    //数字 num的d倍的元素个数有 (preSum[right] - preSum[left])，因此这些数字的和除以元素商都是d，但是又有cnt[num] 个元素
                    ans +=(long) cnt[num]*(preSum[right] - preSum[left]) * d;//
                    d++;
                }
            }
        }
        return (int) (ans % mod);
    }


    /**
     * 2245. 转角路径的乘积中最多能有几个尾随零
     *     想要尾0，其实就是统计2，5因数个数，​取两者最小值；
     *枚举转角点 (i, j)，并计算转角点上、下、左、右路径所组成的转角路径（仅包含一个直角）中尾随零的数目即可。
     * @param grid
     * @return
     */
    public static int maxTrailingZeros(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] factor2 = new int[1001];//预处理 下标作为num 的数 含有因子2的个数
        int[] factor5 = new int[1001];

        //1、预处理
        for (int i = 1; i <= 1000; i++) {
            if (i % 2 == 0) {
                //他是在i/2的基础上进行+1操作的
                factor2[i] = factor2[i / 2] + 1;
            }

            if (i % 5 == 0) {
                //他是在i/5的基础上进行+1操作的
                factor5[i] = factor5[i / 5] + 1;
            }
        }

        //2、统计每行中每个元素的因子2、5个数的前缀次数和

        int[][] rowFactor2 = new int[m ][n + 1];//每行的前缀因子次数和,只需要列多一行就行了
        int[][] rowFactor5 = new int[m ][n + 1];
        System.out.println("**************元素组******************");
        for (int []row:grid){
            System.out.println(Arrays.toString(row));
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //统计当前行中 到当前点位因子2的个数
                rowFactor2[i ][j + 1] = rowFactor2[i][j] + factor2[grid[i][j]];
                rowFactor5[i ][j + 1] = rowFactor5[i][j] + factor5[grid[i][j]];
            }
        }

        System.out.println("**************每行2的因子个数******************");
        for (int []row:rowFactor2){
            System.out.println(Arrays.toString(row));
        }

        System.out.println("**************每行5的因子个数******************");
        for (int []row:rowFactor5){
            System.out.println(Arrays.toString(row));
        }

        //3、统计每列中因子2、5的个数
        int[][] colFactor2 = new int[n ][m+1 ];
        int[][] colFactor5 = new int[n] [m +1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                colFactor2[i ][j + 1] = colFactor2[i][j] + factor2[grid[j][i]];
                colFactor5[i ][j + 1] = colFactor5[i][j] + factor5[grid[j][i]];
            }
        }

        System.out.println("**************每列2的因子个数******************");
        for (int []column:colFactor2){
            System.out.println(Arrays.toString(column));
        }
        System.out.println("**************每列5的因子个数******************");
        for (int []column:colFactor5){
            System.out.println(Arrays.toString(column));
        }

        //4、遍历每个点位，统计转角中2、5个数的最小值
        int ans = 0;
        for (int i = 0; i <m; i++) {
            for (int j = 0; j <n; j++) {
                if (i==3&&j==2){
                    System.out.println("111");
                }
//                left->upper
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][j] + colFactor2[j][i] + factor2[grid[i][j]],
                                rowFactor5[i][j] + colFactor5[j][i] + factor5[grid[i][j]]));

//                left--->down
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][j] + (colFactor2[j][m] - colFactor2[j][i]),
                                rowFactor5[i][j] + (colFactor5[j][m] - colFactor5[j][i])));

                //right-->upper
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][n] - rowFactor2[i][j] + colFactor2[j][i],
                                rowFactor5[i][n] - rowFactor5[i][j] + colFactor5[j][i]));

                //right-->down
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][n] - rowFactor2[i][j] + colFactor2[j][m] - colFactor2[j][i + 1],
                                rowFactor5[i][n] - rowFactor5[i][j] + colFactor5[j][m] - colFactor5[j][i + 1]));
            }
        }

        return ans;
    }

    public static int maxTrailingZeros2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] factor2 = new int[1001];//预处理 下标作为num 的数 含有因子2的个数
        int[] factor5 = new int[1001];

        //1、预处理
        for (int i = 1; i <= 1000; i++) {
            if (i % 2 == 0) {
                //他是在i/2的基础上进行+1操作的
                factor2[i] = factor2[i / 2] + 1;
            }
            if (i % 5 == 0) {
                //他是在i/5的基础上进行+1操作的
                factor5[i] = factor5[i / 5] + 1;
            }
        }

        //2、统计每行中每个元素的因子2、5个数的前缀次数和

        int[][] rowFactor2 = new int[m ][n + 1];//每行的前缀因子次数和,只需要列多一行就行了
        int[][] rowFactor5 = new int[m ][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //统计当前行中 到当前点位因子2的个数
                rowFactor2[i ][j + 1] = rowFactor2[i][j] + factor2[grid[i][j]];
                rowFactor5[i ][j + 1] = rowFactor5[i][j] + factor5[grid[i][j]];
            }
        }

        //3、统计每列中因子2、5的个数:从上到下遍历,从左到右遍历
        int[][] colFactor2 = new int[m+1 ][n ];//因此只需要多一行就行了，也好理解
        int[][] colFactor5 = new int[m+1] [n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                colFactor2[j + 1][i] = colFactor2[j][i] + factor2[grid[j][i]];
                colFactor5[j + 1][i] = colFactor5[j][i] + factor5[grid[j][i]];
            }
        }

        //4、遍历每个点位，统计转角中2、5个数的最小值
        int ans = 0;
        for (int i = 0; i <m; i++) {
            for (int j = 0; j <n; j++) {
//                left->upper
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][j] + colFactor2[i][j] + factor2[grid[i][j]],
                                rowFactor5[i][j] + colFactor5[i][j] + factor5[grid[i][j]]));

//                left--->down
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][j] + (colFactor2[m][j] - colFactor2[j][i]),
                                rowFactor5[i][j] + (colFactor5[m][j] - colFactor5[j][i])));

                //right-->upper
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][n] - rowFactor2[i][j] + colFactor2[i][j],
                                rowFactor5[i][n] - rowFactor5[i][j] + colFactor5[i][j]));

                //right-->down
                ans = Math.max(ans,
                        Math.min(rowFactor2[i][n] - rowFactor2[i][j] + colFactor2[m][j] - colFactor2[i+1][j],
                                rowFactor5[i][n] - rowFactor5[i][j] + colFactor5[m][j] - colFactor5[i+1][j ]));
            }
        }

        return ans;
    }


    /**
     * 363. 矩形区域不超过 K 的最大数值和
     * 解决方案：使用降维的思想，把二维数组转换成一维数组，然后在进行遍历
     * 二维数组前缀和的时间复杂度是n^2*m^2
     *
     * 优化点
     * 1、什么时候固定列什么时候固定行？谁的长度小固定谁，就是遍历谁，然后在创建一个大的一维数组
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        int m=matrix.length,n=matrix[0].length;

        //我们固定列：把某几个列的元素都向左压缩到左边的一个一维数组总，然后问题转化成了：求一维数组中元素不超过k的最大值
        int max=Integer.MIN_VALUE;
        for (int first=0;first<n;first++){
            int [] sum=new int[m];//
            for (int second=first;second<n;second++){
                for (int i=0;i<m;i++){
                    sum[i]+=matrix[i][second];
                }

                //计算当前子数组的最大值,然后在跟进当前的最大值比较
                max=Math.max(max,near(sum,k));
            }

        }

        return max;
    }

    /**
     * 求一维子数组 连续子数组的和不超过k的最大值
     * 这里使用暴力算法
     *
     * @param sum
     * @param k
     * @return
     */
    private static int near(  int [] sum,int k) {
        int n = sum.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int tmpSum = 0;
            for (int j = i; j < n; j++) {
                tmpSum += sum[j];
                if (tmpSum == k) {
                    return tmpSum;
                } else if (tmpSum < k) {
                    max = Math.max(max, tmpSum);
                }
            }
        }
        return max;
    }

    /**
     * 前缀和+二分法
     * @param matrix
     * @param k
     * @return
     */
    public static int maxSumSubmatrix2(int[][] matrix, int k) {
        int m=matrix.length,n=matrix[0].length;

        //我们固定列：把某几个列的元素都向左压缩到左边的一个一维数组总，然后问题转化成了：求一维数组中元素不超过k的最大值
        int max=Integer.MIN_VALUE;
        for (int first=0;first<n;first++){
            int [] sum=new int[m];//
            for (int second=first;second<n;second++){


                for (int i=0;i<m;i++){
                    sum[i]+=matrix[i][second];
                }
                // 使用TreeSet快速查找到离k最近的数
                TreeSet<Integer> sumSet = new TreeSet<Integer>();
                sumSet.add(0);
                //二分法查找 s - k=x 查找大于等于x的最小值：其实就是二分法中的upperBound方法
//                还是类似之前的枚举右边维护左边思想
                int s = 0;
                for (int v : sum) {
                    s += v;
                    Integer ceil = sumSet.ceiling(s - k);
                    if (ceil != null) {
                        max = Math.max(max, s - ceil);
                    }
                    sumSet.add(s);
                }
            }
        }
        return max;
    }


    /**
     * \2281. 巫师的总力量和
     * 方案：单调栈+前缀和的前缀和
     * 遍历点：求每个点左边和右边的第一个比他小的元素
     * 帮灵神补充两个类似题： 907 和 1856
     * @param strength
     * @return
     */
    public int totalStrength(int[] strength) {
        final int mod = (int) 1e9 + 7;
        int n=strength.length;
        int [] left=new int[n];
        int [] right=new int[n];
        Arrays.fill(right,n);
        Stack<Integer> stack=new Stack<>();
        stack.push(-1); // 哨兵
        //使用单调递增栈
        for (int i=0;i<n;i++){
//            while (!stack.isEmpty()&&strength[i]<=strength[stack.peek()]){
            while (stack.size()>1&&strength[i]<=strength[stack.peek()]){
                //把栈顶元素弹出了，说明i就是当前栈顶元素右边的第一个最小元素
                right[stack.peek()]=i;
                stack.pop();
            }
            //左侧元素都小于他了
//            left[i]=stack.isEmpty()?-1:stack.peek();
            left[i]=stack.peek();;//使用了哨兵模式
            stack.push(i);
        }

        //求前缀和&前缀和的前缀和
        long sum=0L;// 前缀和
        long [] ss=new long[n+2];
        for (int i=1;i<=n;i++){
            sum+=strength[i-1];
            ss[i+1]=(ss[i]+sum)%mod;
        }

        long ans=0;
        for (int i=0;i<n;i++){
            int l=left[i]+1;
            int r=right[i]-1;// [l,r] 左闭右闭
            long total=(long)((i-l+1)*(ss[r+2]-ss[i+1])- (long)(r-i+1)*(ss[i+1]-ss[l]))%mod;
            ans=(ans+strength[i]*total)%mod;
        }
        return (int) ((ans+mod)%mod);//防止算出负数
    }

    /**
     * 1314. 矩阵区域和
     * 二维矩阵区域和 的求法:先构造前缀和，然后在求出每个点 在k的影响下的最小和最大的点，然后求着两个点区间的和
     * 遍历每个店：
     * @param mat
     * @param k
     * @return
     */
    public static int[][] matrixBlockSum(int[][] mat, int k) {

        int m = mat.length, n = mat[0].length;

        int preSum[][] = new int[m + 1][n + 1];
        int ans[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i + 1][j + 1] = preSum[i][j + 1] + preSum[i + 1][j] - preSum[i][j] + mat[i][j];
            }
        }

        //2、遍历点
//        i - k <= r <= i + k,
//                j - k <= c <= j + k 且
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //最最大和最小的点位坐标
                int minRow = Math.max(0, i - k);
                int maxRow = Math.min(m-1, i + k);

                int minCol = Math.max(0, j - k);
                int maxCol = Math.min(n-1, j + k);
//                [minRow,minCol]---[maxRow,maxCol]
                int sum = preSum[maxRow + 1][maxCol + 1] - preSum[maxRow + 1][minCol] - preSum[minRow][maxCol + 1] + preSum[minRow][minCol];
                ans[i][j] = sum;
            }
        }
//        for (int[] row:ans){
//            System.out.println(Arrays.toString(row));
//        }
        return ans;
    }


    /**
     * 3070. 元素和小于等于 k 的子矩阵的数目
     * 给你一个下标从 0 开始的整数矩阵 grid 和一个整数 k。
     *
     * 返回包含 grid 左上角元素、元素和小于或等于 k 的
     * 子矩阵
     * 的数目。
     * @param grid
     * @param k
     * @return
     */
    public static int countSubmatrices(int[][] grid, int k) {

        int m=grid.length,n=grid[0].length;
        int preSum[][]=new int[m+1][n+1];
        int ans=0;
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++) {
                preSum[i + 1][j + 1] = preSum[i][j + 1] + preSum[i + 1][j] - preSum[i][j] + grid[i][j];
                //当前点的和小于等于k 则增加计数
                if (preSum[i + 1][j + 1] <= k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * 方案二：降维处理；把二维的和降到一维，遍历每一行，把每一行的数字压缩到 一个列长的数组中，然后在遍历的同时计算当前的总和是否小于k，
     * 如果小于+1，否则退出，因为都是正数，所以和会越来越大，后面的就没必要遍历了
     * @param grid
     * @param k
     * @return
     */
    public int countSubmatrices2(int[][] grid, int k) {

        int m=grid.length,n=grid[0].length;

        int columnSum[]=new int[n];//列长度的和，主要存储把每一行的数字加到对应的列中，类似压缩的效果，这样就能起到了 累加
        int ans=0;
        for (int row[]:grid){
            int sum=0;
            //遍历row[] 变量中的每一个值
            for (int j=0;j<n;j++){
                columnSum[j]+=row[j];
                sum+=columnSum[j];//累加当前列 到所有和中
                if (sum>k){
                    //后面的都不满足了，不需要在遍历
                    break;
                }
                ans++;
            }
        }
        return ans;
    }

    /**
     * 1738. 找出第 K 大的异或坐标值
     * 方案一：使用二维数组前缀亦或
     * 方案二：使用列前缀亦或，降维处理
     * @param matrix
     * @param k
     * @return
     */
    public static int kthLargestValue(int[][] matrix, int k) {

        int m=matrix.length,n=matrix[0].length;
        int [][] preXOrSum=new int[m+1][n+1];
        List<Integer> list=new ArrayList<>();
        int idx=0;
        int [] arr=new int[m*n];
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                preXOrSum[i+1][j+1]=preXOrSum[i][j+1]^preXOrSum[i+1][j]^preXOrSum[i][j]^matrix[i][j];
                list.add(preXOrSum[i+1][j+1]);
                arr[idx++]=preXOrSum[i+1][j+1];
            }
        }
        Collections.sort(list);
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(list.toArray()));
        System.out.println(list.get(list.size()-k)+"========"+arr[k-1]);
        return list.get(list.size()-k);
    }

    /**
     * 使用列的亦或计算
     * @param matrix
     * @param k
     * @return
     */
    public static int kthLargestValue2(int[][] matrix, int k) {

        int m = matrix.length, n = matrix[0].length;
        int[] xOrSum = new int[n];
        List<Integer> list = new ArrayList<>(m * n);
        for (int row[] : matrix) {
            int tmpXor = 0;
            for (int j = 0; j < n; j++) {
                xOrSum[j] ^= row[j];
                tmpXor ^= xOrSum[j];//累加当前列

                list.add(tmpXor);
            }
        }

        Collections.sort(list);
        return list.get(list.size() - k);
    }

    /**
     * 3212. 统计 X 和 Y 频数相等的子矩阵数量
     * 方案一：使用二维前缀和 统计 x、y的个数，每次在进行判断
     * 方案二：使用列的方式统计前缀次数
     * @param grid
     * @return
     */
    public int numberOfSubmatrices(char[][] grid) {

        int m = grid.length, n = grid[0].length;
        //可以使用三维：第三个维度长度是2，代表x、y元素的个数
        int preCnt[][][] = new int[m + 1][n + 1][2];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //分别计算x、y个数的前缀次数
                preCnt[i + 1][j + 1][0] = preCnt[i + 1][j][0] + preCnt[i][j + 1][0] - preCnt[i][j][0] + (grid[i][j] == 'X' ? 1 : 0);

                preCnt[i + 1][j + 1][1] = preCnt[i + 1][j][1] + preCnt[i][j + 1][1] - preCnt[i][j][1] + (grid[i][j] == 'Y' ? 1 : 0);

                if (preCnt[i + 1][j + 1][0] > 0 && preCnt[i + 1][j + 1][0] == preCnt[i + 1][j + 1][1]) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * 列示计算
     * @param grid
     * @return
     */
    public int numberOfSubmatrices2(char[][] grid) {

        int m = grid.length, n = grid[0].length;
        //可以使用三维：第三个维度长度是2，代表x、y元素的个数
        int preCnt[][]= new int[n ][2];
        int ans = 0;
        for (char[] row:grid) {
            int cntX=0,cntY=0;
            for (int j=0;j<n;j++){
                preCnt[j][0]+=row[j]=='X'?1:0;
                cntX+= preCnt[j][0];

                preCnt[j][1]+=row[j]=='Y'?1:0;
                cntY+= preCnt[j][1];
                if (cntX>0&&cntX==cntY){
                    ans++;
                }
            }
        }
        return ans;
    }


    /**
     * 1292. 元素和小于等于阈值的正方形的最大边长
     * 还是二维前缀和问题,先求前缀和，在倒序遍历min(m,n)，如果某个子矩阵的和小于法治，则返回当前的最大边长
     * @param mat
     * @param threshold
     * @return
     */
    public  static int maxSideLength(int[][] mat, int threshold) {

        int m = mat.length, n = mat[0].length;

        int[][] preSum = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i + 1][j + 1] = preSum[i + 1][j] + preSum[i][j + 1] - preSum[i][j] + mat[i][j];
            }
        }

        //方案一：枚举最大的边，然后在枚举左上角顶点 时间复杂度是m*n*Min(m,n)
//        for (int edge = Math.min(m, n); edge >= 1; edge--) {
//            for (int i = 0; i < m; i++) {
//                for (int j = 0; j < n; j++) {
//                    //把当前点当做正方形的左上角，然后在求正方形的最大面积
//                    int xx = i + edge - 1, yy = j + edge - 1;
//
//                    //越界处理
//                    if (xx >= m || yy >= n) {
//                        continue;
//                    }
//                    //
//                    int sumArea = preSum[xx + 1][yy + 1] - preSum[xx + 1][j] - preSum[i][yy + 1] + preSum[i][j];
//                    if (sumArea <= threshold) {
//                        return edge;
//                    }
//                }
//            }
//        }

//        方案二:使用二分法 处理最大边长 时间复杂度：O(MN∗logmin(M,N))
//        int l = 1, r = Math.min(m, n);
//        while (l <= r) {
//            int middleEdge = l + (r - l) / 2;
//            boolean find = false;
//            for (int i = 0; !find && i < m; i++) {
//                for (int j = 0; j < n; j++) {
//                    //把当前点当做正方形的左上角，然后在求正方形的最大面积
//                    int xx = i + middleEdge - 1, yy = j + middleEdge - 1;
//                    //越界处理
//                    if (xx >= m || yy >= n) {
//                        continue;
//                    }
//                    //
//                    int sumArea = preSum[xx + 1][yy + 1] - preSum[xx + 1][j] - preSum[i][yy + 1] + preSum[i][j];
//                    if (sumArea <= threshold) {
//                        find = true;
//                        break;
//                    }
//                }
//            }
//            if (find) {
//                ans = middleEdge;
//                l = middleEdge + 1;
//            } else {
//                r = middleEdge - 1;
//            }
//        }
        //方案三:外侧遍历顶点坐标，然后内部第三次循环遍历边长，但是有个优化：内部的边长 要在上一次的边长基础上进行+1遍历的，二不是从0开始遍历
        //因为整个矩阵都是正数，因此他们的和从左上角到右下角都是递增的，因此，如果
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //在上一次的基础上递增
                for (int edge = ans + 1; edge <= Math.min(m, n); edge++) {
                    //把当前点当做正方形的左上角，然后在求正方形的最大面积
                    int xx = i + edge - 1, yy = j + edge - 1;
                    //越界处理
                    if (xx >= m || yy >= n) {
                        continue;
                    }
                    int sumArea = preSum[xx + 1][yy + 1] - preSum[xx + 1][j] - preSum[i][yy + 1] + preSum[i][j];
                    if (sumArea <= threshold) {
                        ans = edge;
                    }
                    else {
                        //因为当前边都不满足了，更何况更大的边呢
                        break;
                    }
                }
            }
        }
        return ans;
    }


    /**
     * 方案一：可以使用动态规划：
     * 方案二：使用二维前缀和+二分边长法
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {

        int m = matrix.length, n = matrix[0].length;

        int preSum[][] = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i + 1][j + 1] = preSum[i + 1][j] + preSum[i][j + 1] - preSum[i][j] + (matrix[i][j]=='1'?1:0);
            }
        }

        int l = 0, r = Math.min(m, n), ans = 0;

        while (l <= r) {
            //二分判断中间 边长
            int middle = l + (r - l) / 2;
            boolean find = false;
            for (int i = 0; !find && i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int xx = i + middle - 1, yy = j + middle - 1;
                    if (xx >= m || yy >= n) {
                        continue;
                    }
                    int area = preSum[xx + 1][yy + 1] - preSum[xx + 1][j] - preSum[i][yy + 1] + preSum[i][j];
                    if (area == middle * middle) {
                        find = true;
                        break;
                    }
                }
            }
            if (find) {
                ans = middle;
                l = middle + 1;
            } else {
                r = middle - 1;
            }
        }

        return ans*ans;
    }

    /**
     * 1277. 统计全为 1 的正方形子矩阵
     * 根之前的题目一样：先计算二维前缀和，然后再遍历顶点，把顶点当做子矩阵的左上角，然后在二分查找子矩阵的最大边长
     * 比如二分最大变成为5，则以这个点为子矩阵的 矩阵有5个，因为：最大变成为5说明 长宽为5的矩阵中全部都是1，那么以[i,j]为顶点的矩阵的 其他边长还可以是
     * 5-1、5-2、5-3等子矩阵。这样计算出来的就没有重叠问题了
     * @param matrix
     * @return
     */
    public static int countSquares(int[][] matrix) {

        int m = matrix.length, n = matrix[0].length;
        int[][] preSum = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i + 1][j + 1] = preSum[i + 1][j] + preSum[i][j + 1] - preSum[i][j] + matrix[i][j];
            }
        }

        int ans = 0;
        //其实问题转化成了以顶点（i,j）为左上角顶点的最大正方形边长问题
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //这个顶点都不是1，肯定不行
                if (matrix[i][j] == 0) {
                    continue;
                }
                //二分求最大边长问题：
                int l = 1, r = Math.min(m - i, n - j), maxEdge = 0;
                while (l <= r) {
                    int midEdge = l + (r - l) / 2;
                    //计算右下角的订单
                    int xx = i + midEdge - 1, yy = j + midEdge - 1;
                    int area = preSum[xx + 1][yy + 1] - preSum[xx + 1][j] - preSum[i][yy + 1] + preSum[i][j];
                    if (area == midEdge * midEdge) {
                        maxEdge = Math.max(maxEdge, midEdge);
                        l = midEdge + 1;
                    } else {
                        r = midEdge - 1;
                    }
                }
                ans += maxEdge;
            }
        }
        return ans;
    }


    /**1504. 统计全 1 子矩形

     * 子矩阵的个数
     * 预处理前缀和，然后在遍历4个点，再次计算
     * @param mat
     * @return
     */
    public static int numSubmat(int[][] mat) {
        int m=mat.length,n=mat[0].length;
        int preSum[][]=new int[m+1][n+1];
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                preSum[i+1][j+1]=preSum[i+1][j]+preSum[i][j+1]-preSum[i][j]+mat[i][j];
            }
        }

        //此种方法的时间复杂度太高M^2*n^2
        int ans=0;
//        for (int i=0;i<m;i++){
//            for (int j=0;j<n;j++){
//                if (mat[i][j]==0){
//                    continue;
//                }
//                for (int p=i;p<m;p++){
//                    for (int q=j;q<n;q++){
//                        if (mat[p][q]==0){
//                            continue;
//                        }
//                        //求矩阵的和
//                        int area=preSum[p+1][q+1]-preSum[p+1][j]-preSum[i][q+1]+preSum[i][j];
//                        if (area==(p-i+1)*(q-j+1)){
//                            ans++;
//                        }
//                    }
//                }
//            }
//        }

        //方案二：优化：我们固定了三个点之后，使用二分法 求最大的另一个点，然后他们之前的区域全是1 子矩阵的个数就是
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                if (mat[i][j]==0){
                    continue;
                }
                for (int p=i;p<m;p++){
                    if (mat[p][j]==0){
                        continue;
                    }
                    //使用二分法来进行 求q的最大值
                    int left=j,right=n-1;
                    while (left<=right) {
                        int middle = left + (right - left) / 2;
                        //求矩阵的和
                        int area = preSum[p + 1][middle + 1] - preSum[p + 1][j] - preSum[i][middle + 1] + preSum[i][j];
                        if (area == (p - i + 1) * (middle - j + 1)) {
                            left = middle + 1;
                        } else {
                            right = middle - 1;
                        }
                    }
                    //
                    ans+=left-j;
                }
            }
        }
        return ans;
    }


    /**
     * 1074. 元素和为目标值的子矩阵数量
     * 方案一：前缀和+枚举4个点:可以枚举右下角的点，然后在这个子矩阵中查找
     * 方案二：枚举三个点， 然后使用map 维护左边遍历过的前缀和次数，然后在获取当前面积，到过去的map中查找 k-area 的次数
     * @param matrix
     * @param target
     * @return
     */
    public int numSubmatrixSumTarget(int[][] matrix, int target) {

        int m = matrix.length, n = matrix[0].length;
//        int preSum[][] = new int[m + 1][n + 1];
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                preSum[i + 1][j + 1] = preSum[i + 1][j] + preSum[i][j + 1] - preSum[i][j] + matrix[i][j];
//            }
//        }

        int ans = 0;
        // 方案一： 左上角的点
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//
//                //右下角的点
//                for (int p = i; p < m; p++) {
//                    for (int q = j; q < n; q++) {
//                        int area = preSum[p + 1][q + 1] - preSum[p + 1][j] - preSum[i][q + 1] + preSum[i][j];
//                        if (area == target) {
//                            ans++;
//                        }
//                    }
//                }
//            }
//        }

//        方案二：枚举右下角的点，然后在枚举左上角的点
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//
//                //右下角的点
//                for (int p = 0; p <= i; p++) {
//                    for (int q = 0; q <= j; q++) {
//                        int area = preSum[i + 1][j + 1] - preSum[i + 1][q] - preSum[p][j + 1] + preSum[p][q];
//                        if (area == target) {
//                            ans++;
//                        }
//                    }
//                }
//            }
//        }

        //方案三：使用枚举三条边+hash的方式
        int [][] preSum=new int[m+1][n];//创建列的前缀和

        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                preSum[i+1][j]=preSum[i][j]+matrix[i][j];
            }
        }

        for (int first=0;first<m;first++){
            for (int second=first;second<m;second++){
                Map<Integer,Integer> numCounts=new HashMap<>();

                numCounts.put(0,1);//表示和为0个的元素右一个：就是处理前缀preSum[0]=0;的问题
                int sum=0;
                //遍历列
                for (int j=0;j<n;j++){
                    int tmpSum=preSum[second+1][j]-preSum[first][j];
                    sum+=tmpSum;
                    //判断左边数组中是否存在这样的和
                    if (numCounts.containsKey(sum-target)){
                        ans+=numCounts.get(sum-target);
                    }
                    //维护左边已经遍历过的和的个数
                    numCounts.put(sum,numCounts.getOrDefault(sum,0)+1);
                }
            }
        }
        return ans;
    }
}


