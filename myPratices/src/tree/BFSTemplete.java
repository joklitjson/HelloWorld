package tree;

import datastrucs.TreeNode;

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
}
