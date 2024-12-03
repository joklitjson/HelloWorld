package tree;

import graph.TreeNode;

import java.util.*;

/**
 * 二叉树的层序遍历
 * 也可以使用DFS实现层序遍历输出节点
 */
public class BFSTemplete {


    /**
     * 使用深度遍历 把一层的节点放在一个数组中
     * @param node
     * @param list
     * @param depth
     */
    private void dfsCengxuTravel(TreeNode node, List<List<Integer>> list, int depth){
        if (node==null){
            return;
        }
        if (depth>list.size()){
            list.add(new ArrayList<>());
        }
        //获取当前层次，然在加入数组
        //锯齿形遍历，层级是奇数从左到右，，层级偶数则从右向左
        if (depth%2==1){
            list.get(depth-1).add(node.val);
        }else {
            list.get(depth-1).add(1,node.val);
        }

        //遍历子节点
        dfsCengxuTravel(node.left,list,depth+1);
        dfsCengxuTravel(node.right,list,depth+1);
    }
    /**
     * BFS 算法 模板，其实就是从一个起点向一个目标搜索，一层一层 向外扩散，因此能找到最短路径
     * 比如A路寻星问题
     * @param start
     * @param target
     */
    private int bfs(TreeNode start,TreeNode target) {

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(start);
        //校验是否已经访问过了
        Set<TreeNode> visited = new HashSet<>();
        visited.add(start);//已经加入进来
        int step = 0;//定义需要的次数、或者最小深度
        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {
                // 把这些节点的相邻节点在一次加入到队列中,就是加入下一层节点
                TreeNode node = queue.poll();

                /* 划重点：这里判断是否到达终点 */
                if (node == target) {
                    return step;
                }
                //3加入他的子节点
                if (node.getLeft() != null && !visited.contains(node.getLeft())) {
                    queue.add(node.getLeft());
                    visited.add(node.getLeft());
                }

                if (node.getRight() != null && !visited.contains(node.getRight())) {
                    queue.add(node.getRight());
                    visited.add(node.getRight());
                }
            }
            step++;
        }

