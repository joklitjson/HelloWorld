import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridDFSTest {

    public static void main(String[] args) {
        int[][] a1 = new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
        };
        int row = a1.length;
        int col = a1[0].length;

        boolean[][] visited = new boolean[row][col];
        List<Integer> list=new ArrayList<>();
        dfs(a1,0,0,visited,list);

    }

    private static void dfs(int[][] nums,int x,int y,boolean[][] visited, List<Integer> list){
        int row = nums.length;
        int col = nums[0].length;
        if(x>=row){
            System.out.println("当前路径"+ Arrays.toString(list.toArray()));
            return;
        }
        if(y>=col){
            System.out.println("当前路径"+ Arrays.toString(list.toArray()));
            return;
        }
        if(visited[x][y]){
            return;
        }
//        System.out.print(nums[x][y] +",");
        visited[x][y] = true;
        list.add(nums[x][y]);
        int size=list.size();
        dfs(nums,x+1,y,visited,list);


        dfs(nums,x,y+1,visited,list);

        list.remove(size-1);
        visited[x][y]=false;
    }
}