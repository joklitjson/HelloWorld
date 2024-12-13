package alingchasan;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
}
