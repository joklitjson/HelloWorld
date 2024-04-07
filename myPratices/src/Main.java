import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {


        LinkedList<Integer> stack=new LinkedList<>();

        /**
         * 双向队列 左边是头，右边是last
         */
        stack.offerFirst(1);
        stack.offerFirst(2);
        stack.offerFirst(3);
        stack.offerLast(6);
        System.out.println(stack);


        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();

        priorityQueue.offer(12);
        priorityQueue.offer(16);
        priorityQueue.offer(1);
        priorityQueue.offer(14);
        priorityQueue.offer(4);
        System.out.println("堆顶元素=="+priorityQueue.peek());

        priorityQueue.poll();
        System.out.println("移除堆顶之后的堆顶元素=="+priorityQueue.peek());
        System.out.println("Hello world!");

        System.out.println("第k大的值"+findKthLargest(new int[]{3,2,3,1,2,4,5,5,6},4));
    }

    /**
     * 都有一个胃口值 g[i]
     * 并且每块饼干 j，都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count=0;
        int i=g.length-1;
        //遍历饼干
        for (int j=s.length-1;j>=0;j--){
            //遍历当前最大的饼干是否能满足当前最大需求孩子，不满足则
            for (;i>=0;i--){
                if (s[j]>=g[i]){
                    count++;
                }
            }
        }
        return count;
    }

    public boolean lemonadeChange(int[] bills) {
        if (bills[0] != 5) {
            return false;
        }

        int five = 0, ten = 0;
        for (int value : bills) {
            if (value == 5) {
                five++;
            } else if (value == 10) {
                ten++;
                five--;
            } else if (value == 20) {
                //找零,优先找10元的零钱,因为5元的是万能的
                if (ten > 0) {
                    ten--;
                    five--;
                } else {
                    five = five - 3;
                }
            }
            if (five < 0 || ten < 0) {
                return false;
            }
        }
        //判断最后是否大大于零
        return five >= 0 && ten >= 0;
    }

    public int candy(int[] ratings) {

        int [] candys=new int[ratings.length];
        candys[0]=1;
        //分糖果
        for (int i=1;i<ratings.length;i++){
            //相邻的元素，如果后面的大于前面的则糖果要多余前面
            if (ratings[i]>ratings[i-1]){
                candys[i]=candys[i-1]+1;
            }
            else {
                candys[i]=1;
            }
        }
        int count=candys[ratings.length-1];
        for (int i= ratings.length-2;i>=0;i--){
            //在从右向左比遍历一遍
            if (ratings[i]>ratings[i+1]) {
                candys[i] = Math.max(candys[i], candys[i + 1]+ 1) ;
            }
            count+=candys[i];
        }

        return count;
    }

    public static int findKthLargest(int[] nums, int k) {
        return quickSelect(nums,0, nums.length -1,k);
    }
    private static int quickSelect(int[] nums,int l,int r,  int k){
        int partionIndex=partion(nums,l,r,k);
        if (partionIndex==k-1){
            return nums[partionIndex];
        }
        if (k>partionIndex){
            return quickSelect(nums,partionIndex+1,r,k);
        }
        else {
            return quickSelect(nums,l,partionIndex-1,k);
        }
    }
    //此时的排序是降序，从大到小
    private static int partion(int[] nums, int l,int r, int k){

        int partionValue=nums[l];
        int left=l,right=r;
        while (left<=right){
            while (right>=left&&nums[right]<partionValue){
                right--;
            }
            while (left<=right&&nums[left]>partionValue){
                left++;
            }

            if (left<=right){
                int tmp=nums[right];
                nums[right]=nums[left];
                nums[left]=tmp;
            }
        }
        return left;
    }
}