package mydatastructs;

import java.util.Stack;

/**
 * 使用栈实现队列
 * 使用两个栈来实现，一个是入栈，一个是出站
 * 双栈模拟队列
 */
public class MyQueue {
    Stack<Integer> inStack=new Stack<>();
    Stack<Integer> outStack=new Stack<>();

    /** 添加元素到队尾 */
    public void push(int x){
        inStack.push(x);
    }

    /** 删除队头的元素并返回 */
    public int pop(){
        Integer integer= peek();//目的就是为了让 inStack的元素进入到outstack 中
        return outStack.pop();
    }

    /** 返回队头元素 */
    public Integer peek(){
        //从出栈中移除元素
        if (!outStack.empty()) {
            return outStack.peek();
        }

        while (!inStack.empty()){
            outStack.push(inStack.pop());
        }
        return outStack.peek();
    }

    /** 判断队列是否为空 */
    public boolean empty() {
        //两个队列同是空 则队列为空
        return outStack.empty() && inStack.empty();
    }

    public static void main(String[] args) {
        MyQueue queue=new MyQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.print("出队列的顺序:");
        while (!queue.empty()){
            System.out.print(queue.pop()+"、");
        }
        System.out.println();
    }
}
