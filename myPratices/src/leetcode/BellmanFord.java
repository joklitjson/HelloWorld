package leetcode;

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

    public int getShort(Edge [] edges,int from ,int to){

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
        for (int i=0;i< edges.length;i++){
            int a=edges[i].a;
            int b=edges[i].b;
            int weight=edges[i].weight;
            if (dis[b]>dis[a]+weight){
                //说明还存在负的权重边
            }
        }
    }
}
