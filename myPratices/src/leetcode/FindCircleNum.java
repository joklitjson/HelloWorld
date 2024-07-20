package leetcode;

/**
 * 不同的省份，使用连通图进行联通
 */
public class FindCircleNum {

    public static void main(String[] args) {
     int count=  new   FindCircleNum().findCircleNum2(new int[][]{{1,0,0,1},{0,1,1,0},{0,1,1,1},{1,0,1,1}}
     );
        System.out.println(count);
    }

    //使用深度遍历，把联通的城市连接起来，这些城市则属于一个联通分量
    public int findCircleNum2(int[][] isConnected) {

        int n=isConnected.length;
        int ans=0;

        //代表城市i已经被访问
        boolean [] visited=new boolean[n];

        for (int i=0;i<n;i++){
            if (!visited[i]){
                dfs(visited,isConnected,i);
                ans++;
            }
        }
        return ans;
    }

    private void dfs(boolean [] visited,int[][] isConnected,int i) {
        for (int j = 0; j < visited.length; j++) {
            if (!visited[j] && isConnected[i][j] == 1) {
                visited[j] = true;
                //遍历第j个城市
                dfs(visited, isConnected, j);
            }
        }

    }
        public int findCircleNum(int[][] isConnected) {
        int n=isConnected.length;
        UnionFind unionFind=new UnionFind(n);

       for (int i=0;i<n;i++){
           for (int j=0;j<n;j++){
               if (isConnected[i][j]==1){
                   unionFind.union(i,j);
               }
           }
       }

       return unionFind.count;
    }

    class UnionFind{
        public int [] parent;

        //联通分量
        public int count;
        public UnionFind(int n){
            parent=new int[n];
            count=n;
            //指向自己
            for (int i=0;i<n;i++){
                parent[i]=i;
            }
        }

        public void union(int a,int b){

            int parentA=find(a);
            int parentB=find(b);

            if (parentA!=parentB){
                //把B指向A
                parent[parentB]=parentA;
                count--;
            }
        }

        public int find(int a) {
            //寻找a的父亲
            while (parent[a] != a) {
                //路径压缩
//                parent[parent[a]] = a;
                a = parent[a];
            }
            //寻找a的父亲
            return parent[a];
        }
    }
}
