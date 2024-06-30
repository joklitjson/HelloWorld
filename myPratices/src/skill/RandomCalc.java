package skill;

import linkedList.ListNode;

import java.util.Arrays;
import java.util.Random;

public class RandomCalc {

    /**
     * 分析洗牌算法正确性的准则：产生的结果必须有n!种可能。这个很好解释，因为一个长度为n的数组的全排列就有n!种，也就是说打乱结果总共有n!种。算法必须能够反映这个事实，才是正确的。
     * <p>
     * 有了这个原则再看代码应该就容易理解了：
     * <p>
     * 对于nums[0]，我们把它随机换到了索引[0, n)上，共有n种可能性；
     * <p>
     * 对于nums[1]，我们把它随机换到了索引[1, n)上，共有n - 1种可能性；
     * <p>
     * 对于nums[2]，我们把它随机换到了索引[2, n)上，共有n - 2种可能性；
     * <p>
     * 以此类推，该算法可以生成n!种可能的结果，所以这个算法是正确的，能够保证随机性。
     *
     * @param nums
     * @return
     */
    //洗牌算法：
    public static int[] shuffle(int[] nums) {

        Random random = new Random();
        int n = nums.length;
        for (int i = 0; i < nums.length; i++) {
            // 生成一个 [i, n-i] 区间内的随机数
            int r = i + random.nextInt(n - i);

            //交换i、r两个元素
            int tmp = nums[i];
            nums[i] = nums[r];
            nums[r] = tmp;
        }
        return nums;
    }

    //google面试题，未知长度的数组，随机返回链表中的一个元素，要求只能遍历一次
//    先说结论，当你遇到第i个元素时，应该有1/i的概率选择该元素，1 - （1/i）的概率保持原有的选择。看代码容易理解这个思路：
    //此时我们选择水塘抽样算法
    int getRandom(ListNode head) {
        ListNode p = head;
        int i = 0, res = 0;
        Random random = new Random();
        while (p != null) {
            i++;
            // 生成一个 [0, i) 之间的整数
            // 这个整数等于 0 的概率就是 1/i
            int rad = random.nextInt(i);
            if (rad == 0) {
                res = p.val;
            }
            p = p.next;
        }
        return res;
    }

    /**
     * 同理，如果要在单链表中随机选择k个数，只要在第i个元素处以k/i的概率选择该元素，以1 - (k/i)的概率保持原有选择即可
     *
     * @param head
     * @param k
     * @return
     */
    int[] getRandom(ListNode head, int k) {

        //先默认前k个数字被选中
        int[] res = new int[k];
        ListNode p = head;
        for (int i = 0; i < k; i++) {
            res[i] = p.val;
            p = p.next;
        }

        Random random = new Random();
        int i = k;
        while (p != null) {
            i++;
            // 生成一个 [0, i) 之间的整数
            int rad = random.nextInt(i);
            // 这个整数小于 k 的概率就是 k/i
            if (rad < k) {
                res[rad] = p.val;
            }

            p = p.next;
        }
        return res;
    }


    int [] num;
    //含有重复元素的数组，然后数组中目标为target的元素下表，如果有多个，每个元素的下标的概率都是相等的
    public int pick(int target) {
        int i=0,count=0;
        Random random=new Random();
        int res=0;
        while (i<num.length);{
            if (num[i]==target){
                count++;
                //概率正好命中了0
                if (random.nextInt(count)==0){
                    res=i;
                }
            }
        }
        return res;
    }

    // 在区间 [lo, hi) 中随机抽取 k 个数字
    static int[] sample(int lo, int hi, int k) {

        int res[] = new int[k];

        //先把前k个选择上
        for (int i = 0; i <  k; i++) {
            res[i] = lo+i;
        }
        Random random = new Random();
        int i = k;

        while (i < hi - lo) {
            i++;
            //生成一个[0,i)区间的随机数
            int j = random.nextInt(i);
            // 这个整数小于 k 的概率就是 k/i
            if (j < k) {
                res[j] = i + lo - 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        //
        for (int i = 0; i < 10; i++) {
            System.out.println(Arrays.toString(shuffle(new int[]{1, 2, 3, 4, 5})));
        }

        //测试下水塘抽样算法

        int lo = 12, hi = 32, k = 3;

        // 重复 10 万次
        int N = 1000000;
        int[] res = new int[hi - lo];

        for (int i = 0; i < N; i++) {
            int[] tmp = sample(lo, hi, k);
            for (int val : tmp) {
                // 对随机选取的元素进行记录
                res[val - lo]++;
            }
        }

        System.out.println("随机元素个数统计：" + Arrays.toString(res));
    }
}
