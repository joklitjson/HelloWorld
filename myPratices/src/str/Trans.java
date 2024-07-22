package str;

import java.util.Stack;

public class Trans {

    public static void main(String[] args) {
        System.out.println(trans("This is a sample"));
        System.out.println(trans2("This is a sample"));
    }
    public static String trans(String s) {

        StringBuilder stringBuilder = new StringBuilder();
        if (s == null || s.length() == 0) {
            return s;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c >= 'A' && c <= 'Z') {
                char gg = (char) (c - 'A' + 'a');
                stringBuilder.append((char) (c - 'A' + 'a'));
            } else if (c >= 'a' && c <= 'z') {
                stringBuilder.append((char) (c - 'a' + 'A'));
            } else {
                stringBuilder.append(c);
            }
        }
        //翻转
        char arr[] = stringBuilder.reverse().toString().toCharArray();
        //每个单次进行翻转
        for (int i = 0; i < arr.length; ) {
            int j = i;
            while (j < arr.length && arr[j] != ' ') {
                j++;
            }

            //单个单词翻转
            int l = i, r = j - 1;
            while (l < r) {
                char tmp = arr[l];
                arr[l] = arr[r];
                arr[r] = tmp;
                l++;
                r--;
            }
            i = j + 1;
        }

        String res = new String(arr);
        return res;
    }


    /**
     * 借助栈实现
     * @param s
     * @return
     */
    public static String trans2(String s) {

        StringBuilder stringBuilder = new StringBuilder();
        if (s == null || s.length() == 0) {
            return s;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c >= 'A' && c <= 'Z') {
                char gg = (char) (c - 'A' + 'a');
                stringBuilder.append((char) (c - 'A' + 'a'));
            } else if (c >= 'a' && c <= 'z') {
                stringBuilder.append((char) (c - 'a' + 'A'));
            } else {
                stringBuilder.append(c);
            }
        }

        //翻转
        String [] ss= stringBuilder.toString().split(" ");

        Stack<String> stack=new Stack<>();
        for (String st:ss){
            stack.push(st);
        }

        StringBuilder tmp = new StringBuilder();
        while (!stack.isEmpty()){
            tmp.append(stack.pop());
            tmp.append(" ");
        }
        String res = tmp.toString();
        return res;
    }
}
