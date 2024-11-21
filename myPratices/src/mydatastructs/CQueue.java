package mydatastructs;

import java.util.Stack;

/**
 * LCR 125. 图书整理 II
 * 使用双栈 模拟队列，可以联想两个薯片桶
 * https://leetcode.cn/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/solutions/1042283/ci-shi-ni-xu-yao-liang-ge-shu-pian-tong-z0jxf/
 */
public class CQueue {

    Stack<Integer> inStack=new Stack<>();
    Stack<Integer> outStack=new Stack<>();
    public CQueue() {
        
    }
    
    public void appendTail(int value) {
        //把出栈的元素压入 原始的栈中
        while (!outStack.isEmpty()){
            inStack.push(outStack.pop());
        }
        inStack.push(value);
    }
    
    public int deleteHead() {

        if (outStack.isEmpty() && inStack.isEmpty()) {
            return -1;
        }
        if (!outStack.isEmpty()) {
            return outStack.pop();
        }

        //先把栈一的元素都压入到出栈中，这样就能够获得了 栈顶元素
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
        return outStack.pop();
    }
}