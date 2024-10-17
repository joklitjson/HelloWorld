package fenzhi;

/**
 * 使用分治算法 计算
 */
public class KaratsubaMultipy {

    //Karatsuba乘法
    //此种情况使用long时,数过大可能出现越界,应考虑使用BigInteger
    public static long karatsuba(long num1, long num2) {
//        (a+b)*(c+d)=
        //不在分解
        if (num1 < 10 || num2 < 10) {
            return num1 * num2;
        }

        //计算拆分长度
        int size1 = String.valueOf(num1).length();
        int size2 = String.valueOf(num2).length();

        //取对半
        int half = Math.max(size1, size2) / 2;

        //
        long a = (long) (num1 / Math.pow(10, half));
        long b = (long) (num1 % Math.pow(10, half));
        long c = (long) (num2 / Math.pow(10, half));
        long d = (long) (num2 % Math.pow(10, half));

        // 计算z2, z0, z1, 此处的乘法使用递归

        long first = karatsuba(a, c);
        long second = karatsuba(b, d);
        long third = karatsuba(a + b, c + d);

        long result = (long) (first * (Math.pow(10, 2 * half)) + (third - first - second) * Math.pow(10, half) + second);
        return result;
    }
}
