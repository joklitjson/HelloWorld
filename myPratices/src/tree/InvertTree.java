package tree;

import graph.TreeNode;

public class InvertTree {

    /**
     * 2、分解法则
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {

        if (root == null) {
            return root;
        }
        //分解子问题，然后再交换两个值
        TreeNode left = invertTree(root.getLeft());
        TreeNode right = invertTree(root.getRight());

        root.setLeft(right);
        root.setRight(left);
        return root;
    }

    /**
     * 1\使用遍历法：交换二叉树的左右节点
     *
     * @param root
     */
    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();
        root.setLeft(right);
        root.setRight(left);
        //翻转他们的子节点
        traverse(root.getLeft());
        traverse(root.getRight());
    }


    /**
     * 填充右指针：
     *
     * @param root
     * @return
     */
    Node connect(Node root) {
        if (root == null) {
            return root;
        }
        // 遍历「三叉树」，连接相邻节点
        traverse(root.left,root.right);
        return root;
    }

    /**
     * 不仅仅要连接相邻节点的指针，还要连接夸父节点的指针
     *
     * @param node1
     * @param node2
     */
    public void traverse(Node node1, Node node2) {
        if (node1 == null || node2 == null) {
            return;
        }
        //设置指向
        node1.next = node2;
//        2、分别遍历两个指针
        traverse(node1.left, node1.right);
        traverse(node2.left, node2.right);

        //3、连接跨越父节点的两个子节点
        traverse(node1.right, node2.left);
    }

    static class Node {
        int data;
        Node left;
        Node right;
        Node next;
    }

    //将二叉树展开为链表
//    1可以使用遍历法，在外部定义一个节点，使用先序遍历，但是本题的目的是向让我们使用原地遍历的方法
    void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        //递归处理他的左右节点
        flatten(root.getLeft());
        flatten(root.getRight());

        /**** 后序遍历位置 ****/
        // 1、左右子树已经被拉平成一条链表
        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();
        root.setLeft(null);

        //2、把左子树当成右子树
        root.setRight(left);

        //3、新的右子树，链接上旧的右子树
        TreeNode p = root;
        // 寻找新的右子树 的最右侧节点
        while (p.getRight() != null) {
            p = p.getRight();
        }
        p.setRight(right);
    }

}
