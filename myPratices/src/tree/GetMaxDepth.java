package tree;

import graph.TreeNode;

public class GetMaxDepth {

    private int res=0;
    int depth=0;

    /**
     * 获取二叉树的最大深度
     * 一次遍历，配合外部变量搞定
     * @param root
     * @return
     */
    int maxDepth(TreeNode root) {
        traverse(root);
        return res;
    }

    /**
     * 获取左右子树的高度，然后比较，取最大值
     * 总结：定义递归函数，通过左右子树的答案 推导出原问题的答案
     * @param treeNode
     * @return
     */
    private  int maxDepth2(TreeNode treeNode){
        if (treeNode==null){
            return 0;
        }

        int leftMaxDepth=maxDepth2(treeNode.getLeft());
        int rightMaxDepth=maxDepth2(treeNode.getRight());
        //在比较左右的高度，+1是加上当前节点
        return Math.max(leftMaxDepth,rightMaxDepth)+1;
    }

    private void  traverse(TreeNode node){
        if (node==null){
            //更新最大深度
            res=Math.max(depth,res);
            return;
        }
        //  // 前序位置
        depth++;
        traverse(node.getLeft());
        traverse(node.getRight());

        // 后序位置
        depth--;
    }
}