        return -1;
    }

    public static void main(String[] args) {

        List<List<String>> lists=new ArrayList<>();
        lists.add(Arrays.asList("a","b"));
        lists.add(Arrays.asList("b","c"));

        List<List<String>> query=new ArrayList<>();
        query.add(Arrays.asList("a","c"));

        calcEquation(lists,new double[]{2.0,3.0},query);
    }
    public int openLock(String[] deadends, String target){
        String start="0000";
        Queue<String> queue=new LinkedList<>();
        Set<String> visited=new HashSet<>();//是否已经访问
        Set<String> deadendsSet=new HashSet<>();
        for (String de:deadends){
            deadendsSet.add(de);
        }

        queue.add(start);
        visited.add(start);
        int step=0;
        while (!queue.isEmpty()){
            int  count=queue.size();
            for (int i=0;i<count;i++){
                String current= queue.poll();
                //判断是否等于目标
                if (target.equals(current)){
                    return step;
                }
                //如果包含了这个，那就跳过他的扩展
                if (deadendsSet.contains(current)){
                    continue;
                }
                //然后再四个位置上，向上或向下变换
                for (int j=0;j<4;j++){
                    String plusOn=plusOne(current,j);
                    if (!visited.contains(plusOn)){
                        queue.offer(plusOn);
                        visited.add(plusOn);
                    }
                    String subOn=subOne(current,j);
                    if (!visited.contains(subOn)){
                        queue.add(subOn);
                        visited.add(subOn);
                    }
                }
            }
            /* 在这里增加步数 */
            step++;
        }
//如果找不到，那就直接返回-1
        return -1;
    }

    private static String plusOne(String orign,int index){
        char[] chars= orign.toCharArray();
        if (chars[index]=='9'){
            chars[index]='0';
        }
        else {
            chars[index]+=1;
        }

        return new String(chars);
    }

    private static String subOne(String orign,int index){
        char[] chars= orign.toCharArray();
        if (chars[index]=='0'){
            chars[index]='9';
        }
        else {
            chars[index]-=1;
        }

        return new String(chars);
    }


    /**
     * LCR 111. 除法求值
     * 解决方案：先把点位进行映射，例如 a->1,b->2,
     * 2、在构造有向 加权图
     * 3、遍历问题：通过广度优先遍历 找到问题a->k 之间的权值
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {

        int pointIdx = 0, n = equations.size();
        Map<String, Integer> pointMap = new HashMap<>();
        for (List<String> edges : equations) {
            if (!pointMap.containsKey(edges.get(0))) {
                pointIdx++;
                pointMap.put(edges.get(0), pointIdx);
            }

            if (!pointMap.containsKey(edges.get(1))) {
                pointIdx++;
                pointMap.put(edges.get(1), pointIdx);
            }
        }

        //2构建 图
        List<Pair>[] edges = new List[pointIdx];

        for (int i = 0; i < edges.length; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            List<String> edge = equations.get(i);
            Integer pointA = pointMap.get(edge.get(0));
            Integer pointB = pointMap.get(edge.get(1));

            //构建有向带权图
            edges[pointA].add(new Pair(pointB, values[i]));
            edges[pointB].add(new Pair(pointA, 1.0 / values[i]));
        }

        //遍历问题:
        double[] ans = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            double result = -1.0;
            Integer pointA = pointMap.get(query.get(0));
            Integer pointB = pointMap.get(query.get(1));

            //节点不在图中
            if (pointA==null||pointB==null){
                ans[i] = result;
                continue;
            }
            if (pointA == pointB) {
                result = 1.0;
            } else {
                //使用广度优先遍历，遍历从pointA-->pointB
                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(pointA);
                double[] ratios = new double[pointIdx];
                Arrays.fill(ratios, -1.0);
                ratios[pointA] = 1.0;
                System.out.println(pointA+"===>"+pointB);
                while (!queue.isEmpty() && ratios[pointB] < 0) {
                   Integer  one=  queue.poll();
                    //遍历点的边界
                    for (Pair pair : edges[one]) {
                        int toPoint = pair.point;
                        //如果当前点没进行计算，则加入进来
                        if (ratios[toPoint] < 0) {
                            //通过a-->k,k-->b,建立 a--b之间的权重
                            ratios[toPoint] =ratios[one] * pair.value;
                            queue.offer(toPoint);
                        }
                    }
                }
                //获取结算的结果
                result = ratios[pointB];
            }
            ans[i] = result;

        }
        return ans;
    }


    /**
     * 使用flordly 算法，提前计算好 各个顶点的距离，然后在进行多次查询
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public static double[] calcEquation2(List<List<String>> equations, double[] values, List<List<String>> queries) {

        int pointIndex = 0;
        Map<String, Integer> idxMap = new HashMap<>();

        //映射
        for (List<String> edge : equations) {
            if (!idxMap.containsKey(edge.get(0))) {
                idxMap.put(edge.get(0), pointIndex++);
            }

            if (!idxMap.containsKey(edge.get(1))) {
                idxMap.put(edge.get(1), pointIndex++);
            }
        }

        double[][] graph = new double[pointIndex][pointIndex];

        //初始化值
        for (int i = 0; i < pointIndex; i++) {
            Arrays.fill(graph[i], -1);
        }

        for (int i = 0; i < equations.size(); i++) {
            Integer pointA = idxMap.get(equations.get(i).get(0));
            Integer pointB = idxMap.get(equations.get(i).get(1));
            graph[pointA][pointB] = values[i];
            graph[pointB][pointA] = 1.0 / values[i];
        }


        for (int k = 0; k < pointIndex; k++) {
            for (int i = 0; i < pointIndex; i++) {
                for (int j = 0; j < pointIndex; j++) {

                    if (graph[i][k] > 0 && graph[k][j] > 0) {
                        graph[i][j] = graph[i][k] * graph[k][j];
                    }
                }
            }
        }

        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            if (!idxMap.containsKey(queries.get(i).get(0)) || !idxMap.containsKey(queries.get(i).get(1))) {
                result[i] = -1;
                continue;
            }
            Integer pointA = idxMap.get(queries.get(i).get(0));
            Integer pointB = idxMap.get(queries.get(i).get(1));

            result[i] = graph[pointA][pointB];
        }

        return result;
    }


    /**
     * LCR 107. 01 矩阵
     * 计算1距离最近的0的距离
     * 方案：使用广度优先遍历算法，优先把0加入队列，然后在向四周搜索，发现新节点则更新答案，以及在把新节点加入到队列中
     * @param mat
     * @return
     */
    public int[][] updateMatrix(int[][] mat) {

        int m = mat.length, n = mat[0].length;
        int ans[][] = new int[m][n];
        boolean visited[][] = new boolean[m][n];

        Queue<Integer[]> queue = new ArrayDeque<>();
        int[][] dir = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.add(new Integer[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        //使用队列搜索
        while (!queue.isEmpty()) {
            Integer[] cell = queue.poll();
            //向四个方向进行扩散
            for (int[] di : dir) {
                int xx = cell[0] + di[0];
                int yy = cell[1] + di[1];
                //已经访问了，或者是不合法
                if (xx < 0 || xx >= m || yy < 0 || yy >= n || visited[xx][yy]) {
                    continue;
                }
                //在当前基础上递增一个长度
                ans[xx][yy] = ans[cell[0]][cell[1]] + 1;
                queue.offer(new Integer[]{xx, yy});
                visited[xx][yy] = true;
            }
        }
        return ans;
    }

   public  static class Pair {

        public Pair(int point,double value){
            this.point=point;
            this.value=value;
        }
        //点位
         int point;
        //权重
        double value;
    }
}
