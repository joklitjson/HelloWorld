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
}
