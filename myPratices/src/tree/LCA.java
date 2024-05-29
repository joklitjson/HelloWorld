package tree;

import datastrucs.Node;
import datastrucs.TreeNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 最近公共父节点的题目都是 源于find函数的变化
 */
public class LCA {

    /**
     * 查找元素的通用写法：前提条件，val必定在树中
     *
     * @param root
     * @param val
     * @return
     */
    TreeNode find(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        /**  前序位置************** **/
        if (root.val == val) {
            return root;
        }
        TreeNode left = find(root.left, val);
        TreeNode right = find(root.right, val);

        /******后续位置*****/
        if (root.val == val) {
            return root;
        }
//        这段代码相当于你先去左右子树找，然后才检查root，依然可以到达目的，但是效率会进一步下降。
//        因为这种写法必然会遍历二叉树的每一个节点。
//        //在他的左右子节点中找到了元素
//        if (left != null || right != null) {
//            return root;
//        }
//        //都没有找到
//        return null;

//        以上代码可以简写成以下部分
        // 看看哪边找到了(左右两个节点  肯定有一个存在值的，因为题目的前提条件就是存在的)
        return left != null ? left : right;
    }

    /**
     * 变种二：寻找两个节点
     * @param root
     * @param val1
     * @param val2
     * @return
     */
//    TreeNode find(TreeNode root, int val1, int val2) {
//        if (root == null) {
//            return null;
//        }
//        if (root.val == val1 || root.val == val2) {
//            return root;
//        }
//
//        TreeNode left = find(root.left, val1, val2);
//        TreeNode right = find(root.right, val1, val2);
//
//
//        return left != null ? left : right;
//    }

    /**
     * p q 都在树种
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return find(root, p.val, q.val);
    }

    TreeNode find(TreeNode root, int val1, int val2) {
        if (root == null) {
            return null;
        }
        // 前序位置 命中情况二
        if (root.val == val1 || root.val == val2) {
//            发现了一个值
            return root;
        }

        TreeNode left = find(root.left, val1, val2);
        TreeNode right = find(root.right, val1, val2);

        // 后序位置，已经知道左右子树是否存在目标值(命中情况一)
        if (left != null && right != null) {
            return root;
        }

        return left != null ? left : right;
    }


    boolean findP = false, findQ = false;

    /**
     * 要查找的节点不一定在树里，因此需要定义外部变量 用于判断是否存在
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (!findP || !findQ) {
            return null;
        }
        return find2(root, p.val, q.val);
    }

    private TreeNode find2(TreeNode root, int pval, int qval) {

        if (root == null) {
            return null;
        }
        TreeNode left = find2(root.left, pval, qval);
        TreeNode right = find2(root.right, pval, qval);
        //当前节点就是的
        if (left != null && right != null) {
            findP = true;
            findQ = true;
            return root;
        }
        if (root.val == pval) {
            findP = true;
            return root;
        }
        if (root.val == qval) {
            findQ = true;
            return root;
        }

        return left != null ? left : right;
    }

    /**
     * 存在一组节点的公共祖先
     *
     * @param root
     * @param nodes
     * @return
     */
    TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {

        Set<Integer> nodesSet = new HashSet<>();
        for (TreeNode node : nodes) {
            nodesSet.add(node.val);
        }
        return find(root, nodesSet);
    }

    /**
     * 查找包含nodes节点的节点
     *
     * @param root
     * @param nodes
     * @return
     */
    TreeNode find(TreeNode root, Set<Integer> nodes) {
        if (root == null) {
            return null;
        }

        //先序判断 表明当前节点就是lca节点
        if (nodes.contains(root.val)) {
            return root;
        }
        TreeNode left = find(root.left, nodes);
        TreeNode right = find(root.right, nodes);

        //左右两边都存在，父节点是他自己，只左或只右存在，谁存在谁就是公共祖先节点
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }

    /**
     * 二叉搜索数 中查找两个节点的父节点：可以利用bst的性质，只搜索符合条件的节点
     * @param root
     * @param p
     * @param q
     * @return
     */
    TreeNode lowestCommonAncestorBSt(TreeNode root, TreeNode p, TreeNode q) {
        int min=Math.min(p.val,q.val);
        int max=Math.max(p.val,q.val);
        return  findBst(root,min,max);
    }

    /**
     * val1<val2
     * @param root
     * @param val1
     * @param val2
     * @return
     */
    TreeNode findBst(TreeNode root, int val1,int val2){
        if (root==null){
            return null;
        }

        //最小值 大于当前节点，说明要搜索的两个值都大于当前节点，因此要到右子树搜索
        if (root.val<val1){
            return findBst(root.right,val1,val2);
        }
        //最大值都小于当前节点，因此要去左子树搜索
        if (root.val>val2){
            return findBst(root.left,val1,val2);
        }

        // val1 <= root.val <= val2
        // 则当前节点就是最近公共祖先
        return root;
    }

    /**
     * 类似于 两个链表查找公共父节点
     * @param p
     * @param q
     * @return
     */
    public Node lowestCommonAncestor(Node p, Node q){
        Node p1=p, p2=q;
        while (p1!=p2){
            if (p1.parent!=null){
                p1=p1.parent;
            }
            else {
                p1=q;
            }
            if (p2.parent!=null){
                p2=p2.parent;
            }
            else {
                p2=p1;
            }
        }

        return p1;
    }
}
