package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 归并排序和二叉树的关系，其实归并排序就是二叉树的后序遍历
 *
 */
public class MegeSortWithTreeApplication {
    class Pair{
        int value;//值
        int index;//索引
        public  Pair(int value,int index){
            this.value=value;
            this.index=index;
        }
    }

    Pair [] tmp;
    int [] count;
    /**
     * 计算一个数组的右侧比他小的数的个数：借助归并排序的特点，交换元素的时候 就可以比较其大小
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        Pair [] pairs=new Pair [nums.length];
        count=new int[nums.length];
        tmp=new Pair[nums.length];
        //构造数据
        for (int i=0;i< nums.length;i++){
            pairs[i]=new Pair(nums[i],i );
        }
        List<Integer> result=new ArrayList<>();
        for (int value:count){
            result.add(value);
        }
        return result;
    }

    private void  merge(  Pair [] pairs,int lo,int hi){
        if (lo==hi){
            return;
        }
        int middle=lo+(hi-lo)/2;
        merge(pairs,lo,middle);
        merge(pairs,middle+1,hi);
        mergeSort(pairs,lo,middle,hi);

    }

    /**
     * 计算右侧比当前数小的数量
     * @param pairs
     * @param lo
     * @param middle
     * @param hi
     */
    private void mergeSort(Pair [] pairs,int lo, int middle, int hi){

        for (int t=lo;t<=hi;t++){
            tmp[t]=pairs[t];
        }
        int i=lo,j=middle+1;
        for (int p=lo;p<=hi;p++){
            if (i==middle+1){
                //左侧的遍历完成了，只剩下右侧的数值了：当前右侧的数字都大于左侧数字
                pairs[p]=tmp[j++];
            }
            else if(j==hi+1){
                //右边都遍历完成了，p这个位置的数字都大于左侧的数：
                pairs[p]=tmp[i++];
                count[pairs[p].index]+=j-middle-1;
            } else if (pairs[i].value<pairs[j].value) {
                pairs[p]=tmp[i++];
            }
            else {
                pairs[p]=tmp[j++];
                count[pairs[p].index]+=j-middle-1;
            }
        }
    }
}
