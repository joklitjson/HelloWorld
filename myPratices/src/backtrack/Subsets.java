package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsets {

    public static void main(String[] args) {
//        subsets(new int[]{1,2,3});
//        for (List<Integer> element:res){
//            System.out.println(Arrays.toString(element.toArray()));
//        }

//        combine(3,2);
//        for (List<Integer> element:res){
//            System.out.println(Arrays.toString(element.toArray()));
//        }
        combinationSum2(new int[]{10,1,2,7,6,1,5},8);

        for (List<Integer> element:res){
            System.out.println(Arrays.toString(element.toArray()));
        }
    }
   static List<List<Integer>> res=new ArrayList<>();
    // 求子集问题
    public static List<List<Integer>> subsets(int[] nums){

        backtrack(nums,0,new ArrayList<>());
        return res;
    }

    private static void backtrack(int[] nums,int start,List<Integer> trace){
        res.add(new ArrayList<>(trace));

        for (int i=start;i<nums.length;i++){
            // 剪枝逻辑，值相同的相邻树枝，只遍历第一条
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            //选择
            trace.add(nums[i]);
            // 通过 start 参数控制树枝的遍历，避免产生重复的子集
            backtrack(nums,i+1,trace);
            //撤销
            trace.remove(trace.size()-1);
        }
    }

    //组合问题：选择k个数字
   static List<List<Integer>> combine(int n, int k){
        backtrackCombine(n,1,k,new ArrayList<>());

        return res;
    }

    //在子集的基础上进行判断路径的长度是否等于k即可
    private static void backtrackCombine(int n,int start,int k,List<Integer> trace){
        //
        if (trace.size()==k){
            res.add(new ArrayList<>(trace));
            return;
        }

        for (int i=start;i<=n;i++) {

            trace.add(i);

            backtrackCombine(n, i + 1, k, trace);

            trace.remove(trace.size() - 1);
        }
    }

    /**
     * 目标和为target的组合，可能存在相同元素
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {

        Arrays.sort(candidates);
        combinationSum2Backtrace(candidates,target,0,0,new ArrayList<>());
        return res;
    }

    private static void combinationSum2Backtrace(int[] candidates, int target,int start,int traceSum,List<Integer> trace){
        if (traceSum==target){
            res.add(new ArrayList<>(trace));
        }

        for (int i=start;i<candidates.length;i++){
            //相同元素 则跳过
            if (i>start&&candidates[i]==candidates[i-1]){
                continue;
            }
            if (candidates[i]+traceSum>target){
                continue;
            }
            trace.add(candidates[i]);
            traceSum+=candidates[i];

            combinationSum2Backtrace(candidates,target,i+1,traceSum,trace);

            trace.remove(trace.size()-1);
            traceSum-=candidates[i];

        }
    }
    }
