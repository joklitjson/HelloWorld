package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combine {

    public static void main(String[] args) {
        new Combine().combine(4,2);
    }
    List<List<Integer>> ans;
    public List<List<Integer>> combine(int n, int k) {
        ans = new ArrayList();
        boolean[] visited = new boolean[n + 1];
        backtrack(n, k, 0,visited, new ArrayList<>());
        for (List<Integer> res : ans) {
            System.out.println(Arrays.toString(res.toArray()));
        }
        return ans;
    }

    public void backtrack(int n, int k, int index, boolean[] visited, List<Integer> path) {

        if (path.size() > k) {
            return;
        }
        if (path.size() == k) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = index+1; i <= n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                path.add(i);
                backtrack(n, k,i, visited, path);
                visited[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}
