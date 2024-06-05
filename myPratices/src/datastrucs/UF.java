package datastrucs;

/**
 * 连通图相关的用例
 */
public class UF {

    /**
     * 联通分量
     */
    private int count;
    //存放元素的父节点
    int[] parent;

    public UF(int count) {
        this.count = count;
        parent = new int[count];

        //设置每一个节点的父节点指向自己
        for (int i = 0; i < count; i++) {
            parent[i] = i;
        }
    }

    /**
     * 判断两个节点是否联通
     *
     * @param a
     * @param b
     * @return
     */
    public boolean connect(int a, int b) {
        int parentA = findParent(a);
        int parentB = findParent(b);
        return parentA == parentB;
    }

    /**
     * 联通a和b
     *
     * @param a
     * @param b
     */
    public void union(int a, int b) {
        int parentA = findParent(a);

        int parentB = findParent(b);
        //随便把他指向一个节点
        if (parentA != parentB) {
            parent[parentA] = parentB;
            count--;
        }
    }

    public int getCount() {
        return count;
    }

    /**
     * 查找a的父节点s
     *
     * @param x
     * @return
     */
    public int findParent(int x) {

        //会造成数据变成链表一样，因此我们需要把他进行压缩
//        while (x!=parent[x]){
//            x=parent[x];
//        }
//        return x;

        //2、压缩版
        while (x != parent[x]) {
            //把当前节点的父节点指向他的爷爷
            parent[x] = parent[parent[x]];
            x = parent[x];
        }

        return x;
    }

    //计算图的连通分量
    int countComponents(int n, int[][] edges) {
        UF uf = new UF(n);
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        return uf.getCount();
    }

    /**
     * ["c==c","b==d","x!=z"]，算法返回 true
     *
     * @param equations
     * @return
     */
    boolean equationsPossible(String[] equations) {

        UF uf = new UF(26);

        // 1、先把相等的进行联通
        for (String str : equations) {
            if (str.charAt(1) == '=') {
                uf.union(str.charAt(0) - 'a', str.charAt(3) - 'a');
            }
        }
        // 2、判断不相等的是否能联通，如果联通则从图
        for (String str : equations) {
            if (str.charAt(1) == '!') {
                boolean result = uf.connect(str.charAt(0) - 'a', str.charAt(3) - 'a');
                if (result) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 比如力扣第 130 题「被围绕的区域」：
     * <p>
     * 给你一个 M×N 的二维矩阵，其中包含字符X和O，让你找到矩阵中四面被X围住的O，并且把它们替换成X。
     * 可以使用联通问题，
     * 1、先把边上的和O和一个虚拟节点进行联通，
     * 2、然后在遍历除边上的O元素和他周围哦联通性，
     * 3最后在遍历下 中间的O是否和虚拟节点具有联通行，不具有联通性的则设置成X
     *
     * @param board
     */
    void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        int count = m * n;
        int dummy = count;//最后一个节点当做周围o的联通性
        UF uf = new UF(count + 1);
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'o') {
                uf.union(dummy, i * n);
            }

            if (board[i][n - 1] == 'o') {
                uf.union(dummy, i * n + n - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (board[0][i] == 'o') {
                uf.union(dummy, i);
            }
            if (board[m - 1][i] == 'o') {
                uf.union(dummy, n * (m - 1) + i);
            }
        }

        //处理中间元素的和他四周元素的联通性问题
        int[][] direct = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

        //注意：遍历的边界前后上下都少了一，主要是为了在计算某个元素的上下左右的元素时，可以不需要再关心边界问题，可直接获取了
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (board[i][j] == 'o') {
                    for (int k = 0; k < 4; k++) {
                        int x = i + direct[k][0];
                        int y = i + direct[k][1];
                        //把i,j和他周围的o进行联通，此时很有可能就和全局的dummy节点进行了联通
                        if (board[x][y] == 'o') {
                            uf.union(i * n + j, x * n + y);
                        }
                    }
                }
            }
        }

        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (board[i][j] == 'o') {
                    boolean connect = uf.connect(i * n + j, dummy);
                    if (!connect) {
                        board[i][j] = 'x';
                    }
                }
            }
        }


    }

}
