package tree;

import datastrucs.TreeNode;

import java.util.ArrayList;
import java.util.List;

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

    List<List<Integer>> ans=new ArrayList<>();
    List<Integer> path=new ArrayList<>();
    /**
     * LCR 153. 二叉树中和为目标值的路径
     * 使用先序遍历 然后在回溯
     * @param root
     * @param target
     * @return
     */
    public List<List<Integer>> pathTarget(TreeNode root, int target) {
        dfs(root,target);
        return ans;
    }

    private void  dfs(TreeNode root, int target) {
        if (root == null) {
            return;
        }

        //当前路径的和
        int res = target - root.val;
        path.add(root.val);
        if (res == 0 && root.left == null && root.right == null) {
            ans.add(new ArrayList<Integer>(path));
        }
        dfs(root.left, res);
        dfs(root.right, res);
        path.remove(path.size()-1);
    }


    /**
     * LCR 143. 子结构判断
     * @param a
     * @param b
     * @return
     */
    public boolean isSubStructure(TreeNode a, TreeNode b) {
        return dfs(a,b);
    }

    private boolean dfs(TreeNode a, TreeNode b){
        if (a == null || b == null) {
            return false;
        }
        //把a节点当做顶点和B节点比较，或者在A的子孩子当做节点再次和B进行比较 只要右一个可以的就可以
        return subTree(a, b) || dfs(a.right, b) || dfs(a.left, b);
    }
    private boolean subTree(TreeNode a, TreeNode b){
        //说明B数已经遍历完成
        if (b==null){
            return true;
        }

        //如果a为空或者两者的值不同 则非子结构
        if (a==null||a.val!=b.val){
            return false;
        }
        //再去比较两者的 孩子节点
        return subTree(a.left,b.left)&&subTree(a.right,b.right);
    }
}
