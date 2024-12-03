package tree;

import graph.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class BuildTree {
    TreeNode constructMaximumBinaryTree(int[] nums){
        if (nums==null||nums.length==0){
            return null;
        }
        return buildTree(nums,0, nums.length-1);
    }

    /**
     * 使用分解算法，构建二叉树，先构建根节点，然后在构建左右两个节点
     * @param nums
     * @param l
     * @param h
     * @return
     */
    private  TreeNode  buildTree(int[] nums,int l,int h) {
        if (l > h) {
            return null;
        }
        //1、// 找到数组中的最大值和对应的索引
        int maxValueIndex = l;
        int maxValue = nums[l];
        for (int i = l; i <= h; i++) {
            if (nums[i] > maxValue) {
                maxValue = nums[i];
                maxValueIndex = i;
            }
        }
        TreeNode root = new TreeNode(maxValue);
        root.left = buildTree(nums, l, maxValueIndex - 1);
        root.right = buildTree(nums, maxValueIndex + 1, h);

        return root;
    }

    /**
     * 还是根据分解算法，定义分解函数，返回当前根节点
     *利用先序遍历特点：第一个是最大值
     * 中序遍历特点，根节点左边是左子数，右边是右子数
     * @param preorder
     * @param inorder
     * @return
     */
    TreeNode buildTree(int[] preorder, int[] inorder){
        for (int i=0;i<=inorder.length;i++){
            valueToIdx.put(inorder[i],i);
        }
       return build(preorder,0, preorder.length-1,inorder,0,inorder.length-1 );
    }
    Map<Integer,Integer> valueToIdx=new HashMap<>();

    private TreeNode build(int[] preorder,int prel,int prer,int[] inorder,int inl,int inr){

        if (prel>prer){
            return null;
        }
        int rootValue=preorder[prel];
        //构造根节点
        TreeNode treeNode=new TreeNode(rootValue);

        int rootIndexInOrder=valueToIdx.get(rootValue);
//        int rootIndexInOrder=0;
        //找到根节点在 inOrder中的索引:使用map 进行优化
//        for (int i=inl;i<=inr;i++){
//            if ( inorder[i]== rootValue){
//                rootIndexInOrder=i;
//                break;
//            }
//        }
        int leftSize=rootIndexInOrder-inl;//左子数个数
        treeNode.left=build(preorder,prel+1,prel+leftSize,inorder,inl,rootIndexInOrder-1);
        treeNode.right=build(preorder,prel+leftSize+1,prer,inorder,rootIndexInOrder+1,inr);
        return treeNode;
    }


    /**
     * 翻转二叉树的左右子节点
     * 解决方案：定义递归函数，重新设置左右子孩子节点
     * @param root
     * @return
     */
    public TreeNode flipTree(TreeNode root) {

        return dfs(root);
    }

    private TreeNode dfs(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = dfs(right);
        root.right = dfs(left);
        return root;
    }


    /**
     * LCR 145. 判断对称二叉树
     * 方案：dfs 遍历中设置两个指针：
     * 1、左子数的左节点 和右子数的右节点进行比较，
     * 2、左子数的右节点和右子数的左节点进行比较
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
     return    check(root,root);
    }

    private boolean check(TreeNode p1,TreeNode p2) {
        if (p1 == null && p2 == null) {
            return true;
        }
        if (p1 == null && p2 != null) {
            return false;
        }
        if (p2 == null && p1 != null) {
            return false;
        }

        //判断值是否相等，以及再次检查这两个节点的子节点
        return p1.val == p2.val && check(p1.left, p2.right) && check(p1.right, p2.left);
    }


}
