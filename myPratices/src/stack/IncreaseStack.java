package stack;

import java.util.Stack;

/**
 * 单调递增栈
 * 从栈顶到栈低是递增的，
 * https://blog.csdn.net/zy_dreamer/article/details/131036101
 */
public class IncreaseStack {
    public static void main(String[] args) {
//        increaseStack();
        decreaseStack();
    }

    public static void decreaseStack() {
        Stack<Integer> stack=new Stack<>();
        int [] arr=new int[]{3,4,7,9,10,33,5,99};

        for (int value:arr){
            //栈顶元素大于要插入的值，需要弹出该值
            while (!stack.isEmpty()&& stack.peek()>=value){
                stack.pop();
            }
            stack.push(value);
        }
        System.out.print("递减队列从右到左是变小的的：");
        System.out.println(stack);

    }
    public static void increaseStack() {
        Stack<Integer> stack=new Stack<>();
        int [] arr=new int[]{3,4,7,9,10,33,5,99};

        for (int value:arr){
            //栈顶元素小于要插入的值，需要弹出该值
            while (!stack.isEmpty()&& stack.peek()<=value){
                stack.pop();
            }
            stack.push(value);
        }
        System.out.print("递增队列从右到左是变大的：");
        System.out.println(stack);

    }


    /**
     * LCR 148. 验证图书取出顺序
     * 模拟图书入栈的顺序，然后在判断栈顶元素是否和即将要去除的元素一直，一致则可以取出，最后在判断栈是否为空
     * @param putIn
     * @param takeOut
     * @return
     */
    public boolean validateBookSequences(int[] putIn, int[] takeOut) {
        Stack<Integer> stack = new Stack<>();

        int takeOutIdx = 0;
        for (int value : putIn) {
            stack.push(value);
            while (!stack.isEmpty() && stack.peek() == takeOut[takeOutIdx]) {
                stack.pop();
                takeOutIdx++;
            }
        }
        return stack.isEmpty();
    }
}
