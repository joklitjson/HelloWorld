package linkedList;

import datastrucs.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteDump {

    /* 删除*/
    ListNode deleteDuplicates(ListNode head) {
        if (head==null){
            return null;
        }

        ListNode cur = head;
        while (cur.next != null) {
            //把next 移除
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    /**
     * 删除重复元素，只保留不重复的元素
     * 由于头节点可能被删除，因此需要创建一个头节点
     * 比较节点的下一个节点和下下一个节点：然后删除下一个节点
     * @param head
     * @return
     */
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) {
                    //删除next节点
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }

        return dummy.next;
    }


    List<Integer> list=new ArrayList<>();
    /**
     * 1、深度优先、或广度优先 +hash表，看看k-i是否在hash中
     * 2、中序遍历 成List+双指针
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget(TreeNode root, int k) {
        list.clear();
        if (root==null){
            return false;
        }
        middleOrder(root);
        System.out.println(Arrays.toString(list.toArray()));
        int left = 0, right = list.size() - 1;
        if (k<list.get(0)||k>list.get(right)){
            return false;
        }
        while (left < right) {
            int sum = list.get(left) + list.get(right);
            if (sum == k) {
                return true;
            } else if (sum > k) {
                right--;
            } else {
                left++;
            }
        }

        return false;
    }

    private void middleOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        middleOrder(root.left);
        list.add(root.val);
        middleOrder(root.right);
    }
}
