package tree;

import datastrucs.TreeNode;

import java.util.ArrayList;
import java.util.List;


/**
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
}
