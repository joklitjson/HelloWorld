package backtrack;

import java.util.ArrayList;
import java.util.List;

public class Permute {

    public static void main(String[] args) {
        System.out.println(new Permute().permute(new int[]{1,2,2}));
    }
    List<List<Integer>> res;
    List<List<Integer>> permute(int[] nums) {
        res=new ArrayList<>();
        backtrack(nums,new ArrayList<>(),new boolean[nums.length]);
        return res;
    }

    private void backtrack(int[] nums,List<Integer> result,boolean []  visited) {
        if (result.size() == nums.length) {
            res.add(new ArrayList<>(result));
            return;
        }

        //回溯
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            //有重复元素，则跳过
            if (i>0&&nums[i]==nums[i-1]&& !visited[i-1]) {
                continue;
            }
            if (visited[i]) {
                continue;
            }
            result.add(current);
            visited[i]=true;
            backtrack(nums, result,visited);
            result.remove(result.size() - 1);
            visited[i]=false;
        }
    }
}
