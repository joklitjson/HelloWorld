package alingchasan;

import java.util.*;

/**
 * 差分数组
 */
public class CDifferArr {


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
        System.out.println(maxSumRangeQuery(new int[]{1,2,3,4,5,10},new int[][]{{0,2},{1,3},{1,1}}

        ));

        System.out.println(splitPainting(new int[][]{{1,4,5},{1,4,7},{4,7,1},{4,7,11}}));
        System.out.println(minimumOperations(new int[]{5,9,2,2},new int[]{7,9,3,8}));
//        System.out.println(Arrays.toString(rawArr));
        // 2~5+1
    }

    /**
     * 2848. 与车相交的点
     * 直接创建一个差分数组，然后对其进行操作，在求其上的结果
     * @param nums
     * @return
     */
    public int numberOfPoints(List<List<Integer>> nums) {
        int differ[] = new int[101];

        for (List<Integer> one : nums) {
            differ[one.get(0)]++;
            differ[one.get(1) + 1]--;
        }

        int ans = 0;
        int sum=0;
        for (int value : differ) {
            sum+=value;
            if (sum > 0) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 1893. 检查是否区域内所有整数都被覆盖
     * 创建差分数组，然后进行增减，在还原
     * @param ranges
     * @param left
     * @param right
     * @return
     */
    public boolean isCovered(int[][] ranges, int left, int right) {
        int[] differ = new int[52];
        for (int[] rg : ranges) {
            differ[rg[0]]++;
            differ[rg[1] + 1]--;
        }

        //还原
        int sum = 0;
        for (int i = 0; i < differ.length; i++) {
            sum += differ[i];
            //还原的同时判断是否符合
            if (i >= left && i <= right && sum == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 1854. 人口最多的年份
     * @param logs
     * @return
     */
    public int maximumPopulation(int[][] logs) {
        int base = 1950;//主要为了数组短一些
        int[] yearDiff = new int[101];
        for (int[] year : logs) {
            yearDiff[year[0] - base]++;
            yearDiff[year[1] - base]--;

        }
        int max = 0;
        int maxPeoplo = 0;
        int sum = 0;
        for (int i = 0; i < yearDiff.length; i++) {
            sum += yearDiff[i];//还原
            if (sum > maxPeoplo) {
                maxPeoplo = sum;
                max = i;
            }
        }
        return base + max;
    }


    /**
     * 2960. 统计已测试设备
     * 其实就是一遍进行差分数组操作，一边进行遍历数组的问题，把差分的和 +元数组的和就是当前元素的真正的值
     * @param batteryPercentages
     * @return
     */
    public int countTestedDevices(int[] batteryPercentages) {

        int n = batteryPercentages.length;
        int diff[] = new int[n];

        int ans = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += diff[i];
//加上原数组
            if (sum+  batteryPercentages[i]> 0) {
                ans++;
                diff[i + 1]--;
//                diff[n-1]++;//不需要了，因为都是减到底的数字
            }
        }
        return ans;
    }

    /**
     * 3355. 零数组变换 I
         把【l,r】 中的元素都减一，最终数组中的所有元素是否都 ≤0？
     * 如果所有元素都 ≤0，那么我们可以撤销一部分元素的减一，使其调整为 0，从而满足原始题意的要求。
     * @param nums
     * @param queries
     * @return
     */
    public boolean isZeroArray(int[] nums, int[][] queries) {

        int n = nums.length;
        //
        int[] differ = new int[n + 1];//n+1为了方便处理最后一个下标
        for (int[] quer : queries) {
            differ[quer[0]]--;
            differ[quer[1] + 1]++;
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            int diff = differ[i];
            sum += diff;//累加差分的值
            if (sum + nums[i] > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 1109. 航班预订统计
     * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
     * 操作差分数组，然后在还原
     * @param bookings
     * @param n
     * @return
     */
    int[] corpFlightBookings(int[][] bookings, int n) {
        int diff[] = new int[n + 2];

        for (int[] book : bookings) {
            diff[book[0]] += book[2];
            diff[book[1] + 1] -= book[2];
        }

        int flight[] = new int[n];
        int sum = 0;
        //还原：注意下表的问题
        for (int i = 1; i <= n; i++) {
            sum += diff[i];
            flight[i - 1] = sum;
        }

        return flight;
    }

    /**
     * 56. 合并区间
     * 有两种方法：一种是排序：
     * 第二种这是差分数组：需要把区间进行扩大两杯，，因为主要是为了把区间 【2，5]、[5,6]中的两个5不进行合并
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {

        int max = intervals[0][1];
        for (int[] it : intervals) {
            max = Math.max(max, it[1]);
        }

        int diff[] = new int[2 * max + 2];

        for (int[] it : intervals) {
            diff[it[0] * 2]++;
            diff[it[1] * 2 + 1]--;
        }
        List<int[]> list = new ArrayList<>();
        int left = -1;
        int sum = 0;
        for (int i = 0; i < diff.length; i++) {
            sum += diff[i];
            //遇到大于0的则 说明是开启了一段新的区间
            if (sum > 0 && left == -1) {
                //开始一段新的起点
                left = i / 2;
            } else if (sum == 0 && left != -1) {
                //区间和等于0 说明是这一段区间遍历完成了
                list.add(new int[]{left, i / 2});
                left = -1;
            }
        }

        int[][] arr = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }


    /**
     * 57. 插入区间
     * 可以使用差分数组 扩展范围进行合并
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {

        //1、求最大值
        int max =0;//第一个区间可能是空
        for (int[] it : intervals) {
            max = Math.max(max, it[1]);
        }
        max = Math.max(max, newInterval[1]);
        //2、操作差分区间
        int[] diff = new int[max * 2 + 2];
        for (int[] it : intervals) {
            diff[it[0] * 2]++;
            diff[it[1] * 2 + 1]--;
        }
        diff[newInterval[0] * 2]++;
        diff[newInterval[1] * 2 + 1]--;

        //3、还原区间
        List<int[]> result = new ArrayList<>();
        int sum = 0, left = -1;
        for (int i = 0; i < diff.length; i++) {
            sum+=diff[i];
            if (sum > 0 && left == -1) {
                //开启新区间
                left = i / 2;
            } else if (sum == 0 && left != -1) {
                result.add(new int[]{left, i / 2});
                left=-1;
            }
        }

        int[][] ans = new int[result.size()][2];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;
    }


    /**
     * 2406. 将区间分为最少组数
     * 差分数组思想：思路是转换成上下车模型，每个区间看成一个人，他在 left 时刻上车，right+1 时刻下车，
     * 最后答案为同时在车上的人数的最大值（会议室模型，只要任意时刻至多有 x 个会议室在同时使用，那么就至多需要 x 个会议室）
     *
     * 方案二：使用优先级队列，按照 left 排序后，用最小堆模拟，堆顶存储每个组最后一个区间的 right。
     * 遍历区间：
     * 如果当前的 left 大于堆顶，则可以接在这个组的末尾，更新堆顶为 right；
     * 否则需要创建一个新的组。
     * @param intervals
     * @return
     */
    public int minGroups(int[][] intervals) {
//
//        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue();
//        for (int[] it : intervals) {
//            if (priorityQueue.isEmpty()) {
//                //空的情况下直接开辟一个分组
//                priorityQueue.add(it[1]);
//            } else if (priorityQueue.peek() < it[0]) {
//                //如果当前元素大于上一个组的right，则他们俩可以分在一起
//                priorityQueue.poll();
//                priorityQueue.add(it[1]);
//            } else {
//                //当前元素和上一个元素有交易：因此需要新开辟一个分组
//                priorityQueue.add(it[1]);
//            }
//        }
//        return priorityQueue.size();

        //使用差分数组
        TreeMap<Integer, Integer> cntMap = new TreeMap<>();
//        for (int[] ite : intervals) {
//            cntMap.put(ite[0], cntMap.getOrDefault(ite[0], 0) + 1);
//            cntMap.put(ite[1], cntMap.getOrDefault(ite[1] + 1, 0) - 1);
//        }
//        int max = 0;
//        int sum = 0;

//        for (Map.Entry<Integer,Integer> entry: cntMap.entrySet()){
//            sum += entry.getValue();
//            max = Math.max(max, sum);//记录最大的值
//        }
//        for (Integer value : cntMap.values()) {
//            sum += value;
//            max = Math.max(max, sum);//记录最大的值
//        }
//        return max;

        int[] arr=new int[1000002];
        for(int i=0;i<intervals.length;i++){
            arr[intervals[i][0]]++;
            arr[intervals[i][1]+1]--;
        }
        int ans=0;
        int temp=arr[0];
        for(int i=1;i<arr.length;i++){
            temp=temp+arr[i];
            ans=(ans>temp)?ans:temp;
        }
        return ans;

    }

    /**
     * 2381. 字母移位 II
     * @param s
     * @param shifts
     * @return
     */
    public String shiftingLetters(String s, int[][] shifts) {

        int n=s.length();
        int diff[]=new int[n+1];
        for(int [] ope:shifts){
            diff[ope[0]]+=(ope[2]==1?1:-1);
            diff[ope[1]+1]-=(ope[2]==1?1:-1);
        }

        int sum=0;
        char [] str=new char[n];
        //还原
        for (int i=0;i<n;i++){
            sum+=diff[i];
            int t= (sum%26+ (s.charAt(i)-'a')+26)%26;
            str[i]= (char) (t +'a');
        }
        return new String(str);

    }

    /**
     * 3356. 零数组变换 II
     * @param nums
     * @param queries
     * @return
     */
    public int minZeroArray(int[] nums, int[][] queries) {
        int n=queries.length;
        int left=0,right=queries.length;
        int ans=-1;
        while (left<=right){
            int middle=left+(right-left)/2;
            //在middle 次操作下 可以成功的把数组变成0，因此我们尝试使用更小的次数看看能否成功
            if (checkZeroArray(nums,queries,middle)){
                ans=middle;
                right=middle-1;
            }
            else {
                left=middle+1;
            }
        }
        return ans;
    }

    /**
     * 检查第k次能否让数组都变成0
     *
     * @param nums
     * @param queries
     * @param k
     * @return
     */
    public boolean checkZeroArray(int[] nums, int[][] queries,int k) {

        int n=nums.length;
        int differ[]=new int[n+1];
        //差分处理:进行k次减一操作
        for (int i=0;i<k;i++){
            differ[queries[i][0]]-=queries[i][2];
            differ[queries[i][1]+1]+=queries[i][2];
        }
        int sum=0;
        //还原操作结果+原始数组的值:看看是否大于0
        for (int i=0;i<n;i++){
            sum+=differ[i];
            if (sum+nums[i]>0)
                return false;
        }
        return true;
    }


    /**
     * 995. K 连续位的最小翻转次数:让集合全部变成1
     * 一遍进行翻转：一遍进行差分计算:遍历的是前部分，差分操作的是后半部分，因此遍历的同时互不影响
     *
     * @param nums
     * @param k
     * @return
     */
    public int minKBitFlips(int[] nums, int k) {

        int n = nums.length;
        int[] diff = new int[n + 1];

        int sum = 0, cnt = 0;
        for (int i = 0; i < n; i++) {
            sum += diff[i];//统计在第i位上的差分操作次数

            //如果之前是0翻转了0次或者之前是1翻转了1次，则这次都需要再次进行翻转：统一处理逻辑就是(翻转次数+当前值)%2==0 需要再次翻转
//            if ((sum % 2 == 0 && nums[i] == 0) || (nums[i]) == 1 && sum % 2 == 1) {
            if ((sum%2+nums[i])%2==0){
                //需要再次进行翻转 翻转区间；【i,i+k-1】
                //最后不足k位 不能进行翻转
                if (i + k - 1 >= n) {
                    return -1;
                }
                //更新区间后的次数
                diff[i + k] -= 1;
                sum += 1;//翻转区间i
                cnt++;
            }
        }
        return cnt;
    }


    /**
     * 1589. 所有排列中的最大和
     * 差分数组：然后在还原，在按高低排序查询次数，按查询次数多少匹配数组的大小，然后乘积相加
     * @param nums
     * @param requests
     * @return
     */
    public static int maxSumRangeQuery(int[] nums, int[][] requests) {
        int mod= (int) (Math.pow(10,9)+7);
        int n=requests.length;
        int differ[]=new int[nums.length+1];
        for (int request[]:requests){
            differ[request[0]]++;
            differ[request[1]+1]--;
        }

        int sum=0;
        for (int i=0;i<differ.length;i++){
            sum+=differ[i];
            differ[i]=sum;
        }
        Arrays.sort(nums);
        Arrays.sort(differ);
        System.out.println(nums.length+"=="+differ.length);
        long ans=0;
        for (int i=nums.length-1,j=differ.length-1;i>=0&&differ[j]>0;i--,j--){
            ans=(long)nums[i]*differ[j];
        }

        return (int) (ans%mod);
    }


    /**
     * 1526. 形成目标数组的子数组最少增加次数
     * 方案：其实就是问如何把数组都是0的数字转换成目标数组，可以把问题转换成 把 target的差分数组 d 的元素都变成0的最小操作次数
     * 因此我们可以操作差分数组进行计算，我们只能对数组d进行 -1，操作，但是后一段的数组要进行+1操作。
     * 有个理论 最小操作次数就是把差分数组中的所有大于0的元素 的和就是差分数组的最小操作次数
     * @param target
     * @return
     */
    public int minNumberOperations(int[] target) {
        int ans = target[0];
        for (int i = 1; i < target.length; i++) {
            ans += Math.max(0, target[i] - target[i - 1]);
        }
        return ans;
    }


    /**
     * 1943. 描述绘画结果
     * 还是差分数组处理 线段上的值
     * @param segments
     * @return
     */
    public static List<List<Long>> splitPainting(int[][] segments) {
        int len = 15;// (int) (Math.pow(10,5)+2);

        int max = 0;
        TreeSet<Integer> treeSet = new TreeSet<>();//记录每个点位
        for (int[] seg : segments) {
            int begin = seg[0];
            int end = seg[1];
            treeSet.add(begin);
            treeSet.add( end);
            max = Math.max(max, end);
        }
        long diff[] = new long[max + 1];
        for (int[] seg : segments) {
            int begin = seg[0];
            int end = seg[1];
            int color = seg[2];
            diff[begin] += (long)color;
            diff[end] -=  (long)color;
        }
        long preSum[] = new long[max + 1];
        preSum[0] = (long) diff[0];
//        统计前缀和
        for (int i = 1; i < diff.length; i++) {
            preSum[i] = preSum[i - 1]+diff[i];
        }
        //还原数组的同时进行统计
        List<List<Long>> list = new ArrayList<>();
        while (treeSet.size() > 1) {
            int begin = treeSet.pollFirst();
            int end = treeSet.first();

            if (preSum[begin] == 0) {
                continue;
            }
            list.add(Arrays.asList((long)begin, (long)end, preSum[begin]));
        }
        return list;
    }


    /**
     * 2251. 花期内花的数目
     * 计算差分，然后在求和
     * @param flowers
     * @param people
     * @return
     */
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {


        //对于需要开辟大数组的情况下,可以使用有序集合进行存储
        TreeMap<Integer,Integer> treeMap=new TreeMap<>();

        for (int []range:flowers){
            treeMap.put(range[0],treeMap.getOrDefault(range[0],0)+1);
            treeMap.put(range[1]+1,treeMap.getOrDefault(range[1]+1,0)-1);
        }

        //2、其实这里可以先把treeMap进行求和，但是我们先不这么做。我们可以把people 进行排序 从小到大进行，然后在把人员所在的指针进行排序
        int m=people.length;
        Integer [] idxArr=new Integer[m];
        for (int i=0;i<m;i++){
            idxArr[i]=i;
        }
        //就是把人对应的指针进行升序排列
        Arrays.sort(idxArr,(x,y)->people[x]-people[y]);
        int [] ans=new int[m];
        int sum=0;
        for (int idx:idxArr){
            //这个操作算是求前缀和
            while (!treeMap.isEmpty()&&treeMap.firstKey()<=people[idx]){
                sum+=treeMap.pollFirstEntry().getValue();
            }
            ans[idx]=sum;
        }
        return  ans;

//        int max = 0;
//        int min=0;//求最大和最小值，主要就是为了不创建大数组，否则内存太大了
//        for (int[] fl : flowers) {
//            min = Math.min(min, fl[0]);
//            max = Math.max(max, fl[1]);
//
//        }
//
//        int diff[] = new int[max + 2];
//
//        for (int[] range : flowers) {
//            //花期的区间的左闭右闭
//            diff[range[0]]++;
//            diff[range[1]+1]--;
//        }
//
//        //还原：在原数组还原
//        for (int i = 1; i < diff.length; i++) {
//            diff[i] = diff[i] + diff[i - 1];
//        }
//        int ans[] = new int[people.length];
//        for (int i = 0; i < people.length; i++) {
//            if (people[i]>max){
//                ans[i]=0;
//            }
//            else {
//                //到前缀和中查找答案:
//                ans[i] = diff[people[i]];
//            }
//
//        }
//        return ans;
    }


    /**
     * 2772. 使数组中的所有元素都等于零
     * 先求差分数组，如果差分数组的元素大于0，则进行 -t操作，并更新i+k的差分值+t，
     * 如果某个值已经被操作小于0了或者长度不足k了在，则返回false
     * @param nums
     * @param k
     * @return
     */
    public boolean checkArray(int[] nums, int k) {

        int n = nums.length;
        int diff[] = new int[n+1];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += diff[i];//统计差分值
            int current=sum + nums[i];
            //不需要计算
            if (current == 0) {
                continue;
            }
            //当前值都是负数了，或者当前i 后的k个长度已经超过了数组长度，因此不能继续操作l
            if (current< 0 || i + k > n) {
                return false;
            }
            sum -=  (current);//需要再第i个位置上减去 sum+nums[i] 个数字
            diff[i + k] += current;//后一个数进行+1;
        }
        return true;
    }

    /**
     * 3229. 使数组等于目标数组所需的最少操作次数
     *总结:
     *     1.前后元素数字符号不相同的时候
     *            当前后元素数字符号不同的时候，前后计算毫无关联，下一元素所的变动的次数为该元素的绝对值
     *      2.前后元素数字符号相同的时候（使用^结果>=判断是否符号相同）
     *             a.当前元素的绝对值>前一个元素的绝对值:
     *                 需要变化的次数 =  当前元素的绝对值 - 前一个元素的绝对值:
     *             b.当前元素的绝对值<=前一个元素的绝对值:
     *                 需要变化的次数 = 0
     * @param nums
     * @param target
     * @return
     */
    public static long minimumOperations(int[] nums, int[] target) {
        int n = nums.length;
        int[] diff = new int[n];
        //使用目标数组减去 之前的数组，计算差值
        diff[0] = target[0] - nums[0];
        long cnt = Math.abs(diff[0]);
        for (int i = 1; i < n; i++) {
            diff[i] = target[i] - nums[i];
            if ((diff[i] ^ diff[i - 1]) >= 0) {
                //两者是同符号的符号
                if (diff[i]==1){
                    System.out.println();
                }
                System.out.println(diff[i]+";;;"+diff[i-1]);
                if (Math.abs(diff[i]) >= Math.abs(diff[i - 1])) {
                    //当前元素比之前元素高，因此需要多几个操作，让他变成0
                    cnt += Math.abs(diff[i]) - Math.abs(diff[i - 1]);
                } else {
                    //当前元素没有之前元素高，因此修改前面的元素的同时 可以把他顺便修改了
                }
            } else {
                cnt += Math.abs(diff[i]);
            }
        }
        return cnt;
    }
    /**
     * 731. 我的日程安排表 II
     */
    class MyCalendarTwo {

        TreeMap<Integer, Integer> momentMap = null;

        public MyCalendarTwo() {
            momentMap = new TreeMap<>();
        }

        //注意区间的范围[start,end)前闭后开
        public boolean book(int startTime, int endTime) {
            momentMap.put(startTime, momentMap.getOrDefault(startTime, 0) + 1);
            momentMap.put(endTime, momentMap.getOrDefault(endTime, 0) - 1);

            boolean canBooking = true;
            int cnt = 0;
            for (Integer value : momentMap.values()) {
                cnt += value;
                if (cnt >= 3) {
                    canBooking = false;
                    break;
                }
            }

            //还原
            if (!canBooking) {
                momentMap.put(startTime, momentMap.getOrDefault(startTime, 0) - 1);
                momentMap.put(endTime, momentMap.getOrDefault(endTime, 0) + 1);
            }
            return canBooking;
        }
    }

    /**
     * 732. 我的日程安排表 III
     */
    class MyCalendarThree {

        int[] differ = null;//使用数组空间太大，因此不行，所以我们使用排序的map
        TreeMap<Integer, Integer> diffMap = null;

        public MyCalendarThree() {
//            differ=new int[(int) Math.pow(10,9)];
            diffMap = new TreeMap<>();
        }

        public int book(int startTime, int endTime) {

            //区间是左闭右开 区间
            diffMap.put(startTime, diffMap.getOrDefault(startTime, 0) + 1);
            diffMap.put(endTime, diffMap.getOrDefault(endTime, 0) - 1);
//            differ[startTime]++;
//            differ[endTime]--;
            int sum = 0, maxFre = 0;
//            for (int value:differ){
//                sum+=value;
//                maxFre=Math.max(maxFre,sum);
//            }

            //还原 求和
            for (Map.Entry<Integer, Integer> entrySet : diffMap.entrySet()) {
                sum += entrySet.getValue();
                maxFre = Math.max(maxFre, sum);
            }
            return maxFre;
        }
    }
}
