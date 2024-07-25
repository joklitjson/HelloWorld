package slidwindow;

public class RabinKarp {
    public static void main(String[] args) {
        System.out.println(search("abcddffg","ffg"));
    }

    //Rabin-Karp算法的时间复杂度为O(n+m)，其中n是文本长度，m是模式长度。
    // 在文本串 txt 中搜索模式串 pat 的起始索引
    static int search(String txt, String pat) {
        int N = txt.length(), L = pat.length();

        // 取一个比较大的素数作为求模的除数
        long Q = 1658598167;

        int R = 256;
//        int RL= (int) Math.pow(R,L-1);

        //求Math.pow(R,L-1) 的值
        long RL = 1;
        for (int i = 1; i <= L - 1; i++) {
            RL = (RL * R) % Q;
        }


        //由于进制比较大，会有移除的情况，因此我们需要进行取模运算
//               X % Q == (X + Q) % Q
//                (X + Y) % Q == (X % Q + Y % Q) % Q

        long patHash = 0;
        for (int i = 0; i < L; i++) {
            patHash = (patHash * R + (pat.charAt(i) - '0')) % Q;
        }

        int left = 0, right = 0;
        long windowHash = 0;
        while (right <= N) {
            windowHash = ((windowHash * R) % Q + (txt.charAt(right) - '0') % Q) % Q;
            right++;
            //判断窗口的数字
            if (right - left == L) {
                //比较hash
                if (patHash == windowHash) {
                    if (pat.equals(txt.substring(left, right))) {
                        return left;
                    }
                } else {
                    //移除窗口内的左元素, 加上Q是防止 减法之后有负数
                    windowHash = (windowHash - ((txt.charAt(left) - '0') * RL) % Q + Q) % Q;
                }
                left++;
            }
        }

        return -1;
    }
}
