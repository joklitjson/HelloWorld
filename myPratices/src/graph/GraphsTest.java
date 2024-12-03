package graph;

import java.util.*;

/**
 * 图的表示法以及两种遍历方式
 *
 */
public class GraphsTest {
    public static void main(String[] args) {
//        Graph graph=new Graph(6);
//        graph.adj[0].add(new Edge(1,1));
//        graph.adj[0].add(new Edge(2,1));
//        graph.adj[0].add(new Edge(3,1));
//
//        graph.adj[1].add(new Edge(0,1));
//        graph.adj[1].add(new Edge(5,1));
//        graph.adj[2].add(new Edge(0,1));
//        graph.adj[3].add(new Edge(0,1));
//        graph.adj[4].add(new Edge(5,1));
//        graph.adj[5].add(new Edge(1,1));
//        graph.adj[5].add(new Edge(4,1));
//        boolean[] visited=new boolean[6];
//        dfs(graph,0,visited);
//        bfs(graph,0,new boolean[6]);

//        Graph graph = new Graph(7);
//        initGraph(graph);
//        int[] distances = dijkstra(graph, 0);
//        System.out.println(distances[6]);
//        System.out.println("输出完整路径：");
//        int[] prevs = dijkstraV2(graph, 0);
//        printPrevs(graph.vertices, prevs, graph.vertices.length- 1);

        int[][] prerequisites = new int[2][2];
        prerequisites[0][0] = 0;
        prerequisites[0][1] = 1;

//        prerequisites[1][0]=0;
//        prerequisites[1][1]=1;

        System.out.println(new GraphsTest().canFinishBfs(2, prerequisites));
    }

    /**
     * 深度优先遍历
     *
     * @param graph
     * @param start
     * @param visited
     */
    private static void dfs(Graph graph, int start, boolean[] visited) {
        System.out.println("当前访问节点:" + graph.getVertices()[start].data);
        visited[start] = true;//已经访问过了
        //遍历他的所到达的节点
        for (Edge index : graph.adj[start]) {
            if (visited[index.index]) {
                continue;
            }
            //继续深度遍历他的孩子
            dfs(graph, index.index, visited);
        }
    }


