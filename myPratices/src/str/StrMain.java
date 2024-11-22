package str;

import java.util.*;

/**
 * 字符串句子的处理可以使用双指针：还可以使用字符串处理模板
 * https://leetcode.cn/problems/fan-zhuan-dan-ci-shun-xu-lcof/solutions/804817/yi-ge-mo-ban-shua-bian-suo-you-zi-fu-chu-x6vh/
 */
public class StrMain {

    public static void main(String[] args) {

//        System.out.println(new StrMain().reverseMessage("the sky is blue"));
//        System.out.println(new StrMain().reverseWords("Let's take LeetCode contest"));
        System.out.println(new StrMain().truncateSentence("Let's take LeetCode contest",2));
        System.out.println(new StrMain().toGoatLatin("I speak Goat Latin"));
    }
    /**
     * 字符串处理模板:适合前后包含有空格的情况
     * @param str
     */
    private void templete(String str){

        //1、清楚前后空字符
        str.trim();
        //2、添加空字符，用于遍历到最后一个单词
        str+= " ";
        List<String > result=new ArrayList<>();
        StringBuilder tmp=new StringBuilder();//临时字符串
        for (char c:str.toCharArray()){
            if (c==' '){
                if (tmp.length()>0){
                    result.add(tmp.toString());
                    tmp.delete(0,tmp.length());
                }
            }
            else {
                //添加进来
                tmp.append(c);
            }
        }
    }
    /**
     * LCR 181. 字符串中的单词反转
     * 输入: message = "the sky is blue"
     * 输出: "blue is sky the"
     * @param message
     * @return
     */
    public String reverseMessage(String message) {

        message = message.trim();
        if (message.length()==0){
            return message;
        }
        message =  message+" ";
        List<String> result = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        char[] chars = message.toCharArray();
        for (int i =0; i <chars.length; i++) {
            char c = chars[i];
            if (c == ' ') {
                if (tmp.length() > 0) {
                    result.add(0, tmp.toString() + " ");
                    tmp.delete(0,tmp.length());
                }
            } else {
                tmp.append(c);
            }
        }

        for (String str : result) {
            tmp.append(str);
        }
        return tmp.toString().trim();
    }


    /**
     * LCR 182. 动态口令
     * 前k个字符移动到末尾位置
     * 方案1：字符串切片 str.subing(target+1,length-1)+str.subing(0,target)
     * 方案二：列表遍历，先向列表添加 [tartget,n]的元素，在添加[0,target]元素
     * 解决方案三：求余运算
     * @param password
     * @param target
     * @return
     */
    public String dynamicPassword(String password, int target) {
        int l = password.length();
        StringBuilder builder = new StringBuilder();
        //从target开始进行填充元素
        for (int i = target; i < target + l; i++) {

            builder.append(password.charAt(i % l));
        }
        return builder.toString();
    }

    /**
     *58. 最后一个单词的长度
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        s = s.trim() + " ";
        int length = 0;
        StringBuilder tmp = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (tmp.length() > 0) {
                    length = tmp.length();
                    tmp.delete(0, length);
                }
            } else {
                tmp.append(c);
            }
        }
        return length;
    }


    /**
     * 557. 反转字符串中的单词 III
     * 输入：s = "Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        s =" "+ s.trim() ;
        int length = s.length();
        List<String> ans = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        for (int i = length - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ' ') {
                if (tmp.length() > 0) {
                    ans.add(0, tmp.toString());
                    tmp.delete(0, tmp.length());
                }
            } else {
                tmp.append(c);
            }
        }

        for (String value : ans) {
            tmp.append(value + " ");
        }
        return tmp.toString().trim();
    }


    /**
     * 1816. 截断句子
     * 只包含前k个单词
     * @param s
     * @param k
     * @return
     */
    public String truncateSentence(String s, int k) {
        s += " ";

        int i = 0;
        for (; i < s.length(); i++) {
            //遇到一次空格 就减去1个单词
            if (s.charAt(i) == ' ') {
                k--;
            }
            if (k == 0) {
                break;
            }
        }
        return s.substring(0, i);
    }

    /**
     * 1805. 字符串中不同整数的数目
     * @param word
     * @return
     */
    public int numDifferentIntegers(String word) {

        word+="a";

        Set<String> set=new HashSet<>();
        StringBuilder tmp=new StringBuilder();

        for (char c:word.toCharArray()){

            if (Character.isLetter(c)){
                //集合不为空
                if (tmp.length()>0){
                    set.add((tmp.toString()));
                    tmp.delete(0,tmp.length());
                }
            }
            else {

                //避免前导0也加入进去了
                //如果之前有过前导0(注意这里不是temp.back()=='0',因为前导0的前面肯定是字母,如果不是字母就不是前导0)
                if (tmp.length() == 1 && tmp.charAt(0) == '0') {
                    tmp.delete(0, 1); //清空前导0
                }
                tmp.append(c);
            }
        }
        return  set.size();
    }

    /**
     * 819. 最常见的单词
     * 非违禁词之外的 词频最高的单词
     * @param paragraph
     * @param banned
     * @return
     */
    public String mostCommonWord(String paragraph, String[] banned) {

        paragraph+=" ";
        Set<String> bannedSet=new HashSet<>();
        for (String str:banned){
            bannedSet.add(str.toLowerCase());
        }
        String maxFreWord="";
        int maxFre=0;
        StringBuilder tmp=new StringBuilder();
        Map<String,Integer> fre=new HashMap<>();
        for (char c:paragraph.toCharArray()){
            if (!Character.isLetter(c)){
                if (tmp.length()>0){
                    String word=tmp.toString();
                    tmp.delete(0,tmp.length());
                    //计算词频
                    if (!bannedSet.contains(word)){
                        fre.put(word,fre.getOrDefault(word,0)+1);
                        if (fre.get(word)>maxFre){
                            maxFre=fre.get(word);
                            maxFreWord=word;
                        }
                    }
                }
            }
            else {
                tmp.append(Character.toLowerCase(c));
            }
        }
        return maxFreWord;
    }


    /**
     * 824. 山羊拉丁文
     * @param sentence
     * @return
     */
    public String toGoatLatin(String sentence) {

        sentence += " ";
        Set<Character> yuanYinMap = new HashSet<>();
        yuanYinMap.add('a');
        yuanYinMap.add('e');
        yuanYinMap.add('i');
        yuanYinMap.add('o');
        yuanYinMap.add('u');

        StringBuilder tmp = new StringBuilder();
        StringBuilder reslut = new StringBuilder();
        String tail = "a";
        for (char c : sentence.toCharArray()) {

            if (c == ' ') {
                if (tmp.length() > 0) {
                    //元音添加ma
                    if (yuanYinMap.contains(Character.toLowerCase(tmp.charAt(0)))) {
                        tmp.append("ma");
                    } else {
                        //辅音第一个字符移动到最后
                        tmp.append(tmp.charAt(0));
                        tmp.append("ma");
                        tmp.delete(0, 1);

                    }
                    tmp.append(tail);
                    tail += "a";//增长
                    reslut.append(tmp.toString() + " ");
                    tmp.delete(0, tmp.length());
                }
            } else {
                tmp.append(c);
            }
        }
        return reslut.toString().trim();
    }
}
