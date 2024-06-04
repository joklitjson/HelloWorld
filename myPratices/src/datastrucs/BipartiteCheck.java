package datastrucs;

import java.util.LinkedList;
import java.util.List;

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

    private List<Integer> [] buildGraph(int[][] graph){
        int n=graph.length;
        List<Integer> [] graphs=new LinkedList[n];

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
}
