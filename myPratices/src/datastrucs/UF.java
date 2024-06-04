package datastrucs;

/**
 * 连通图相关的用例
 */
public class UF {

    /**
     * 联通分量
     */
    private int count;
    //存放元素的父节点
    int [] parent;

    public UF(int count){
        this.count=count;
        parent=new int[count];

        //设置每一个节点的父节点指向自己
        for (int i=0;i<count;i++){
            parent[i]=i;
        }
    }

    /**
     * 判断两个节点是否联通
     * @param a
     * @param b
     * @return
     */
    public boolean connect(int a,int b){
        int parentA=findParent(a);
        int parentB=findParent(b);
        return parentA==parentB;
    }

    /**
     * 联通a和b
     * @param a
     * @param b
     */
    public void union(int a,int b){
        int parentA=findParent(a);

        int parentB=findParent(b);
        //随便把他指向一个节点
        if (parentA!=parentB){
          parent[parentA]=parentB;
          count--;
        }
    }

    public int getCount() {
        return count;
    }

    /**
     * 查找a的父节点s
     * @param x
     * @return
     */
    public int findParent(int x){

        //会造成数据变成链表一样，因此我们需要把他进行压缩
//        while (x!=parent[x]){
//            x=parent[x];
//        }
//        return x;

        //2、压缩版
        while (x!=parent[x]){
            //把当前节点的父节点指向他的爷爷
            parent[x]=parent[parent[x]];
            x=parent[x];
        }

        return x;
    }

    //计算图的连通分量
    int countComponents(int n, int[][] edges){
        UF uf=new UF(n);
        for (int[] edge:edges){
            uf.union(edge[0],edge[1]);
        }
        return uf.getCount();
    }


}
