package tree;

import graph.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DecorateRecord {

    /**
     * LCR 149. 彩灯装饰记录 I
     * 层序遍历二叉树
     * @param root
     * @return
     */
    public int[] decorateRecord(TreeNode root) {

        if (root==null){
            return new int[0];
        }
        List<Integer> ans = new ArrayList<>();

        Queue<TreeNode> queue = new ArrayDeque<>();

        queue.add(root);
        while (!queue.isEmpty()) {

            int count = queue.size();
            while (count > 0) {
                TreeNode node = queue.poll();
                ans.add(node.val);
                count--;
                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }

        int[] result = new int[ans.size()];
        int idx = 0;
        for (Integer value : ans) {
            result[idx++] = value;
        }
        return result;
    }

    /**
     * LCR 150. 彩灯装饰记录 II
     * 层序遍历，每一层作为一个集合
     * @param root
     * @return
     */
    public List<List<Integer>> decorateRecord2(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }


        Queue<TreeNode> queue = new ArrayDeque<>();

        queue.add(root);
        while (!queue.isEmpty()) {

            int count = queue.size();
            List<Integer> currentList = new ArrayList<>();
            while (count > 0) {
                TreeNode node = queue.poll();
                currentList.add(node.val);
                count--;
                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            ans.add(currentList);
        }
        return ans;
    }

    /**
     * LCR 151. 彩灯装饰记录 III
     * 层序遍历，然后是每隔一行方向相反
     * @param root
     * @return
     */
    public List<List<Integer>> decorateRecord3(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        boolean reverse=false;//反序列加入
        while (!queue.isEmpty()) {
            int count = queue.size();
            List<Integer> currentList = new ArrayList<>();
            while (count > 0) {
                TreeNode node = queue.poll();
                if (reverse){
                    //插入头部
                    currentList.add(0,node.val);
                }
                else {
                    currentList.add(node.val);
                }

                count--;
                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            reverse=!reverse;//取反
            ans.add(currentList);
        }
        return ans;
    }

}
