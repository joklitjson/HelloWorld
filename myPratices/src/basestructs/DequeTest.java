package basestructs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class DequeTest {
    public static void main(String[] args) {
        Deque<Integer> stringDeque=new LinkedList<>();

        stringDeque.addFirst(2);
        stringDeque.addFirst(5);
        stringDeque.addFirst(6);

        System.out.println(stringDeque.element());
        System.out.println(stringDeque);

        stringDeque.offerLast(77);

        stringDeque.offerLast(88);

        System.out.println(stringDeque);
        int [] arr=new int[]{4,2,0,3,2,5};
        System.out.println(trap(arr));;
    }

    public  static int trap(int[] height) {
        int ans=0;
        Deque<Integer> deque=new ArrayDeque<>();
        for (int i=0;i<height.length;i++){
            while (!deque.isEmpty()&&height[i]>height[deque.peek()]){
                 int top= deque.pop();
                 //左边没元素了，所以不能接水
                 if (deque.isEmpty()){
                    continue;
                 }
                 int left=deque.peek();
                 int width=i-left-1;
//                 计算高度，左右两边的最小值，然后减去中间元素 的高度
                 int minHeight=Math.min(height[left],height[i])-height[top];
                 ans+=width*minHeight;
            }
            deque.push(i);
        }
        return ans;
    }
}
