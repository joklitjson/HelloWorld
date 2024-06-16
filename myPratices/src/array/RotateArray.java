package array;

import java.util.Arrays;

public class RotateArray {

    /**
     * 顺时针 旋转90二维数组
     * 1、先按坐上对角线调换，然后每行在进行调换
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int m = matrix.length;

        for (int i = 0; i < m; i++) {
            //对调一半的元素
            for (int j = i; j < m; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;

            }
        }

        //每行左右对调
        for (int[] arr : matrix) {
            int left = 0, right = m - 1;
            while (left < right) {
                int tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
                left++;
                right--;
            }
        }
    }

    //逆时针旋转90度
    public void rotate2(int[][] matrix) {
        int m=matrix.length;
        for (int i=0;i<m;i++){
            for (int j=0;j<m-i;j++){
                //
                int tmp = matrix[i][j];
                matrix[i][j] =  matrix[m-i-1][m-j-1] ;
                matrix[m-i-1][m-j-1]  = tmp;
            }
        }

        //每行左右对调
        for (int[] arr : matrix) {
            int left = 0, right = m - 1;
            while (left < right) {
                int tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
                left++;
                right--;
            }
        }
    }


    /**
     * 原地反转所有单词的顺序。
     *
     * 比如说，给你输入这样一个字符串：
     *
     * s = "hello world labuladong"
     * 你的算法需要原地反转这个字符串中的单词顺序：
     *
     * s = "labuladong world hello"
     */
    public static String  rotateStr(String str){
        int left=0,right=str.length()-1;
        char [] arr= str.toCharArray();
        while (left<right){
            char tmp=arr[left];
            arr[left]=arr[right];
            arr[right]=tmp;
            left++;
            right--;
        }
        left=0;
        for (int i=0;i<arr.length;i++){
            if (arr[i]==' '||i== arr.length-1){
                //对调左右两个字符
                int r=i-1;
                if (i== arr.length-1){
                    r=i;
                }
                while (left<r){
                    char tmp=arr[left];
                    arr[left]=arr[r];
                    arr[r]=tmp;
                    left++;
                    r--;
                }
                left=i+1;
            }
        }
        return new String(arr);
    }
    public static void main(String[] args) {
        RotateArray rotateArray=new RotateArray();

        int [][] arr=new int[][]{{1,2,3},{4,5,6},{7,8,9}};
//        rotateArray.rotate(new int[][]{{1,2,3},{4,5,6},{7,8,9}});

//        rotateArray.rotate2(arr);
//
//        for (int [] t:arr){
//            System.out.println(Arrays.toString(t));
//        }

        System.out.println(rotateStr("hello world labuladong"));
        System.out.println(rotateStr("the sky is blue"));


    }
}
