package leetcode;

import datastrucs.TreeNode;

import java.util.Stack;

/**
 * 右两种方案：
 * 1、利用中序遍历把节点都加载到列表中，每次从列表区数字
 * 2、使用迭代法，每次取一个
 */
public class BSTIterator {

    TreeNode cur;
    Stack<TreeNode> stack=new Stack<>();
    public BSTIterator(TreeNode root) {
        this.cur=root;
    }

    public int next() {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        int val = 0;
        cur = stack.pop();
        val = cur.val;
        cur = cur.right;
        return val;
    }

    public boolean hasNext() {
        return cur!=null||!stack.isEmpty();
    }
}
