package leetcode;


public class GreatestCommonDivisorTest {

    public static void main(String[] args) {
        int re= getGreatestCommonDivisor2(12, 78);
//        int re= getGreatestCommonDivisor2(12, 78);
//        int re= getGreatestCommonDivisor3(12, 78);
        System.out.println(re);
    }

    /**
     * 方案一：辗转相除法
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor(int a, int b) {
        int big = a > b ? a : b;
        int small = a < b ? a : b;
        if (big % small == 0) {
            return small;
        }
        return getGreatestCommonDivisor(big % small, small);
    }


    /**
     * 方案二：更相减损术
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor2(int a, int b) {
        if (a==b){
            return a;
        }
        int big = a > b ? a : b;
        int small = a < b ? a : b;
//        if (big % small == 0) {
//            return small;
//        }
        return getGreatestCommonDivisor(big - small, small);
    }

    /**
     * 方案三：简单处爆发
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor3(int a, int b) {
        int big = a > b ? a : b;
        int small = a < b ? a : b;
        if (big % small == 0) {
            return small;
        }
        for (int i=small/2;i>1;i--){
            if (small%i==0&&big%i==0){
                return i;
            }
        }
        return 1;
    }

    /**
     * 方案四：辗转相除法和更像结合
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor4(int a, int b) {
        if (a == b) {
            return a;
        }

        if ((a & 1) == 0 && (b & 1) == 0) {
            return getGreatestCommonDivisor4(a >> 1, b >> 1) << 1;
        } else if ((a & 1) != 0 && (b & 1) == 0) {
            return getGreatestCommonDivisor4(a, b >> 1);
        } else if ((a & 1) == 0 && (b & 1) != 0) {
            return getGreatestCommonDivisor4(a >> 1, b);
        } else {
            int big = a > b ? a : b;
            int small = a < b ? a : b;
            return getGreatestCommonDivisor4(big - small, small);
        }
    }
}