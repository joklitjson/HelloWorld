package interview;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 单次拆解，就是 能否使用一个集合中的单词组成一个目标字符串
 * 有两种方案：
 * 1、回溯算法：使用组合的方式遍历
 * 2、动态规划
 */
public class WordBreak {
   static   HashSet<String> wordSet=new HashSet<>();
    public static void main(String[] args) {

        System.out.println(wordBreak("hello", Arrays.asList("lo","he","l")));
    }
   static boolean  wordBreak(String s, List<String> wordDict){
       wordSet.addAll(wordDict);
        return backtrack(s,0,wordDict);
    }

    private static  boolean backtrack(String s,int index,List<String> wordDict){

        //到达最后一步了，因此是找到了
        if (index==s.length()){
            return true;
        }

        for (String str:wordDict){
            int len=str.length();
            //1当前位置和 匹配中字符串的长度 之和要小于目标字符串子和
//            if (index+len<=s.length()&&s.substring(index,index+len).equals(str)){
//                //回溯下一个位置
//                return backtrack(s,index+len,wordDict);
//            }
            //2优化点：可以用Set装wordDict，用于快速匹配
            for (int i=1;i+index<=s.length();i++){
                // 看看 wordDict 中是否有单词能匹配 s[i..] 的前缀
                String prefix = s.substring(index, i + index);
                if (wordSet.contains(prefix)){
                    return backtrack(s,index+i,wordDict);
                }
            }
        }
        //遍历完成了 也没找到
        return false;
    }
}
