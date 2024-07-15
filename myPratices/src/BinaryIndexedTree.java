/**
 * https://seramasumi.github.io/docs/Algorithms/mc-%E5%BE%AE%E8%AF%BE%E5%A0%82-%E6%A0%91%E7%8A%B6%E6%95%B0%E7%BB%84_Indexed_Tree.html
 * 树状数组：树状数组只能维护前缀(前缀和，前缀积，前缀最大最小)，而线段树可以维护区间。我们求区间和，是用两个前缀和相减得到的，
 * 而区间最大最小值是无法用树状数组求得的。(经过一些修改处理，也可以处理)
 */
public class BinaryIndexedTree {

    private int c[];
    private int a[];
    private int n;
    public BinaryIndexedTree(int [] arr){
        n=arr.length;
        a=arr;
        c=new int[n];
        //初始化
        for (int i=0;i<n;i++){
            add(i,arr[i]);
        }

    }
    private void add(int idx,int value) {
        idx++;
        for (int i = idx; i < n; i += lowbit(i)) {
            c[i] += value;
        }
    }

    public void update(int x,int value){
        add(x,a[x]-value);
        a[x]=value;
    }

    int sum(int idx){
        idx++;
        int res=0;
        for (int i=idx;i>=0;i-=lowbit(i)){
            res+=c[i];
        }
        return res;
    }

    int rangeSum(int i,int j) {
        return sum(j) - sum(i - 1);
    }
    private int lowbit(int x){
        return x&(-x);
    }
}
