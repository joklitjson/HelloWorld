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
    boolean isBipartite(int[][] graph){
        return  true;
    }

    private List<Integer> [] buildGraph(int[][] graph){
        int n=graph.length;
        List<Integer> [] graphs=new LinkedList[n];
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
