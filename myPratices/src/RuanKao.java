public class RuanKao {

    public static void main(String[] args) {
        RuanKao ruanKao=new RuanKao();
        int p[] = {30,35,15,5,10,20,25};
        int n=p.length;
        ruanKao.MatrixChain(p,p.length-1,new int[n][n],new int[n][n]);
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
    public void MatrixChain(int [] p,int n,int [][] m,int [][] s) {

        //对角线设置成0
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }

        for (int r = 1; r < n; r++) {//矩阵长度

            for (int i = 1; i <= n - r + 1; i++) {//矩阵乘法的起点,链长是r
                int j = i + r - 1;//j依次取值i+1,i+2,……,n
                m[i][j] = Integer.MAX_VALUE;
//                m[i][j] = m[i][i] + m[i + 1][j] + p[i - 1] * p[i] * p[j];//即m[i][j] = m[i][i]+m[i+1][j]+ p[i-1]*p[i]*p[j]
                s[i][j] = i;//表示i~j的断开点在i处，
                for (int k = i + 1; k < j; k++) {//使用绝对位置表示k的位置:k表示断开点
                    int t = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];//计算断开点在k处的最小乘法数
                    if (t < m[i][j]) {
                        m[i][j] = t;
                        s[i][j] = k;
                    }
                }
            }
        }

        //cong1,dao
        traceBack(s, 1, n - 1);
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
}
