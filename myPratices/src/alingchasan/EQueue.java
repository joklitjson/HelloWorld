package alingchasan;

import java.math.BigDecimal;
import java.util.*;

/**
 * 队列的相关题目：
 * 什么事队列？先进先出，尾部进入，头部出。
 * 单端队列、循环队列、使用栈模拟队列（双栈模拟队列）、
 * 使用队列模拟栈(单队列模拟栈,要输出栈顶元素则，循环把队头元素插入到队尾，这样队尾就会很快变成队头，则可以进行输出了)
 *
 *
 * 单调队列和单调栈的区别：单调队列允许在两侧进行修改，一般是左侧移除数据，右侧添加数据，但是单调栈 只能在栈顶进行修改数据
 */
public class EQueue {

    public static void main(String[] args) {
//        System.out.println(Arrays.toString(new EQueue().maxSlidingWindow(new int[]{7,2,4},2)));

        System.out.println(longestSubarray(new int[]{4,2,2,2,4,4,2,2},0));
        System.out.println(continuousSubarrays(new int[]{1,2,3}));
        System.out.println(maximumRobots(new int[]{3,6,1,3,4},new int[]{2,1,3,4,5},25));

        System.out.println(maxResult(new int[]{1,-1,-2,4,-7,3},2));

        System.out.println(smallestChair(new int[][]{{7,10},{6,7},{1,3},{2,7},{4,5}}
        ,0));

        System.out.println(Arrays.toString(getOrder(new int[][]{{7,10},{7,12},{7,5},{7,4},{7,2}})));
    }
    /**
     * 2810. 故障键盘
     * 遇到i则进行翻转字符串，然后在插入：其实我们可以想：正常的输入是使用尾插入法则，如果遇到了i，则进行在头部插入，其实就是调转了
     * 插入的顺序
     * @param s
     * @return
     */
    public String finalString(String s) {

        Deque<Character> queue = new ArrayDeque<>();

        //是否使用头插法：默认是尾部插入
        boolean appendHHead = false;

        for (char c : s.toCharArray()) {
            if (c == 'i') {
                //改变插入方向
                appendHHead = !appendHHead;
            } else {
                //插入到头部
                if (appendHHead) {
                    queue.offerFirst(c);
                } else {
                    //插入到尾部
                    queue.offerLast(c);
                }
            }
        }

        //输出、在判断之前是否的插入方法
        StringBuilder stringBuilder = new StringBuilder();

        while (!queue.isEmpty()) {

            if (appendHHead) {
                //如果使用头插入法：从尾部遍历
                stringBuilder.append(queue.pollLast());
            } else {
                //如果使用的是头部插入法：则从头部开始遍历
                stringBuilder.append(queue.pollFirst());
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 933. 最近的请求次数
     * 1、可以使用队列，然后每次删除小于 t-3000的元素，然后在求元素个数
     * 2、使用数组，然后在使用左侧二分法求:>=t-300的最小值，然后当前元素 -left 就是元素的个数
     * @param t
     * @return
     */
    public int ping(int t) {
        integerDeque.offerLast(t);
        //删除不在区间t-3000之间的数字
        while (integerDeque.peek()<t-3000){
            integerDeque.pollFirst();
        }
        return integerDeque.size();
    }

    Deque<Integer> integerDeque=new ArrayDeque<>();


    /**
     * 239. 滑动窗口最大值
     * 单调双端队列：
     * 单调队列套路
     * 入（元素进入队尾，同时维护队列单调性）
     * 出（元素离开队首）
     * 记录/维护答案（根据队首）
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> deque = new ArrayDeque<>();
        int ans[] = new int[n - k + 1];

        for (int i = 0; i < n; i++) {

            //1、入队列:把小于等于当前元素的数字弹出队列，让队列保持单调递减
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);

            //2、出队列:队首 已经离开了队列
            if (deque.peek() <= i - k) {
                deque.pollFirst();
            }

            //记录答案
            if (i - k + 1 >= 0) {
                ans[i - k + 1] = nums[deque.peek()];
            }
        }
        return ans;
    }

    /**
     * 1438. 绝对差不超过限制的最长连续子数组
     * 维护两个单调队列：一个最大值、一个最小值，然后在判断最大值和最小值的 差是否大于limit 如果超过了，则划出左侧元素
     * @param nums
     * @param limit
     * @return
     */
    public  static int longestSubarray(int[] nums, int limit) {

        Deque<Integer> maxQueue = new ArrayDeque<>();

        Deque<Integer> minQueue = new ArrayDeque<>();
        int left = 0, max = 0;
        for (int i = 0; i < nums.length; i++) {
//            int value=nums[i];

            //当前值 大于队列的最后一个字
            while (!maxQueue.isEmpty() && nums[maxQueue.peekLast()] < nums[i]) {
                maxQueue.pollLast();
            }
            maxQueue.offerLast(i);
            while (!minQueue.isEmpty() && nums[minQueue.peekLast()] > nums[i]) {
                minQueue.pollLast();
            }
            minQueue.offerLast(i);


            //最大和最小值 超过了limit，则移除左侧元素
            while (left < i+1 && nums[maxQueue.peekFirst()] - nums[minQueue.peekFirst()] > limit) {
                //移除左侧的元素
                if (nums[maxQueue.peekFirst()] == nums[left]) {
                    maxQueue.pollFirst();
                }
                //移除左侧元素
                if (nums[minQueue.peekFirst()] == nums[left]) {
                    minQueue.pollFirst();
                }

                left++;
            }
            max = Math.max(max, i - left+1);
        }
        return max;
    }


    /**
     * 2762. 不间断子数组
     * 维护两个单调队列：一个最大值、一个最小值，然后在判断最大值和最小值的 差是否大于limit 如果超过了，则划出左侧元素
     * @param nums
     *
     * @return
     */
    public  static   long continuousSubarrays(int[] nums) {

         int limit=2;
        Deque<Integer> maxQueue = new ArrayDeque<>();

        Deque<Integer> minQueue = new ArrayDeque<>();
        int left = 0;
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
//            int value=nums[i];

            //当前值 大于队列的最后一个字
            while (!maxQueue.isEmpty() && nums[maxQueue.peekLast()] < nums[i]) {
                maxQueue.pollLast();
            }
            maxQueue.offerLast(i);
            while (!minQueue.isEmpty() && nums[minQueue.peekLast()] > nums[i]) {
                minQueue.pollLast();
            }
            minQueue.offerLast(i);


            //最大和最小值 超过了limit，则移除左侧元素
            while (left < i+1 && nums[maxQueue.peekFirst()] - nums[minQueue.peekFirst()] > limit) {
                //移除左侧的元素
                if (nums[maxQueue.peekFirst()] == nums[left]) {
                    maxQueue.pollFirst();
                }
                //移除左侧元素
                if (nums[minQueue.peekFirst()] == nums[left]) {
                    minQueue.pollFirst();
                }
                left++;
            }
            ans+=i - left+1;
        }
        return ans;
    }


    /**
     * 2398. 预算内的最多机器人数目
     * 使用单调 队列 保存窗口内的最大值，然后再记录最大的子数组
     * @param chargeTimes
     * @param runningCosts
     * @param budget
     * @return
     */
    public static int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        Deque<Integer> maxQueue = new ArrayDeque<>();
        int maxLength = 0;
        long sum = 0;
        int left = 0;//窗口的左下标
        for (int i = 0; i < chargeTimes.length; i++) {
            //维护单调队列
            while (!maxQueue.isEmpty() && chargeTimes[maxQueue.peekLast()] <= chargeTimes[i]) {
                maxQueue.pollLast();
            }
            maxQueue.offerLast(i);
            sum += runningCosts[i];
            //移除不符合条件的左边的下标
            while (!maxQueue.isEmpty() && chargeTimes[maxQueue.peekFirst()] + (i + 1 - left) * sum > budget) {
                if (maxQueue.peekFirst() == left) {
                    maxQueue.pollFirst();
                }
                sum -= runningCosts[left++];
            }
            maxLength = Math.max(maxLength, (i + 1 - left));
        }
        return maxLength;
    }

    /**
     * 862. 和至少为 K 的最短子数组
     *前缀和+滑动窗口
     * @param nums
     * @param k
     * @return
     */
    public int shortestSubarray(int[] nums, int k) {
        int ans = Integer.MAX_VALUE, n = nums.length;


        Deque<Integer> deque = new ArrayDeque<>();
        long[] presum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + nums[i];
        }

        for (int right = 0; right <= n; right++) {
            long currentSum = presum[right];
            //和至少为k的最短子数组：当和大于k的情况下 搜索左下标
            while (!deque.isEmpty() && currentSum - presum[deque.peekFirst()] >= k) {
                ;//移除左边的，缩短左边界
                ans = Math.min(ans, (right - deque.pollFirst()));//优化一
            }

            while (!deque.isEmpty() && presum[deque.peekLast()] >= currentSum) {
                deque.pollLast(); // 优化二
            }

            deque.offerLast(right);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 1499. 满足不等式的最大值
     * 求：yi + yj + |xi - xj| 的 最大值 可以转换成===>yj+xj+(yi-xi)，就是在枚举j的时候，需要获取左侧 窗口中 (yi-xi)的最大值
     * 因此这里符合使用单调队列的方式
     * 我们使用单调队列存储二元组{xi,yi-xi}
     * 什么时候收缩左边界？xj-xi>k的时候收缩
     * @param points
     * @param k
     * @return
     */
    public int findMaxValueOfEquation(int[][] points, int k) {

        //单调队列
        Deque<int[]> deque=new ArrayDeque<>();

        int n=points.length,ans=Integer.MIN_VALUE;

        for (int j=0;j<n;j++){
            int x=points[j][0];
            int y=points[j][1];

            //判断是否满足条件 xj-xi<=k,弹出左边的值
            while (!deque.isEmpty()&&x-deque.peekFirst()[0]>k){
                deque.pollFirst();
            }

            //求集合的值
            if (!deque.isEmpty()){
                ans=Math.max(ans,x+y+deque.peekFirst()[1]);
            }

            //加入当前坐标:维护 y-x的单调性.保持单调递减性，让队列左边的值是最大的
            while (!deque.isEmpty()&&deque.peekLast()[1]<=y-x){
                deque.pollLast();;
            }

            deque.offerLast(new int[]{x,y-x});

        }

        return ans;
    }

    /**
     * 2071. 你可以安排的最多任务数目
     *
     * 二分查找 + 贪心选择工人
     * 二分遍历 可以完成的任务k，然后使用k个工人完成k个任务。
     * 在使用贪心算法：使用k个体力最大的工人、完成k个最小工作量的工作.
     * 判断是否能完成，如果能完成则继续 k-->k+1,否则缩小k
     * @param tasks
     * @param workers
     * @param pills
     * @param strength
     * @return
     */
    int[] tasks;
    int[] workers;
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        this.tasks = tasks;
        this.workers = workers;

        Arrays.sort(tasks);
        Arrays.sort(workers);

        //不包含右边
        int left = 0, right = Math.min(tasks.length, workers.length)+1;

        int ans=0;
        //不包含右边
        while (left < right) {
            int middle = left + (right - left) / 2;
            //判断是否能完成middle 个任务
            if (checkComplete(middle, pills, strength)) {
                ans=middle;
                left = middle+1;
            } else {
                right = middle;
            }
        }
        return ans;
    }

    private boolean checkComplete(int k, int pills, int strength) {

        //统计力量，人数 的映射
        TreeMap<Integer, Integer> workSteCntMap = new TreeMap<>();
        for (int i = workers.length - k; i < workers.length; i++) {
            workSteCntMap.put(workers[i], workSteCntMap.getOrDefault(workers[i], 0) + 1);
        }

        //遍历最小的k个任务
        for (int i = k - 1; i >= 0; i--) {
            Map.Entry<Integer, Integer> en = workSteCntMap.lastEntry();

            //当前力量大于工作量
            if (en.getKey() > tasks[i]) {
                if (en.getValue() > 1) {
                    //使用一个工人
                    workSteCntMap.put(en.getKey(), en.getValue() - 1);
                } else {
                    workSteCntMap.remove(en.getKey());
                }
            } else if (pills > 0 && (workSteCntMap.ceilingKey(tasks[i] - strength) != null)) {
                //当前工人使用了一个药丸，看看能否大于当前任务的值
                pills--;
                Map.Entry<Integer, Integer> ceilingEntry = workSteCntMap.ceilingEntry(tasks[i] - strength);
                if (ceilingEntry.getValue() <= 1) {
                    workSteCntMap.remove(ceilingEntry.getKey());
                } else {
                    //使用一个工人
                    workSteCntMap.put(ceilingEntry.getKey(), ceilingEntry.getValue() - 1);
                }
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * LCR 184. 设计自助结算系统
     * get_max()：获取结算商品中的最高价格，如果队列为空，则返回 -1
     * add(value)：将价格为 value 的商品加入待结算商品队列的尾部
     * remove()：移除第一个待结算的商品价格，如果队列为空，则返回 -1
     * 注意，为保证该系统运转高效性，以上函数的均摊时间复杂度均为 O(1)
     *
     */
    class Checkout {

        //使用一个队列，维护其单调性:单调递减
        Deque<Integer> queue=new ArrayDeque<>();

        Deque<Integer> numsQueue=new ArrayDeque<>();
        /**
         * 使用单调队列，保存左侧最大值
         */
        public Checkout() {

        }

        public int get_max() {
            return queue.isEmpty()?-1:queue.peekFirst();
        }

        public void add(int value) {
            numsQueue.add(value);
            while (!queue.isEmpty()&&value>queue.peekLast()){
                queue.pollLast();
            }
            queue.offerLast(value);
        }

        public int remove() {
            if (numsQueue.isEmpty()){
                return -1;
            }
            int value= numsQueue.pollFirst();
            //移除队首元素
            if (value==queue.peekFirst()){
                queue.pollFirst();
            }
            return value;
        }
    }


    /**
     * 1696. 跳跃游戏 VI
     * 动态规划+单调队列
     * 状态定义： dp[i]表示从起点跳到i的最大得分，在这个定义下，dp[n−1]就是答案。
     *
     * 位置i 可以跳跃到的位置是 [i+1,i+k},反过来思考就是 哪些位置可以跳跃到i？ [i-k,i-1]，因此跳跃到i的最大得分就是 max(dp[i-k],dp[i-1])+num[i]
     * 因此就是转换成了 求 区间最大值的问题：可以使用单调队列
     * @param nums
     * @param k
     * @return
     */
    public static int maxResult(int[] nums, int k) {

        int n = nums.length;
        int[] dp = new int[n];
        dp[0]=nums[0];//第一个最大得分就是他自己
        Deque<Integer> monontQueue = new ArrayDeque<>();
        monontQueue.offerLast(0);
        for (int i = 1; i < n; i++) {

            //弹：判断队列中的 索引是否在区间 [i-k,i-1] 范围内
            while (!monontQueue.isEmpty() && monontQueue.peekFirst() < i - k) {
                monontQueue.pollFirst();
            }
            //取：
            dp[i] =dp[monontQueue.peekFirst()] + nums[i];

            //入：弹出之前小于他的
            while (!monontQueue.isEmpty() && dp[monontQueue.peekLast()] <= dp[i]) {
                monontQueue.pollLast();
            }
            monontQueue.offerLast(i);
        }

        //获取值
        return dp[n - 1];
    }


    /**
     * 1425. 带限制的子序列和
     *动态规划+单调队列
     * 首先 每个i 只能跟他之前的[i-k,i]的索引组成子序列，因此 我们想让以i结尾的下表组层的子序列的和最大，则需要再【i-k,i-1】 区间选择一个最大的值
     * 这就可以使用到了单调队列了。然后在遍历的过程中求 某个子序列的最大值
     *
     * @param nums
     * @param k
     * @return
     */
    public int constrainedSubsetSum(int[] nums, int k) {

        int n=nums.length;
        int maxScore=nums[0];
        //定义以i结尾的元素 组成的最大的子序列的和
        int dp[]=new int[n];
        Deque<Integer> monotQueue=new ArrayDeque<>();
        dp[0]=nums[0];
        monotQueue.offerLast(0);

        for (int i=1;i<n;i++) {

            //弹：弹出左侧不在 下表范围内的元素
            while (!monotQueue.isEmpty() && monotQueue.peekFirst() < i - k) {
                monotQueue.pollFirst();
            }

            //计算dp[i]最大值，如果前面都是负数的，则他可以自己开头层位一个子序列
            dp[i] = Math.max(dp[monotQueue.peekFirst()], 0) + nums[i];

            maxScore = Math.max(maxScore, dp[i]);

            //:入队列，为下一个元素做准备：新员工可能挤兑老员工
            while (!monotQueue.isEmpty() && dp[monotQueue.peekLast()] <= dp[i]) {
                monotQueue.pollLast();
            }
            monotQueue.offerLast(i);
        }

        return maxScore;
    }

    /**
     * 1046. 最后一块石头的重量
     * 使用大根堆：获取两个元素 在进行比较
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {

        //创建大根堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> b - a);

        for (int value : stones) {
            priorityQueue.offer(value);
        }

        //循环取出石头进行碰撞
        while (priorityQueue.size() > 1) {
            int a = priorityQueue.poll();
            int b = priorityQueue.poll();
            if (a > b) {
                priorityQueue.offer(a - b);
            }
        }

        return priorityQueue.isEmpty() ? 0 : priorityQueue.peek();
    }


    /**
     * 3264. K 次乘运算后的最终数组
     * 使用小跟堆存储 数组的下表
     * @param nums
     * @param k
     * @param multiplier
     * @return
     */
    public int[] getFinalState(int[] nums, int k, int multiplier) {

        int n = nums.length;
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>((a, b) -> {
            //小跟堆，值相同的情况下，优先索引小的
            if (nums[a] == nums[b]) {
                return a - b;
            }
            return nums[a] - nums[b];
        });

        for (int i = 0; i < n; i++) {
            queue.offer(i);
        }

        for (int i = 0; i < k; i++) {
            int minIdx = queue.poll();
            int value = nums[minIdx] * multiplier;
            nums[minIdx] = value;
            queue.offer(minIdx);
        }
        return nums;
    }


    /**
     * 2558. 从数量最多的堆取走礼物
     * 每次取最多的礼物，然后在剩下一半
     * @param gifts
     * @param k
     * @return
     */
    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> b - a);
        for (int value : gifts) {
            priorityQueue.offer(value);
        }
        while (k > 0) {
            int val = priorityQueue.poll();
            priorityQueue.offer((int) Math.sqrt(val));
            k--;
        }
        long sum = 0;
        while (!priorityQueue.isEmpty()) {
            sum += priorityQueue.poll();
        }
        return sum;
    }

    /**
     * 2336. 无限集中的最小数字
     */
    class SmallestInfiniteSet {

        TreeSet<Integer> avalibe=new TreeSet<>();
        int idx=1;
        /**
         * 使用 优先级队列收集 已经释放的数字，然后在使用一个变量 表示最小的数字的索引，可以一直往后滚动
         */
        public SmallestInfiniteSet() {

        }

        /**
         * 弹出最小数字
         * @return
         */
        public int popSmallest() {
            if (!avalibe.isEmpty()){
                return avalibe.pollFirst();
            }
            else {
                return idx++;
            }
        }

        //释放最小数字
        public void addBack(int num) {
            //后面的数字还未使用，所以不用管
            if (num>=idx){
                return;
            }

            avalibe.add(num);
        }
    }


    /**
     * 2530. 执行 K 次操作后的最大分数
     * k次操作，每次都选择最大的加入到分数中
     * @param nums
     * @param k
     * @return
     */
    public long maxKelements(int[] nums, int k) {

        long maxScore = 0;
        //创建一个大根堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a,b)->b-a);
        for (int val : nums) {
            priorityQueue.offer(val);
        }
        while (k > 0) {
            int val = priorityQueue.poll();
            maxScore += val;
            //把这个数字变为  ceil(nums[i] / 3)
            priorityQueue.offer((val + 2) / 3);
            k--;
        }
        return maxScore;
    }


    /**
     * 3066. 超过阈值的最少操作数 II
     * 小跟堆，计算最小的两个数
     * 选择 nums 中最小的两个整数 x 和 y 。
     * 将 x 和 y 从 nums 中删除。
     * 将 min(x, y) * 2 + max(x, y) 添加到数组中的任意位置。
     * @param nums
     * @param k
     * @return
     */
    public int minOperations(int[] nums, int k) {

        int cnt=0;
        PriorityQueue<Long> queue=new PriorityQueue<>();
        for (int val:nums){
            queue.offer((long) val);
        }
        while (queue.size()>1&&queue.peek()<k){
            Long a=queue.poll();
            Long b=queue.poll();
//            queue.offer(Math.min(a,b)*2+Math.max(a,b));
            queue.offer(a*2+b);
            cnt++;
        }

        return cnt;
    }

    /**
     * 1962. 移除石子使总数最小
     * 大根堆+贪心:优先对折石子多的
     * @param piles
     * @param k
     * @return
     */
    public int minStoneSum(int[] piles, int k) {
        int sum = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);

        for (int val : piles) {
            queue.add(val);
        }

        while (k > 0) {
            //对折
            int value = queue.poll();
            queue.offer(value - value / 2);
            k--;
        }

        //统计
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    /**
     * 703. 数据流中的第 K 大元素
     * 使用小跟堆，只保存k个元素，多余的元素弹出，这样就能保证堆顶 是第k个大的元素
     */
    class KthLargest {

        int limit=0;
        //小跟堆
        PriorityQueue<Integer> priorityQueue=new PriorityQueue();
        public KthLargest(int k, int[] nums) {
            this.limit=k;
            for (int val:nums){
                priorityQueue.offer(val);
                //超过数量 就弹出
                if (priorityQueue.size()>k){
                    priorityQueue.poll();
                }
            }
        }

        public int add(int val) {
            priorityQueue.offer(val);
            if (priorityQueue.size() > limit) {
                priorityQueue.poll();
            }
            return priorityQueue.peek();
        }
    }


    /**
     * 3275. 第 K 近障碍物查询
     * 使用大根堆：堆顶就是第k小的元素
     * @param queries
     * @param k
     * @return
     */
    public int[] resultsArray(int[][] queries, int k) {
        int n = queries.length;
        int[] result = new int[n];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a,b)->b-a);
        for (int i = 0; i < n; i++) {
            int distance = Math.abs(queries[i][0]) + Math.abs(queries[i][1]);
            minHeap.offer(distance);
            if (minHeap.size() < k) {
                result[i] = -1;
                continue;
            } else if (minHeap.size() > k) {
                minHeap.poll();
            }
            result[i] = minHeap.peek();
        }
        return result;
    }


