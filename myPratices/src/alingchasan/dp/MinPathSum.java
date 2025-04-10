package alingchasan.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 1、定义dp方程：dp[i][j]表示 从左上角的顶点到i,j 点的最小距离，状态转移方程为 f(i,j)=min(f(i-1,j),f(i,j-1)),可以使用递归写法
 * 2、使用带备忘录的自顶向下的方式写
 * 3、递推方案：使用二维数组：自底向上的方式 计算答案
 * 4、使用状态压缩：一维数组，自底相上的方式写。空间是O(n)
 * 5.状态压缩：使用元数组，空间状态是O(1)
 */
public class MinPathSum {

    public static void main(String[] args) {

        MinPathSum minPathSum=new MinPathSum();

//        System.out.println(minPathSum.minFallingPathSum(new int[][]{{2,1,3},{6,5,4},{7,8,9}}));

//        System.out.println(minPathSum.minFallingPathSum2(new int[][]{{2,1,3},{6,5,4},{7,8,9}}));

        System.out.println(minPathSum.minFallingPathSum111(new int[][]{{1,2,3},{4,5,6},{7,8,9}}));

//        System.out.println(minPathSum.maxMoves(new int[][]{{2,4,3,5},{5,4,9,3},{3,4,2,11},{10,9,13,15}}));
    }
    /**
     * 64. 最小路径和
     * 从左上角到右下角的最小路径和：
     * 定义dp[i][j]表示从左上角到i,j的最小路径，则dp[i][j] 只能从上、左节点达到，
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        int dp[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 0;
                } else if (i == 0) {
                    //只能从左边的方向走过来，即最小路径就是左边的最小路径加上当前grid的路径
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    //只能从上方路径演化而来
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                } else {
                    //比较左边和上面的最小路径，然后选择最小的，在加上当前路径
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 62. 不同路径
     * 从左上角到右下角 有多少不同的路径:每次只能向右或者向下移动
     *
     * 定义dp[i][j]:表示从左上角到当前点位 的不同路径，dp[i][j]=dp[i-1][j]+dp[i][j-1]
     * 到当前点的路径总数等于到上方和左边的路径综合
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {

        int [][]dp=new int[m][n];

        for (int i=0;i<m;i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;//到达第一个点 只有一种方案
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1];//第一行的点位 的总数 只能和他左边的总数一致
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //等于两者之和
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                }
            }
        }

        return dp[m-1][n-1];
    }


    /**
     * 63. 不同路径 II
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int m=obstacleGrid.length,n=obstacleGrid[0].length;

        //第一个位置是障碍物：因此不能像任何位置移动了
        if (obstacleGrid[0][0]==1){
            return 0;
        }
        int [][]dp=new int[m][n];

        for (int i=0;i<m;i++) {
            for (int j = 0; j < n; j++) {
                //当前位置是障碍物：因此当当前位置的路径总数是0
                if (obstacleGrid[i][j]==1){
                    dp[i][j] = 0;
                    continue;
                }
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;//到达第一个点 只有一种方案
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1];//第一行的点位 的总数 只能和他左边的总数一致
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //等于两者之和
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                }
            }
        }

        return dp[m-1][n-1];

    }

    /**
     *120. 三角形最小路径和
     * 定义f(i,j) 表示从i,j出发到达最后一排的最小路径和.这个节点可以走到(i+1,j)、(i+1,j+1)节点,则
     * f(i,j)=min(f(i+1,j),f(i+1,j+1))+triangle[i][j] 则表示最小值
     * 可以加上记忆化搜索,程序的入口就是f(0,0)
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {

        int n=triangle.size();
        int meme[][]=new int[n][n];

        for (int [] row:meme){
            Arrays.fill(row,Integer.MIN_VALUE);
        }

        return dfs(meme,0,0,triangle);
    }


    /**
     * 也可以使用递推的方式(就是循环的方式)：就是循环的方式，需要使用倒序的方式循环
     * 把元素想想成这种类型
     * 1
     * 1 1
     * 1 1 1
     * 1 1 1 1
     * @param triangle
     * @return
     */
    public int minimumTotal2(List<List<Integer>> triangle) {
        int n=triangle.size();
        int [][] f=new int[n][n]; //f函数的意思就是表示到最后一排的最小路径
        //先初始化f最后一行的数据,后面的倒序遍历需要依赖到这个数据
        for (int i=0;i<n;i++){
            f[n-1][i]=triangle.get(n-1).get(i);
        }

        for (int i=n-2;i>=0;i--){
            for (int j=0;j<=i;j++){
                //比较后面一层的 路径最小值，然后在和当前点位链接在一起
                f[i][j]=Math.min(f[i+1][j],f[i+1][j+1])+ triangle.get(i).get(j);
            }
        }

        //返回00位置的数据
        return  f[0][0];
    }

