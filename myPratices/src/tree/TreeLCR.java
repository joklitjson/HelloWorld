package tree;

import graph.TreeNode;

import java.util.*;

public class TreeLCR {

    /**
     * LCR 047. 二叉树剪枝
     * 后续遍历算法：左右元素都是0，且当前元素是0的情况下可以进行减去
     * 删除节点中都是0的元素
     * @param root
     * @return
     */
    public TreeNode pruneTree(TreeNode root) {
        int val = afterOrder(root);
        return (val == 0 && root.left == null && root.right == null) ? null : root;
    }

    private int afterOrder(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftNode = afterOrder(root.left);

        int rightNode = afterOrder(root.right);

        if (leftNode == 0) {
            root.left = null;
        }
        if (rightNode == 0) {
            root.right = null;
        }

        int value = root.val == 0 && leftNode == 0 && rightNode == 0 ? 0 : 1;
        return value;
    }


    List<Integer> ans=new ArrayList<>();
    int hight=0;//最大高度
    /**
     * LCR 046. 二叉树的右视图
     * 解决方案：使用层序遍历，每层的最右侧元素
     * 方案二：使用深度优先遍历，记录最大高度和每次遍历的高度
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        if(root==null){
            return ans;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        //层序遍历
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                size--;

                TreeNode tmp = queue.poll();
                //表示是当前层级的最右侧的元素
                if (size == 0) {
                    ans.add(tmp.val);
                }

                if (tmp.left != null) {
                    queue.add(tmp.left);
                }

                if (tmp.right != null) {
                    queue.add(tmp.right);
                }
            }
        }
        dfs(root,0);
        return ans;
    }

    private void  dfs(TreeNode root,int h) {

        //当前高度是否和最大高度相等
        if (h==hight){
            hight++;
            ans.add(root.val);
        }

        if (root.right != null) {
            dfs(root.right,h+1);
        }

        if (root.left != null) {
            dfs(root.left,h+1);
        }
    }


    /**
     * LCR 045. 找树左下角的值
     * 方案一：使用深度优先遍历，设置最大高度变量，如果都当前高度大于最大高度则把当前告诉设置给最大高度，同时把当前值给最左元素(因为遍历是从左到右遍历的，因此同等高度下左边的元素先遍历到)
     * 方案二：使用广度优先遍历（从右相左遍历的方式，最后一个元素就是我们要找的）
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {

        Queue<TreeNode> queue=new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode p=  queue.poll();
            if (p.right!=null){
                queue.offer(p.right);
            }

            if (p.left!=null){
                queue.offer(p.left);
            }
            //从右向左遍历
            curValue=p.val;
        }

        dfs2(root,0);
        return curValue;
    }

    int curValue=0;
    int curHeight=0;
    private void dfs2(TreeNode root,int hight){
        if (root==null){
            return;
        }
        hight++;
        dfs2(root.left,hight);
        dfs2(root.right,hight);

        if (hight>curHeight) {
            curHeight = hight;
            curValue = root.val;
        }
    }


    /**
     * LCR 044. 在每个树行中找最大值
     * 方案一：dfs+map方式，维护hight 高度
     * 方案二：广度优先遍历，记录每层的最大值
     * @param root
     * @return
     */
    public List<Integer> largestValues(TreeNode root) {
        largestValuesDfs(root, 0);

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            ans.add(map.get(i));
        }
        return ans;
    }

    Map<Integer,Integer> map=new HashMap<>();
    private void largestValuesDfs(TreeNode root,int hight){
        if (root==null){
            return;
        }
        Integer currentMax=  map.get(hight);
        //更新最大值
        if (currentMax==null||root.val>currentMax){
            currentMax=root.val;
            map.put(hight,currentMax);
        }

        //遍历下一层
        hight++;
        largestValuesDfs(root.left,hight);
        largestValuesDfs(root.right,hight);
    }


    /**
     *
     * LCR 050. 路径总和 III
     * 解决方案:哈希化 前缀和
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {

        countMap.put(0L,1);//和为0的元素有一个
        pathSumDfs(root,0,targetSum);
        return count;
    }

    private Map<Long,Integer> countMap=new HashMap<>();
    int count=0;
    private void pathSumDfs(TreeNode root, long sum, int targetSum){
        if (root==null){
            return;
        }
        sum+=root.val;
        if (countMap.containsKey(sum-targetSum)){
            count+=countMap.get(sum-targetSum);
        }
        //把和等于sum的数量+1
        countMap.put(sum,countMap.getOrDefault(sum,0)+1);
        pathSumDfs(root.left,sum,targetSum);

        pathSumDfs(root.right,sum,targetSum);

        //把这个 sum的个数进行去掉（当前节点遍历完成了）
        countMap.put(sum,countMap.getOrDefault(sum,0)-1);
    }


    /**
     * LCR 049. 求根节点到叶节点数字之和
     *
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {
        return sumNumbersDfs(root,0);
    }

    private int sumNumbersDfs(TreeNode root,int sum){
        if (root==null){
            return 0;
        }
        sum=sum*10+root.val;
        if (root.left==null&&root.getRight()==null){
            return sum;
        }
        else {
            return sumNumbersDfs(root.left,sum)+sumNumbersDfs(root.right,sum);
        }
    }
}
