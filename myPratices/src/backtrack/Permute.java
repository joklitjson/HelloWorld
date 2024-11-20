package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组的排列
 * 1、可以使用递归法：回溯法
 * 2、递归法：就是将m个数依次放在第0个位置并固定，剩下的部分再次进行全排列，依次递归，最后，一个数的全排列作为递归的出口。
 */
public class Permute {

    public static void main(String[] args) {
//        System.out.println(new Permute().permute(new int[]{1, 2, 2}));

        permutation(new int[]{1, 2, 3},0);
        System.out.println(Arrays.toString(new Permute().countNumbers(2)));;
    }

    List<List<Integer>> res;

    List<List<Integer>> permute(int[] nums) {
        res = new ArrayList<>();
        Arrays.sort(nums);//避免重复元素出现，因此前面需要先进行排序
        backtrack(nums, new ArrayList<>(), new boolean[nums.length]);
        return res;
    }

    private void backtrack(int[] nums, List<Integer> result, boolean[] visited) {
        //全排列问题
        if (result.size() == nums.length) {
            res.add(new ArrayList<>(result));
            return;
        }
        //如果让你计算k个数的全排列：
//        if (result.size() == k) {
//            res.add(new ArrayList<>(result));
//            return;
//        }

        //回溯
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            //有重复元素，则跳过
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                continue;
            }
            if (visited[i]) {
                continue;
            }
            result.add(current);
            visited[i] = true;
            backtrack(nums, result, visited);
            result.remove(result.size() - 1);
            visited[i] = false;
        }
    }

    /**
     * 递归法：就是将m个数依次放在第0个位置并固定，剩下的部分再次进行全排列，依次递归，最后，一个数的全排列作为递归的出口。
     * @param arr
     * @param n
     */
    private static void permutation(int[] arr, int n) {

        //准备把某个数放在第n个位置了，但是超出了索引的范围了，因此需要返回
        if(n>= arr.length-1){
            for (int i:arr){
                System.out.print(i);
            }
            System.out.println();
            return;
        }

        //准备把剩下的数字 依次排列在第n个位置

        for (int start=n;start<arr.length;start++) {
            //把arr[i] 依次放在第n个位置，返回在排列剩余数字
            int tmp = arr[start];
            arr[start] = arr[n];
            arr[n] = tmp;

            permutation(arr, n + 1);

            //回退刚刚的排列

            tmp = arr[start];
            arr[start] = arr[n];
            arr[n] = tmp;
        }
    }

    /**
     * LCR 135. 报数
     * 其实就是考虑的排列问题，位数是cnt，每个位置上的范围是0~9，特殊点在于第一个必须是非零
     * 因此我们一次遍历位数比如 二位数、三位数、四位数等的全排列，先把第一位数固定起来
     * @param cnt
     * @return
     */
    int ans[];
    int count=0;
    public int[] countNumbers(int cnt) {

        ans = new int[(int) Math.pow(10, cnt)-1];
        for (int digit = 1; digit <= cnt; digit++) {//代表位数
            char[] nums = new char[digit];
            for (char first = '1'; first <= '9'; first++) {
                nums[0] = first;
                permute(nums, 1, digit);
            }
        }
        return ans;
    }

    /**
     * 站在第idx位的角度排列，这个位置上可以存放 0~9中的任意数字
     * @param nums
     * @param idx
     * @param digit
     */
    public void permute(  char [] nums,int idx,int digit){
        //位数慢了
        if (idx==digit){
            ans[count++]=Integer.parseInt(String.valueOf(nums));
            return;
        }

        //第n位依次放 0~9个数字
        for (char n='0';n<='9';n++) {
            nums[idx] = n;
            permute(nums, idx + 1, digit);
        }

    }
}
