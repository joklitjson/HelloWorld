
import java.util.Arrays;

public class StringMultiply {

    public static void main(String[] args) {

        System.out.println(multiply("15","3"));
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
}
