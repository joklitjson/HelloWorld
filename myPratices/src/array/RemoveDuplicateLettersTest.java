package array;

import java.util.Stack;

public class RemoveDuplicateLettersTest {

    public static void main(String[] args) {

        System.out.println(removeDuplicateLetters("bacba"));
    }
  static   String removeDuplicateLetters(String s) {

        int [] count=new int[256];
        boolean []inStack=new boolean[256];

        Stack<Character> stack=new Stack<>();

        char[] chars=s.toCharArray();

        for (char c:chars){
            count[c-'0']++;
        }

        StringBuffer buffer=new StringBuffer();

        for ( int i=0;i<chars.length;i++) {
            count[chars[i] - '0']--;

            if (inStack[chars[i] - '0']) {
                continue;
            }
            while (!stack.isEmpty() && stack.peek() > chars[i]) {
                //后面没有栈顶元素了
                if (count[stack.peek() - '0']==0){
                    break;
                }
                char remove = stack.pop();
                inStack[remove - '0'] = false;
            }

            stack.push(chars[i]);
            inStack[chars[i] - '0']=true;

        }

        while (!stack.isEmpty()){
            buffer.append(stack.pop());
        }

        return buffer.reverse().toString();
    }
    }
