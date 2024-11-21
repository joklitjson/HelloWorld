package tree;

import datastrucs.TreeNode;

public class TreeMain {

    /**
     * LCR 175. 计算二叉树的深度
     * 后续遍历，比较两个孩子的最大深度
     * @param root
     * @return
     */
    public int calculateDepth(TreeNode root) {
        if (root==null){
            return 0;
        }
        return depth(root);
    }

    private int depth(TreeNode root){
        if (root==null){
            return 0;
        }
        int leftDepth=depth(root.left);
        int rightDepth=depth(root.right);

        return Math.max(leftDepth,rightDepth)+1;
    }
}
