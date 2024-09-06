package greedy;

import java.util.*;

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
     *
     * @param intvs
     * @return
     */
    int findMinArrowShots(int[][] intvs) {
        Arrays.sort(intvs, (a, b) -> a[1] - b[1]);

        int count = 1;
        int currentEnd = intvs[0][1];

        for (int i = 1; i < intvs.length; i++) {
            int nextStart = intvs[i][0];
            if (nextStart > currentEnd) {
                count++;
                currentEnd = intvs[i][1];
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
        int currentEnd = 0, nextEnd = 0;
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
     *
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


    //区间合并问题
    //先按start升序，end降序排列,如果下一个区间的左边界在上一个区间的之间，则扩大右边界
    public int[][] merge(int[][] intervals) {

        Arrays.sort(intervals,(a,b)->{
            if (a[0]==b[0]){
                return b[0]-a[0];
            }
            return a[0]-b[0];
        });

        List<int[]> mergeList=new ArrayList<>();
        int start=intervals[0][0];
        int end=intervals[0][1];


        for (int i=1;i<intervals.length;i++){

            if (intervals[i][0]<=end){
                //合并区间
                end=Math.max(intervals[i][1],end);
            }
            else {
                //不想交，新开一个区间
                mergeList.add(new int[]{start,end});
                start=intervals[i][0];
                end=intervals[i][1];
            }
        }
        mergeList.add(new int[]{start,end});
        return mergeList.toArray(new int[mergeList.size()][1]);
    }


    //求区间列表的交集，每个区间中的元素都是不想交的
    //方案：定义两个指针 一起遍历他们俩数组，如果两个线段有交集，在求交集，然后end小的一方在向前移动
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {

        List<int[]> res=new ArrayList<>();
        int i=0,j=0;

        while (i<firstList.length&&j<secondList.length){
            // 判断有交集的情况
            int aStart=firstList[i][0],aEnd=firstList[i][1];
            int bStart=secondList[j][0],bEnd=secondList[j][1];

            if (aStart>bEnd||aEnd<bStart){
                //两则没有交集
            }
            else {
                //右交集
                res.add(new int[]{Math.max(aStart,bStart),Math.min(aEnd,bEnd)});
            }
            //移动指针：谁小谁移动
            if (aEnd<bEnd){
                i++;
            }
            else {
                j++;
            }
        }

        return res.toArray(new int[res.size()][2]);
    }
    public static void main(String[] args) {

        int res = new IntervalSchedule().videoStitching(new int[][]{{0, 2}, {4, 6}, {8, 10}, {1, 9}, {1, 5}, {5, 9}}, 10);
        System.out.println("videoStitching=" + res);

//        int[][] merged= new IntervalSchedule().merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});
        int[][] merged= new IntervalSchedule().merge(new int[][]{{1,3},{3,6}});
        System.out.println("区间合并结果===:");
        for (int [] range:merged){
            System.out.print(Arrays.toString(range)+" ");
        }

        System.out.println();
        int[][] intervalIntersection= new IntervalSchedule().intervalIntersection(new int[][]{{0,2},{5,10},{13,23},{24,25}},new int[][]{{1,5},{8,12},{15,24},{25,26}});

        System.out.println("区间交集结果===:");
        for (int [] range:intervalIntersection){
            System.out.print(Arrays.toString(range)+" ");
        }
    }

    public int[][] merge2(int[][] intervals) {
        //优先按照区间的起点排序，起点相同的，按终点升序

        Arrays.sort(intervals,(a,b)->{
            return a[0]!=b[0]?a[0]-b[0]:a[1]-b[1];
        });

        List<int[]> ans=new ArrayList<>();
        int start=intervals[0][0],end=intervals[0][1];
        for (int[] one:intervals){
            if (one[0]<=end){
                //合并区间
                end=Math.max(one[1],end);
            }
            else {
                ans.add(new int[]{start,end});
                //新开启一个区间
                start=one[0];
                end=one[1];
            }
        }
        ans.add(new int[]{start,end});

        return ans.toArray(new int[0][0]);
    }


    /**
     * 狒狒吃香蕉
     * 二分遍历吃香蕉的速度，分别判断当前速度是否满足条件
     * 最小速度是
     * @param piles
     * @param h
     * @return
     */
    public int minEatingSpeed(int[] piles, int h) {

        int lowSpeed = 1, maxSpeed = 1;
        for (int val : piles) {
            maxSpeed = Math.max(val, maxSpeed);
        }
        while (lowSpeed < maxSpeed) {
            int mid = (maxSpeed - lowSpeed) / 2 + lowSpeed;
            if (canFinish(mid, piles, h)) {
                maxSpeed = mid;
            } else {
                lowSpeed = mid + 1;
            }
        }
        return lowSpeed;
    }

    private boolean canFinish(int speed,int[] piles, int h){
        int cost=0;
        for (int val:piles){
            //向上取整
            cost+=(val-1)/speed+1;
            //已经超时了
            if (cost>h){
                return false;
            }
        }
        return true;
    }


    /**
     * 求平方根：列入二分法求
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        int ans = 1;
        int low = 1, hi = x;
        while (low < hi) {
            int mid = (hi - low) / 2 + low;
            if ((long) mid * mid <= x) {
                //缩小middle
                ans = mid;
                low = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }


     Random random=null;
    //构造前缀和数组，然后再根据最大值生成一个随机数，然后再前缀和数组中进行 二分查找小于目标元素最右侧的元素 下标
    int prefixSum[]=null;
    int sum=0;
    public void Solution(int[] w) {
        random=new Random();
        prefixSum=new int[w.length];
        for (int i=0;i<w.length;i++){
            prefixSum[i]=sum+w[i];
            sum=prefixSum[i];
        }
    }

    public int pickIndex() {
        int target = random.nextInt(sum) + 1;

        int left = 0, right = prefixSum.length - 1;
        while (left < right) {
            int middle = (right - left) / 2 + left;
            if (target <= prefixSum[middle]) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        //获取
        return right;
    }
}

