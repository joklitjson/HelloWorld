package array;

import java.util.*;

public class ALl {
    public static void main(String[] args) {
//        System.out.println(searchMatrix(new int[][]{{5}},5));
        List<List<Integer>> triangle=new ArrayList<List<Integer>>();
//        [[2],[3,4],[6,5,7],[4,1,8,3]]
        List<Integer> dd=new ArrayList<>();
        dd.add(2);
        triangle.add(dd);
        dd=new ArrayList<>();
        dd.add(3);
        dd.add(4);
        triangle.add(dd);

        dd=new ArrayList<>();
        dd.add(6);
        dd.add(5);
        dd.add(7);

        triangle.add(dd);

        dd=new ArrayList<>();
        dd.add(4);
        dd.add(1);
        dd.add(8);
        dd.add(3);

        triangle.add(dd);
//        triangle.add(Arrays.asList(4,1,8,3).iterator());

        System.out.println(minimumTotal(triangle));

        int [] result=  spiralArray(new int[][]{{2,3}});
//        int [] result=  spiralArray(new int[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}});
        System.out.println(Arrays.toString(result));
    }

    /**
     * Z字型搜索，从右上角开始搜索，比目标值小则向右下角搜搜，比目标值大则向左上角搜索
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix2(int[][] matrix, int target) {

        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length, n = matrix[0].length;

        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
            return false;
        }

        int i = 0, j = n - 1;//起点在右上角
        while (i < m && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                j--;
            } else {
                i++;
            }
        }
        return false;
    }


    public static boolean searchMatrix(int[][] matrix, int target) {

        if (matrix.length == 0||matrix[0].length ==0) {
            return false;
        }

        int m = matrix.length, n = matrix[0].length;

        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
            return false;
        }

        for (int i = 0; i < m; i++) {
            //在这一行内进行二分查找
            if (target >= matrix[i][0] && target <= matrix[i][n - 1]) {
                int arr[] = matrix[i];
                int a = 0, b = n - 1;
                //进行二分查找
                while (a <= b) {
                    int middle = (a + b) / 2;
                    if (arr[middle] == target) {
                        return true;
                    } else if (arr[middle] > target) {
                        b = middle - 1;
                    } else {
                        a = middle + 1;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 动态规划算法:定义dp[i][j] 表示的是从顶部到第i,j节点的最小路径，则最小路径路径只跟他的上一层的对应节点和上一层的左边节点的值有关
     * dp[i][j]=min(dp[i-1][j-1],dp[i-1][j])+grid[i][j]:
     * 还可以优化成一维数组
     * @param triangle
     * @return
     */
    public static int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        if (triangle.size() == 1) {
            return triangle.get(0).get(0);
        }

        int m = triangle.size();
        int dp[][] = new int[m][m];
        //初始化第一个
        dp[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < m; i++) {
            //特殊处理每一行的 第一个元素和最后一个元素
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);

            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + triangle.get(i).get(j);
            }
            //最后一个元素
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }

        //然后再遍历最后一行
        int min = dp[m - 1][0];//
        for (int i = 1; i < m; i++) {
            min = Math.min(min, dp[m - 1][i]);
        }

        return min;
    }

    public static int minimumTotal2(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        if (triangle.size() == 1) {
            return triangle.get(0).get(0);
        }

        int m = triangle.size();
        int dp[] = new int[m];
        //初始化第一个
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < m; i++) {
            //优先处理最后一个元素
            dp[i] = dp[i-1] + triangle.get(i).get(i);

            for (int j = i-1; i>=1; j--) {//排除最后一个元素0
                //倒序遍历不会覆盖上一层的前面部分元素
                dp[j] = Math.min(dp[j], dp[j - 1]) + triangle.get(i).get(j);
            }
            //处理第一个元素
            dp[0] = dp[0] + triangle.get(i).get(0);
        }

        //然后再遍历最后一行
        int min = dp[0];//
        for (int i = 1; i < m; i++) {
            min = Math.min(min, dp[i]);
        }

        return min;
    }


    Deque<Integer> integerDeque=new ArrayDeque<>();
    int size=0,sum=0;

//    public MovingAverage(int size) {
//        this.size=size;
//    }

    /**
     * 数据流中的平均数
     * @param val
     * @return
     */
    public double next(int val) {
        if (integerDeque.size()>=size){
            sum-=integerDeque.pollFirst();
        }
        integerDeque.offerLast(val);
        sum+=val;
        return sum*1.0/integerDeque.size();
    }

    /**
     * 螺旋输出
     * @param array
     * @return
     */
    public  static int[] spiralArray(int[][] array) {
        if (array.length==0){
            return new int[0];
        }
        int [] result=new int[array.length*array[0].length];
        for (int [] add:array){
            System.out.println(Arrays.toString(add));
        }
        int top=0,right=array[0].length-1,bottom=array.length-1,left=0;
        int idx=0;
        while (top<bottom){
            //遍历上面的边
            for (int i=top;i<right;i++){
                result[idx++]=array[top][i];
            }
            if (++top>bottom){
                break;
            }
            //右侧边向下遍历
            for (int i=top;i<bottom;i++){
                result[idx++]=(array[i][right-1]);
            }
            if (--right<left){
                break;
            }
            //底部从右向左遍历
            for (int i=right-1;i>=left;i--){
                result[idx++]=(array[bottom-1][i]);
            }
            if (--bottom<top){
                break;
            }

            for (int i=bottom-1;i>=top;i--){
                result[idx++]=(array[i][left]);
            }
            if (++left>right){
                break;
            }
        }
        return result;
    }
}
