package datastrucs;

import java.util.*;

/**
 * 二叉树的 递归遍历和非递归遍历
 * https://leetcode.cn/problems/binary-tree-preorder-traversal/solutions/87526/leetcodesuan-fa-xiu-lian-dong-hua-yan-shi-xbian-2/
 *
 */
public class TreeTest {
    public static void main(String[] args) {
        //定义数结构 1
        //       5    6
        //    8,  9  3
        //  2

        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        treeNode1.setLeft(treeNode5);
        treeNode1.setRight(treeNode6);

        TreeNode treeNode8 = new TreeNode(8);
        TreeNode treeNode9 = new TreeNode(9);
        treeNode5.setLeft(treeNode8);
        treeNode5.setRight(treeNode9);
        TreeNode treeNode3 = new TreeNode(3);

        treeNode6.setLeft(treeNode3);
        TreeNode treeNode2 = new TreeNode(2);
        treeNode8.setLeft(treeNode2);

        preDFS(treeNode1);
        System.out.println();
        preBfs(treeNode1);
    }

    /**
     * 递归版的先序遍历
     * @param treeNode
     */
    private static void  preDFS(TreeNode treeNode){
        TreeNode root=treeNode;
        //使用栈进行处理,因此这里只需要if就行了
        if (root!=null){
            System.out.print(root.getData()+" ");
            preDFS(root.getLeft());
            preDFS(root.getRight());

        }
    }

    private  static void  preBfs(TreeNode treeNode){
        Stack<TreeNode> stack=new Stack<>();
        stack.push(treeNode);
        while (!stack.isEmpty()){
            TreeNode peek= stack.pop();
            System.out.print(peek.getData()+" ");
            //此时一定要先把right 后left入栈,这样left就在栈顶了
            if (peek.getRight()!=null){
                stack.push(peek.getRight());
            }
            if (peek.getLeft()!=null){
                stack.push(peek.getLeft());
            }
        }
    }
}
