package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class TrapWater {
    public static void main(String[] args) {

        int [] arr=new int[]{4,2,0,3,2,5};
        System.out.println(trap(arr));
        System.out.println(trap2(arr));
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

    /**
     * 前后缀分解:  构建左右最大值前后缀
     * @param height
     * @return
     */
    public  static int trap2(int[] height) {

        int [] preMax=new int[height.length];
        int sufMax[] =new int[height.length];
        preMax[0]=height[0];
        for (int i=1;i< height.length;i++){
            preMax[i]=Math.max(height[i],preMax[i-1]);
        }

        sufMax[height.length-1]=height[height.length-1];
        for (int i=height.length-2;i>=0;i--){
            sufMax[i]=Math.max(height[i],sufMax[i+1]);
        }

        int ans=0;
        for (int i=0;i< height.length;i++){
            //左右最小值 在减去当前元素的高度，就是当前元素能接的最大水量
            ans+=Math.min(sufMax[i], preMax[i])-height[i];
        }
        return  ans;
    }

}
