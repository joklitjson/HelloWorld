package skill;

import java.util.Arrays;
import java.util.List;

public class Mypow {

    public static void main(String[] args) {

        System.out.println(mypow(3, 5));
    }

    static int mypow2(int a, int k) {
        int res = 1;
        // 对因子求模

        a = a % base;
        for (int i = 0; i < k; i++) {
//            / 这里有乘法，是潜在的溢出点
            res *= a;
            res = res % base;
        }
        return res;
    }

    static int base = 1337;

    /**
     * 超级求幂的取模算法
     * 属于是先分解求幂的因子，然后在利用公式：(a*b)%k = (a%k)(b%k)%k 求幂的模
     *
     * @param a
     * @param b
     * @return
     */
    int superPow(int a, int[] b) {

//        base case
        if (b.length == 0) {
            return 1;
        }

        // 取出最后一个数
        int last = b[b.length - 1];

        b = Arrays.copyOf(b, b.length - 1);

        int part1 = mypow2(a, last);
        int part2 = mypow2(superPow(a, b), 10);

        return (part1 * part2) % base;
    }

    /**
     * 快速求幂 算法（递归写法）
     *
     * @param a
     * @param k
     * @return
     */
    static int mypow(int a, int k) {
        if (k == 0) {
            return 1;
        }

        if (k % 2 == 1) {
            //奇数个k
            return a * mypow(a, k - 1);
        } else {
            //偶数个k，先计算一般的值，然后在直接相乘即可
            int sub = mypow(a, k / 2);
            return sub * sub;
        }
    }

    /**
     * 快速幂：循环法，比递归法快一些
     *
     * @param a
     * @param b
     * @return
     */
    long mypow3(long a, long b) {

        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = res * a;
            }
            //向右少一位
            b = b >> 1;
            a = a * a;//同时a也升高了
        }
        return res;
    }
}
