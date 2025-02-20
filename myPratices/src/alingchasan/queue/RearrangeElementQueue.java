package alingchasan.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 主体：重新排列元素：就是一堆元素个数 不相同，需要按照一定的规则进行重新排列(不相邻、不包含 aaa等结构)，判断是否能排列 或者是否排列完成的最短时间等
 * 总体方案：使用 大根堆：按元素的个数进行排序，优先使用数量多的前一个或者前两个进行，然后在进行入队。就这样循环
 */
public class RearrangeElementQueue {

    public static void main(String[] args) {
        RearrangeElementQueue test=new RearrangeElementQueue();
        test.rearrangeBarcodes(new int[]{1,1,1,1,2,2,3,3});
    }

    /**
     * 984. 不含 AAA 或 BBB 的字符串
     * 方案：优先使用多的字符
     * @param a
     * @param b
     * @return
     */
    public String strWithout3a3b(int a, int b) {

        StringBuilder sb = new StringBuilder();

        while (a > 0 || b > 0) {
            boolean writeA = false;
            //判断已经有两个字符一样了
            if (sb.length() >= 2 && sb.charAt(sb.length() - 1) == sb.charAt(sb.length() - 2)) {
                if (sb.charAt(sb.length() - 1) == 'b') {
                    writeA = true;
                }
            } else {
                //优先选择字符多的
                if (a >= b) {
                    writeA = true;
                }
            }
            if (writeA) {
                sb.append('a');
                a--;
            } else {
                sb.append('b');
                b--;
            }
        }
        return sb.toString();
    }


    /**
     * 767. 重构字符串
     * 相邻元素不相同: 统计元素个数，然后放入大根堆中，优先使用字符个数多的，如果当前字符和上一个字符一样，则使用第二大字符
     * @param s
     * @return
     */
    public String reorganizeString(String s) {

        int[] cnt = new int[26];
        int max = 0;

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
            max = Math.max(max, cnt[c - 'a']);
        }
        //入队列:大根堆
        PriorityQueue<Character> maxQueue = new PriorityQueue<>((a, b) -> cnt[b - 'a'] - cnt[a - 'a']);

        for (char i = 'a'; i <= 'z'; i++) {
            if (cnt[i - 'a'] > 0) {
                maxQueue.offer(i);
            }
        }
//        if (maxQueue.size()==1&&cnt[]){
//            return "";
//        }

        StringBuilder stringBuilder = new StringBuilder();
        while (!maxQueue.isEmpty()) {
            //只剩一个字符且与已经拼接的最后一个字符一样
            if (maxQueue.size() == 1 && cnt[maxQueue.peek() - 'a'] >= 1 &&stringBuilder.length()>0&& maxQueue.peek() == stringBuilder.charAt(stringBuilder.length() - 1)) {
                return "";
            }
            Character character = maxQueue.poll();
            if (stringBuilder.length() == 0 || character != stringBuilder.charAt(stringBuilder.length() - 1)) {
                stringBuilder.append(character);
                cnt[character - 'a']--;
                //剩余还有字符
                if (cnt[character - 'a'] > 0) {
                    maxQueue.offer(character);
                }
            } else {
                //使用第二最大个字符

                Character second = maxQueue.poll();

                stringBuilder.append(second);
                cnt[second - 'a']--;
                //剩余还有字符
                if (cnt[second - 'a'] > 0) {
                    maxQueue.offer(second);
                }

                maxQueue.offer(character);

            }
        }

        return stringBuilder.toString();
    }


    /**
     * 1054. 距离相等的条形码:不相邻排列
     *
     * @param barcodes
     * @return
     */
    public int[] rearrangeBarcodes(int[] barcodes) {

        Map<Integer, Integer> mapCnt = new HashMap<>();

        //统计个数
        for (int value : barcodes) {
            mapCnt.put(value, mapCnt.getOrDefault(value, 0) + 1);
        }

        //进入优先级队列
        PriorityQueue<int[]> maxQueue = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        mapCnt.entrySet().forEach(entry -> {
            maxQueue.offer(new int[]{entry.getValue(), entry.getKey()});
        });

        int ans[] = new int[barcodes.length];
        int index = 0;
        while (!maxQueue.isEmpty()) {
            //第一个或者不等于上一个
            if (index == 0 || maxQueue.peek()[1] != ans[index-1]) {
                int p[] = maxQueue.poll();
                ans[index++] = p[1];
                p[0]--;
                if (p[0] > 0) {
                    maxQueue.offer(p);
                }
            } else {
                int p[] = maxQueue.poll();
                //使用第二个最大的
                int second[] = maxQueue.poll();
                ans[index++] = second[1];
                second[0]--;
                if (second[0] > 0) {
                    maxQueue.offer(second);
                }
                maxQueue.offer(p);
            }
        }
        return ans;
    }

    /**
     * 1953. 你可以工作的最大周数
     * 限制：同一个项目组的项目不能重复
     * @param milestones
     * @return
     */
    public long numberOfWeeks(int[] milestones) {

        return 0L;
    }

}
