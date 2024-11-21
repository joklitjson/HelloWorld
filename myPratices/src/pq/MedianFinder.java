package pq;

import java.util.PriorityQueue;

/**
 * LCR 160. 数据流中的中位数
 * 可以联想薯片桶算法：保证两个薯片桶内的元素差在1个之内，就是left可以多余right 一个元素
 * 如果要插入的元素 小于等于左边大顶堆的堆顶，则应该插入左边，反之如果元素要大于right的顶部元素，则应该插入到右边
 * left：大顶堆
 * right：小顶堆
 */
public class MedianFinder {

    private PriorityQueue<Integer> left=new PriorityQueue<>((a,b)->b-a);
    PriorityQueue<Integer> right=new PriorityQueue<>();
    public MedianFinder() {
        
    }
    
    public void addNum(int num) {
        if (left.isEmpty()||left.peek()>=num){
            left.offer(num);
        }
        else {
            right.offer(num);
        }

        //调整两个集合的元素数量关系:left最多比right多一个元素？还没想明白为啥
        if (left.size()- right.size()>1){
            right.offer(left.poll());
        }
        else if (right.size()> left.size()){
            left.offer(right.poll());
        }
    }
    
    public double findMedian() {

        if (left.size()==right.size()){
            return (double)(left.peek()+ right.peek())/2.0;
        }
        else {
            return (double)left.peek();
        }
    }
}