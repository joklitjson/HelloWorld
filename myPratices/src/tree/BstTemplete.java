package tree;

import datastrucs.TreeNode;

/**
 * 二叉搜索树 代码模板
 */
public class BstTemplete {


    void bsfTempelete(TreeNode root, int target) {
        if (root.val == target) {
            return;
        } else if (root.val < target) {
            bsfTempelete(root.right, target);
        } else if (root.val > target) {
            bsfTempelete(root.left, target);
        }
    }

    /**
     * 求二叉树中的第k个元素
     *
     * @param root
     * @param k
     * @return
     */
    int kthSmallest(TreeNode root, int k) {
        // 利用 BST 的中序遍历特性
        traverse(root, k);
        return result;
    }

    int rank = 0;
    int result = 0;

    /**
     * 缺点：没利用到二叉搜索树的 左小右大的特性
     * 那么，如何让每一个节点知道自己的排名呢？
     * 这就是我们之前说的，需要在二叉树节点中维护额外信息。每个节点需要记录，以自己为根的这棵二叉树有多少个节点。
     *
     * @param root
     * @param k
     */
    private void traverse(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        traverse(root.getLeft(), k);

        /* 中序遍历代码位置:中序遍历是有序的 */
        rank++;
        if (rank == k) {
            result = root.val;
            return;
        }
        /********************/

        traverse(root.getRight(), k);
    }

    int sum = 0;

    /**
     * 数是二叉搜索树：特点：中序遍历输入的是升序排列的？如何降序输出？
     * 降序输出：先遍历右子数，然后在遍历中间，其次在遍历左子树（右、中、左） 是降序的，因此值需要把右+中的值，赋值给左 则可以实现累加
     * 把数中的值变成 大于等于他的节点之和
     *
     * @param root
     * @return
     */

    TreeNode convertBST(TreeNode root) {
        traverseBst(root);
        return root;
    }

    void traverseBst(TreeNode root) {
        if (root == null) {
            return;
        }
        traverseBst(root.right);
        /** ********* 中序遍历****************/
        sum += root.val;
        root.val = sum;
        traverseBst(root.left);
    }


    /**
     * 不仅仅越少某一个节点的左右子节点，还需要约束他的子节点符合他的父节点的值
     * @param root
     * @return
     */
    boolean isValidBST(TreeNode root) {
        return isValidBST(root,null,null);
    }

    /**
     * 判断 max>=root.val>=min
     * @param root
     * @param min
     * @param max
     * @return
     */
    boolean isValidBST(TreeNode root,Integer min,Integer max) {
        if (root==null){
            return true;
        }
        System.out.println(root.val+"--min="+min+"--max="+max);
        if (min!=null&&root.val<=min){
            return false;
        }
        if (max!=null&&max<=root.val){
            return false;
        }

        return isValidBST(root.left,min,root.val)&&
                isValidBST(root.right,root.val,max);
    }

    public TreeNode searchBST(TreeNode root, int val) {

        //方案一：遍历方法
//        TreeNode search=root;
//        while (search!=null){
//            if (root.val==val){
//                return search;
//            }
//            //向右侧搜索
//            else if (search.val<val){
//                search=search.right;
//            }
//            else {
//                //向左侧搜索
//                search=search.left;
//            }
//        }
//        return  search;

        //方案二：递归算法
        return searchBSTInner(root,val);
    }
    public TreeNode searchBSTInner(TreeNode root, int val) {
        if (root==null){
            return null;
        }
        if (root.val==val){
            return root;
        }
        if (root.val<val){
            return searchBSTInner(root.right,val);
        }
        else {
            return searchBSTInner(root.left,val);
        }
    }

    /**
     * 向二叉搜索数中插入一个值；数中的数都不相同
     * 解题方案：就是根据大小，选择是向左边插入还是右边插入
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            TreeNode treeNode = new TreeNode();
            treeNode.val = val;
            return treeNode;
        } else if (root.val < val) {
            root.right = insertIntoBST(root.right, val);
        } else {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }

}