    /**
     * 1845. 座位预约管理系统
     * 每次选择最小的
     */
    class SeatManager {

        int seatIdx;
        int max;
        TreeSet<Integer> avaliableSeats=new TreeSet<>();
        public SeatManager(int n) {
            this.seatIdx=1;
            this.max=n;
        }

        public int reserve() {
            if (!avaliableSeats.isEmpty()){
                return avaliableSeats.pollFirst();
            }
            return seatIdx++;
        }

        public void unreserve(int seatNumber) {
            avaliableSeats.add(seatNumber);
        }
    }

    /**
     * 2208. 将数组和减半的最少操作次数
     * 请你返回将 nums 数组和 至少 减少一半的 最少 操作数。
     * @param nums
     * @return
     */
    public int halveArray(int[] nums) {

        PriorityQueue<Double> bigHeap = new PriorityQueue<Double>((a, b) -> b.compareTo(a));
        double sum = 0;
        for (int val : nums) {
            bigHeap.offer((double) val);
            sum += val;
        }
        double sum2 = 0;//减少的量
        int cnt = 0;
        while (sum2 < sum / 2) {
            double val = bigHeap.poll();
            sum2 += val / 2.0;
            //重新入队列
            bigHeap.offer(val / 2.0);
            cnt++;
        }
        return cnt;
    }


