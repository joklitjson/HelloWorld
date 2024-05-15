package tree;

import datastrucs.TreeNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFSTemplete {

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
        int step = 0;//定义需要的次数、或者最小深度
        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {
                // 把这些节点的相邻节点在一次加入到队列中,就是加入下一层节点
                TreeNode node = queue.poll();
                if (node == target) {
                    return step;
                }
                //3加入他的子节点
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }

                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
            step++;
        }

        return -1;
    }
}
