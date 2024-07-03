import java.util.LinkedList;
import java.util.Stack;

public class Calculate {

    public static void main(String[] args) {
        System.out.println(calculate("6-(9-5)*2"));
    }
    static int calculate(String s) {
        LinkedList<Character> str=new LinkedList<>();
        for (char c:s.toCharArray()){
            str.add(c);
        }
        return calculate(str);
    }
    //个人计算器，能实现加减乘除 和括号优先级运算
   static int calculate(LinkedList<Character> numsList) {
        Stack<Integer> stack = new Stack<>();
        String opt = "+";
        int num=0;

        while (!numsList.isEmpty()) {
            char c = numsList.removeFirst();
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }
            //开始递归
            if (c == '(') {
                //开启一个子计算，计算括号中的数字
                int sum = calculate(numsList);
                num=sum;
            }

            if (!Character.isDigit(c)||numsList.isEmpty()) {
                //遇到了下一个符号，因此之前的符号和数字需要入栈
                if (opt.equals("+")) {
                    stack.push(num);
                } else if (opt.equals("-")) {
                    stack.push(-num);
                } else if (opt.equals("*")) {
                    int before = stack.pop();
                    stack.push(before * num);
                } else if (opt.equals("/")) {
                    int before = stack.pop();
                    stack.push(before / num);
                }

                // 更新符号为当前符号，数字清零
                opt = c + "";
                num = 0;

            }

            //子计算结束，返回数据
            if (c == ')') {
               break;
            }
        }


        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }
    }
