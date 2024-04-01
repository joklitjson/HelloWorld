import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {


        LinkedList<Integer> stack=new LinkedList<>();

        /**
         * 双向队列 左边是头，右边是last
         */
        stack.offerFirst(1);
        stack.offerFirst(2);
        stack.offerFirst(3);
        stack.offerLast(6);
        System.out.println(stack);

        System.out.println("Hello world!");
    }
}