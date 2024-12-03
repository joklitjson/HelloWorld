package tree;

import graph.TreeNode;

/**
 * 2、如何打印出每个节点的左右子树各有多少节点？
 *
 * 分析：需要先计算出节点的左右子树
 */
public class PrintCountOfTree {

    public int  count(TreeNode treeNode){
        if (treeNode==null){
            return 0;
        }

        int leftCOunt=count(treeNode.getLeft());
        int rightCOunt=count(treeNode.getRight());

        return  leftCOunt+rightCOunt+1;
    }

    // 记录最大直径的长度
    int maxDiameter = 0;

    /**
     * 记算二叉树的最长直径
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        // 对每个节点计算直径，求最大直径
        traverse(root);
        return maxDiameter;
    }

    /**
     * 需要先计算每个子节点的左右节点
     * @param root
     * @return
     */
    private int traverse(TreeNode root) {
        if (root==null){
            return 0;
        }
        int leftDiameter=traverse(root.getLeft());
        int rightDiameter=traverse(root.getRight());
        int tmp=leftDiameter+rightDiameter;
        maxDiameter=Math.max(maxDiameter,tmp);//更新最大值
        return Math.max(leftDiameter,rightDiameter)+1;//取左右子节点的最大值，然后再加上他本身
    }
}


