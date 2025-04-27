package aaaruankao;

/**
 * https://blog.csdn.net/qq_44766883/article/details/105859956
 */
public class MatrixChainMultiplication {
    static final int N = 7;

    // p为矩阵链，p[0],p[1]代表第一个矩阵的行数和列数，p[1],p[2]代表第二个矩阵的行数和列数......length为p的长度
    // 所以如果有六个矩阵，length=7，m为存储最优结果的二维矩阵，s为存储选择最优结果路线的二维矩阵
    static void MatrixChainOrder(int[] p, int[][] m, int[][] s, int length) {
        int n = length - 1;
        int l, i, j, k, q;
        // m[i][i]只有一个矩阵，所以相乘次数为0，即m[i][i]=0;
        for (i = 1; i < length; i++) {
            m[i][i] = 0;
        }
        // l表示矩阵链的长度
        // l=2时，计算 m[i,i+1],i=1,2,...,n-1 (长度l=2的链的最小代价)
        for (l = 2; l <= n; l++) {
            for (i = 1; i <= n - l + 1; i++) {
                j = i + l - 1; // 以i为起始位置，j为长度为l的链的末位
                m[i][j] = Integer.MAX_VALUE;
                // k从i到j-1,以k为位置划分
                for (k = i; k <= j - 1; k++) {
                    q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        System.out.println(m[1][N - 1]);
    }

    static void PrintAnswer(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            PrintAnswer(s, i, s[i][j]);
            PrintAnswer(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }

    public static void main(String[] args) {
        int[] p = {30, 35, 15, 5, 10, 20, 25};
        int[][] m = new int[N][N];
        int[][] s = new int[N][N];
        MatrixChainOrder(p, m, s, N);
        PrintAnswer(s, 1, N - 1);
    }
}    