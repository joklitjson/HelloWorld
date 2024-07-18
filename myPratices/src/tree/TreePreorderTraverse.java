package tree;

import datastrucs.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 *
 * https://mp.weixin.qq.com/s?__biz=MzAxMjY5NDU2Ng==&mid=2651862003&idx=3&sn=d50a4e5bde1cd4c3b285f3247f2fd332&chksm=804976bab73effacd8c0f76646d6d9a30489239742ba7dedc90f9a54d37b7562426dd93321b1&scene=27
 * 前序位置的代码执行是自顶向下的，而后序位置的代码执行是自底向上的：
 *
 * 前序位置是刚刚进入节点的时刻，后序位置是即将离开节点的时刻。
 *
 * 但这里面大有玄妙，意味着前序位置的代码只能从函数参数中获取父节点传递来的数据，
 * 而后序位置的代码不仅可以获取参数数据，还可以获取到子树通过函数返回值传递回来的数据。
 *
 * 一旦你发现题目和子树有关，那大概率要给函数设置合理的定义和返回值，在后序位置写代码了。
 */
public class TreePreorderTraverse {

    /**
     * 前序遍历二叉树，输出字符
     * 利用二叉树在数组存储中的特性[跟，左子树，右子树]
     * @param treeNode
     * @return
     */
    List<Integer> preorderTraverse(TreeNode treeNode){
        List<Integer> result=new ArrayList<>();
        if (treeNode==null){
            return result;
        }
        result.add(treeNode.getData());
        //遍历左子树，获取他的前序遍历结果
        result.addAll(preorderTraverse(treeNode.getLeft()));
        //遍历右子树，获取右子树的
        result.addAll(preorderTraverse(treeNode.getRight()));
        return result;
    }

    //前序遍历 迭代写法,借助栈
    public  List<Integer>  preOrderIterator(TreeNode treeNode) {
        List<Integer> result = new ArrayList<>();
        if (treeNode == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();

        stack.push(treeNode);
        while (!stack.isEmpty()) {
            //取出根节点
            TreeNode root = stack.pop();
            result.add(root.val);
            //把左右子数都加入到栈中，因为下一次需要先取出左子数遍历，因此需要先把右子树加入到栈中
            if (root.right != null) {
                stack.push(root.right);
            }

            if (root.left != null) {
                stack.push(root.left);
            }
        }
        return result;
    }

    //迭代的第二种写法
    public  List<Integer>  preOrderIterator2(TreeNode treeNode) {

        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = treeNode;

        while (!stack.isEmpty() || cur != null) {

            while (cur != null) {
                stack.push(cur);
                /************************/
                //此处就是先访问的根节点
                result.add(cur.val);
                //访问左子树
                cur = cur.left;
            }
            //把根节点弹出
            cur = stack.pop();

            //准备便利右子树
            cur = cur.right;
        }
        return result;
    }
}
