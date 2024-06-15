package mydatastructs;

import java.util.LinkedList;
import java.util.Queue;

//使用队列模拟栈:队尾元素即是栈顶元素
public class MyStackWithQueue {

    Queue<Integer> queue=new LinkedList<>();
    Integer topElement;//栈顶元素，插入时是最后一个元素时栈顶，出栈后再更新
    /** 添加元素到栈顶 */
    public void push(int x){
        queue.offer(x);
        topElement=x;
    }

    /** 删除栈顶的元素并返回 */
    public int pop(){
        int size= queue.size();
        //保留队头的两位，第一位是要出站的，第二位是新的栈顶
        while (size>2){
            //把队头的前年 n-2个元素从新插入到队列中，则之前第一位变成了下一次的栈顶，第二位就是要出栈的元素
            //比如：1<2<3 ----> 3<1<2,此时把1、2再次插入，则3就是变成了队头
            queue.offer(queue.poll());
            size--;
        }

        //记录新的栈顶元素：队尾元素，然后再把他加入到队列的尾部
        topElement=queue.peek();
        queue.offer(queue.poll());

        //最后一位就是要出栈的元素了
        Integer element= queue.poll();

        return element;

    }

    /** 返回栈顶元素 */
    public int top(){
        return topElement;
    }

    /** 判断栈是否为空 */
    public boolean empty(){
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        MyStackWithQueue stackWithQueue=new MyStackWithQueue();
        stackWithQueue.push(1);
        stackWithQueue.push(2);
        stackWithQueue.push(3);

        System.out.println("栈顶元素=="+stackWithQueue.pop());
        System.out.println("栈顶元素=="+stackWithQueue.top());
        System.out.println("栈顶元素=="+stackWithQueue.pop());
    }
}
