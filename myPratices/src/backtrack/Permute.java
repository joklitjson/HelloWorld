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
}
