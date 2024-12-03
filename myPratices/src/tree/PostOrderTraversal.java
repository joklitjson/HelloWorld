package tree;

import graph.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树的后续遍历，递归法和迭代法
 * 迭代法右两种方式
 */
public class PostOrderTraversal {

    //借助栈来实现递归
    public void postOrderIterator(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        TreeNode pre = null;//前面遍历到的节点：已经出栈的节点
        while (!stack.isEmpty() || cur != null) {

            while (cur != null) {
                //先把根节点都压住入栈
                stack.push(cur);
                cur = cur.left;
            }

            TreeNode peek = stack.peek();

            //如果元素的右节点为空或者已经被访问过，则弹出当前节点
            if (peek.right == null || peek.right == pre) {
                stack.pop();
                /****** 子元素都访问完成之后的位置 ******/
                result.add(peek.val);
                pre = peek;
                cur = null;
            } else {
                //否则就继续先遍历右节点入栈
                cur = peek.right;
            }
        }
    }

    /**
     * 迭代法遍历二：后续遍历的顺序是左右根，因此入栈的顺序应该是根左右，然后再把节点弹出 采用头插法如队列
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {

        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            root = stack.pop();
            result.add(0, root.val);//把根节点插入到头部，则就实现了倒序

            if (root.left != null) {
                stack.push(root.left);
            }

            if (root.right != null) {
                stack.push(root.right);
            }
        }
        return result;
    }
}
