import java.util.*;

public class Main {
    public static void main(String[] args) {

        LinkedList<Character> str = new LinkedList<>();
        for (char c : "6-(9-5)*2".toCharArray()) {
            str.add(c);
        }
        System.out.println("6-(9-5)*2==="+calc(str));


//        LinkedList<Integer> stack=new LinkedList<>();
//
//        /**
//         * 双向队列 左边是头，右边是last
//         */
//        stack.offerFirst(1);
//        stack.offerFirst(2);
//        stack.offerFirst(3);
//        stack.offerLast(6);
//        System.out.println(stack);
//
//
//        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();
//
//        priorityQueue.offer(12);
//        priorityQueue.offer(16);
//        priorityQueue.offer(1);
//        priorityQueue.offer(14);
//        priorityQueue.offer(4);
//        System.out.println("堆顶元素=="+priorityQueue.peek());
//
//        priorityQueue.poll();
//        System.out.println("移除堆顶之后的堆顶元素=="+priorityQueue.peek());
//        System.out.println("Hello world!");

//        System.out.println("第k大的值"+findKthLargest(new int[]{3,2,3,1,2,4,5,5,6},4));

//        System.out.println("第k大的值"+findKthLargest2(new int[]{3,2,1,5,6,4},2));

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

    /**
     * 方案二：使用小跟堆，只保留k个数，遍历每次都去除根部的最小值，由于是小跟堆，因此根元素就是第k个最大值
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest2(int[] nums, int k) {

        PriorityQueue<Integer> heap=new PriorityQueue<>(k);
        for (int num:nums){
            if (heap.size()==k){
                //移除小于优先级队列的最小值，只保存k个大值
                if (num>heap.peek()){
                    heap.poll();
                    heap.offer(num);
                }
            }
            else {
                heap.offer(num);
            }
        }
        return  heap.peek();
    }

    public static int findKthLargest(int[] nums, int k) {
        return quickSelect(nums,0, nums.length -1,nums.length-k);
    }
    private static int quickSelect(int[] nums,int l,int r,  int k){
        int partionIndex=partion(nums,l,r,k);
        System.out.println("partionIndex="+partionIndex+"partionValue="+nums[partionIndex]+Arrays.toString(nums));
        if (partionIndex==k){
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

        if (l>=r){
            return l;
        }
        int partionValue=nums[l];
        int left=l,right=r;
        while (left!=right){
            while (right>left&&nums[right]>partionValue){
                right--;
            }
            while (left<right&&nums[left]<=partionValue){
                left++;
            }

            if (left!=right){
                int tmp=nums[right];
                nums[right]=nums[left];
                nums[left]=tmp;
            }
        }

        int tmp=nums[left];
        nums[left]=partionValue;
        nums[l]=tmp;
        return left;
    }

    public static int calc(LinkedList<Character> list) {
        Stack<Integer> numStack = new Stack<Integer>();

        int num = 0;
        String operator = "+";//默认数字的符合都是正的
        while (!list.isEmpty()) {
            Character character = list.remove(0);
            if (Character.isDigit(character)) {
                num += num * 10 + (character - '0');
            }
            //开启递归模式计算括号中的内容
            if (character == '(') {
                num = calc(list);
            }

            //遇到新的符号位，或者集合为空了,因此需要把数字压入盏中
            if (!Character.isDigit(character) || list.isEmpty()) {
                if (operator.equals("+")) {
                    numStack.push(num);
                } else if (operator.equals("-")) {
                    numStack.push(-num);
                } else if (operator.equals("*")) {
                    int before = numStack.pop();
                    numStack.push(before * num);
                } else if (operator.equals("/")) {
                    int before = numStack.pop();
                    numStack.push(before / num);
                }
                operator = character.toString();
                num = 0;
            }
            //当前扩展中的计算完成
            if (character == ')') {
                break;
            }

        }
        int result = 0;
        while (!numStack.isEmpty()) {
            result += numStack.pop();
        }
        return result;
    }
}