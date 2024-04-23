package datastrucs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 图的表示法以及两种遍历方式
 *
 */
public class GraphsTest {
    public static void main(String[] args) {
        Graph graph=new Graph(6);
        graph.adj[0].add(1);
        graph.adj[0].add(2);
        graph.adj[0].add(3);

        graph.adj[1].add(0);
        graph.adj[1].add(5);
        graph.adj[2].add(0);
        graph.adj[3].add(0);
        graph.adj[4].add(5);
        graph.adj[5].add(1);
        graph.adj[5].add(4);
        boolean[] visited=new boolean[6];
        dfs(graph,0,visited);
        bfs(graph,0,new boolean[6]);
    }

    /**
     * 深度优先遍历
     * @param graph
     * @param start
     * @param visited
     */
    private static void  dfs(Graph graph,int start, boolean[] visited) {
        System.out.println("当前访问节点:" + graph.getVertices()[start].data);
        visited[start] = true;//已经访问过了
        //遍历他的所到达的节点
        for (int index : graph.adj[start]) {
            if (visited[index]) {
                continue;
            }
            //继续深度遍历他的孩子
            dfs(graph, index, visited);
        }
    }

    /**
     * 图的广度优先遍历
     */
    private static void  bfs(Graph graph, int start, boolean[] visited){
        Queue<Integer> queue=new LinkedList<>();
        //第一个入队列
        queue.offer(start);

        while (!queue.isEmpty()){
            int font= queue.poll();
            if (visited[font]){
                continue;
            }
            System.out.println("遍历的当前节点是："+graph.vertices[font].data);
            visited[font]=true;
            //把他的邻居都加入队列
            for (int value:graph.adj[font]){
                if (!visited[value]){
                    queue.add(value);
                }
            }
        }
    }

    //邻接表表示法
    static class  Graph{

        private  int size;
        private Vertex [] vertices;
        public LinkedList<Integer>[] adj;//图的边 数组,类似于hashMap
        public Graph(int size){
            vertices=new Vertex[size];
            adj=new LinkedList[size];
            //初始化每一个数组
            for (int i=0;i<size;i++){
                vertices[i]=new Vertex(i);
                adj[i]=new LinkedList<>();
            }
        }

        public int getSize() {
            return size;
        }


        public Vertex[] getVertices() {
            return vertices;
        }

        public void setVertices(Vertex[] vertices) {
            this.vertices = vertices;
        }

        public List<Integer>[] getAdj() {
            return adj;
        }

    }
}
