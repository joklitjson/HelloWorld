package aaaruankao;

public class RuanKao {

    public static void main(String[] args) {
        RuanKao ruanKao=new RuanKao();
        int p[] = {30,35,15,5,10,20,25};
        int n=p.length-1;
        ruanKao.MatrixChain(p,new int[n+1][n+1],new int[n+1][n+1]);

//        System.out.println(ruanKao.RNA_2("ACCGGUAGU".toCharArray(),"ACCGGUAGU".length()-1));

//        String string = "ACCGGUAGU";
//        System.out.println();
//        System.out.println("==="+maxPairs(string));
    }


    /**
     * 计算矩阵乘法的最小次数:
     * p:表示矩阵的行列 比如： {20,15,12,3,18};//第1个矩阵是20X15，第二个矩阵是15X12，等等。
     * m[i,j] 表示 i,j 最小乘法次数
     * s[i,j] 表示是从 i,j 在那个位置是断开位置  分割表达式：可以获得最小乘法次数
     * ,然后在i~j之间 递增断开位置 遍历求解
     * m[i][j]=min(m[i][k]+m[k+1][j]+p[i-1]*p[i]*p[i])
     *
     * 采用动态规划写法：自底向上的写法
     * @param p
     * @param n
     * @param m
     * @param s
     * @return
     */
    public void MatrixChain(int [] p,int [][] m,int [][] s) {

        int N=p.length; //矩阵链的长度
        int n=N-1;//矩阵链的最后一个元素下表
        //对角线设置成0:以你为矩阵链的长度都是0，所以设置成0
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }

        // l=2时，计算 m[i,i+1],i=1,2,...,n-1 (长度l=2的链的最小代价)

        for (int l = 2; l <= n; l++) {//矩阵长度

            for (int i = 1; i <= n - l + 1; i++) {//矩阵乘法的起点,链长是r
                int j = i + l - 1;////以i为起始位置，j为长度为l的链的末位， j依次取值i+1,i+2,……,n

                m[i][j] = Integer.MAX_VALUE;
//                m[i][j] = m[i][i] + m[i + 1][j] + p[i - 1] * p[i] * p[j];//即m[i][j] = m[i][i]+m[i+1][j]+ p[i-1]*p[i]*p[j]
                s[i][j] = i;//表示i~j的断开点在i处，

                //k从i到j-1,以k为位置划分
                for (int k = i ; k <= j-1; k++) {//使用绝对位置表示k的位置:k表示断开点
                    int t = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];//计算断开点在k处的最小乘法数
                    if (t < m[i][j]) {
                        m[i][j] = t;
                        s[i][j] = k;
                    }
                }
            }
        }

        System.out.println("最小乘法次数是："+m[1][N - 1]);
        //cong1,dao
        traceBack(s, 1, N - 1);
    }

    /**
     * 计算
     * @param s
     * @param i
     * @param j
     */
    private void  traceBack(int [][] s,int i,int j){
        if (i==j){
            System.out.print("A"+i);
        }
        else {
            //从分割点 再次递归
            System.out.print("(");
            traceBack(s,i,s[i][j]);
            traceBack(s,s[i][j]+1,j);
            System.out.print(")");
        }
    }


    /*求最大配对数*/
    int RNA_2(char B[], int n) {
        int i, j, k, t;
        int max;
        int C[][] = new int[n + 2][n + 2];

        for (k = 5; k <= n - 1; k++) {
            for (i = 1; i <= n - k + 1; i++) {
                j = i + k;
                // 此处代码缺失，标记为(1)
                max = C[i][j - 1];
                for (t = i; t <= j - 4; t++) {
                    if (isMatch(B[t], B[j-1]) == 1 && (max < C[i][t - 1] + 1 + C[t + 1][j - 1])) {
                        max = C[i][t - 1] + 1 + C[t + 1][j - 1];
                    }

                }
                C[i][j] = max;
                System.out.println( String.format("c[%d][%d] = %d--", i, j, C[i][j]));
            }
        }
        return C[1][n];
    }

    int isMatch(char a, char b){
        if((a == 'A' && b == 'U') || (a == 'U' && b == 'A'))
            return 1;
        if((a == 'C' && b == 'G') || (a == 'G' && b == 'C'))
            return 1;
        return 0;
    }


    public static int maxPairs(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int k = 4; k < n; k++) {
            for (int i = 0; i < n - k; i++) {
                int j = i + k;
                int maxVal = dp[i][j - 1];
                for (int t = i + 1; t < j - 3; t++) {
                    if ((s.charAt(i) == 'A' && s.charAt(t) == 'U' || s.charAt(i) == 'U' && s.charAt(t) == 'A' ||
                            s.charAt(i) == 'C' && s.charAt(t) == 'G' || s.charAt(i) == 'G' && s.charAt(t) == 'C')) {
                        maxVal = Math.max(maxVal, dp[i + 1][t - 1] + 1 + dp[t + 1][j]);
                    }
                }
                dp[i][j] = maxVal;
            }
        }
        return dp[0][n - 1];
    }

}