    /**
     * 2233. K 次增加后的最大乘积
     * 每次选择最小的进行增大
     * @param nums
     * @param k
     * @return
     */
    public int maximumProduct(int[] nums, int k) {
        int mod = (int) (Math.pow(10, 9) + 7);
        PriorityQueue<Long> minHeap = new PriorityQueue<>();

        for (int val : nums) {
            minHeap.offer((long) val);
        }

        while (k > 0) {
            minHeap.offer(minHeap.poll() + 1);
            k--;
        }

        long ans = 1;
        while (!minHeap.isEmpty()) {
            ans = (ans * minHeap.poll() % mod);
        }

        return (int) (ans%mod);
    }


    /**
     * 1942. 最小未被占据椅子的编号
     * @param times
     * @param targetFriend
     * @return
     */
    public static int smallestChair(int[][] times, int targetFriend) {

        int[][] timeLines = new int[times.length * 2][3];
        int id = 0, friedsIdx = 0;
        for (int[] time : times) {
            //进入
            timeLines[id][0] = time[0];
            timeLines[id][1] = 1;//进入类型
            timeLines[id][2] = friedsIdx;
            id++;

            timeLines[id][0] = time[1];
            timeLines[id][1] = 0;
            timeLines[id][2] = friedsIdx;
            id++;
            //朋友id
            friedsIdx++;
        }
        //按时间节点进行排序
        Arrays.sort(timeLines, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if ( o1[0] == o2[0]){
                    //同时刻：优先归还
                    return o1[1]-o2[1];
                }
                else {
                    return o1[0] - o2[0];
                }
            }
        });

        TreeSet<Integer> avaliabe = new TreeSet<>();
        Map<Integer, Integer> seatMap = new HashMap<>();
        int seatIdx = 0;
        int tarResult = 0;
        for (int i = 0; i < timeLines.length; i++) {
            int[] time = timeLines[i];
            int friendId = time[2];
            if (time[1] == 1) {
                //进入
                if (!avaliabe.isEmpty()) {
                    seatMap.put(friendId, avaliabe.pollFirst());
                } else {
                    //从
                    seatMap.put(friendId, seatIdx++);
                }
                if (friendId == targetFriend) {
                    tarResult = seatMap.get(friendId);
                    break;
                }

//                System.out.println(String.format("朋友%s,在时间 %s到达，占据%s座位",friendId,time[0],seatMap.get(friendId)));

            } else {
                //离开
                avaliabe.add(seatMap.get(friendId));
//                System.out.println(String.format("朋友%s,在时间 %s离开，归还%s座位",friendId,time[0],seatMap.get(friendId)));
                //不需要从map中移除，因为后面不会在进入
            }
        }
        return tarResult;
    }


    /**
     * 1942. 最小未被占据椅子的编号
     * 使用小跟堆 leaveTimes 记录 即将离开人的座位编号和离开时间，然后按时间先后线进行遍历，如果leaveTimes 的顶部(最小离开时间)小于当前遍历元素的进入时间，则回收此离开人的座位号
     * @param times
     * @param targetFriend
     * @return
     */
    public int smallestChair2(int[][] times, int targetFriend) {
        int targetFriendComingTime = times[targetFriend][0];//目标朋友的进入时间

        Arrays.sort(times, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];//根据进入时间进行排序：模拟时间线
            }
        });


        //保留时间线 已经遍历过的人 的离开时间和座位号
        PriorityQueue<int[]> leavingTimes = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1]-o2[1];
            }
        });

        //回收的可以再次使用的座位号
        PriorityQueue<Integer> avaliabeSeats = new PriorityQueue<>();

        int seatNumber = 0;//座位号开始编号
        for (int[] time : times) {

            //当前 最先离开的人的时间小于当前进入人的时间，因此这个人的座位号可以进行回收
            while (!leavingTimes.isEmpty() && time[0] >= leavingTimes.peek()[1]) {
                avaliabeSeats.offer(leavingTimes.poll()[0]);
            }

            //为当前人分配座位号
            int currentSeat = avaliabeSeats.isEmpty() ? seatNumber++ : avaliabeSeats.poll();

            //命中目标时间
            if (targetFriendComingTime == time[0]) {
                return currentSeat;
            }
            //记录当前人的离开时间
            leavingTimes.offer(new int[]{currentSeat, time[1]});
        }

        return -1;
    }

        /**
         * 1801. 积压订单中的订单总数
         * 解决方案：使用两个优先级队列：
         * 销售单队列使用小跟堆，最小的价格在最上面‘，方便采购人员 查看是否有低于采购价格的订单
         * 采购单使用大根堆：价格最高的放在最上面，方便销售单能快速查询是否有大于当前价格的采购
         * @param orders
         * @return
         */
    public int getNumberOfBacklogOrders(int[][] orders) {
        // 价格、数量、类型(0、采购 1、销售)
        int mod= (int) (Math.pow(10,9)+7);
        //int[] 价格、数量.按价格小跟堆
        PriorityQueue<Integer[]> sellMinHeap = new PriorityQueue<Integer[]>((a, b) -> {
            return a[0] - b[0];
        });


        //购买 大根堆
        PriorityQueue<Integer[]> buyBigHeap = new PriorityQueue<Integer[]>((a, b) -> {
            return b[0] - a[0];
        });

        for (int[] order : orders) {
            int price = order[0];
            int quality = order[1];
            int type = order[2];
            if (type == 1) {
                //销售
                while (!buyBigHeap.isEmpty() && buyBigHeap.peek()[0] >= price && quality > 0) {
                    //可以使用采购单
                    if (quality >= buyBigHeap.peek()[1]) {
                        //使用当前的全部
                        quality -= buyBigHeap.poll()[1];
                    } else {
                        //当前订单已经销售完成
                        buyBigHeap.peek()[1] = buyBigHeap.peek()[1] - quality;
                        quality = 0;
                    }
                }

                if (quality > 0) {
                    //剩余的继续卖
                    sellMinHeap.offer(new Integer[]{price, quality});
                }
            } else {
                //采购://最低价格小于采购价格
                while (!sellMinHeap.isEmpty() && sellMinHeap.peek()[0] <= price && quality > 0) {

                    if (quality < sellMinHeap.peek()[1]) {
                        sellMinHeap.peek()[1] = sellMinHeap.peek()[1] - quality;
                        quality = 0;
                    } else {
                        //采购的数量多余销售的
                        quality -= sellMinHeap.poll()[1];
                    }
                }
                //有剩余的采购单
                if (quality > 0) {
                    buyBigHeap.offer(new Integer[]{price, quality});
                }
            }
        }

        long ans = 0;

        while (!buyBigHeap.isEmpty()) {
            ans += buyBigHeap.poll()[1];
            ans=ans%mod;
        }

        while (!sellMinHeap.isEmpty()) {
            ans += sellMinHeap.poll()[1];
            ans=ans%mod;
        }
        return (int) ans;
    }


    /**
     * 2462. 雇佣 K 位工人的总代价
     * 维护 小跟堆：前K个+后k个元素，然后在定义左边的边界和右边k个元素的边界，如果边界没有超过限制，则表示还有两个端，如果超过了限制 ，就
     * @param costs
     * @param k
     * @param candidates
     * @return
     */
    public long totalCost(int[] costs, int k, int candidates) {

        PriorityQueue<int[]> minHeap=new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                //先按成本优先级，在按下标优先级
                if (o1[0]!=o2[0]){
                    return o1[0]-o2[0];
                }
                return o1[1]-o2[1];
            }
        });

        long cost=0;

        int n=costs.length;
        int left=candidates-1,right=n-candidates;

        if (left+1<right){
            for (int i=0;i<candidates;i++){
                //成本和下标
                minHeap.offer(new int[]{costs[i],i});
            }

            for (int i=right;i<n;i++){
                //成本和下标
                minHeap.offer(new int[]{costs[i],i});
            }
        }
        else {

            //优化：--有交集，直接取前k个
            Arrays.sort(costs);
            for(int i=0; i<k; i++){
                cost += costs[i];
            }
            return cost;

//            for (int i=0;i<n;i++){
//                //成本和下标
//                minHeap.offer(new int[]{costs[i],i});
//            }
        }


        //选择k位工人
        for (int i=0;i<k;i++){
            int []  ca= minHeap.poll();
            cost+=ca[0];

            int idx=ca[1];
            if (left+1<right){
                //说明刚才得元素来自左边，因此需要左边在填充一个元素
                if (idx<=left){
                    left++;
                    minHeap.offer(new int[]{costs[left],left});
                }
                else {
                    right--;
                    minHeap.offer(new int[]{costs[right],right});
                }
            }
        }

        return cost;
    }

    /**
     * 1834. 单线程 CPU
     *  设计一个带有优先级的等待队列，等待中的人物可以先放在等待队列中
     * @param tasks
     * @return
     */
    public  static int[] getOrder(int[][] tasks) {
        //把任务变成3元组，携带任务的编号
        int[][] tasksWithId = new int[tasks.length][3];
        for (int i = 0; i < tasks.length; i++) {
            tasksWithId[i][0] = tasks[i][0];
            tasksWithId[i][1] = tasks[i][1];
            tasksWithId[i][2] = i;
        }

        //按开始时间排序
        Arrays.sort(tasksWithId, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });

        int n = tasks.length;
        PriorityQueue<int[]> waitQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                //先按运行时长排序，在按任务索引排序
                if (a[1] != b[1]) {
                    return a[1] - b[1];
                }
                return a[2] - b[2];
            }
        });

        int[] ans = new int[n];
        int idx = 0;
        //按时间先后顺序排序
        for (int endTime = 1, i = 0; idx< n;) {
            //当前任务的开始时间 小于上一个任务的结束时间。因此可以加入队列
            while (i < n && tasksWithId[i][0] <= endTime) {
                //当前任务加入队列
                waitQueue.offer(tasksWithId[i++]);
            }

            if (waitQueue.isEmpty()) {
                //如果当前等待队列没任务，直接跳转到下一个任务的入队时间
                endTime = tasksWithId[i][0];
            } else {
                //取出一个任务进行执行
                int[] task = waitQueue.poll();
                ans[idx++] = task[2];
                endTime = endTime + task[1];//加上本任务的运行周期
            }
        }
        return ans;
    }

    /**
     * 1792. 最大平均通过率
     * 要想增加最大通过率，则需要 比较 +1情况下每个班级的通过率的增量，增量大的才需要进行加，增量下的就不加
     * 使用大根堆，比较通过率的增量
     * @param classes
     * @param extraStudents
     * @return
     */
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                //大根堆 增量=(1+c)/(c+d) -c/d
                double increasea=incr(a);
                double increaseb=incr(b);
                double result = increaseb - increasea;

                return  result>0?-1:1;
            }
        });

        for (int[] clazz : classes) {
            priorityQueue.offer(clazz);
        }

        while (extraStudents > 0) {
            int[] clazz = priorityQueue.poll();
            //增加1 在进入进去
            priorityQueue.offer(new int[]{clazz[0] + 1, clazz[1] + 1});
            extraStudents--;
        }

        //统计平均通过率

        double sumRat = 0;
        while (!priorityQueue.isEmpty()) {
            int[] clazz = priorityQueue.poll();
            sumRat += 1.0 * clazz[0] / clazz[1];
        }

        //在进行平均
        return sumRat / classes.length;
    }

    private double incr(int[] o) {
        return (o[1] - o[0]) / ((double) o[1] * (o[1] + 1));
    }


    /**
     * 1882. 使用服务器处理任务
     * 使用双堆
     * 空闲服务器：按权重和索引下标排序
     * 忙碌的服务器：按任务的结束时间进行排序
     * 线性模拟 任务的执行，然后从空闲服务器中获取任务：如果空闲服务器没有了，则向 忙碌的服务器 获取最先结束的那个机器执行任务
     * @param servers
     * @param tasks
     * @return
     */
    public int[] assignTasks(int[] servers, int[] tasks) {

        //int[]：权重、机器编号、结束时间
        PriorityQueue<int[]> idelQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                //按权重、机器编号进行升序排列
                if (a[0] != b[0]) {
                    return a[0] - b[0];
                }
                return a[1] - b[1];
            }
        });

        //加入空闲机器
        for (int i = 0; i < servers.length; i++) {
            idelQueue.offer(new int[]{servers[i], i, 0});
        }
        //权重、机器编号、结束时间
        PriorityQueue<int[]> buys = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[2] != b[2]) {
                    return a[2] - b[2];
                } else {
                    if (a[0] != b[0]) {
                        return a[0] - b[0];
                    } else {
                        return a[1] - b[1];
                    }
                }
            }
        });

        int ans[] = new int[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            //当前运行的人物的结束时间 小于等于当前时间 因此当前任务结束了
            while (!buys.isEmpty() && buys.peek()[2] <= i) {
                idelQueue.offer(buys.poll());
            }

            if (!idelQueue.isEmpty()) {
                //
                int[] server = idelQueue.poll();
                ans[i] = server[1];
                server[2] = i + tasks[i];//计算结束时间：当前时刻 加上运行时间
                buys.offer(server);
            } else {
                //选择一个最先结束的忙碌任务
                int[] server = buys.poll();
                ans[i] = server[1];
                server[2] += tasks[i];//计算结束时间：当前时刻 加上运行时间
                buys.offer(server);
            }
        }

        return ans;
    }


    /**
     * 2402. 会议室 III
     * 解决方案：使用空闲的集合存储空闲的会议室，按id 递增排列
     * 使用忙碌的会议号 集合，使用结束时间、会议号进行递增 排序
     * 类似上面的服务器处理时间
     * @param n
     * @param meetings
     * @return
     */
    public int mostBooked(int n, int[][] meetings) {

        PriorityQueue<Integer> idleQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            idleQueue.offer(i);
        }

        //结束时间：会议室编号
