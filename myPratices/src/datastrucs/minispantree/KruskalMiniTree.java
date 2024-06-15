package datastrucs.minispantree;

import datastrucs.UF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KruskalMiniTree {

    /**
     * 给你输入编号从0到n - 1的n个结点，和一个无向边列表edges（每条边用节点二元组表示），请你判断输入的这些边组成的结构是否是一棵树。
     *
     * @param n
     * @param edges
     * @return
     */
    boolean validTree(int n, int[][] edges) {

        UF uf = new UF(n);

        for (int[] row : edges) {
            int u = row[0];

            int v = row[1];

            //如果新添加的两条边 之前就联通，则能形成一个环，因此就不是一棵树
            if (uf.connect(u, v)) {
                return false;
            }
            uf.union(u, v);
        }
        //判断最后的联通分量是否是1，如果大于1则是一个深林
        return uf.getCount() == 1;
    }

    /**
     * 城市中的最低联通成本
     * 每座城市相当于图中的节点，连通城市的成本相当于边的权重，连通所有城市的最小成本即是最小生成树的权重之和。
     *
     * @param n
     * @param connections
     * @return
     */
    int minimumCost(int n, int[][] connections) {

        //升序排列
        Arrays.sort(connections, (a, b) -> a[2] - b[2]);
        // 记录最小生成树的权重之和
        int sum = 0;
        UF uf = new UF(n + 1);

        for (int[] edge : connections) {
            int u = edge[0];
            int v = edge[1];
            if (uf.connect(u, v)) {
                continue;
            }
            uf.union(u, v);

            //假设权重
            sum += edge[2];
        }

        //因为有节点0 未被联通，因此最后的联通分量是2
        return uf.getCount() == 2 ? sum : -1;
    }

    /**
     * 连接二位平面上点的最短距离
     * 1584
     * 1、先把点位之间的距离转为成边，然后在排序，在使用克里斯卡尔算法计算
     *
     * @param points
     * @return
     */
    int minCostConnectPoints(int[][] points) {


        int n = points.length;

        List<Integer[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges.add(new Integer[]{i, j, len});
            }
        }

        Collections.sort(edges, (a, b) ->{
            return  a[2] - b[2];
        });

        UF uf = new UF(n);
        int smt = 0;//权重之和

        for (Integer[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (uf.connect(u, v)) {
                continue;
            }
            uf.union(u, v);
            smt += edge[2];
        }

        return smt;
    }

    public static void main(String[] args) {

    }
}
