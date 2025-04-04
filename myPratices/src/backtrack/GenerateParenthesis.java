package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateParenthesis {

    public static void main(String[] args) {
        List<String> tm=  new GenerateParenthesis().generateParenthesis(1);
        for (String str:tm){
            System.out.println(str);
        }
//        System.out.println(Arrays.toString(tm.toArray()));
    }
    List<String> result = new ArrayList<>();

    /**
     * 括号生成：
     * 可以抽象成，在2n个位置 存放 （、）,每个位置可以在这两者之间进行选择
     * 但是有条件：因为是从左往右放括号，因此，如果左括号(的剩余数量多余右括号的剩余数量，则剪枝
     * 括号有一方提前使用完则也进行剪枝
     *
     * @param n
     * @return
     */
    List<String> generateParenthesis(int n) {
        backtrack(n,n,new StringBuilder());
        return result;
    }

    private void backtrack(int leftRemaining, int rightRemaining, StringBuilder path) {
        //left 剩余数量多余right
        if (leftRemaining > rightRemaining) {
            return;
        }
        if (leftRemaining < 0 || rightRemaining < 0) {
            return;
        }
        if (leftRemaining == 0 && rightRemaining == 0) {
            result.add(path.toString());
            return;
        }

        //进行回溯
        path.append("(");
        backtrack(leftRemaining - 1, rightRemaining, path);
        path.deleteCharAt(path.length()-1);

        //进行回溯
        path.append(")");
        backtrack(leftRemaining, rightRemaining-1, path);
        path.deleteCharAt(path.length()-1);
    }

}
