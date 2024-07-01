package dfs;

//使用DFS递归遍历岛屿问题
public class Islands {

    //岛屿的数量:0代表海水，1代表陆地
    //使用的是沉岛策略，为啥不使用visited数组，似乎因为懒得维护了
    int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid,int i,int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == '0') {
            return;
        }
        //填海
        grid[i][j] = '0';

        //向上下左右递归
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }
}
