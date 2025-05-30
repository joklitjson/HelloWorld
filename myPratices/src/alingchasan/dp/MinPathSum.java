package alingchasan.dp;

import java.util.*;

/**
 * 1、定义dp方程：dp[i][j]表示 从左上角的顶点到i,j 点的最小距离，状态转移方程为 f(i,j)=min(f(i-1,j),f(i,j-1)),可以使用递归写法
 * 2、使用带备忘录的自顶向下的方式写
 * 3、递推方案：使用二维数组：自底向上的方式 计算答案
 * 4、使用状态压缩：一维数组，自底相上的方式写。空间是O(n)
 * 5.状态压缩：使用元数组，空间状态是O(1)
 */
public class MinPathSum {

    public static void main(String[] args) {

        MinPathSum minPathSum = new MinPathSum();

//        System.out.println(minPathSum.minFallingPathSum(new int[][]{{2,1,3},{6,5,4},{7,8,9}}));

//        System.out.println(minPathSum.minFallingPathSum2(new int[][]{{2,1,3},{6,5,4},{7,8,9}}));

        System.out.println(minPathSum.minFallingPathSum111(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));

//        System.out.println(minPathSum.maxMoves(new int[][]{{2,4,3,5},{5,4,9,3},{3,4,2,11},{10,9,13,15}}));

        System.out.println(minPathSum.maxProductPath(new int[][]{{1, -2, 1}, {1, -2, 1}, {3, -4, 1}}));

        List<String> path=new ArrayList<>(Arrays.asList("E12","1X1","21S"));
//        path=new ArrayList<>(Arrays.asList("E11","XXX","11S"));
//        System.out.println(Arrays.toString(minPathSum.pathsWithMaxScore(path)));

        System.out.println(minPathSum.numberOfPaths(new int[][] {{5,2,4},{3,0,5},{0,7,2}},3));

        System.out.println(minPathSum.countPaths(new int[][]{{1,1},{3,4}}));
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


    /**
     * 3418. 机器人可以获得的最大金币数
     * 分析：定义f[i][j][k]:表示在最大感化次数为k的情况下，在点 i,j位置可以获取的最大金币数。
     * 自顶向下的进行 分析：f[i][j] 的最大值 是来自f[i-1][j] 或者f[i][j-1]中的最大值，然后在加上conin[i][j]
     * 情况二：如果当前位置是小于0的，而且有剩余感化次数k的情况下，可以选择感化
     * 然后我们在加上 使用备忘录模式，记录已经计算的情况，防止反复计算
     * @param coins
     * @return
     */
    public int maximumAmount(int[][] coins) {

        int m=coins.length,n=coins[0].length;
        //注意：这里为啥是3？代表这 有0、1、2次感化次数的情况下的 可以获得的金币的最大值
        int[][][] memo=new int[m][n][3];
        //设置初始值是最小值
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, Integer.MIN_VALUE);
            }
        }

        return dfs(m-1, n-1,memo,2,coins);
    }

    private int dfs(int i,int j, int[][][] mem, int k,int[][] coins) {
        //越界处理
        if (i < 0 || j < 0) {
            return Integer.MIN_VALUE;
        }

        int x = coins[i][j];
        if (i == 0 && j == 0) {
            return k > 0 ? Math.max(x, 0) : x;
        }


        if (mem[i][j][k] != Integer.MIN_VALUE) { // 之前计算过
            return mem[i][j][k];
        }

        //不使用感化次数
        int res = Math.max(dfs(i - 1, j, mem, k, coins), dfs(i, j - 1, mem, k, coins)) + coins[i][j];

        //可以使用感化次数
        if (k > 0 && coins[i][j] < 0) {
            res = Math.max(res, Math.max(dfs(i - 1, j, mem, k - 1, coins), dfs(i, j - 1, mem, k - 1, coins)));
        }

        mem[i][j][k] = res;// 记忆化
        return mem[i][j][k];
    }


    /**
     * 1594. 矩阵的最大非负积
     * 分析：求矩阵的非负数 乘机最大值，需要记录每个单元格从0,0到此单元格的最大值和最小值，然后在乘以当前单元格的值，在进行比较，在计算出单元格的最大
     * 值和最小值
     * @param grid
     * @return
     */
    public int maxProductPath(int[][] grid) {

        int N = (int) (Math.pow(10, 9) + 7);
        int m = grid.length, n = grid[0].length;
        long maxDp[][] = new long[m][n];//防止溢出
        long minDp[][] = new long[m][n];
        maxDp[0][0] = grid[0][0];
        minDp[0][0] = grid[0][0];//设置第一个空的最大和最小值

        for (int i = 1; i < m; i++)
            maxDp[i][0] = minDp[i][0] = grid[i][0] * maxDp[i - 1][0];//设置第一列元素的最大和最小值

        for (int j = 1; j < n; j++) {
            maxDp[0][j] = minDp[0][j] = grid[0][j] * maxDp[0][j - 1];//设置第一行元素的最大和最小值
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                long one = grid[i][j] * minDp[i - 1][j];
                long one2 = grid[i][j] * maxDp[i - 1][j];
                long one3 = grid[i][j] * minDp[i][j - 1];
                long one4 = grid[i][j] * maxDp[i][j - 1];
                minDp[i][j] = Math.min(Math.min(one, one2), Math.min(one3, one4));
                maxDp[i][j] = Math.max(Math.max(one, one2), Math.max(one3, one4));
            }
        }

        return maxDp[m - 1][n - 1] >= 0 ? (int) (maxDp[m - 1][n - 1] % N) : -1;
    }


    /**
     * 1301. 最大得分的路径数目
     * 把是X的字符表示成 到达该字符的最大路径和表示成-1，如果一个元素的 【前面的三个元素】都是-1 则这个元素不可达，
     * 然后在求 到达每个节点的最大值，在比较 【前面的三个元素】 与最大值的关系，相等则把方案书相加
     * @param board
     * @return
     */
    public int[] pathsWithMaxScore(List<String> board) {

        int mod= (int) (Math.pow(10,9)+7);
        int m = board.size(), n = board.get(0).length();
        int[][] dp = new int[m][m];//表示从左上角到dian[i][j]的最的路径和
        long[][] dpCnt = new long[m][m];

        dp[0][0] = 0;
        dpCnt[0][0]=1;
        for (int i = 1; i < m; i++) {
            //设置第一列的最大路径和
            dp[i][0] = (board.get(i).charAt(0) == 'X' || dp[i - 1][0] == -1) ? -1 : dp[i - 1][0] + (board.get(i).charAt(0) - '0');
            dpCnt[i][0]=(board.get(i).charAt(0) == 'X' || dp[i - 1][0] == -1) ? 0 : 1;
        }

        for (int j = 1; j < n; j++) {
            //设置第一行的最大值
            char c = board.get(0).charAt(j);
            if (c == 'X' || dp[0][j - 1] == -1) {
                dp[0][j] = -1;//表示过不去
            } else {
                dp[0][j] = dp[0][j - 1] + (board.get(0).charAt(j) - '0');
                dpCnt[0][j]=1L;
            }

        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                char c = board.get(i).charAt(j);
                if (c == 'X') {
                    dp[i][j] = -1;//表示过不去
                } else {
                    if (dp[i - 1][j] == -1 && dp[i - 1][j - 1] == -1 && dp[i][j - 1] == -1) {
                        dp[i][j] = -1;//表示过不去
                    } else if (c == 'S') {
                        dp[i][j] = Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);

                        //计算最大次数
                        if (dp[i - 1][j] == dp[i][j]) {
                            dpCnt[i][j]+=dpCnt[i-1][j];
                        }
                        if (dp[i - 1][j - 1] == dp[i][j]) {
                            dpCnt[i][j]+=dpCnt[i-1][j-1];
                        }
                        if (dp[i][j - 1] == dp[i][j]) {
                            dpCnt[i][j]+=dpCnt[i][j-1];
                        }
                        dpCnt[i][j]=dpCnt[i][j]%mod;
                    } else {
                        int max = Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) ;
                        dp[i][j] = max + (c - '0');
                        //达到最大值的 方案数 累加
                        if (dp[i - 1][j] == max) {
                            dpCnt[i][j]+=dpCnt[i-1][j];
                        }
                        if (dp[i - 1][j - 1] == max) {
                            dpCnt[i][j]+=dpCnt[i-1][j-1];
                        }
                        if (dp[i][j - 1] == max) {
                            dpCnt[i][j]+=dpCnt[i][j-1];
                        }

                        dpCnt[i][j]=dpCnt[i][j]%mod;
                    }

                }
            }
        }

        return dp[m - 1][n - 1] == -1 ? new int[]{0, 0} : new int[]{dp[m - 1][n - 1], (int) (dpCnt[m - 1][n - 1]%mod)};
    }

    /**
     * 2435. 矩阵中和能被 K 整除的路径
     * 分析：其实就是求 矩阵中从 左上角 到右下角 有多少中路径组合，需要记录每个点的 每个路径的组合
     *
     * 具体地，定义 f[i][j][v] 表示从左上走到 (i,j)，且路径和模 k 的结果为 v 时的路径数。
     * @param grid
     * @param k
     * @return
     */
    public int numberOfPaths(int[][] grid, int k) {


        int MOD = (int) (Math.pow(10, 9) + 7);
        int m = grid.length, n = grid[0].length;

//        //点位信息|路径和%k-->个数
//        Map<Long, Long>[][] dp = new Map[m][n];
//        dp[0][0] = new HashMap();
//        dp[0][0].put((long) grid[0][0]%k, 1L);
//        for (int i = 1; i < m; i++) {
//            //设置第一列的元素
//            dp[i][0] = new HashMap();
//            //统计个数
//            for (Long preSum : dp[i-1][0].keySet()) {
//                dp[i][0].put((preSum + grid[i][0])%k, 1L);
//            }
//
//        }
//        for (int j = 1; j < n; j++) {
//            //设置第一行的元素
//            dp[0][j] = new HashMap();
//            //统计个数
//            for (Long preSum : dp[0][j-1].keySet()) {
//                dp[0][j].put((preSum + grid[0][j])%k, 1L);
//            }
//        }
//
//
//        for (int i = 1; i < m; i++) {
//            for (int j = 1; j < n; j++) {
//                dp[i][j] = new HashMap( );
//                //计算从上方移动下来路径的和
//                for (Map.Entry<Long, Long> entry : dp[i - 1][j].entrySet()) {
//                    Long pathSum = (entry.getKey() + grid[i][j])%k;
//                    dp[i][j].put(pathSum, (dp[i][j].getOrDefault(pathSum, 0L) + entry.getValue()) % MOD);
//                }
//
//                //计算从左边移动下来路径的和的余数
//                for (Map.Entry<Long, Long> entry : dp[i][j - 1].entrySet()) {
//                    Long pathSum = (entry.getKey() + grid[i][j])%k;
//                    dp[i][j].put(pathSum, (dp[i][j].getOrDefault(pathSum, 0L) + entry.getValue()) % MOD);
//                }
//            }
//        }
//
//        //获取模是0的个数
//        return (int) (dp[m - 1][n - 1].getOrDefault(0L,0L)%MOD);

        //优化：直接定义一个三维数组:定义 f[i][j][v] 表示从左上走到 (i,j)，且路径和模 k 的结果为 v 时的路径数。

        int dp[][][] = new int[m + 1][n + 1][k];
        dp[0][1][0] = 1;//设置第一个是1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int v = 0; v < k; v++) {
                    //把上方、左方余数是v 的结果数相加，然后在计算 新的余数
                    dp[i + 1][j + 1][(v + grid[i][j]) % k] = (dp[i][j + 1][v] + dp[i + 1][j][v]) % MOD;
                }
            }
        }

        return dp[m][n][0];
    }

    /**
     * 174. 地下城游戏
     *
     * 分析：需要从左上角到右下角，保证到达每个格子的血量都是要大于0的，否则可能救不出公主，因此我们采用倒推的方式，要保证到达最后一个节点时血量是大于0 的。
     *
     * 而这两个值的重要程度相同。
     *
     * 如果按照从左上往右下的顺序进行动态规划，我们无法直接确定到达某一点的方案，因为有两个重要程度相同的参数同时影响后续的决策。也就是说，这样的动态规划是不满足「无后效性」的。
     *
     * 所以我们的动态规划考虑从右下往左上进行。
     *
     * 令dp[i][j] 表示从坐标 (i,j)到终点所需的最小初始值。换句话说，当我们到达坐标 (i,j) 时，如果此时我们的路径和不小于dp[i][j]，我们就能到达终点(意思就是当前血量要不小于到达终点所需的最少血量)。
     *
     * 这样一来，我们就无需担心路径和的问题，只需要关注最小初始值。对于 dp[i][j]，我们只要关心 dp[i][j+1] 和 dp[i+1][j] 的最小值 minn。
     * 记当前格子的值为 dungeon(i,j)，那么在坐标(i,j) 的初始值只要达到minn−dungeon(i,j)即可(为什么是减号呢？假设当前格子值为正，表示这一格可以回血，
     * 则从当前点到终点所需的最小血量比下一格到终点所需的最小血量要小，小的这个数就是当前格回的血量；若当前格子值为负，表示这一格扣血，
     * 则从当前点到终点所需的最小血量比下一格到终点所需的最小血量要多，多的数就是这一格要扣的血量)。同时，初始值还必须大于等于 1(骑士最少也需要一滴血，不然就gg了)
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {

        int m = dungeon.length, n = dungeon[0].length;

        int dp[][] = new int[m + 1][n + 1];//定义：令dp[i][j] 表示从坐标 (i,j)到终点所需的最小初始值
        for (int[] arr : dp) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        dp[m][n - 1] =dp[m-1][n]= 1;//到达最后一个节点时的血量需要达到1

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int min = Math.min(dp[i + 1][j], dp[i][j + 1]);//顺加取大，逆减取小：
                dp[i][j] = Math.max(min - dungeon[i][j], 1);//1是兜底的血量
            }
        }

        return dp[0][0];
    }


    /**
     * 329. 矩阵中的最长递增路径
     * 分析：使用深度优先搜索+记忆化,逐个遍历节点
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int mem[][] = new int[m][n];

        int ans = 0;//最长的结果
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //计算点 i,j的最大递增长度
                ans = Math.max(ans, dfsGetLongestIncre(matrix, i, j, mem));
            }
        }

        return ans;
    }

    static  int [][] DIR={{-1,0},{1,0},{0,1},{0,-1}};
    private int dfsGetLongestIncre(int[][] matrix,int i,int j,int mem[][]) {
        //已经搜索过了
        if (mem[i][j] != 0) {
            return mem[i][j];
        }
        mem[i][j] = 1;//他自己构成一个长度,而是表示这个点已经被搜索了，防止搜索的过程中形成环

        for (int[] dir : DIR) {
            int xx = dir[0] + i;
            int yy = dir[1] + j;
            //新的坐标不越界，并且大于当前这个元素
            if (xx >= 0 && xx < mem.length && yy >= 0 && yy < mem[0].length && matrix[xx][yy] > matrix[i][j]) {
                //搜邻居节点xx,yy，查看他得最大递增长度
                mem[i][j] = Math.max(mem[i][j], dfsGetLongestIncre(matrix, xx, yy, mem)+1);
            }
        }

        return mem[i][j];
    }

    int     MOD = (int) (Math.pow(10,9)+7);
    /**
     * 2328. 网格图中递增路径的数目
     * @param grid
     * @return
     */
    public int countPaths(int[][] grid) {

        int m=grid.length,n=grid[0].length;
        int ans=0;
        int mem[][] = new int[m][n]; //记录按某个点开始的递增的个数

        for (int []tmp:mem){
            Arrays.fill(tmp,-1);
        }
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                ans=(ans+count(grid,i,j,mem))%MOD;
            }
        }

        return ans;
    }

    private int count(int[][] matrix,int i,int j,int mem[][]) {
        if (mem[i][j] != -1) {
            return mem[i][j];
        }

        int res = 1;
        //向四个方向遍历
        for (int[] dir : DIR) {
            int xx = dir[0] + i;
            int yy = dir[1] + j;
            //新的坐标不越界，并且大于当前这个元素
            if (xx >= 0 && xx < mem.length && yy >= 0 && yy < mem[0].length && matrix[xx][yy] > matrix[i][j]) {
                //统计个数
                res = (res + count(matrix, xx, yy, mem)) % MOD;
            }
        }
        mem[i][j] = res;
        return mem[i][j];
    }


    /**
     * 1937. 扣分后的最大得分
     * @param points
     * @return
     */
    public long maxPoints(int[][] points) {

//        int m = points.length, n = points[0].length;
//        long[][] maxScoreDp = new long[m][n];//定义数组：表示从上到下 选择这个元素的最大分数
//
//        //第一列直接转移
//        for (int j = 0; j < n; j++) {
//            maxScoreDp[0][j] = (long) points[0][j];
//        }
//
////      状态转移方程  dp[i][j]= point[i][j]+Max(dp[i-1][k]-abs(k-j));
//        for (int i = 1; i < m; i++) {
//            //遍历这一行的元素
//            for (int j = 0; j < n; j++) {
//                maxScoreDp[i][j] = Integer.MIN_VALUE;
////                计算元素  maxScoreDp[i][j] 和上一行的所有元素的组合
//                for (int k = 0; k < n; k++) {
//                    maxScoreDp[i][j] = Math.max(maxScoreDp[i][j], maxScoreDp[i - 1][k] - Math.abs(k - j));
//                }
//                //在加上本身
//                maxScoreDp[i][j]+= points[i][j];
//            }
//        }
//        long max = maxScoreDp[m - 1][0];
//        for (int k = 1; k < n; k++) {
//            max = Math.max(max, maxScoreDp[m - 1][k]);
//        }
//        return max;


        //方案二：优化：根据状态转移方程dp[i][j]= point[i][j]+Max(dp[i-1][k]-abs(k-j)); 我们可以把方程拆分出两部分

//        dp[i][j]= point[i][j]+Max(dp[i-1][k]-j+k) (k<j;)====>point[i][j]-j+Max(dp[i-1][k]+k)
//        dp[i][j]= point[i][j]+Max(dp[i-1][k]-k+j) (k>=j;)====>point[i][j]+j+Max(dp[i-1][k]-k)
//        其实我们就是求j左侧的【dp[i-1][k]+k】最大值，以及位置j右侧【Max(dp[i-1][k]-k)】的最大值，然后在 比较 就能快速求出j位置处的最值

        int m = points.length, n = points[0].length;

        //分析可知    我们求当前行的最大值 是和上一行的数据有关，因此我们只需要创建两个数组，然后交替使用就行了
        long pre[] = new long[n];

        for (int i = 0; i < m; i++) {

            long cur[] = new long[n];

            long leftMaxValue=Integer.MIN_VALUE;//寻找j左侧最大值[point[i][j]-j+Max(dp[i-1][k]+k)]
            //以下循环的说明
            // 1、以下循环会从左到右循环：会记录j左边的最大值
            // 2、会更新cur[j],当k<j时的最大值
            // 3、此种写法的好处：一个循环就能找到每个每个元素j左边的最大值
            for (int j=0;j<n;j++) {
                leftMaxValue = Math.max(leftMaxValue, pre[j] + j);
                cur[j] = Math.max(cur[j], points[i][j]-j + leftMaxValue);
            }

            long rightMaxValue=Integer.MIN_VALUE;//记录j左右的最大值
            //从右向左遍历：一次就能找到每个元素右边的最大值，然后在和当前比较，就能获取到当前的最大值值了
            for (int j=n-1;j>=0;j--) {
                rightMaxValue = Math.max(rightMaxValue, pre[j] - j);
                cur[j] = Math.max(cur[j], points[i][j] + j + rightMaxValue);
            }
            //准备遍历下一行了，则把当前行求的结果给上一行
            pre = cur;
        }

        //获取最大值
        long max = pre[0];
        for (long value : pre) {
            max = Math.max(max, value);
        }
        return max;
    }
}
