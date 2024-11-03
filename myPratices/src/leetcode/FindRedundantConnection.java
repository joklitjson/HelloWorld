package leetcode;

public class FindRedundantConnection {

    private int [] parent;
    /**
     * LCR 118. 冗余连接
     * 判断连通性问题：可以使用联通图来解决
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        parent=new int[edges.length+1];
        //自己指向自己
        for (int i=0;i<parent.length;i++){
            parent[i]=i;
        }

        for (int[] edge:edges){
            int p0=getParent(edge[0]);
            int p1=getParent(edge[1]);

            //表明他们已经处在一个联通分量重了，因此如果再把他们加在一起 会形成环
            if (p0==p1){
                return edge;
            }
            union(edge[0],edge[1]);
        }
        return new int[0];
    }

    public int getParent(int element) {

        //判断他不等于他的父亲
        while (element != parent[element]) {
            element = parent[element];
        }
        return element;
    }

    public void  union(int a,int b){
        int p1=getParent(a);
        int p2=getParent(b);
        if (p1!=p2){
            //随机进行指向
            parent[p1]=p2;
        }
    }


    /**
     * 相似字符串：
     * 使用并查集来解决；把每个字符串当前一个元素，如果两个元素相似，则把这两个元素 进行联通，最后求联通分量问题
     * @param strs
     * @return
     */
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        parent = new int[n];

        //1、指向自己
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        //2、进行遍历
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int p0 = getParent(i);
                int p1 = getParent(j);
                if (p0 != p1) {
                    //再次判断他俩是否相似
                    if (isSimilar(strs[i], strs[j])) {
                        parent[p0] = p1;
                    }
                } else {
                    continue;
                }
            }
        }

        //统计联通分量
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (parent[i] == i) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 只有两个位置的元素 不相同
     * @param a
     * @param b
     * @return
     */
    private boolean isSimilar(String a,String b){

        int diff=0;
        for (int i=0;i<a.length();i++){
            if (a.charAt(i)!=b.charAt(i)){
                diff++;
            }
        }
        return diff<=2;
    }
}
