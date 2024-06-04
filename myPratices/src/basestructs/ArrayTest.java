package basestructs;

public class ArrayTest {

    public static void main(String[] args) {
        int [][] array=new int[3][3];
        int row=array.length;//数组的行数
        int columns=array[0].length;//数组的列

        //1\遍历，[首列、尾列]
        for (int i=0;i<row;i++){
            int first=array[i][0];
            int last=array[i][columns-1];
        }

        //2、遍历首行和尾行的 的元素
        for (int j=0;j<columns;j++){
            int firtRowCell=array[0][j];
            int lastRowCell=array[row-1][j];
        }

        //二位数组转一维数组的方式 i*n+j
        int sum=1;
        //3、正向行遍历
        for (int i=0;i<row;i++){
            for (int j=0;j<columns;j++){
                array[i][j]= sum++;
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
        sum=0;
        //4 正向行遍历
//        for (int i=0;i<columns;i++){
//            for (int j=0;j<row;j++){
//                array[j][i]= sum++;
//            }
//        }

        //5螺旋遍历:定义4条边的遍历
        int top=0,bottom=row,left=0,right=columns;
        while (true){
            //遍历上面的边
            for (int i=top;i<right;i++){
                System.out.print(array[top][i]+" ");
            }
            if (++top>bottom){
                break;
            }
            //右侧边向下遍历
            for (int i=top;i<bottom;i++){
                System.out.print(array[i][right-1]+" ");
            }
            if (--right<left){
                break;
            }
            //底部从右向左遍历
            for (int i=right-1;i>=left;i--){
                System.out.print(array[bottom-1][i]+" ");
            }
            if (--bottom<top){
                break;
            }

            for (int i=bottom-1;i>=top;i--){
                System.out.print(array[i][left]+" ");
            }
            if (++left>right){
                break;
            }
        }
    }
}
