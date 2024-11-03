package binary;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * 安排日程，判断日程是否有冲突
 * 方案一：直接遍历，判断是否有交集
 * 方案：需要一个 有序集合保持日程顺序，
 * 2、找到要插入日程的前一个日程和一个日程，判断是否有交集
 */
class MyCalendar {
    TreeSet<int[]> books;
    public MyCalendar() {
        books=new TreeSet<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                //按开始时间升序排列
                return o1[0]-o2[0];
            }
        });
    }
    
    public boolean book(int start, int end) {
        int[] element = new int[]{start, end};

        //下雨target 的第一个元素
        int[] pre = books.floor(element);

        //大于目标元素的第一个元素
        int[] next = books.ceiling(element);

        //与前一个元素的结尾元素相交或与后一个元素的开头相交
        if ((pre != null && pre[1] > start) || (next != null && next[0] < end)) {
            return false;
        }
        books.add(element);
        return true;
    }
}