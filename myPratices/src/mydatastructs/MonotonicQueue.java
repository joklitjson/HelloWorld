package mydatastructs;

import java.util.*;

public class MonotonicQueue {

    LinkedList<Integer> queue=new LinkedList<>();

    private int size=0;
    public void push(Integer element){
        while (!queue.isEmpty()&&element>queue.getLast()){
            queue.removeLast();
        }
        queue.addLast(element);
        size++;
    }
    public  void pop(int element){
        if (queue.getFirst()==element){
            queue.pollFirst();
        }
    }
    public int max(){
        return queue.getFirst();
    }

    public int mim(){
        return queue.getLast();
    }

    /* 解题函数的实现 */
    int[] maxSlidingWindow(int[] nums, int k) {
        //滑动窗口求最值

        MonotonicQueue monotonicQueue = new MonotonicQueue();
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                monotonicQueue.push(nums[i]);
            } else {
                monotonicQueue.push(nums[i]);
                res.add(monotonicQueue.max());
                monotonicQueue.pop(nums[i - k +1]);
            }
        }

        int[] re = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            re[i] = res.get(i);
        }
        return re;
    }


    /**
     * 使用优先级队列，存储value,index二元组，就是大根堆，堆顶是最大值，每次看看最大值是否在窗口范围内
     * @param nums
     * @param k
     * @return
     */
    static int[] maxSlidingWindow2(int[] nums, int k) {

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] != o2[0] ? o2[0] - o1[0] : o2[1] - o1[1];
            }
        });

        for (int i = 0; i < k; i++) {
            priorityQueue.add(new int[]{nums[i], i});
        }
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        //第一个最大值
        ans[0] = priorityQueue.peek()[0];

        for (int i = k; i < n; i++) {
            //入队列
            priorityQueue.add(new int[]{nums[i], i});
            //不在窗口内
            while (priorityQueue.peek()[1] <= i - k) {
                priorityQueue.poll();
            }
            //新的最大值
            ans[i - k + 1] = priorityQueue.peek()[0];
        }
        return ans;
    }
    public static void main(String[] args) {

        int[] nums= new MonotonicQueue().maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},3);
        System.out.println(Arrays.toString(nums));

        int[] nums2= maxSlidingWindow2(new int[]{1,3,-1,-3,5,3,6,7},3);
        System.out.println(Arrays.toString(nums2));

    }
}
