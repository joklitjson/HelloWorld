package mydatastructs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    public static void main(String[] args) {

        int[] nums= new MonotonicQueue().maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},3);
        System.out.println(Arrays.toString(nums));

    }
}
