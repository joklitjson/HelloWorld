package pq;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PrioryQueueMain {

    public static void main(String[] args) {

        System.out.println(magicTower(new int[]{-9635,71923,-37495,8366,54303,-86422,-48303,-47858,98424}));
        System.out.println(magicTower2(new int[]{-9635,71923,-37495,8366,54303,-86422,-48303,-47858,98424}));
//        System.out.println(magicTower2(new int[]{100,100,100,-250,-60,-140,-50,-50,100,150}));
//
//        PriorityQueue<Integer> pq1 = new PriorityQueue<Integer>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2-o1;
//            }
//        });
//
//        for (int i=1;i<5;i++){
//            pq1.offer(i);
//        }
//        while (!pq1.isEmpty()){
//            System.out.println(pq1.poll());
//        }

    }

    /**
     * https://leetcode.cn/problems/minimum-number-of-refueling-stops/solutions/665147/xian-gao-ming-bai-qi-che-wei-shi-yao-xu-qkpug/
     * 最小的加油次数
     * 题意：一辆汽车将路上遇到的汽油，都放在自己的后备箱中，没油的时候，取最大的一桶加上，直到到达终点，问用了几桶油。fuel是汽车当前的油量
     * 整体的思路：把消耗收集起来
     * 此题本意还是消耗过程，我们采用逆向思维：化消耗过程为收集过程。
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {

        //初始容量已经满足
        if (startFuel >= target) {
            return 0;
        }

        //创建大根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });

        int pre = 0, ans = 0, fuel = startFuel;

        for (int i = 0; i < stations.length; i++) {

            //到达第i个加油站
            int cost = stations[i][0] - pre;//从上个位置 到当前位置的消耗
            fuel -= cost;

            //邮箱的油不够了，需要取出加油
            while (fuel < 0 && !queue.isEmpty()) {
                fuel += queue.poll();//取最大的油包
                ans++;
            }
            //不能到达目的地
            if (fuel < 0) {
                return -1;
            }
            //将当前油站的油包放在车上
            queue.offer(stations[i][1]);
            pre = stations[i][0];//记录上一个站点
        }

        //记录终点到最后一个加油站的距离
        fuel = fuel - (target - pre);

        while (fuel < 0 && !queue.isEmpty()) {
            fuel += queue.poll();//取最大的油包
            ans++;
        }
        return fuel < 0 ? -1 : ans;
    }

    /**
     * 魔塔项目，调整关卡
     * @param nums
     * @return
     */
    public static int magicTower(int[] nums) {
        long sum = 1;

        //创建小跟堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        int ans = 0;
        int delay = 0;//延迟扣血
        for (int i = 0; i < nums.length; i++) {

            //记录消耗的值
            if (nums[i] < 0) {
                queue.offer(nums[i]);
            }

            //记录当前的血值
            sum += nums[i];
            if (sum <= 0) {
                ans++;
                if (!queue.isEmpty()){
                    int tmp=queue.poll();
                    sum -= tmp;
                    delay += tmp;
                }

            }

        }
        return (sum + delay) <= 0 ? -1 : ans;
    }
    public static int magicTower2(int[] nums) {

        int delay = 0, ans = 0;
        long sum = 1;

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for (int value : nums) {

            if (value < 0) {
                priorityQueue.add(value);
            }

            sum += value;
            if (sum <= 0) {
                //反悔一个最大的
                ans++;
                if (!priorityQueue.isEmpty()){
                    int tmp= priorityQueue.poll();//先记账 现在你扛不住 最后再打你
                    delay +=tmp;
                    sum -= tmp;//回血
                }

            }

        }
//记账结算
        sum+=delay;
        return sum  <= 0 ? -1 : ans;
    }

    /**
     * 给你一个整数数组 heights ，表示建筑物的高度。另有一些砖块 bricks 和梯子 ladders 。
     *
     * 你从建筑物 0 开始旅程，不断向后面的建筑物移动，期间可能会用到砖块或梯子。
     *
     * 当从建筑物 i 移动到建筑物 i+1（下标 从 0 开始 ）时：
     *
     * 如果当前建筑物的高度 大于或等于 下一建筑物的高度，则不需要梯子或砖块
     * 如果当前建筑的高度 小于 下一个建筑的高度，您可以使用 一架梯子 或 (h[i+1] - h[i]) 个砖块
     * 如果以最佳方式使用给定的梯子和砖块，返回你可以到达的最远建筑物的下标（下标 从 0 开始 ）
     *
     * 对于我们每次往上走的过程，能够确定的是如果这个高度差越大，我们尽可能的优先选用梯子，这是个贪心的思想。也就是说，对于N个高度差，我们希望最大的K个高度差使用梯子来解决，这里的K其实就是ladders梯子个数，剩下的高度我们用砖头解决。所以我们需要通过优先级队列来记录当前所有的高度差，如果队列长度大于梯子数，则取出最小的那个高度差，将其分配给砖头使用（这样能够保证ladders个高度差是最大的）
     *
     * 建立一个最小堆，记录当前的高度差。遍历所有建筑物，遇到当前比前一个高度要大情况下我们将其将入到优先级队列，队列长度大于梯子数则取出堆顶元素（最小的）判断是否可以用砖头解决，如果不行则返回前一次的高度作为答案
     *
     * @param heights
     * @param bricks
     * @param ladders
     * @return
     */
    public int furthestBuilding(int[] heights, int bricks, int ladders) {

        long sum = 0;
        PriorityQueue<Integer> maxBricks = new PriorityQueue<>();
        for (int i = 0; i < heights.length - 1; i++) {
            if (heights[i] >= heights[i + 1]) {
                continue;
            } else {
                //高度差 计入进来
                maxBricks.offer(heights[i + 1] - heights[i]);
                //优先消耗梯子，大于梯子的高度差，则在使用砖头
                if (maxBricks.size() > ladders) {
                    sum += maxBricks.poll();//已经使用的砖头数
                }
            }
            //跳不过去了
            if (sum > bricks) {
                return i;
            }
        }

        return heights.length - 1;
    }


    /**
     * 吃苹果问题，每一天产生的苹果有过期时间，因此需要优先吃块过期的苹果
     * @param apples
     * @param days
     * @return
     */
    public int eatenApples(int[] apples, int[] days) {

        int i = 0, n = apples.length;

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                //小跟堆，优先把小的有效天数吃掉
                return o1[0] - o2[0];
            }
        });

        int ans = 0;
        while (i < n) {

            //优先清理过期的
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[0] <= i) {
                priorityQueue.poll();
            }

            if (apples[i]>0){
                //加入当前苹果
                priorityQueue.offer(new int[]{days[i] + i, apples[i]});
            }
            //消耗一个苹果
            if (!priorityQueue.isEmpty()) {
                int[] current = priorityQueue.peek();
                current[1]--;
                //没有苹果的则剔除
                if (current[1] == 0) {
                    priorityQueue.poll();
                }
                //吃了一个苹果
                ans++;
            }
            //进行第二天的判断
            i++;
        }

        //过了n天之后
        while (!priorityQueue.isEmpty()) {
            //优先清理过期的
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[0] <= i) {
                priorityQueue.poll();
            }
            if (priorityQueue.isEmpty()) {
                break;
            }

            int[] current = priorityQueue.poll();


            //取苹果数和过期数的最小值,表示直接过了n天
            int min = Math.min(current[1], current[0] - i);
            ans += min;
            i += min;
        }

        return ans;

    }
}
