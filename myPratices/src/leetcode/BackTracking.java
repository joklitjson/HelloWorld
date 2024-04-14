package leetcode;

import java.util.ArrayList;
import java.util.List;

public class BackTracking {

    private List<String> ans=new ArrayList<>();
    private List<String> tmp=new ArrayList<>();
    public List<String> binaryTreePaths(TreeNode root) {
        dfs(root);
        return  ans;
    }

    private void  dfs(TreeNode root){
        if (root==null){
            return;
        }
        tmp.add(root.val+"");
        if (root.left==null&&root.right==null){
            ans.add(getPath(tmp));
        }
        dfs(root.left);;
        dfs(root.right);
        tmp.remove(tmp.size()-1);
    }

    private String getPath( List<String> tmp){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(tmp.get(0));
        for (int i=0;i<tmp.size();i++){
            stringBuilder.append("->"+i);
        }
        return stringBuilder.toString();
    }


     static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

}
