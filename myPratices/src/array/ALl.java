package array;

public class ALl {
    public static void main(String[] args) {
        System.out.println(searchMatrix(new int[][]{{5}},5));
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
}
