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

    /**
     * 求二叉树中的第k个元素
     * @param root
     * @param k
     * @return
     */
    int kthSmallest(TreeNode root, int k) {
        // 利用 BST 的中序遍历特性
        traverse(root, k);
        return result;
    }

    int rank=0;
    int result=0;

    /**
     * 缺点：没利用到二叉搜索树的 左小右大的特性
     * 那么，如何让每一个节点知道自己的排名呢？
       这就是我们之前说的，需要在二叉树节点中维护额外信息。每个节点需要记录，以自己为根的这棵二叉树有多少个节点。
     * @param root
     * @param k
     */
    private void traverse(TreeNode root, int k) {
        if (root==null){
            return;
        }
        traverse(root.getLeft(),k);

        /* 中序遍历代码位置:中序遍历是有序的 */
        rank++;
        if (rank==k){
            result= root.val;
            return;
        }
        /********************/

        traverse(root.getRight(),k);
    }
    int sum=0;
    /**
     * 数是二叉搜索树：特点：中序遍历输入的是升序排列的？如何降序输出？
     * 降序输出：先遍历右子数，然后在遍历中间，其次在遍历左子树（右、中、左） 是降序的，因此值需要把右+中的值，赋值给左 则可以实现累加
     * 把数中的值变成 大于等于他的节点之和
     * @param root
     * @return
     */

    TreeNode convertBST(TreeNode root){
        traverseBst(root);
        return root;
    }
    void traverseBst(TreeNode root){
        if (root==null){
            return;
        }
        traverseBst(root.right);
        /** ********* 中序遍历****************/
        sum+=root.val;
        root.val=sum;
        traverseBst(root.left);
    }
}
