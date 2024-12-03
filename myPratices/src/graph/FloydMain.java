package graph;

/**
 * 是计算多源路径 最短路径算法
 */
public class FloydMain {
    //保证数据比最大二倍大(两相加不能比它大)，并且不能溢出，不要Int最大 相加为负会出错,也可以使用Long替代
    int INF = 0x3f3f3f3f;
    private int [][] floyd(int n,int[][] edges){
        int [][] dis=new int[n][n];

        //1、
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                dis[i][j]=i==j?0:INF;//初始化值
            }
        }
        //2、使用元数据进行填充
        for (int[] edge:edges){
            int a=edge[0];
            int b=edge[1];
            int weight=edge[2];
            dis[a][b]=weight;//因为是无向的，所以有两个
            dis[b][a]=weight;
        }

        for (int k=0;k<n;k++){
            for (int i=0;i<n;i++){
                for (int j=0;j<n;j++){
                    //更新距离
                    if (dis[i][k]+dis[k][j]<dis[i][j]){
                        dis[i][j]=dis[i][k]+dis[k][j];
                    }
                }
            }
        }
        return dis;
    }

    /**
     * 1334. 阈值距离内邻居最少的城市
     * 返回在路径距离限制为 distanceThreshold 以内可到达城市最少的城市。如果有多个这样的城市，则返回编号最大的城市。
     * @param n
     * @param edges
     * @param distanceThreshold
     * @return
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dis = floyd(n, edges);

        //寻找最长的点位
        int[] cnt = new int[n];
        int ans = -1,cityCount=n+10;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dis[i][j] <= distanceThreshold) {
                    cnt[i]++;//表示这个点到达另一个点的最大距离小于distanceThreshold 的点位又多了一个，因此可以进行+1操作，然后在获取cnt 中的最大值
                }
            }
            //当前点到达的城市数量小于之前的，因此使用当前的点位覆盖
           if (cnt[i]<=cityCount){
               ans=i;
               cityCount=cnt[i];//更新最小值
           }
        }
        return ans;
    }
}
