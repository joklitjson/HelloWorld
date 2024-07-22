
import java.util.Arrays;

public class StringMultiply {

    public static void main(String[] args) {

        System.out.println(multiply("15","3"));
        System.out.println(karatsuba(123L,45L));
    }
    /**
     * 字符串相乘
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();

        int[] result = new int[m + n];
        Arrays.fill(result, 0);

        //从个位开始相乘
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int a = num1.charAt(i) - '0';
                int b = num2.charAt(j) - '0';
                int tmp = a * b;
//                i+j 乘机的进位, i+j+1  乘机的低位
                int sum = tmp + result[i + j + 1];
                result[i + j] += sum / 10;//进位
                result[i + j + 1] = sum % 10;//直接设置低位
            }
        }

        //统计结果
        int i = 0;
        while (i < result.length && result[i] == 0) {
            i++;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (i < result.length) {
            stringBuffer.append(result[i]);
            i++;
        }
        return stringBuffer.toString();
    }

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
