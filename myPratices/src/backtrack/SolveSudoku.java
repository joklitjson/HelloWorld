package backtrack;

public class SolveSudoku {

    public static void main(String[] args) {

        char [][] border=new char[][]  {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        SolveSudoku solveSudoku= new SolveSudoku();
        solveSudoku.print(border);
        new SolveSudoku().solveSudoku(border);
    }

    void solveSudoku(char[][] board){
       boolean res=  backtrack(board,9,0,0);
       if (res){
           System.out.println("解开成功!");
           for (int i=0;i<9;i++){
               for (int j=0;j<9;j++){
                   System.out.print(board[i][j]+" ");
               }
               System.out.println();
           }
       }
       else {
           System.out.println("不能解开!");
       }

    }

    int count=0;
    void print(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 返回bool值是可以提前结束回溯
     * @return
     */
    private boolean backtrack(char[][] board, int n, int i,int j){
//        System.out.println("*****************"+count+"************************");
//        print(board);
        //一行到底了，因此需要遍历下一行
        if (j==n){
            System.out.println(i);
            return backtrack(board,n,i+1,0);
        }

        //到最后一行了，因此成功了
        if (i==n){
            return true;
        }
        //已经有数据了，因此可以直接回溯
        if (board[i][j]!='.'){
            return backtrack(board,n,i,j+1);
        }

        for (char ch='1';ch<='9';ch++) {

            //1、进行验证
            if (!valide(board,n,i,j,ch)){
                continue;
            }

            board[i][j] = ch;
            count++;
            // 如果找到一个可行解，立即结束
            boolean result = backtrack(board, n, i, j + 1);
            if (result) {
                return true;
            }
            //撤销
            board[i][j] = '.';
        }
        return false;
    }

    /**
     * 验证这个点的行和列上是否有重复数据
     * @param board
     * @param row
     * @param col
     * @return
     */
    private boolean valide(char[][] board,int n,int row,int col,char ch){

        for (int i=0;i<n;i++){
            //判断行上是否有和ch相同的
            if (board[row][i]==ch){
                return false;
            }
            //判断列
            if (board[i][col]==ch){
                return false;
            }

            // 判断 3 x 3 方框是否存在重复
            if (board[(row/3)*3 + i/3][(col/3)*3 + i%3] == ch) {
                return false;
            }

        }
        return true;
    }
}
