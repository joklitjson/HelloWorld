package greedy;

import java.util.Arrays;

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
    }
