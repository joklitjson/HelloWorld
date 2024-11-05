package array;

import java.sql.Statement;
import java.util.Arrays;
import java.util.Stack;

/**
 * 求柱状图的最大面积
 * LCR 039. 柱状图中最大的矩形
 */
public class LargestRectangleArea {

    /**
     * 方案一：枚举宽度，使用两个指针，遍历指针区间的的最小高度，然后最大面积=(j-i)*min(hight)
     * 方案二：枚举高度：遍历每一个点，把当前点的高度当做矩形的高度，然后向两边延伸，查找小于此高度的点，则宽度就是左右两边点的差值
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;

        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int minHeight = heights[i];
            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                //求此两点之间的最大矩形
                maxArea = Math.max(maxArea, (j - i + 1) * minHeight);
            }
        }
        return maxArea;
    }

    /**
     * 方案二：向左右两边查找小于当前点的坐标
     * @param heights
     * @return
     */
    public int largestRectangleArea2(int[] heights) {
        int n = heights.length;

        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int currentHight=heights[i];//当前点的高度
            int left=i,right=i;
            while (left>=0&&heights[left]>=currentHight){
                left--;
            }

            while (right<n&&heights[right]>=currentHight){
                right++;
            }
            maxArea=Math.max(maxArea,(right-left-1)*currentHight);
        }

        return  maxArea;
    }

    /**
     * 方案三：在方案二的基础上，我们是否能快速找到某个点的左右两侧的最低点，然后可以快速进行计算
     * 使用单调栈 进行计算
     * @param heights
     * @return
     */
    public int largestRectangleArea3(int[] heights) {
        //左侧第一个比他小的，使用单调递增栈，他比栈顶元素大，则弹出栈顶，直到遇到比他小的栈顶
        //右侧第一个比他小的元素：
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            //记录左边第一个比 left[i]小的元素
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        //计算右侧比i小的第一个元素
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            //记录右边第一个比 left[i]小的元素
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int leftIndex = left[i];
            int rightIndex = right[i];
            maxArea = Math.max(maxArea, (rightIndex - leftIndex -1) * heights[i]);
        }
        return maxArea;
    }

    /**
     * 方案三：在方案三的基础上，我们可以使用一次遍历就能求出 节点i的右边界k，如果节点k
     * k节点把i节点弹出：则k节点是i节点的右边界
     * @param heights
     * @return
     */
    public int largestRectangleArea4(int[] heights) {

        int n=heights.length;
        int left[]=new int[n],right[]=new int[n];
        Arrays.fill(right,n);
        Stack<Integer> stack=new Stack<>();
        for (int i=0;i<n;i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                right[stack.peek()] = i;//说明i是栈顶元素右侧最小的值
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int leftIndex = left[i];
            int rightIndex = right[i];
            maxArea = Math.max(maxArea, (rightIndex - leftIndex -1) * heights[i]);
        }
        return maxArea;
    }

    /**
     * 最大矩形面积：类似最大柱状面积，一每一行为第，计算面积
     * @param matrix
     * @return
     */
    public int maximalRectangle(String[] matrix) {

        if (matrix.length==0){
            return 0;
        }
        int row = matrix.length, colmns = matrix[0].length();

        int height[] = new int[colmns];

        int maxMarea = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colmns; j++) {
                //计算高度
                if (matrix[i].charAt(j) == '0') {
                    height[j] = 0;
                } else {
                    //增加高度
                    height[j]++;
                }
            }
            //再次计算以这一行为底的最大面积
            maxMarea = Math.max(maxMarea, largestRectangleArea4(height));
        }

        return maxMarea;
    }


}
