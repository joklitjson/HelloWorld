package graph.minispantree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    //优先级队列 三元组 from to weight
    PriorityQueue<Integer[]> priorityQueue;

    boolean inSmt[];

    //邻接表表示的数字，graph[i] 表示节点i的所有的邻居节点以及到他们的距离
    //[from,to,weight]三元组
    List<Integer[]>[] graph;
    int weightSum = 0;

    public Prim(List<Integer[]>[] graph) {
        priorityQueue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        inSmt = new boolean[graph.length];
        this.graph = graph;

        //把第一个节点当做切点
        inSmt[0] = true;
        cute(0);

        while (!priorityQueue.isEmpty()) {
            Integer[] edges = priorityQueue.poll();
            int to = edges[1];
            if (inSmt[to]) {
                // 节点 to 已经在最小生成树中，跳过
                // 否则这条边会产生环
                continue;
            }
            weightSum += edges[2];

            inSmt[to] = true;
            // 节点 to 加入后，进行新一轮切分，会产生更多横切边
            cute(to);

        }

    }

    /**
     * 获取最小权重
     *
     * @return
     */
    public int getWeight() {
        return weightSum;
    }

    /**
     * 判断是否联通，看看是否所有的节点都已经被访问了
     *
     * @return
     */
    public boolean connected() {
        for (boolean flag : inSmt) {
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    private void cute(int index) {
        List<Integer[]> neights = graph[index];
        for (Integer[] neight : neights) {
            int to = neight[1];
            if (inSmt[to]) {
                //表示当前节点已经被切割过
                continue;
            }
            priorityQueue.offer(neight);
        }
    }

    /**
     * 1135
     * @param n
     * @param connections
     * @return
     */
    public int minimumCost(int n, int[][] connections) {

        //构建grapho

        List<Integer[]>[] graph = new List[n];

        //初始化所有的边 :三元组转换成邻接表
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : connections) {
            // 题目给的节点编号是从 1 开始的，
            // 但我们实现的 Prim 算法需要从 0 开始编号
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            int weight = edge[2];
            // 「无向图」其实就是「双向图」
            // 一条边表示为 int[]{from, to, weight}
            graph[u].add(new Integer[]{u, v, weight});
            graph[v].add(new Integer[]{v, u, weight});
        }

        Prim prim = new Prim(graph);

        return prim.connected() ? prim.getWeight() : -1;
    }

    int minCostConnectPoints(int[][] points){
        //先把节点转换成邻接表 表示法

        int n=points.length;
        //把节点数 当做索引
        List<Integer[]>[] graph=new LinkedList[n];
        for (int i=0;i<n;i++){
            graph[i]=new LinkedList<>();
        }
        for (int i=0;i<n;i++) {
            for (int j = i + 1; j < n; j++) {
                int xi = points[i][0], yi = points[i][1];
                int xj = points[j][0], yj = points[j][1];
                // 求距离
                int distance = Math.abs(xi - xj) + Math.abs(yi - yj);
                //把两个点的临界表中都添加节点
                graph[i].add(new Integer[]{i, j, distance});
                graph[j].add(new Integer[]{j, i, distance});
            }
        }

        Prim prim=new Prim(graph);

        return prim.connected()?prim.getWeight():-1;
    }
}
