import java.util.FormatFlagsConversionMismatchException;
import java.util.LinkedList;
import java.util.Stack;

public class Calculate {

    public static void main(String[] args) {
        System.out.println(calculate("6+(9-5)*2"));
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
        boolean stop=false;
        while (!numsList.isEmpty()&&!stop) {
            char c = numsList.removeFirst();
            if (Character.isDigit(c)) {
                //计算
                if (opt.equals("+")) {
                    stack.push((c - '0'));
                } else if (opt.equals("-")) {
                    stack.push(-(c - '0'));
                } else if (opt.equals("*")) {
                    int before= stack.pop();
                    stack.push(before*(c-'0'));
                } else if (opt.equals("/")) {
                    int before= stack.pop();
                    stack.push(before/(c-'0'));
                }

            } else if (c == '+') {
                opt = "+";
            } else if (c == '-') {
                opt = "-";
            } else if (c == '*') {
                opt = "*";
            } else if (c == '/') {
                opt = "/";
            }
            else if (c == '(') {
                //开启一个子计算，计算括号中的数字
              int sum=calculate(numsList);
                stack.push(sum);
            }
            else if (c == ')') {
                //子计算结束，返回数据
               stop=true;
            }
        }


        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }
    }
