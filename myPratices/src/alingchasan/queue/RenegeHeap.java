package alingchasan.queue;

import java.util.*;

/**
 * 反悔堆：
 * 遍历过程中使用堆记录 已经消耗的物品()，然后在未来某个时间 可能反悔 不消耗他们，然后在堆中选择一个最大的进行反悔
 * 相关例题：魔塔游戏、加油次数、最远到达的建筑等
 */
public class RenegeHeap {

    public static void main(String[] args) {
        RenegeHeap renegeHeap=new RenegeHeap();

        renegeHeap.findMaximumElegance(new int[][]{{3,2},{5,1},{10,1}}
                ,2);
    }
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

    /**
     * 630. 课程表 III
     * 方案：按照结束时间进行排序，在进行遍历：先上先结束的课程，如果当前 课程的周期+当前时间 小于结束时间，则可以进行直接上课(当前时间内，在加上这几天的可能也能完成)
     *
     * 如果不能，则看看已经上的课程中的最大周期的 课程是否大于当前周期，如果大于当前周期，则之前的那个课不上了，上当前的课程
     * @param courses
     * @return
     */
    public int scheduleCourse(int[][] courses) {

        int cnt=0;

        //创建大根堆：能更快选择 周期长的课程
        PriorityQueue<Integer> queue=new PriorityQueue<>((a,b)->b-a);

        //按结束时间排序
        Arrays.sort(courses,(a, b)->a[1]-b[1]);

        int days=0;
        for (int i=0;i<courses.length;i++){
            int [] cc= courses[i];
            if (cc[0]+days<=cc[1]){
                //当前周期加上当前时间 可以在结束时间内完成
                queue.offer(cc[0]);
                cnt++;
                days+=cc[0];//当前使用天数
            }
            else if (queue.isEmpty()&&cc[0]<queue.peek()){
                //返回之前的上课，上当前的可能
                days-=queue.poll()-cc[0];
                queue.offer(cc[0]);
            }
        }
        return queue.size();
    }

    /**
     * 2813. 子序列最大优雅度
     *
     * 优雅度：total_profit + distinct_categories2
     * 方案：先按利润进行降序排列，然后使用滑动窗口 统计k个窗口内的种类数,由于利润和在下降，因此当下一个元素
     * 在之前的种类中不存在时，而且去掉一个最小的利润后种类还是增加的情况下， 优雅度才有可能增加
     * 因此：应当移除已选项目中类别和前面重复且利润最小的项目
     * @param items
     * @param k
     * @return
     */
    public long findMaximumElegance(int[][] items, int k) {

        //降序排列
        Arrays.sort(items, (a, b) -> b[0] - a[0]);

        Set<Integer> vis = new HashSet<>();
        Stack<Integer> dummpleItemProfile = new Stack<>();

        long sum = 0;
        long ans = 0;
        for (int i = 0; i < items.length; i++) {
            int profile = items[i][0];
            int catalore = items[i][1];
            if (i < k) {
                sum += profile;
                //这个重复了
                if (!vis.add(catalore)) {
                    dummpleItemProfile.push(profile);
                }
            } else if (!dummpleItemProfile.isEmpty() && vis.add(catalore)) {
                //当前遍历的 项目 的类别在之前不存在，而且之前有重复的项目
                sum -= dummpleItemProfile.pop() - profile;// 弹出一个重复的且最小利润的种类
                vis.add(catalore);//注意：vis 的种类不需要移除
            }
            ans = Math.max(ans, sum + (long)vis.size() * vis.size());
        }
        return ans;
    }
}