    /**
     * 进行空间优化 只用一个一维数组进行空间优化
     * 1
     * 1 1
     * 1 1 1
     * 1 1 1 1
     *
     *
     * [][][][]============f[n]
     * 其实就是把上面的1映射到下面的一维数组中
     * @param triangle
     * @return
     */
    public int minimumTotal3(List<List<Integer>> triangle) {
        int n=triangle.size();

        //空间压缩:空间压缩的原理就是 当前计算 只依赖上次计算的结果
        int f[]=new int[n];

        //倒序遍历triangle，每一行中在进行正序遍历
        for (int i=n-1;i>=0;i--){
            for (int j=0;j<=i;j++){
                //遍历每一行的某个元素时，需要找到他之前一行的下一个元素和下一个元素的右边元素的 路径最小值，然后在加上当前路径就形成了当前点的最小路径
                f[j]=Math.min(f[j],f[j-1])+triangle.get(i).get(j);
            }
        }

        //返回0位置的数据
        return  f[0];
    }
    private int  dfs( int meme[][],int i,int j,List<List<Integer>> triangle) {

        if (meme[i][j] != Integer.MIN_VALUE) {
            return meme[i][j];
        }
        //边界问题处理
        if (i == meme.length - 1) {
            return triangle.get(i).get(j);
        }

        //记录下来
        meme[i][j] = Math.min(dfs(meme, i + 1, j, triangle), dfs(meme, i + 1, j + 1, triangle)) + triangle.get(i).get(j);

        return meme[i][j];
    }


    /**
     * 931. 下降路径最小和
     * 定义f[i][j] 表示当前点到最后一行路径的最小值
     *  f[r][c]=min(f[r−1][c−1],f[r−1][c],f[r−1][c+1])+matrix[r][c]
     * 其实就是求和最小的路径
     * @param matrix
     * @return
     */
    public int minFallingPathSum(int[][] matrix) {

        int n = matrix.length;
        int f[][] = new int[n][n];
        f[0] = matrix[0];//使用第一行直接填充

        //从第二行开始
        for (int ii = 1; ii < n; ii++) {
            for (int jj = 0; jj < n; jj++) {
                //当前f[ii][jj]的最小值 只能来自他的上一层 的最近三个元素，然后在加上他本身
                int currentMin = f[ii - 1][jj];

                //优化点：1为了避免这种越界的判断 可以把列变宽两列，这样就可以直接计算了
                //优化点二:还可以进行空间压缩，保留一个数组存储，但是需要保留j对应的上一个值pre（因为当前问题存在覆盖）
                if (jj - 1 >= 0) {
                    currentMin = Math.min(currentMin, f[ii - 1][jj - 1]);
                }

                if (jj + 1 < n) {
                    currentMin = Math.min(currentMin, f[ii - 1][jj + 1]);
                }
                f[ii][jj] = currentMin + matrix[ii][jj];
            }
        }

        //遍历最后一排：求最小值
        int min = f[n-1][0];
        for (int k = 1; k < n; k++) {
            min = Math.min(min, f[n - 1][k]);
        }
        return min;
    }

