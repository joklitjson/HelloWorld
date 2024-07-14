package slidwindow;

import java.util.*;

public class FindRepeatedDnaSequences {

    //1、把字符串转换成 数字

    static int R=10;//进制



    //1、把字符串转换成integer类型
    public static int transferToInteget(String str) {
        int number = 0;
        for (int i = 0; i < str.length(); i++) {
            number = number * R + (str.charAt(i) - '0');
            System.out.println(number);
        }

        return number;
    }

    //2、去除数字的高位，保留低位数字
    public static void removeHight(int number){

        String strNum=number+"";
        // 此时 number 的位数
        int L = strNum.length();

        int tmpNum=number;
        System.out.println("原数字：=="+strNum);
        for (int i=0;i<strNum.length();i++){
//            number=number-removel*R^(L-1)
            tmpNum= (int) (tmpNum-(strNum.charAt(i)-'0')*Math.pow(R,(L-1)));
            L--;
            System.out.println("去除高位后：=="+tmpNum);
        }
    }

    /**
     * 187 题「重复的 DNA 序列」
     *1、方案一：可以使用暴力破解法：遍历字符串，在截取长度为10的，然后在进行比较
     * 方案二：滑动窗口+hash值(或位运算)
     * @param s
     * @return
     */
    List<String> findRepeatedDnaSequences(String s) {

        // 记录出现过的子串
//        Set<String> result = new HashSet<>();
//        Map<String, String> map = new HashMap<>();
//
//        for (int i = 0; i + 10 <= s.length(); i++) {
//            String subStr = s.substring(i, i+10);
//            //存在既可以
//            if (map.containsKey(subStr)) {
//                result.add(subStr);
//            } else {
//                //不存在则加入
//                map.put(subStr, subStr);
//            }
//        }
//        return new ArrayList<>(result);
        Set<String> result = new HashSet<>();
        Set<Integer> see = new HashSet<>();
        int left=0,right=0;
        int [] transfer=new int[s.length()];
        //因此可以把其抽象成一个4进制的数字
        for (int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if (c=='A'){
                transfer[i]=0;
            }
            if (c=='G'){
                transfer[i]=1;
            }
            if (c=='C'){
                transfer[i]=2;
            }
            if (c=='T'){
                transfer[i]=3;
            }
        }

        int tmpNum=0;
        int R=4,L=10;
        // 存储 R^(L - 1) 的结果
        int RL = (int) Math.pow(R, L - 1);
        while (right<s.length()){
            // 扩大窗口，移入字符，并维护窗口哈希值（在最低位添加数字）
            tmpNum=tmpNum*R +transfer[right];
            right++;
            if (right-left==L){
                //计算窗口的值
                if (see.contains(tmpNum)){
                    result.add(s.substring(left,right));
                }
                else {
                    //保存hash值
                    see.add(tmpNum);
                }

                //移除高位
                tmpNum=tmpNum-transfer[left]*RL;
                left++;
            }
        }

        return new ArrayList<>(result);
    }
    public static void main(String[] args) {
//        transferToInteget("2345");
        removeHight(12345);
        System.out.println(new FindRepeatedDnaSequences().findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
    }
}
