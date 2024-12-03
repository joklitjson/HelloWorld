package graph;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 贝尔曼-福特算法：可以求付权重的边，进行n-1次松弛，每次松弛就是遍历所有的边，然后判断当前边是否是从起点到当前边目的地的最小值，然后在更新
 * 松弛完成之后 则再次进行判断是否有付权重的边，就是 dis[edge[b]]>dis[edge[a]]+ edge.weight（其实就是说 [0,b]>[0,a]+[a,b].因此说明a->b是一个负权重边）
 * https://www.cnblogs.com/liuzhen1995/p/6533431.html
 */
public class BellmanFord {

    class Edge {
        int a;//起点
        int b;//终点
        int weight;//权重
    }

    public long[] getShort(Edge [] edges,int from ,int to){

        int n=11;
        long dis[]=new long[n];//保存距离的
        Arrays.fill(dis,Integer.MAX_VALUE);
        dis[from]=0;//表示从起点到起点的距离是0
        for (int i=0;i<n-1;i++){//进行n-1次松弛
            for (int j=0;j<edges.length;j++){//遍历所有的边
                if (dis[edges[j].b]>dis[edges[i].a]+edges[i].weight){
                    dis[edges[j].b]=dis[edges[i].a]+edges[i].weight;//更新距离
                }
            }
        }

        //再次进行一点判断是否有环
//        我们要知道带有负环的图是没有最短路径的，所以我们在执行算法的时候，要判断图是否带有负环
        for (int i=0;i< edges.length;i++){
            int a=edges[i].a;
            int b=edges[i].b;
            int weight=edges[i].weight;
            if (dis[b]>dis[a]+weight){
                //说明还存在负的权重边
                System.out.println("存在付权重边");
            }
        }

        return dis;
    }

    /**
     *  1514. 概率最大的路径
     *  使用贝尔曼-福特算法
     * @param n
     * @param edges
     * @param succProb
     * @param start_node
     * @param end_node
     * @return
     */
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {

        double result[] = new double[n];
        result[start_node] = 1;//
        for (int i = 0; i < n ; i++) {
            boolean update=false;
            for (int j = 0; j < edges.length; j++) {
                int a = edges[j][0];
                int b = edges[j][1];
                double weight = succProb[j];
                if (result[b] <result[a] * weight) {
                    result[b] = result[a] * weight;
                    update=true;
                }

                //因为是无向边 因此再计算下从b-a的距离
                if (result[a] < result[b] * weight) {
                    result[a] = result[b] * weight;
                    update=true;
                }
            }
            if (!update){
                break;
            }
        }
        return result[end_node];
    }


    /**
     * 787. K 站中转内最便宜的航班
     * 并且限制 边的条数
     *
     * 「限制最多经过不超过 k 个点」等价于「限制最多不超过 k+1 条边」，而解决「有边数限制的最短路问题」是 SPFA 所不能取代 Bellman Ford 算法的经典应用之一（SPFA 能做，但不能直接做）
     *
     * 作者：宫水三叶
     * 链接：https://leetcode.cn/problems/cheapest-flights-within-k-stops/solutions/955290/gong-shui-san-xie-xiang-jie-bellman-ford-dc94/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param n
     * @param flights
     * @param src
     * @param dst
     * @param k
     * @return
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int N = 110, INF = 0x3f3f3f3f;

        int[] dis = new int[n];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[src] = 0;//起点到起点的距离是0
        for (int i = 0; i < k+1; i++) {//进行k次松弛
            int[] clone = dis.clone();
            for (int[] edge : flights) {
                int a = edge[0];
                int b = edge[1];
                int weight = edge[2];
                //判断距离是否更短
                if (dis[b] > clone[a] + weight) {
                    dis[b] = clone[a] + weight;
                }
                dis[b]=Math.min(dis[b],clone[a]+weight);
            }
        }
        return dis[dst] > INF / 2 ? -1 : dis[dst];

    }

    public static void main(String[] args) {
        System.out.println(0x3f3f3f3f);
        System.out.println(Integer.MAX_VALUE/2);
    }


}
