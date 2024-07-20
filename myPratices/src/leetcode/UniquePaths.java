package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniquePaths {

    public static void main(String[] args) {
        System.out.println(uniquePaths(19,13));
    }
    static int ans=0;


    public int uniquePaths12(int m, int n) {
    // 想要到达m,n顶点，需要向下移动m-1次，向上移动n-1次，因此总的移动次数就是m+n-2，我们需要再这么多次数中选择移动m-1次向下或者n-1次向上
//        因此可以把他理解成一个排列组合问题

        if (m<n){
            int tmp=m;
            m=n;
            n=tmp;
        }

        int ans=1;
        for (int i=m+n-2,j=1;j<n;j++,i--){
            ans*=i;
            ans/=j;
        }
        return ans;
    }
        /**
         * 动态规划解法：
         * 定义dp[i][j] 表示0,0到i,j共有多少中走法，当i=0||j==0是只能选择向右或者向下走
         * 当i,j都大于零时，可以从dp[i-1][j]或者dp[i][j-1]走到dp[i][j],需要把这两个加起来
         * @param m
         * @param n
         * @return
         */
    public static int uniquePaths1(int m, int n) {

        int dp[][] = new int[m][n];
        //
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        //依赖前一个值
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }

    //动态规划；优化版本，只保留一维数组
    public int uniquePaths22(int m, int n) {

        if (m < n) {
            int tmp = m;
            m = n;
            n = tmp;
        }
        //较小的行
        int dp[] = new int[n];

        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                //上一行的结果存放在了dp[j] 因此可以直接使用
                dp[j] += dp[i - 1];
            }
        }
        return dp[n - 1];
    }
        public static int uniquePaths(int m, int n) {
//        int [][] arr=new int[m][n];
        ans=0;
        backtrck(m,n,0,0,new ArrayList<>());
        return ans;
    }

    private static void backtrck(int m, int n, int i, int j, List<String> path) {
        if (i >= m || j >= n) {
//            /越界
            return;
        }
        if (i == m-1 && j == n-1) {
            ans++;
//            System.out.println(Arrays.toString(path.toArray(new String[1])));
            return;
        }

        //huishu
//        path.add("向下");
        backtrck(m, n, i + 1, j,path);
//        path.remove(path.size()-1);


//        path.add("向右");
        backtrck(m, n, i, j + 1,path);
//        path.remove(path.size()-1);
    }
}
