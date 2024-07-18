/**
 * 线段树：主要用于计算区间一些值：最大值、最小值、区间的和等操作
 * 能高效处理区间的查询和修改等操作
 */
public class SegmentTree {

    private SegmentTreeNode root;

    static class SegmentTreeNode{
        int start,end;
        int max;//区间的最大值，你也可以设置区间的最小值或者区间的和
        SegmentTreeNode left,right;
        public SegmentTreeNode( int start,int end,int max){
            this.start=start;
            this.end=end;
            this.max=max;
            this.left=this.right=null;

        }
    }
    public  SegmentTree(int [] arr){

        this.root=builder(0, arr.length -1,arr);


    }

    public  SegmentTreeNode builder(int start,int end, int [] arr) {
        //构建
        if (start > end) {
            return null;
        }
        SegmentTreeNode root = new SegmentTreeNode(start, end, arr[start]);
        //只有一个节点
        if (start == end) {
            return root;
        }

        int middle = (start + end) / 2;

        root.left = builder(start, middle, arr);
        root.right = builder(middle + 1, end, arr);
        //设置区间最大值
        root.max = Math.max(root.left.max, root.right.max);
        return root;
    }

    //查询区间的最大值

    //线段树的区间查询操作就是将当前区间分解为较小的子区间,然后由子区间的最大值就可以快速得到需要查询区间的最大值。
    public int queryMax(int left,int right){
        return queryMax(this.root,left,right);
    }

    public int queryMax(SegmentTreeNode node, int start,int end) {
        if (start <= node.start && node.end <= end) {
            //如果查询区间在当前节点范围内，则直接返回
            return node.max;
        }
        int mid = (node.start + node.end) / 2;
        int ans = Integer.MIN_VALUE;

        if (mid >= start) {
        // 如果查询区间和左边节点区间有交集,则寻找查询区间在左边区间上的最大值
            ans = Math.max(ans, queryMax(node.left, start, end));
        }
        if (mid +1<= end) {
            //查询区间和 右边节点有交集
            ans = Math.max(ans, queryMax(node.right, start, end));
        }

        return ans;
    }

    public void update(int idx,int value){
        this.update(root,idx,value);
    }

    private void update(SegmentTreeNode node,int idx,int value){

        //当前节点
        if (node.start==node.end&&node.start==idx){
            node.max=value;
            return;
        }

        int middle= (node.start+node.end)/2;

        if (idx<=middle){
            //要更新的节点在左侧
            this.update(node.left,idx,value);
        }
        else{
            //要更新的节点在右侧
            this.update(node.right,idx,value);
        }
        //在更新当前节点的最大值
        node.max=Math.max(node.left.max,node.right.max);
    }

    public static void main(String[] args) {
        SegmentTree segmentTree=new SegmentTree(new int[]{1, 2, 7, 8, 5});
        System.out.println("max[0,2]="+segmentTree.queryMax(0,1));
        segmentTree.update(1,10);
        System.out.println("max[0,2]="+segmentTree.queryMax(0,4));
    }
}
