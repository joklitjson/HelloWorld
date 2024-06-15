package mydatastructs;

import java.util.PriorityQueue;

/**
 * 数据流：求中位数：中间两个数的平均数
 *
 * 动态添加数据时要保证两个堆的元素个数相差是1，
 * 同时要保证大顶堆的所有元素都要大于小顶堆的所有元素：因此在往小顶堆中插入元素时 需要先往大顶堆中插入元素，反之也一样
 */
public class MedianFinder {
private PriorityQueue<Integer> larger;
    private PriorityQueue<Integer> small;

    public MedianFinder(){
        this.small=new PriorityQueue<Integer>((a,b)->b-a);//倒三角是大根堆，大元素在上面
        this.larger=new PriorityQueue<Integer>();
    }

    /**
     * 平衡他俩的元素数量
     * @param num
     */
    public void addNum(int num) {

        if (larger.size()>small.size()){
            //向small插入
            larger.offer(num);
            small.offer(larger.poll());
        }
        else {
            //默认向large插入
            small.offer(num);
            larger.offer(small.poll());
        }
    }

    // 计算当前添加的所有数字的中位数
    public double findMedian() {
        if (larger.size()>small.size()){
            return larger.peek();
        }
        else if (larger.size()<small.size()){
            return small.peek();
        }
        //取平均值
        else if (larger.size()==small.size()){
            return (larger.peek()+small.peek())/2.0;
        }
        return -1;
    }

    public static void main(String[] args) {
        MedianFinder medianFinder=new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(4);
        medianFinder.addNum(2);
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
    }
}