//        为啥使用long ？防止有溢出
        PriorityQueue<long[]> busyQueue = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] a, long[] b) {
                if (a[0] != b[0]) {
                    return (int) (a[0]- b[0]);
                }
                return (int) (a[1] - b[1]);
            }
        });

        Arrays.sort(meetings, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        int cnt[] = new int[n];

        for (int[] meet : meetings) {

            //释放当前已经结束的会议室号
            while (!busyQueue.isEmpty() && busyQueue.peek()[0] <= meet[0]) {
                idleQueue.offer((int) busyQueue.poll()[1]);
            }

            //从空闲中获取会议室
            if (!idleQueue.isEmpty()) {
                int meetingId = idleQueue.poll();
                cnt[meetingId]++;
                //进入忙碌时刻
                busyQueue.offer(new long[]{meet[1], meetingId});
            } else {
                //从最先结束的会议室 获取当前会议需要的会议室
                long[] bb = busyQueue.poll();
                cnt[(int) bb[1]]++;
//                bb[0]-meet[0];// 等待时间,等待bb这个任务完成的时间,然后在加上当前任务的结束时间，就是当前任务的最终结束时间
                bb[0] = meet[1]+(bb[0]-meet[0]);
                busyQueue.offer(bb);
            }
        }

        int maxFreIdx = 0;
        for (int i=0;i<n;i++) {
            if (cnt[i]>cnt[maxFreIdx]){
                maxFreIdx=i;
            }
        }
        return maxFreIdx;
    }
}

