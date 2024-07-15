import java.util.Arrays;

/**
 * https://blog.csdn.net/lqy971966/article/details/106026651
 * BM算法采用从右向左比较 的方法
 * 应用到了两种启发式规则，即坏字符规则 和好后缀规则 ，来决定向右跳跃的距离。
 * 在BM算法匹配的过程中，取坏字符规则和好后缀规则中跳跃的较大者作为跳跃的距离。
 */
public class BoyerMoore {

    public static void main(String[] args) {
        System.out.println(bm("abcdef","cde"));
    }
    public static int bm(String main,String pat) {
        int m = main.length();
        int n = pat.length();

        int[] badCharRule = new int[256];
        badCharRul(pat, badCharRule);
        //主串上的指针
        int i = n - 1;

        while (i < m) {
            //子串上的指针
            int j = n - 1;
            while (pat.charAt(j) == main.charAt(i)) {
                i--;
                j--;
                //说明遍历到了模式串的最左侧
                if (j == -1) {
                    return i + 1;
                }
            }
            //计算坏字符规则下移动的位数
            int moveLen = j - badCharRule[main.charAt(i)];
            i += moveLen;
        }
        return -1;
    }

    //生成坏字符数组
    private static void badCharRul(String pat,int [] badRule){

        Arrays.fill(badRule,-1);

        for (int i=0;i<pat.length();i++){
            badRule[pat.charAt(i)]=i;
        }
    }
}
