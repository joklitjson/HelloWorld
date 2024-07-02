import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CakesSort {

    public static void main(String[] args) {
        CakesSort cakesSort= new CakesSort();
        cakesSort.sort(new int[]{3,2,4,1},4);
        System.out.println("翻转次数"+cakesSort.res);
        System.out.println("翻转步骤"+ Arrays.toString(cakesSort.record.toArray(new Integer[0])));
    }

    // 记录反转操作序列
    LinkedList<Integer> record = new LinkedList<>();
    int res=0;
    // cakes 是一堆烧饼，函数会将最上面 n 个烧饼排序
    //每次找到一个最大的，然后把最大的翻转到第一个为止，然后再翻转n个，则第一个就翻转到了最后一个
    //然后再循环寻找第二个最大的元素
    void sort(int[] cakes, int n){// 返回翻转的次数
//base case
        if (n==1){
            return;
        }
        //寻找最大的元素
        int maxIndex=0;
        for(int i=0;i<n;i++){
            if (cakes[i]>cakes[maxIndex]){
                maxIndex=i;
            }
        }
        //翻转0~max
        // 第一次翻转，将最大饼翻到最上面
        res++;
        record.add(maxIndex+1);
        reverse(cakes,0,maxIndex);

        // 第二次翻转，将最大饼翻到最下面
        res++;
        reverse(cakes,0,n-1);
        record.add(n);

        //求下一个最大值
        sort(cakes,n-1);

    }

    private void reverse(int[] cakes,int i,int j){
        while (i<j){
            int tmp=cakes[i];
            cakes[i]=cakes[j];
            cakes[j]=tmp;
            i++;
            j--;
        }
    }
}
