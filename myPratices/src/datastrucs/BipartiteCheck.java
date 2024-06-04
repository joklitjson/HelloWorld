package datastrucs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BipartiteCheck {

    /**
     * 判断是否是二分图：
     * ，题目给你输入一个 邻接表 表示一幅无向图，请你判断这幅图是否是二分图。
     * @param graph
     * @return
     */
    boolean [] visited;
    boolean isOk=true;
    boolean isBipartite(int[][] graph){
        visited=new boolean[graph.length];
        boolean [] color=new boolean[graph.length];//染色
        for (int i=0;i<graph.length;i++){
            if (!isOk){
                return isOk;
            }
            traverse(graph,color,i);
        }
        return  isOk;
    }

    boolean isBipartite2(int[][] graph){
        visited=new boolean[graph.length];
        boolean [] color=new boolean[graph.length];//染色
        Queue<Integer> queue=new LinkedList<>();
        queue.add(0);
        visited[0]=true;

        for (int v=0;v<graph.length;v++){
            if (isOk){
                bfs(graph,color,v);
            }
        }
        return  isOk;
    }

    /**
     * 广度遍历
     * @param graph
     * @param color
     * @param element
     */
    void bfs(int[][] graph, boolean [] color,int element) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(element);
        visited[element] = true;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neight : graph[curr]) {
                if (visited[neight]) {
                    if (color[neight] == visited[curr]) {
                        isOk = false;
                        break;
                    }
                } else {
                    color[neight] = !visited[curr];
                    queue.offer(neight);
                    visited[neight] = true;
                }
            }
        }
    }
    void traverse(int[][] graph, boolean [] color,int element){
        if (!isOk){
            return;
        }
        if (visited[element]){
            return;
        }
        visited[element]=true;
        for (int neight:graph[element]){
            if (visited[neight]){
                //已经设置了颜色：判断他是否和element的颜色相同
                if (color[neight]==color[element]){
                    isOk=false;
                    return;
                }
                continue;
            }
            else {
                //未设置颜色，则测试不同的颜色
                color[neight]=!color[element];
                traverse(graph,color,neight);
            }
        }
    }

    /**
     * 构建无向图
     * @param n
     * @param graph
     * @return
     */
    private List<Integer> [] buildGraph(int n,int[][] graph){
        List<Integer> [] graphs=new LinkedList[n+1];

        //初始化邻接表
        for (int i=0;i<n;i++){
            graphs[i]=new LinkedList<>();
        }
        for (int[] edge:graph){
            //无向图 就是添加两个边
            graphs[edge[0]].add(edge[1]);
            graphs[edge[1]].add(edge[0]);
        }
        return graphs;
    }

    /**
     * 把互相讨厌的两个人 分成两个小组,实际就是二分图
     * @param n
     * @param dislikes
     * @return
     */
    boolean possibleBipartition(int n, int[][] dislikes) {
        visited = new boolean[n + 1];
        boolean[] color = new boolean[n + 1];

        List<Integer>[] list = buildGraph(n, dislikes);
        for (int i = 1; i <= n; i++) {
            travese(list, color, i);
        }
        return isOk;
    }

    private void  travese(List<Integer> [] graph,boolean [] color,int element){
        if (visited[element]){
            return;
        }
        visited[element]=true;
        for (int neight:graph[element]){
            if (visited[neight]){
                if (color[neight]==color[element]){
                    isOk=false;
                    break;
                }
            }
            else {
                color[neight]=!color[element];
                travese(graph,color,neight);
            }
        }
    }

}
