package slidwindow;

public class BruteForce {

    public static void main(String[] args) {
        System.out.println(search("abcfdff","dff"));;
    }
    //时间复杂度 O(M*N)
    static int search(String txt, String pat) {
        int m = txt.length();
        int n = pat.length();

        int i = 0, j = 0;
        while (i < m && j < n) {
            if (txt.charAt(i) == pat.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - (j - 1);// 匹配失败，需要把i移动到上次开始匹配的元素的下一个位置
                j = 0;//移动到开头元素
            }
            if (j == n) {
                //匹配成功，返回匹配开始的位置
                return i - j;
            }
        }
        return -1;
    }
}
