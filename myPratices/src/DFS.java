public class DFS {

    /**
     * LCR 112. 矩阵中的最长递增路径
     * 解决方案：遍历每个店，然后向点的四周扩展，如果下一个点大于当前点，则进行+1
     * 在使用 回忆录进行存储某个点的最大递增长度
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        row = matrix.length;
        colomn=matrix[0].length;
        int[][] mem = new int[row][colomn];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colomn; j++) {
                //遍历求每个店的最大递增长度
                int tmp = dfs(matrix, mem,i, j);
                max = Math.max(tmp, max);
            }
        }
        return max;
    }

    int max=0;
    int row=0,colomn=0;
    int [][]dirs=new int[][]{{0,1},{0,-1},{1,0},{-1,0}};

    private int dfs(int[][] matrix, int [][]mem, int x,int y) {

        //表示这个点 还没进行计算
        if (mem[x][y] == 0) {
            mem[x][y] = 1;//他自己算是一个增长

            for (int[] dir : dirs) {
                int xx = x + dir[0];
                int yy = y + dir[1];
                //当前点事合法的，并且值大于上一个点
                if (xx >= 0 && xx < row && yy >= 0 && yy < colomn && matrix[xx][yy] > matrix[x][y]) {
                    //比较他俩的最大值(同时把新的点在进行递归)
                    mem[x][y] = Math.max(mem[x][y], dfs(matrix, mem, xx, yy) + 1);
                }
            }
        }
        return mem[x][y];
    }

    /**
     * 方案二：使用拓扑排序的初度概念，当元素比四周的元素小，则表示出度+1，
     * 使用广度遍历发：优先遍历初度为0的点，然后在把他周围的点的初度
     * @param matrix
     * @return
     */
    public int longestIncreasingPath2(int[][] matrix) {
        return 0;
    }
}