    public int minFallingPathSum2(int[][] matrix) {
//        int n = matrix.length;
//        int f[][] = new int[n][n+2];
//        System.arraycopy(matrix[0],0,f[0],1,n);
//        //从第二行开始
//        for (int ii = 1; ii < n; ii++) {
//            f[ii-1][0]=f[ii-1][n+1]=Integer.MAX_VALUE;//把第一个和最后一个都填充为最大值
//            for (int jj = 0; jj < n; jj++) {
//                f[ii][jj+1] =Math.min(f[ii-1][jj+2], Math.min(f[ii-1][jj],f[ii-1][jj+1]))+matrix[ii][jj];
//            }
//        }
//
//        //遍历最后一排：求最小值
//        int min = f[n-1][1];
//        for (int k = 1; k < n+1; k++) {
//            min = Math.min(min, f[n - 1][k]);
//        }
//        return min;


        //空间优化版：
        int n = matrix.length;
        int f[] = new int[n+2];
        System.arraycopy(matrix[0],0,f,1,n);
        f[0]=f[n+1]=Integer.MAX_VALUE;//把第一个和最后一个都填充为最大值


        //从第二行开始
        for (int ii = 1; ii < n; ii++) {
            int pre=f[0];//第一次的pre 就是他自己
            for (int jj = 0; jj < n; jj++) {
                int tmp=pre;//存放上次的pre
                pre=f[jj+1];//记录当前位置的pre
                f[jj+1] =Math.min(tmp, Math.min(f[jj+1],f[jj+2]))+matrix[ii][jj];
            }
        }

        //遍历最后一排：求最小值
        int min = f[1];
        for (int k = 1; k < n+1; k++) {
            min = Math.min(min, f[k]);
        }
        return min;
    }


    /**
     * 2684. 矩阵中移动的最大次数
     * 方案一：使用深度dfs 算法，遍历第一列的所有元素，然后逐个向后进行，最大的列j就是可以移动的最大步数，同时我们还可以使用vis 数组记录单元格 被访问过了，在此题中我们可以直接设置单元格的值是0
     * 这就表示已经被访问(注意：如果不设置vis数组也是可行的，但是会存储多次搜索的过程)
     *
     * 方案二：BFS写法(其实就是广度遍历每一列 可以达到的下一列的 行号元素，收集行号，内部在遍历这些行号 在向后一列进行判断收集)
     *
     * @param grid
     * @return
     */
    public int maxMoves(int[][] grid) {
        //方案一：dfs
//        int m=grid.length,n=grid[0].length;
//        for (int i=0;i<m;i++){
//            dfs(i,0,grid);
//        }
//        return ans;

        //方案二：bfs

        int m = grid.length, n = grid[0].length;
        Set<Integer> rowIdxSet = new HashSet<>();
        //把初始行号都加入进来
        for (int i = 0; i < m; i++) {
            rowIdxSet.add(i);
        }

        for (int j = 0; j < n - 1; j++) {
            //遍历行号：这就能定位到二维数组了
            Set<Integer> nextRowIdxSet = new HashSet<>();
            for (Integer rowIdx : rowIdxSet) {

                //2、遍历可以到达的三个元素的行号
                for (int k = Math.max(0, rowIdx - 1); k < Math.min(m, rowIdx + 2); k++) {
                    if (grid[k][j + 1] > grid[rowIdx][j]) {
                        nextRowIdxSet.add(k);//使用set的原因就是不用重复
                    }
                }
            }
            //没有大于后面的行号了
            if (nextRowIdxSet.isEmpty()) {
                return j;
            }
            //下一次的遍历集合
            rowIdxSet = nextRowIdxSet;
        }

        return n - 1;
    }

    int ans =0;
    private  void dfs(int i,int j,  int[][] grid) {
        ans = Math.max(ans, j);
        //达到了最后一列
        if (ans == grid[0].length - 1) {
            return;
        }

        //其实就是遍历 右上、右、右下三个坐标点位
        for (int k = Math.max(0, i - 1); k < Math.min(i + 2,grid.length); k++) {
            //后面的点位值 要大于当前的值
            if (grid[k][j + 1] > grid[i][j]) {
                dfs(k, j + 1, grid);
            }
        }

        grid[i][j]=0;
    }


