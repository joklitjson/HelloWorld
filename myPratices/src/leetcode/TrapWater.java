package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class TrapWater {
    public static void main(String[] args) {

        int [] arr=new int[]{4,2,0,3,2,5};
        System.out.println(trap(arr));
        System.out.println(trap2(arr));
        System.out.println(trap3(arr));
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


    /***
     * 方案3 是有左右指针法，也是最前后缀的优化版本，也是属于竖向接水的
     * @param height
     * @return
     */
    public  static int trap3(int[] height) {
        int ans=0;

        int left=0,right= height.length-1;
        int leftMax=height[left],rightMax=height[right];

        while (left<right){
            //计算最测的最大值
            leftMax=Math.max(leftMax,height[left]);
            rightMax=Math.max(rightMax,height[right]);
            //所有当前是最低又高(一个趋势图)
            if (leftMax<rightMax) {
                //因此当前元素以及左侧能接水,并向右移动
                ans+=leftMax-height[left++];
            }
            else {
                //说明当前是左高右低
                ans+=rightMax-height[right--];
            }
        }
        return ans;
    }

}
