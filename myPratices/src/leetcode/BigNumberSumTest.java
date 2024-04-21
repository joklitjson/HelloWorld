package leetcode;

public class BigNumberSumTest {

    public static void main(String[] args) {
        System.out.println(bigNumberSum("423","849"));
    }
    /**
     * 就是把两个数 按位相加，从低位到高位
     * @param numberA
     * @param numbersB
     * @return
     */
    public static  String bigNumberSum(String numberA,String numbersB) {
        int maxLength = numberA.length() > numbersB.length() ? numberA.length() : numbersB.length();

        int[] numsA = new int[maxLength + 1];
        //把字符串转成 int数组 倒序
        for (int i = 0; i < numberA.length(); i++) {
            numsA[i] = numberA.charAt(numberA.length() - 1 - i) - '0';
        }

        int[] numsB = new int[maxLength + 1];
        for (int i = 0; i < numbersB.length(); i++) {
            numsB[i] = numbersB.charAt(numbersB.length() - 1 - i) - '0';
        }

        int[] result = new int[maxLength + 1];

        for (int i = 0; i < result.length; i++) {
            int tmp = numsA[i] + numsB[i];
            //结果大于10，则进位
            if (tmp > 9) {
                tmp = tmp - 10;
                result[i + 1] = 1;
            }
            result[i] += tmp;
        }

        StringBuilder stringBuilder=new StringBuilder();

        for (int i= result.length-1;i>=0;i--){
            if (i== result.length-1&&result[i]==0){
                continue;
            }
            stringBuilder.append(result[i]);
        }
        return  stringBuilder.toString();
    }
}
