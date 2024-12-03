package tree;

import graph.TreeNode;

public class BalanceTree {

    boolean balance=true;
    /**
     * LCR 176. 判断是否为平衡二叉树
     * 它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
     * 1、验证二叉树是否是平衡二叉树：
     * 平衡二叉树：左右子数的高度不超过1
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {

        deep(root);
        return balance;
    }

    private  int deep(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (!balance) {
            return 0;
        }
        int leftHight = deep(root.left);

        int rightHight = deep(root.right);

        if (Math.abs(leftHight - rightHight) > 1) {
            balance = false;
        }
        //子树最大深度加上自己
        return Math.max(leftHight, rightHight) + 1;
    }
}
