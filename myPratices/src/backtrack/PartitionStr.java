package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 把
 */
public class PartitionStr {

    public static void main(String[] args) {
        System.out.println(new PartitionStr().partition("aab"));
    }

    /**
     * 分隔字符串数组，变成回文字符串
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> ans=new ArrayList<>();

        backTrace(s,ans,new ArrayList<>());

        return ans;
    }

    private void backTrace(String string, List<List<String>> ans,List<String> tmp){
        if (string.length()==0){
            //新创建一个集合
            ans.add(new ArrayList<>(tmp));
            return;
        }
        for (int i=0;i<string.length();i++){
            String subStr=string.substring(0,i+1);
            if (isPalindrome(subStr)){
                tmp.add(subStr);
                backTrace(string.substring(i+1),ans,tmp);//在判断剩余的子串是否能拆分
                tmp.remove(tmp.size()-1);
            }
        }
    }
    private boolean isPalindrome(String str) {
        if (str.length() == 0) {
            return true;
        }
        int left = 0, right = str.length() - 1;

        while (left <= right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
