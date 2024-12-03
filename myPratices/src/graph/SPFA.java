package graph;

import java.sql.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class SPFA {

    /**
     * SPFA 求单源最短路径，是对贝尔曼-福特算法的优化
     * 使用一个队列 保存要计算的点，出队之后再计算这个点的邻居节点，然后在更新dis表中数据,如果邻居节点不在队列中，
     * 则把邻居节点加入到队列中,直到队列为空为止
     *
     * spfa算法思想：spfa就是BellmanFord的一种实现方式，其具体不同在于，对于处理松弛操作时，采用了队列（先进先出方式）操作，从而大大降低了时间复杂度
     * @param n
     * @param edges
     * @return
     */
    private int spfa(int n,int [][] edges,int from,int end){

        //创建队列
        Queue<Integer> queue=new ArrayDeque<>();
        //判断是否在队列中
        boolean [] inQueue=new boolean[n];
        int[] num = new int[n];//表示每个顶点入队列的次数
        int [] dis=new int[n];
        Arrays.fill(dis,Integer.MAX_VALUE);
        dis[from]=0;
        num[from]=1;//表示from节点入队列一次
        queue.offer(from);
        //继续
        while (!queue.isEmpty()){
            int u= queue.poll();

            //求num的邻居节点
            for (int[] edge:edges){
                int a=edge[0];
                int b=edge[1];
                int weight=edge[2];
                if (a==u){
                    if (dis[b]>dis[a]+weight){
                        dis[b]=dis[a]+weight;
                        //把他的邻居节点加入到队列中
                        if (!inQueue[b]){
                            queue.offer(b);
                            inQueue[b]=true;

                            num[b]++;//增加入队列次数

                            if (num[b] > n){
                                return 0;//表示存在负权重表
                            }
                        }
                    }
                }
            }

            inQueue[u]=false;//出队列
        }

        return dis[end];
    }
}
