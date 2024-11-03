package tree;

import datastrucs.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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

    /**
     * 删除一个节点：先找到，然后再判断他的子孩子的数量
     * 没有子节点：直接返回
     * 只有一个子节点：直接当做此节点
     * 有左右子节点：返回左边的最大值，或者返回右边的最小值，当做子节点
     * @param root
     * @param key
     * @return
     */
    TreeNode deleteNode(TreeNode root, int key) {
        if (root.val == key) {
            if (root.left==null&&root.right==null){
                return null;
            }
            else if (root.left==null&&root.right!=null){
                return root.right;
            }  else if (root.left!=null&&root.right==null){
                return root.left;
            }
            else {
                //查找右侧的最小值
                TreeNode min=  findMin(root.right);
                root.val=min.val;
                root.right= deleteNode(root.right,min.val);//在执行删除
                return root;
            }

        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            root.left = deleteNode(root.left, key);
        }

        return root;
    }

    TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    /**
     * 不同的二叉搜索树:
     * 给定数字n，构造 [1,n]个数字的二叉搜索树
     * 思路：按每个数字当做根节点，然后再把小于当前的数字构造左子节点，大于当前的数字构造右子节点
     * @param n
     * @return
     */
    int numTrees(int n) {
        memo=new int[n][n];
        // 计算闭区间 [1, n] 组成的 BST 个数
        return count(1, n);
    }

    // 备忘录
    int[][] memo;
    private int count(int lo, int hi) {
        //表示当前是null节点
        if(lo>hi){
            return 1;
        }
        if (memo[lo][hi]!=0){
            return memo[lo][hi];
        }
        int currentResult=0;
        for (int i=lo;i<=hi;i++){
            int leftCount=count(lo,i-1);
            int rightCount=count(i+1,hi);
            //左右所有情况的组合
            currentResult+=leftCount*rightCount;
        }
        // 将结果存入备忘录
        memo[lo][hi]=currentResult;
        return currentResult;
    }

    /**
     * 生成树，并且把数打印出来
     * @param n
     * @return
     */
    List<TreeNode> generateTrees(int n){
        if (n==0){
            return new ArrayList<>();
        }
        return  build(1,n);
    }
    List<TreeNode> build(int low,int hi) {
        List<TreeNode> result = new ArrayList<>();

        if (low > hi) {
            result.add(null);
            return result;
        }


        for (int i = low; i <= hi; i++) {
            List<TreeNode> leftList = build(low, i - 1);
            List<TreeNode> rightList = build(i + 1, hi);

            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode current = new TreeNode();
                    current.val = i;
                    current.left = left;
                    current.right = right;
                    result.add(current);
                }
            }
        }
        return result;
    }


    /**
     * 查找节点p的中序后继结点:就是中序遍历的后一个节点
     * 方案一：使用中序遍历，设变量pre，记录前一个节点的值，如果当前节点是p，则范围pre
     * 方案二：使用二叉搜索树的性质，可以快速找到答案：
     * @param root
     * @param p
     * @return
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {

        Deque<TreeNode> queue=new ArrayDeque<>();
        TreeNode pre=null,curr=root;
        while (curr!=null||!queue.isEmpty()){
            //1、先把自己放在栈中，然后把左边元素放在栈顶
            while (curr!=null){
                queue.push(curr);
                curr=curr.left;
            }

            //2、访问根节点
            curr= queue.poll();
            //前面一个元素是p，则当前元素就是后继节点
            if (pre==p){
                return curr;
            }

            pre=curr;
            //3、访问右节点
            curr=curr.right;
        }
        return null;
    }

    /**
     * 利用二叉搜索树的性质 来进行查找
     * @param root
     * @param p
     * @return
     */
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
        TreeNode successor = null, cur = root;
        while (cur != null) {
            if (cur.val > p.val) {
                //当前节点值 大于p，因此p的分布只能在 left 区间，那么后继节点也只能分布在(left，successor)中
                successor = cur;
                cur = cur.left;
            } else {
                //当前节点小于p，那么p只能分布在cur的右区间，如果等于p，则后继节点也肯定在右侧
                cur = cur.right;
            }
        }

        //层层 逼近cur
        return successor;
    }

}
