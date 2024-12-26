package alingchasan;

import java.util.*;

public class DStack {

    public static void main(String[] args) {
        buildArray(new int[]{1,3},3);
    }
    /**
     * 1441. 用栈操作构建数组
     * 解决方案:遍历记录前一个值，然后计算前一个值和当前遍历的值之间差了多少个数字，然后在向其中插入 n个 push pop,操作
     * @param target
     * @param n
     * @return
     */
    public static List<String> buildArray(int[] target, int n) {
        int pre = 0;
        List<String> result = new ArrayList<>();
        for (int value : target) {

            //两者之间的差值进行插入然后在弹出
            for (int i = pre+1; i < value; i++) {
                System.out.println("loop"+i);
                result.add("Push");
                result.add("Pop");
                System.out.println(Arrays.toString(result.toArray()));
            }
            //插入当前数字
            result.add("Push");
            pre=value;
        }
        return result;
    }

    /**
     * 844. 比较含退格的字符串
     * 解决方案：可以使用栈模拟，删除退格键之前的字符，然后在比较两则
     * @param s
     * @param t
     * @return
     */
    public boolean backspaceCompare(String s, String t) {
        String ss = removeBackSpace(s);

        String tt = removeBackSpace(t);
        return Objects.equals(ss, tt);
    }
    private String removeBackSpace(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        if (s == null || s.length() == 0) {
            return s;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '#') {
                //删除
                if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 682. 棒球比赛
     * 解决方式：使用栈 直接计算，遇到计算符号 则进行弹出计算，然后在入栈
     * @param operations
     * @return
     */
    public int calPoints(String[] operations) {
        Stack<Integer> stack = new Stack<>();
        //遍历
        for (String str : operations) {
            if (str.equals("+")) {
                Integer num1 = stack.pop();
                Integer num2 = stack.pop();
                stack.push(num2);
                stack.push(num1);
                stack.push(num1 + num2);
            } else if (str.equals("D")) {
                Integer num1 = stack.peek();
                stack.push(num1 * 2);
            } else if (str.equals("C")) {
                stack.pop();
            } else {
                stack.push(Integer.valueOf(str));
            }
        }
        int score = 0;
        while (!stack.isEmpty()) {
            score += stack.pop();
        }
        return score;
    }

    /**
     * 2390. 从字符串中移除星号
     * 使用栈模拟，然后遇到星号 在移除前一个
     * @param s
     * @return
     */
    public String removeStars(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                if (stringBuilder.length() > 0) {
                    //通过设置长度，也可以起到删除的左右
                    stringBuilder.setLength(stringBuilder.length()-1);
//                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
}