    /**
     * 求最短路径算法
     * 时间复杂度 n的平方(因为有两次循环)
     * 优化点：可以把寻找最短距离的订单 使用小根堆处理
     *
     * @param graph
     * @param startIndex
     * @return
     */
    public static int[] dijkstra(Graph graph, int startIndex) {

        //创建距离表
        int[] distances = new int[graph.vertices.length];
        //是否访问过
        boolean[] accessed = new boolean[graph.vertices.length];
        //1、初始化未最大值
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        // 2、求起点到其他节点的距离，然后更新到距离表中
        for (Edge edge : graph.adj[startIndex]) {
            distances[edge.index] = edge.weight;
        }
        accessed[startIndex] = true;

        //3、重复遍历其他最短距离顶点，刷新最短距离
        for (int k = 0; k < graph.vertices.length; k++) {
            //4、找到distance中的最短距离，然后再把他作为起点，更新他所到达的顶点的距离
            int minDistanceIndex = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < distances.length; i++) {
                if (!accessed[i] && distances[i] < minDistance) {
                    minDistance = distances[i];
                    minDistanceIndex = i;
                }
            }
            if (minDistanceIndex == -1) {
                continue;
            }
            // 在这里加一个判断就行了，其他代码不用改
//            if (minDistanceIndex == end) {
//                return curDistFromStart;
//            }
            accessed[minDistanceIndex] = true;
            //5、计算
            for (Edge edge : graph.getAdj()[minDistanceIndex]) {
                if (accessed[edge.index]) {
                    continue;
                }
                int preDistance = distances[edge.index];
                //新的距离 小于 之前的距离
                if ((minDistance + edge.weight) < preDistance) {
                    distances[edge.index] = minDistance + edge.weight;
                }
            }
        }
        return distances;
    }

    public static int[] dijkstraV2(Graph graph, int startIndex) {
        //创建距离表
        int[] distances = new int[graph.vertices.length];
        //是否访问过
        boolean[] accessed = new boolean[graph.vertices.length];
        //创建前置定点表，存储从起点到每一个顶点的已知最短路径的前置节点
        int[] prevs = new int[distances.length];

        //1、初始化未最大值
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        // 2、求起点到其他节点的距离，然后更新到距离表中
        for (Edge edge : graph.adj[startIndex]) {
            distances[edge.index] = edge.weight;
            prevs[edge.index] = startIndex;
        }
        accessed[startIndex] = true;

        //3、重复遍历其他最短距离顶点，刷新最短距离
        for (int k = 0; k < graph.vertices.length; k++) {
            //4、找到distance中的最短距离，然后再把他作为起点，更新他所到达的顶点的距离
            int minDistanceIndex = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < distances.length; i++) {
                if (!accessed[i] && distances[i] < minDistance) {
                    minDistance = distances[i];
                    minDistanceIndex = i;
                }
            }
            if (minDistanceIndex == -1) {
                continue;
            }
            accessed[minDistance] = true;
            //5、计算
            for (Edge edge : graph.getAdj()[minDistanceIndex]) {
                if (accessed[edge.index]) {
                    continue;
                }
                int preDistance = distances[edge.index];
                //新的距离 小于 之前的距离
                if ((minDistance + edge.weight) < preDistance) {
                    distances[edge.index] = minDistance + edge.weight;
                    prevs[edge.index] = minDistanceIndex;//更新前置顶点
                }
            }
        }
        return prevs;
    }

    private static void initGraph(Graph graph) {
        graph.vertices[0] = new Vertex("A");
        graph.vertices[1] = new Vertex("B");
        graph.vertices[2] = new Vertex("C");
        graph.vertices[3] = new Vertex("D");
        graph.vertices[4] = new Vertex("E");
        graph.vertices[5] = new Vertex("F");
        graph.vertices[6] = new Vertex("G");
        graph.adj[0].add(new Edge(1, 5));
        graph.adj[0].add(new Edge(2, 2));
        graph.adj[1].add(new Edge(0, 5));
        graph.adj[1].add(new Edge(3, 1));
        graph.adj[1].add(new Edge(4, 6));
        graph.adj[2].add(new Edge(0, 2));
        graph.adj[2].add(new Edge(3, 6));
        graph.adj[2].add(new Edge(5, 8));
        graph.adj[3].add(new Edge(1, 1));
        graph.adj[3].add(new Edge(2, 6));
        graph.adj[3].add(new Edge(4, 1));
        graph.adj[3].add(new Edge(5, 2));
        graph.adj[4].add(new Edge(1, 6));
        graph.adj[4].add(new Edge(3, 1));
        graph.adj[4].add(new Edge(6, 7));
        graph.adj[5].add(new Edge(2, 8));
        graph.adj[5].add(new Edge(3, 2));
        graph.adj[5].add(new Edge(6, 3));
        graph.adj[6].add(new Edge(4, 7));
        graph.adj[6].add(new Edge(5, 3));
    }

    /**
     * 输出路径
     *
     * @param vertexes
     * @param prev
     * @param i
     */
    private static void printPrevs(Vertex[] vertexes, int[] prev, int i) {
        if (i > 0) {
            printPrevs(vertexes, prev, prev[i]);
        }

        System.out.println(vertexes[i].data);
    }

    /**
     * 图的广度优先遍历
     */
    private static void bfs(Graph graph, int start, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        //第一个入队列
        queue.offer(start);

        while (!queue.isEmpty()) {
            int font = queue.poll();
            if (visited[font]) {
                continue;
            }
            System.out.println("遍历的当前节点是：" + graph.vertices[font].data);
            visited[font] = true;
            //把他的邻居都加入队列
            for (Edge value : graph.adj[font]) {
                if (!visited[value.index]) {
                    queue.add(value.index);
                }
            }
        }
    }

    //邻接表表示法
    static class Graph {

        // 邻接表
        // graph[x] 存储 x 的所有邻居节点
        public List<Integer>[] graph;

        private Vertex[] vertices;
        public LinkedList<Edge>[] adj;//图的边 数组,类似于hashMap

        public Graph(int size) {
            vertices = new Vertex[size];
            adj = new LinkedList[size];
            //初始化每一个数组
            for (int i = 0; i < size; i++) {
                vertices[i] = new Vertex(i);
                adj[i] = new LinkedList<>();
            }
        }

        public Vertex[] getVertices() {
            return vertices;
        }

        public void setVertices(Vertex[] vertices) {
            this.vertices = vertices;
        }

        public List<Edge>[] getAdj() {
            return adj;
        }

    }

    //图的边
    private static class Edge {
        int index;
        int weight;

        Edge(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    List<List<Integer>> result = null;
    boolean visited[] = null;

    //一幅有向无环图，生成图的所有路径,由于是没有环的，因此不需要设置visited数组
    List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        result = new ArrayList<>();
        int n = graph.length;
        boolean visited[] = new boolean[n];
        traverse(graph, 0, new LinkedList<>());
        return result;
    }

    private void traverse(int[][] graph, int s, LinkedList<Integer> path) {
        int n = graph.length;
        path.add(s);
        if (s == n - 1) {
            result.add(new ArrayList<>(path));
            path.removeLast();
            return;
        }
        //遍历他的邻居
        for (int neight : graph[s]) {
            traverse(graph, neight, path);
        }
        //删除最后一个
        path.removeLast();
    }


    boolean hasCycle = false;
//    boolean[] visited=null;

    /**
     * 选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，
     * 表示如果要学习课程 ai 则 必须 先学习课程  bi 。
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = buildNeights(numCourses, prerequisites);
        visited = new boolean[numCourses + 1];
        boolean[] onPath = new boolean[numCourses + 1];
        //因为可能是一个无环图，因此需要循环没一个节点
        for (int i = 0; i < numCourses; i++) {
            if (!hasCycle) {
                traverse(graph, onPath, i);
            }
        }
        return !hasCycle;
    }

    /**
     * 使用BFS计算是否有环：使用环的入度和出度概念
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinishBfs(int numCourses, int[][] prerequisites) {
        List<Integer>[] grapths = buildNeights(numCourses, prerequisites);
        int[] indegree = new int[numCourses];

        //计算元素的入度
        for (int [] row: prerequisites) {
            int from=row[1];
            int to=row[0];
            if (from!=to){
                indegree[to] ++;
            }
        }
        Queue<Integer> queue = new LinkedList();

        for (int i = 0; i < numCourses; i++) {
            //入度==0 表示他没有依赖项了
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int count = 0;//入度为0的元素
        while (!queue.isEmpty()) {
            int element = queue.poll();
            count++;
            for (int next : grapths[element]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        // 如果所有节点都被遍历过，说明不成环
        return count == numCourses;
    }

        private void traverse(List<Integer>[] graph, boolean[] onPath, int e) {

        //在当前的路径中再次遍历到了当前元素
        if (onPath[e]) {
            hasCycle = true;
            return;
        }

        //已经被遍历或则已经发现了环
        if (visited[e] || hasCycle) {
            return;
        }
        visited[e] = true;
        onPath[e] = true;//当前遍历的路径
        for (Integer element : graph[e]) {
            traverse(graph, onPath, element);
        }
        onPath[e] = false;
    }

    /**
     * 构建邻接表
     *
     * @param prerequisites
     * @return
     */
    private List<Integer>[] buildNeights(int n, int[][] prerequisites) {
        List<Integer>[] result = new LinkedList[n];
        //初始化数据
        for (int i = 0; i < n; i++) {
            result[i] = new LinkedList<>();
        }
        for (int row[] : prerequisites) {
            int from = row[1];
            int to = row[0];
            if (from != to) {
                //被依赖的课程作为起点
                result[from].add(to);
            }

        }
        return result;
    }


    List<Integer> postOrders = new ArrayList<>();

    /**
     * 返回 可能的拓扑排序结构
     * 步骤：使用边构建一个邻接图
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] grapths = buildNeights(numCourses, prerequisites);
        visited = new boolean[numCourses + 1];
        boolean onPath[]=new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            travse(grapths, i,onPath);
        }

        if (hasCycle) {
            return new int[0];
        }

        Collections.reverse(postOrders);
        int[] result = postOrders.stream().mapToInt(Integer::valueOf).toArray();

        return result;
    }

    private void travse( List<Integer>[] grapths, int element, boolean onPath[]) {
        if (onPath[element]) {
            hasCycle = true;
        }

        if (visited[element] || hasCycle) {
            return;
        }
        visited[element]=true;
        onPath[element]=true;

        for (int neight:grapths[element]){
            travse(grapths,neight,onPath);
        }

        //后续遍历的地方添加元素
        postOrders.add(element);

        onPath[element]=false;
    }

}
