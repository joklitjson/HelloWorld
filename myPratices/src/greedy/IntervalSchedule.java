package greedy;

import java.util.Arrays;
import java.util.Map;

public class IntervalSchedule {

    /**
     * 至少有几个不想交的区间
     * 思路：按区间end进行升序排列，在这些区间中查找不想交的区间，不相交的区间的特征就是下一个区间的 start 要大于当前区间的end
     *
     * @param intvs
     * @return
     */
    public int intervalSchedule(int[][] intvs) {

        //先按end进行升序排列
        Arrays.sort(intvs, (a, b) -> {
            return a[1] - b[1];
        });

        int currentEnd = intvs[0][1];
        int notJoinCount = 1;// 至少有一个区间不相交
        for (int i = 1; i < intvs.length; i++) {
            int[] next = intvs[i];
            int nextStart = next[0];
            if (nextStart >= currentEnd) {
                currentEnd = next[1];
                notJoinCount++;
            }
        }
        return notJoinCount;
    }

    /**
     * 找需要删除几个区间，能使区间不重叠，那我们先找到不重叠的区间，在拿总区间数减去不重叠的区间，就能得到需要删除的区间数
     *
     * @param intervals
     * @return
     */
    int eraseOverlapIntervals(int[][] intervals) {

        int n = intervals.length;
        int notJoinCount = intervalSchedule(intervals);

        return n - notJoinCount;
    }

    /**
     * 射气球：
     * 这个问题和区间调度算法一模一样！如果最多有n个不重叠的区间，那么就至少需要n个箭头穿透所有区间：
     * 求 不重叠区间的数量，唯一不同的是 气球的边界相等也重叠
     * @param intvs
     * @return
     */
    int findMinArrowShots(int[][] intvs) {
        Arrays.sort(intvs,(a,b)->a[1]-b[1]);

        int count=1;
        int currentEnd=intvs[0][1];

        for (int i=1;i<intvs.length;i++){
            int nextStart=intvs[i][0];
            if (nextStart>currentEnd){
                count++;
                currentEnd=intvs[i][1];
            }
        }

        return count;
    }

    int videoStitching(int[][] clips, int t) {
        Arrays.sort(clips, (a, b) -> {
            //先按start 升序排列，在按end降序排列
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        int res = 0;
        int currentEnd =0, nextEnd = 0;
        int i = 0, n = clips.length;
        while (i < n && clips[i][0] <= currentEnd) {

            while (i < n && clips[i][0] <= currentEnd) {
                nextEnd = Math.max(nextEnd, clips[i][1]);
                i++;
            }
            res++;
            currentEnd = nextEnd;
            // 已经可以拼出区间 [0, T]
            if (currentEnd >= t) {
                return res;
            }
        }

        //找不到
        return -1;
    }

    /**
     * 删除覆盖区间，返回删除的区间个数
     * :按左端点升序，右端点降序排列
     * @param intvs
     * @return
     */
    int removeCoveredIntervals(int[][] intvs) {

        Arrays.sort(intvs, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - b[0];
            }
            return a[0] - b[0];
        });

        int left = intvs[0][0];
        int right = intvs[0][1];
        int res = 0;
        for (int i = 1; i < intvs.length; i++) {
            int[] range = intvs[i];
            if (range[0] <= left && range[1] <= right) {
                res++;
            } else if (range[0] <= right) {
                right = range[1];
                //更新区间
            } else {
//                range[0]>right
                left = range[0];
                right = range[1];
            }
        }
        return res;
    }
        public static void main(String[] args) {

        int res=new IntervalSchedule().videoStitching(new int[][]{{0,2},{4,6},{8,10},{1,9},{1,5},{5,9}},10);
        System.out.println("videoStitching="+res);
    }
    }
