package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

public class TrapWater {
    public static void main(String[] args) {

        int[] arr = new int[]{4, 2, 0, 3, 2, 5};
        System.out.println(trap(arr));
        System.out.println(trap2(arr));
        System.out.println(trap3(arr));
    }

    public static int trap(int[] height) {
        int ans = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            while (!deque.isEmpty() && height[i] > height[deque.peek()]) {
                int top = deque.pop();
                //左边没元素了，所以不能接水
                if (deque.isEmpty()) {
                    continue;
                }
                int left = deque.peek();
                int width = i - left - 1;
//                 计算高度，左右两边的最小值，然后减去中间元素 的高度
                int minHeight = Math.min(height[left], height[i]) - height[top];
                ans += width * minHeight;
            }
            deque.push(i);
        }
        return ans;
    }

    /**
     * 前后缀分解:  构建左右最大值前后缀
     *
     * @param height
     * @return
     */
    public static int trap2(int[] height) {

        int[] preMax = new int[height.length];
        int sufMax[] = new int[height.length];
        preMax[0] = height[0];
        for (int i = 1; i < height.length; i++) {
            preMax[i] = Math.max(height[i], preMax[i - 1]);
        }

        sufMax[height.length - 1] = height[height.length - 1];
        for (int i = height.length - 2; i >= 0; i--) {
            sufMax[i] = Math.max(height[i], sufMax[i + 1]);
        }

        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            //左右最小值 在减去当前元素的高度，就是当前元素能接的最大水量
            ans += Math.min(sufMax[i], preMax[i]) - height[i];
        }
        return ans;
    }


    /***
     * 方案3 是有左右指针法，也是最前后缀的优化版本，也是属于竖向接水的
     * @param height
     * @return
     */
    public static int trap3(int[] height) {
        int ans = 0;

        int left = 0, right = height.length - 1;
        int leftMax = height[left], rightMax = height[right];

        while (left < right) {
            //计算最测的最大值
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            //所有当前是最低又高(一个趋势图)
            if (leftMax < rightMax) {
                //因此当前元素以及左侧能接水,并向右移动
                ans += leftMax - height[left++];
            } else {
                //说明当前是左高右低
                ans += rightMax - height[right--];
            }
        }
        return ans;
    }

    /**
     * 使用优先级队列，把两头的数据装进去，然后再选择最小的一个
     *
     * @param height
     * @return
     */
    public static int trap4(int[] height) {

        int n = height.length;
        int[] dirs = new int[]{-1, 1};
        boolean[] visitend = new boolean[n];
        PriorityQueue<Integer[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        visitend[0] = visitend[n - 1] = true;
        queue.offer(new Integer[]{0, height[0]});
        queue.offer(new Integer[]{n - 1, height[n - 1]});

        int ans = 0;
        //从队列中去最小的,然后在他的周围找未访问的
        while (!queue.isEmpty()) {
            Integer[] mini = queue.poll();

            for (int i : dirs) {
                int nextIdx = i + mini[0];
                //合法的位置
                if (nextIdx >= 0 && nextIdx <= n - 1 && !visitend[nextIdx]) {

                    if (height[nextIdx] < mini[1]) {
                        ans += mini[1] - height[nextIdx];
                    }
                    //把当前附近的节点加入到集合中，高区取两者的最大值
                    queue.offer(new Integer[]{nextIdx, Math.max(mini[1], height[nextIdx])});
                    visitend[nextIdx] = true;
                }
            }
        }
        return ans;
    }

    /**
     * 3D接雨水项目
     * 1、先把周围的版位加入到队列中，然后寻找最小值，然后再找这个最小值 周围的版位
     *
     * @param heightMap
     * @return
     */
    public int trapRainWater(int[][] heightMap) {

        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        PriorityQueue<Integer[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];


        //边缘数据加入
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    visited[i][j] = true;
                    queue.offer(new Integer[]{i, j, heightMap[i][j]});
                }
            }
        }
        int ans = 0;
        while (!queue.isEmpty()) {
            Integer[] element = queue.poll();
            int x = element[0];
            int y = element[1];
            int height = element[2];
            for (int[] dir : dirs) {
                int nx = dir[0] + x;
                int ny = dir[1] + y;
                if (nx < 0 || nx >= m || ny < 0 || ny >= n || visited[nx][ny]) {
                    continue;
                }
                if (heightMap[nx][ny] < height) {
                    ans += height - heightMap[nx][ny];
                }
                queue.offer(new Integer[]{nx, ny, Math.max(heightMap[nx][ny], height)});
                visited[nx][ny] = true;
            }
        }
        return ans;
    }

}
