package str;

import java.util.Arrays;

/**
 * https://blog.csdn.net/q547550831/article/details/51860017
 */
public class Sunday {

    public static void main(String[] args) {
        System.out.println(sunday("baccdcccd","cccd"));
    }

    public static int sunday(String str, String pat) {
        int n = str.length();
        int m = pat.length();
        int[] shit = new int[27];

        //填充
        Arrays.fill(shit, m + 1);

        //模式串中每个字符出现的最后下标
        //主串参与匹配的最后一个字符的下一个字符移动到该位，需要移动的步数
        for (int i = 0; i < m; i++) {
            shit[pat.charAt(i) - 'a'] = m - i;
        }

        //主串开始匹配的位置
        int s = 0;

        //模式串开始匹配的位置
        int j = 0;
        while (s <= n - m) {
            while (str.charAt(s + j) == pat.charAt(j)) {
                //移动模式串
                j++;

                //匹配成功，返回
                if (j >= m) {
                    return s;
                }
            }

            //计算主串匹配的下一个字符 需要移动的位置
            char nextChar = str.charAt(s + m);
            s += shit[nextChar - 'a'];
        }

        return -1;
    }
}