    /**
     * 1289. 下降路径最小和 II
     * 使用动态规划解决：
     * 定义函数 f(i,j)：选择第i行的第j个元素的最小路径和 因此状态转移方式是
     * f(i,j)=grid(i,j) i=0;
     * f(i,j)=min(f(i-1,k)) + grid(i,j) i!=0&&j=k; 在第i-1行的基础上转移过来的
     * @param grid
     * @return
     */
    public int minFallingPathSum111(int[][] grid) {

        int min = Integer.MAX_VALUE;
        int m = grid.length, n = grid[0].length;

//        //使用备忘录
//        int meme[][]=new int[m][n];
//        for (int[] mm:meme){
//            Arrays.fill(mm,Integer.MIN_VALUE);
//        }
//        for (int j = 0; j < n; j++) {
//            min = Math.min(min, dfs2(m - 1, j, grid,meme));
//        }

        //方案二：使用自底向上的方式 循环计算
//        int f[][]=new int[m][n];//从第0行开始，到底i行的第j个元素的最小路径
//        f[0]=grid[0];//
//
//        for (int i=1;i<m;i++){
//            for (int j=0;j<n;j++){
//                f[i][j]=Integer.MAX_VALUE;//默认先设置一个最大子
//                //求f[i][j] 子问题的最小值:遍历上一行中的所有元素，然后求最小值
//                for (int k=0;k<n;k++){
//                    //遍历非同一列的元素
//                    if (j!=k) {
//                        f[i][j] = Math.min(f[i][j], f[i - 1][k] + grid[i][j]);
//                    }
//                }
//            }
//        }
//
//        //比较最后一行：获取最小值
//        for (int j = 0; j < n; j++) {
//            min = Math.min(min, f[m-1][j]);
//        }
//        return min;
        //优化三：在方案二中 我们最内部的一个循环一直在寻找上一次的最小值，因此我们可以使用三个变量来记录上一行中的【最小值、最小值下表、次小值]这样就能快速获取到结果

        int lastMinPathSum = 0;//上一行的路径最小值
        int lastMinPathSumIndex = -1;//上一行的路径最小值小标
        int lastSecondMinSum = 0;//上一行的路径和的第二最小值
        for (int i = 0; i < m; i++) {
            int currentRowFirstMinSum = Integer.MAX_VALUE;//当前行下 路径和的最小值
            int currentRowMinPathSumIndex = -1;//当前行下的路径和的最小值下表
            int currentRowSecondMinSum = Integer.MAX_VALUE;//第二最小值

            for (int j = 0; j < n; j++) {
                //计算选择grid[i][j]状态下的最小值
                int currentSum = (j != lastMinPathSumIndex ? lastMinPathSum : lastSecondMinSum) + grid[i][j];
                if (currentSum < currentRowFirstMinSum) {
                    currentRowSecondMinSum = currentRowFirstMinSum;
                    currentRowFirstMinSum = currentSum;
                    currentRowMinPathSumIndex = j;
                } else if (currentSum < currentRowSecondMinSum) {
                    //如果小于当前第二小的值
                    currentRowSecondMinSum = currentSum;
                }
            }
            //把当前行的最小值给全局变量，然后继续遍历下一行
            lastMinPathSum = currentRowFirstMinSum;
            lastMinPathSumIndex = currentRowMinPathSumIndex;
            lastSecondMinSum = currentRowSecondMinSum;
        }

        return lastMinPathSum;
    }

    /**
     * 自上而下的子问题的最小值
     * @param i
     * @param j
     * @param grid
     * @return
     */
    private int dfs2(int i,int j, int[][] grid,int meme[][]) {

        //使用备忘录
        if (meme[i][j]!=Integer.MIN_VALUE){
            return meme[i][j];
        }

        //如果到达第一列了，他没有子问题了，因此他本身就是最小值
        if (i==0){
            return grid[i][j];
        }

        int subProbromMin = Integer.MAX_VALUE;
        //求子问题的最小值
        for (int k = 0; k < grid[0].length; k++) {
            //不是同一列
            if (k != j) {
                subProbromMin = Math.min(subProbromMin,dfs2(i - 1, k, grid,meme));
            }
        }

        meme[i][j]=subProbromMin + grid[i][j];
        //子问题的最小值
        return subProbromMin + grid[i][j];
    }
}
