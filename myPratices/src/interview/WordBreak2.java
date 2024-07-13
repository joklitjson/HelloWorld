package interview;

import java.util.*;

/**
 * 单词拆分，就是 能否使用一个集合中的单词组成一个目标字符串
 * 有两种方案：
 * 1、回溯算法：使用组合的方式遍历
 * 2、动态规划
 */
public class WordBreak2 {
   static   HashSet<String> wordSet=new HashSet<>();

  static List<String> res=new ArrayList<>();
   static List<String> [] memo;
    public static void main(String[] args) {

//        System.out.println(wordBreak("hellow", Arrays.asList("ll","he","ow")));
//        System.out.println(wordBreak("leetcode", Arrays.asList("le", "code","et")));
//        List<String> result= wordBreak("leetcode", Arrays.asList("le", "code","et"));
        List<String> result= wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog"));
        System.out.println("target string=[catsanddog]");
        for (String ans:result){
            System.out.println(ans);
        }
//        System.out.println(wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog")));
    }

    /**
     * 需要返回拆解的路径
     * @param s
     * @param wordDict
     * @return
     */
   static List<String>  wordBreak(String s, List<String> wordDict) {
       wordSet.addAll(wordDict);
       res.clear();

       //是被备忘录
       memo = new List[s.length() + 1];
//       return backtrack(s, 0, wordDict);
       return dp(s,0);
   }

    private static  boolean backtrack(String s,int index,List<String> wordDict){

        //到达最后一步了，因此是找到了
        if (index==s.length()){
            return true;
        }
//        for (String str:wordDict){
//            int len=str.length();
//            //1当前位置和 匹配中字符串的长度 之和要小于目标字符串子和
//            if (index+len<=s.length()&&s.substring(index,index+len).equals(str)){
//                //回溯下一个位置
//                return backtrack(s,index+len,wordDict);
//            }
//        }

        //2优化点：可以用Set装wordDict，用于快速匹配
        for (int i=index+1;i<=s.length();i++){
            // 看看 wordDict 中是否有单词能匹配 s[i..] 的前缀
            String prefix = s.substring(index, i);
            if (wordSet.contains(prefix)){
                return backtrack(s,i,wordDict);
            }
        }
        //遍历完成了 也没找到
        return false;
    }

    /**
     * 定义dp[i] 函数：i...n是否匹配，如果 0，i被匹配，同时子问题 i...n也能被匹配，则肯定能拼接
     * @return
     */
    private static  List<String>  dp(String s,int index) {
        List<String> res = new LinkedList<>();
        if (index == s.length()) {
            res.add("");
            return res;
        }

        if (memo[index]!=null) {
            return memo[index];
        }

        for (int i = index+1; i <= s.length(); i++) {
            // 0..i 被匹配，需要看看子问题
            String prefix = s.substring(index, i);

            if (wordSet.contains(prefix)) {
                //子问题也被匹配到了
                List<String> subPromPath = dp(s, i);
                for (String sub : subPromPath) {
                    if (sub.isEmpty()) {
                        res.add(prefix);
                    } else {
                        res.add(prefix + " " + sub);
                    }
                }
            }
        }
        // s[i..] 无法被拼出
        memo[index] =res;
        //遍历完成，都不能被匹配
        return res;
    }
}
