package tree;

import graph.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class MaxWidth {


    int ans = 1;
    Map<Integer,Integer> map=null;
    /**
     * 最大寬度：其实就是给每个节点一个编号，左子树编号为父节点编号的两倍，
     * 右子树编号为父节点编号的两倍加一。这样在同一层的节点编号之间的差值就可以用来计算该层的宽度。
     * @param root
     * @return
     */
    public int widthOfBinaryTree(TreeNode root) {
        map=new HashMap<>();
        dfs(root, 1, 0);
        return ans;
    }

    private void dfs(TreeNode root, int u, int depth) {
        if (root == null) {
            return;
        }
        if (map.containsKey(depth)) {
            ans = Math.max(ans, u - map.get(depth) + 1);
        } else {
            map.put(depth, u);
        }
        dfs(root.left, u << 1, depth + 1);
        dfs(root.right, u << 1 | 1, depth + 1);
    }


}
