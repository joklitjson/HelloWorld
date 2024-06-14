package datastrucs.minispantree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    //int[] dijkstra(int start, int end, List<int[]>[] graph) {
    int[] dijkstra(int start,  List<int[]>[] graph) {
        int n=graph.length;//顶点数量
        int[] distance=new int[n];//节点start到各个顶点的距离
        Arrays.fill(distance,Integer.MAX_VALUE);// 填充最大值

        PriorityQueue<State> priorityQueue=new PriorityQueue<State>((a,b)->a.toCurrentDistance-b.toCurrentDistance);

        //初始化起点的距离是0
        distance[start]=0;
        priorityQueue.add(new State(start,0));

        while (!priorityQueue.isEmpty()){
            State current= priorityQueue.poll();
            int toDistance=current.toCurrentDistance;

            //计算到当前节点的距离
//            if (current.nodeId==end){
//                return
//            }
            //当前距离大于  已经计算出的距离
            if (toDistance>distance[current.nodeId]){
                continue;
            }
            //计算他的邻居节点的距离
            for (int[] neig: graph[current.nodeId]){
                int nextNodeId=neig[0];
                int nextWeight=neig[1];
                //a->b->c
                //此时需要计算a节点到c节点的最短距离：如果a>b+(b>c) 的距离小于distance里的距离，则更新距离表
                if (toDistance+nextWeight<distance[nextNodeId]){
                    distance[nextNodeId]=toDistance+nextWeight;
                    //向队列中插入一条新的节点路径
                    priorityQueue.offer(new State(nextNodeId, distance[nextNodeId]));
                }
            }
        }

        return distance;
    }

    int[] dijkstra2(int start,  List<int[]>[] graph) {
        int n=graph.length;//顶点数量
        int[] distance=new int[n];//节点start到各个顶点的距离
        Arrays.fill(distance,Integer.MAX_VALUE);// 填充最大值

        PriorityQueue<State> priorityQueue=new PriorityQueue<State>((a,b)->a.toCurrentDistance-b.toCurrentDistance);

        //初始化起点的距离是0
        distance[start]=0;
        priorityQueue.add(new State(start,0));

        while (!priorityQueue.isEmpty()){
            State current= priorityQueue.poll();
            int toDistance=current.toCurrentDistance;
            //当前距离大于  已经计算出的距离
            if (toDistance>distance[current.nodeId]){
                continue;
            }
            //计算他的邻居节点的距离
            for (int[] neig: graph[current.nodeId]){
                int nextNodeId=neig[0];
                int nextHeight=Math.abs(neig[1]-toDistance);
                if (nextHeight<distance[nextNodeId]){
                    distance[nextNodeId]=nextHeight;
                    //向队列中插入一条新的节点路径
                    priorityQueue.offer(new State(nextNodeId, distance[nextNodeId]));
                }
            }
        }

        return distance;
    }



    // times 记录边和权重，n 为节点个数（从 1 开始），k 为起点
// 计算从 k 发出的信号至少需要多久传遍整幅图
    int networkDelayTime(int[][] times, int n, int k){
        //1构造邻接表

        //注意编号是从1开始的，因此要多一个元素
        List<int[]> [] graph=new LinkedList[n+1];

        for (int i=1;i<=n;i++){
            graph[i]=new LinkedList<>();
        }

        for (int [] edge:times){
            int from=edge[0];
            int to=edge[1];
            int weight=edge[2];
            //加入进来
            graph[from].add(new int[]{to,weight});
        }

        int[] delays= dijkstra(k,graph);
        int result=0;
        for (int i=1;i<delays.length;i++){
            //有节点没有被传播到
            if (delays[i]==Integer.MAX_VALUE){
                return -1;
            }
            result=Math.max(result,delays[i]);
        }

        return result;
    }

    // Dijkstra 算法，计算 (0, 0) 到 (m - 1, n - 1) 的最小体力消耗
    //不同点在于，需要计算这一条路径上的绝对值的差值，不需要累加和传统的计算最短路径有些区别
    public int  minimumEffortPath(int[][] heights) {
        // 先把所有的节点转换成一维数组中的点
        int m = heights.length, n = heights[0].length;
        int len = m * n;

        //构造邻接表
        List<int[]>[] graph = new LinkedList[len];

        for (int i = 0; i < len; i++) {
            graph[i] = new LinkedList<>();
        }

        int[][] direct = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        //初始化数据
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int node = i * n + j;
                //计算[i,j]的四个邻居
                for (int[] dir : direct) {
                    int nexi = dir[0] + i;
                    int nexj = dir[1] + j;
                    if (nexi < 0 || nexi >= m || nexj < 0 || nexj >= n) {
                        continue;
                    }
                    //计算权重
                    int nextNode = nexi * n + nexj;
                    graph[node].add(new int[]{nextNode, heights[nexi][nexj]});
                }
            }
        }

        int distance[] = dijkstra2(0, graph);
        return distance[m * n - 1];

    }

    public static void main(String[] args) {
        int [][] arr=new int[][]{{1,2,2},{3,8,2},{5,3,5}};

        Dijkstra dijkstra=new Dijkstra();
        System.out.println(dijkstra.minimumEffortPath(arr));;

//        int [][] delays=new int[][] {{2,1,1},{2,3,1},{3,4,1}};
//
//        System.out.println("预计延迟时间--==="+dijkstra.networkDelayTime(delays,4,2));

    }
    static class State{

        public State(int nodeId,int toCurrentDistance){
            this.nodeId=nodeId;
            this.toCurrentDistance=toCurrentDistance;
        }
        //节点id
        public int nodeId;

        //start节点到当前节点的距离
        public  int toCurrentDistance;
    }
}
