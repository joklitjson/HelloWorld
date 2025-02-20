package alingchasan.queue;

import java.util.PriorityQueue;

/**
 * 反悔堆：
 * 遍历过程中使用堆记录 已经消耗的物品()，然后在未来某个时间 可能反悔 不消耗他们，然后在堆中选择一个最大的进行反悔
 * 相关例题：魔塔游戏、加油次数、最远到达的建筑等
 */
public class RenegeHeap {

    /**
     * LCP 30. 魔塔游戏
     *
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
                if (!queue.isEmpty()) {
                    int tmp = queue.poll();
                    sum -= tmp;
                    delay += tmp;
                }
            }
        }
        return (sum + delay) <= 0 ? -1 : ans;
    }
}
