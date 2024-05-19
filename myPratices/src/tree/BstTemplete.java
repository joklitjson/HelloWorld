package tree;

import datastrucs.TreeNode;

/**
 * 二叉搜索树 代码模板
 */
public class BstTemplete {


    void  bsfTempelete(TreeNode root,int target){
        if(root.val==target){
            return;
        } else if (root.val<target) {
            bsfTempelete(root.right,target);
        } else if (root.val>target) {
            bsfTempelete(root.left,target);
        }
    }
}
