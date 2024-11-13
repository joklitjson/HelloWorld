package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackTraceMain {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new BackTraceMain().restoreIpAddresses("25525511135").toArray()));
    }
    List<String> ans=new ArrayList<>();
    List<String> tmpAns=new ArrayList<>();
    /**
     *LCR 087. 复原 IP 地址
     * 使用回溯算法，尽量多进行剪枝，减少循环次数
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        backTrack(s,0);
        return ans;
    }

    private void backTrack(String string,int startIndex) {
        //
        if (tmpAns.size() > 4) {
            return;
        }
        if (startIndex == string.length()&&tmpAns.size()==4) {
            ans.add(String.join(".", tmpAns));
            return;
        }
        int mostLength = Math.min(startIndex + 3, string.length());
        for (int i = startIndex; i < mostLength; i++) {
            String sub = string.substring(startIndex, i + 1);
            if ((sub.length() > 1 && sub.startsWith("0")) || Integer.parseInt(sub) > 255 || tmpAns.size() >= 4) {
                return;
            }
            tmpAns.add(sub);
            backTrack(string, i + 1);
            tmpAns.remove(tmpAns.size() - 1);
        }
    }
}
