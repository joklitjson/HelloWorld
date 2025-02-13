package array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlidingWindowTest {

    /**
     * 76. 最小覆盖子串： 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 方案一：使用两个map 统计 目标字符串的种类和个数，然后在根据s字符串，统计当前窗口内的字符种类和个数。如果两个集合中的个数一致，则匹配到了一个答案
     * 核心原理 就是统计元素种类，以及个数。
     * 方案二：优化：使用一个 cnt[] 数组 统计元素对应的种类和个数，然后在使用 一个变量：less 表示s中出现的字符种类小于t中的字符种类的个数。
     * 两次遍历都less进行增减。当less=0时 表明两个字符串中的种类相同了，是一个合理答案
     *
     *
     * @param s
     * @param t
     * @return
     */
    public static String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        int left = 0, right = 0;
        int valid = 0;//符合窗口元素的个数
        Integer start = null;
        int max_len = Integer.MAX_VALUE;
        //记录需要的窗口内各个字符的个数
        for (Character c : t.toCharArray()) {
            int count = need.getOrDefault(c, 0);
            need.put(c, count + 1);
        }

        while (right < s.length()) {
            char current = s.charAt(right);
            right++;
            if (need.containsKey(current)) {
                int currentCount = window.getOrDefault(current, 0);
                //记录某个字符的个数，可能是多余需要的个数的
                window.put(current, currentCount + 1);

                //找到一个字符 c，而且个数也相同了
                if (need.get(current).equals(currentCount + 1)) {
                    valid++;
                }
            }
            //所有个字符都找到了
            while (valid == need.size()) {

                if (right - left < max_len) {
                    max_len = right - left;
                    start = left;
                }
                char leftC = s.charAt(left);
                left++;//缩小窗口
                if (need.containsKey(leftC)) {
                    //左侧字符去掉后，就不满足条件了，因此需要减去合法字符数量
                    if (need.get(leftC).equals(window.get(leftC))) {
                        valid--;
                    }
                    //窗口元素减去一个数
                    window.put(leftC, window.get(leftC) - 1);
                }
            }

        }



        return start != null ? s.substring(start, max_len+start) : "";
    }

    public static String minWindow2(String s, String t) {
        int m = s.length();
        int[] cnt = new int[128];

        int less = 0;//s中字符的种类小于t的个数种类的个数

        for (char c : t.toCharArray()) {
            if (cnt[c] == 0) {
                less++;
            }
            cnt[c]++;
        }

        int ansL = -1, ansR = m;
        int left = 0, right = 0;

        while (right < m) {
            //向右滑动窗口
            while (right < m && less > 0) {
                cnt[s.charAt(right)]--;
                if (cnt[s.charAt(right)] == 0) {
                    less--;
                }
                right++;
            }

            //收缩左边界
            while (left < m && less == 0) {

                //记录当前答案
                if (right - left < ansR - ansL) {
                    ansL = left;
                    ansR = right;
                }

                //
                if (cnt[s.charAt(left)] == 0) {
                    less++;
                }
                cnt[s.charAt(left)]++;
                left++;
            }
        }

        //判断答案
        return ansL < 0 ? "" : s.substring(ansL, ansR);
    }
    public  static boolean checkInclusion(String s1, String s2) {

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;

        for (char c : s1.toCharArray()) {
            int count = need.getOrDefault(c, 0);
            need.put(c, count + 1);
        }
        int valid = 0;

        while (right < s2.length()) {
            char current = s2.charAt(right);
            right++;

            if (need.containsKey(current)) {
                int fre = window.getOrDefault(current, 0);
                window.put(current, fre + 1);

                if (need.get(current).equals(window.get(current))) {
                    valid++;
                }
            }

            //搜索窗口
            while (right - left >= s1.length()) {
                //正好相等
                if (valid== need.size()) {
                    return true;
                }
                char leftC = s2.charAt(left);
                left++;

                //合法的字符
                if (need.containsKey(leftC)) {
                    int fre = window.get(leftC);
                    if (fre== need.get(leftC)) {
                        valid--;
                    }
                    //减去他的频次
                    window.put(leftC, fre - 1);
                }
            }

        }

        return false;
    }

    /**
     * 没有重复的最长子串
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {

        Map<Character,Integer> window=new HashMap<>();
        int left=0,right=0;
        int max_lenth=-1;
        while (right<s.length()) {
            char current=s.charAt(right);
            right++;
            //缩小左边界，一直到不包含当前的这个curent符号
            while (window.containsKey(current)){
                char leftC= s.charAt(left);
                left++;
                window.remove(leftC);
            }
            window.put(current,1);
            max_lenth = Math.max(right - left, max_lenth);
        }

        return max_lenth;
    }

    //找到所有字符串的已为此 s = "cbaebabacd", p = "abc"
    public static List<Integer> findAnagrams(String s, String p) {

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> widow = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        int valid = 0;
        int left = 0, right = 0;
        for (char c : p.toCharArray()) {
            int fre = need.getOrDefault(c, 0);
            need.put(c, fre + 1);
        }

        while (right < s.length()) {
            char current = s.charAt(right);
            right++;
            if (need.containsKey(current)) {
                int fre = widow.getOrDefault(current, 0);
                widow.put(current, fre
                        + 1);
                if (widow.get(current).equals(need.get(current))) {
                    valid++;
                }
            }

            while (right - left >= p.length()) {
                //
                if (valid == need.size()) {
                    result.add(left);
                }

                char leftC = s.charAt(left);
                left++;
                if (need.containsKey(leftC)) {
                    int count = widow.get(leftC);
                    if (count == need.get(leftC)) {
                        valid--;
                    }
                    widow.put(leftC, count - 1);
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
//        System.out.println("最小字符串：==="+minWindow( "ADOBECODEBANC", "ABC"));
//
//        System.out.println("是否包含字符串的排列s1 = \"ab\" s2 = \"eidbaooo\"：==="+checkInclusion( "abcdxabcde", "abcdeabcdx"));


//        System.out.println("异味词 \"cbaebabacd\", \"abc\"：==="+findAnagrams( "cbaebabacd", "abc"));

        System.out.println("最长无重复子串\"abcabcbb\", \"abc\"：==="+lengthOfLongestSubstring( "bbbbb"));
    }
}
