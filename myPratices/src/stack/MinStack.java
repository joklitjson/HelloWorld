package stack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LCR 147. 最小栈
 * 解决方案：一个栈是主栈 stack，另一个是辅助栈 minStack，用于存放对应主栈不同时期的最小值。
 */
public class MinStack {

    private Deque<Integer> stack=new LinkedList<>();
    private Deque<Integer> minStack=new LinkedList<>();
    public MinStack() {
        minStack.push(Integer.MAX_VALUE);//优先放入一个值，为了后面方便比较栈顶元素和新进入元素的大小
    }
    
    public void push(int x) {
        stack.push(x);
        minStack.push(Math.min(stack.peek(),x));
    }
    
    public void pop() {
        stack.pop();
        minStack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}