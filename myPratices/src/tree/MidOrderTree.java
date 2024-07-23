package tree;

import datastrucs.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树的中序遍历
 */
public class MidOrderTree {

    /**
     * 迭代法 实现二叉树的中序遍历
     */
    public List<Integer> middleOrderIterator(TreeNode root) {

        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        //借助栈实现 先把左边入队，然后在出栈
        TreeNode cur = root;
        int rank = 0;//中序遍历的排名
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || cur != null) {

            //直接先递归左子数，都加入到队列中
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            //需要出栈了
            cur = stack.pop();
            /*********中序遍历的代码位置***********/
            result.add(cur.val);
            rank++;
            cur = cur.right;
        }

        return result;
    }
}
