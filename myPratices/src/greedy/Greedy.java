package greedy;

import java.util.ArrayList;
import java.util.List;

public class Greedy {

    public static void main(String[] args) {
        System.out.println(getMaxValue(new int[]{9,1,2,44,23,60,12}));
    }
    /**
     * 双指针 求数组最大值:从两侧进入比较
     * 也可以求最小值
     * @param A
     * @return
     */
    public static int getMaxValue(int[] A/*输入保证非空*/) {
        final int N = A == null ? 0 : A.length;
        int i = 0, j = N - 1;
        while (i < j) {
            if (A[i] > A[j]) {
                j--;
            } else {
                i++;
            }
        }
        return A[i];
    }

    /**
     * 分隔字符串，让相同字符的分在一个分段中
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        int[] lastPos = new int[26];
        List<Integer> ans = new ArrayList<>();
        //记录每个字符的最后位置
        for (int i = 0; i < s.length(); i++) {
            lastPos[s.charAt(i) - 'a'] = i;
        }

        // 记录start、end区间

        int start = 0, end = 0, i = 0;
        while (i < s.length()) {
            end = Math.max(end, lastPos[s.charAt(i) - 'a']);
            //当前位置i 是区间[strat,end]区间内所有字符的最右侧区间
            if (i == end) {
                ans.add(end - start + 1);
                //新开启一个区间
                start = end + 1;
            }
            i++;
        }
        return ans;
    }

    /**
     * 环形加油站
     * 使用贪心算法
     * @param G
     * @param C
     * @return
     */
    int canCompleteCircuit(int[] G, int[] C) {

        int left = 0,total=0;
        int start = 0;//先从第一个点开始,
        int ans = 0;
//        如果当前加油站补充的油小于当前行程需要的油，则需要新开起点

        for (; start < G.length; start++) {
            int add = G[start];
            int cost = C[start];
            total+=add-cost;
            if (left + add >= cost) {
                left = left + add - cost;
            } else {
                //从下个起点开始重新 计算 是否满足条件
                left = 0;
                ans = start + 1;
            }
        }
        return total >= 0 ? ans : -1;
    }
    }
