package skill;

public class TrailingZero {

    public static void main(String[] args) {
        System.out.println(trailingZeroes(125));;
    }
    /**
     * 转换成能分解出5的因子的数量
     * @param n
     * @return
     */
   static int trailingZeroes(int n) {

        int res = 0;
        long divider = 5;
//        从第向上 计算
        while (divider <= n) {
            res += n / divider;
            divider *= 5;
        }

        return res;
    }
    static int trailingZeroes2(int n) {

        int res = 0;
        for (int i=n;i/5>0;i=i/5){
            res+=(i/5);
        }
        return res;
    }

    static long trailingZeroes3(long n) {

        int res = 0;
        for (long i=n;i/5>0;i=i/5){
            res+=(i/5);
        }
        return res;
    }

    // 给你一个非负整数K，问你有多少个n，使得n!结果末尾有K个 0
    int preimageSizeFZF(int k) {
        // 左边界和右边界之差 + 1 就是答案

        return (int) (rightBound(k)-leftBound(k)-1);
    }

    private long leftBound(int target) {

        long lo = 0, hi = Long.MAX_VALUE;
        while (lo < hi) {
            long middle = lo + (hi - lo) / 2;
            long middleTrilingZero=trailingZeroes3(middle);
            if (middleTrilingZero < target) {
                lo = middle + 1;
            } else if (middleTrilingZero > target) {
                hi = middle;
            } else {
                hi = middle;
            }
        }
        return lo;
    }

    private long rightBound(long target) {
        long lo = 0, hi = Long.MAX_VALUE;

        while (lo < hi) {
            long middle = lo + (hi - lo) / 2;
            long middleTrilIngZero = trailingZeroes3(middle);
            if (middleTrilIngZero < target) {
                lo = middle;
            } else if (middleTrilIngZero > target) {
                hi = middle;
            } else {
                lo = middle + 1;
            }
        }

        return lo - 1;
    }
}
