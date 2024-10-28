package greedy;

import java.util.Arrays;

public class FindContentChildren {

    public static void main(String[] args) {
        System.out.println(findContentChildren(new int[]{1,2,3},new int[]{1,1}));
    }
    /**
     *
     * 此题考察贪心算法：
     * 1、然后从后向前遍历小孩数组，用大饼干优先满足胃口大的，并统计满足小孩数量。
     *
     * 这里的局部最优就是大饼干喂给胃口大的，充分利用饼干尺寸喂饱一个，全局最优就是喂饱尽可能多的小孩。
     * 大胃口需要大饼干，先遍历胃口
     *
     * 都有一个胃口值 g[i]
     * 并且每块饼干 j，都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i
     *
     * @param g
     * @param s
     * @return
     */
    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;

        int idx=s.length-1;//饼干的数组
        for (int j=g.length-1;j>=0;j--){//遍历胃口

            if (idx>=0&&s[idx]>=g[j]){//判断饼干是否大于胃口
                count++;
                idx--;
            }
        }
        return count;
    }
}
