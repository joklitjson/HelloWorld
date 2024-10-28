package linkedList;

import datastrucs.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Stack;

public class DeleteDump {

    public static void main(String[] args) {
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        for (int i = 0; i < 6; i++) {
            p.next = new ListNode(i + 1);
            p = p.next;
        }
       ;

        ListNode head= removeNthFromEnd2(dummy.next,1);
        p = head;
        System.out.print("修改后的链表顺序：");
        while (p != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * 删除链表第n个节点
     * @param head
     * @param n
     * @return
     */
    public  static ListNode removeNthFromEnd(ListNode head, int n) {

        //未什么需要创建头节点？因为头节点也可能被删除
        ListNode dummy=new ListNode(-1);
        dummy.next=head;
        ListNode fast=head,slow=dummy;
        int count=0;
        while (fast!=null&&count<n){
            fast=fast.next;
            count++;
        }
        while (fast!=null){
            slow=slow.next;
            fast=fast.next;
        }

        //slow的位置就是要删除链表的前一个节点
        slow.next=slow.next.next;
        return dummy.next;
    }

    //使用栈
    public  static ListNode removeNthFromEnd2(ListNode head, int n) {

        ListNode cur=head;
        Stack<ListNode> stack=new Stack<>();
        while (cur!=null){
            stack.push(cur);
            cur=cur.next;
        }

        int count=n;
        ListNode deleted=null;
        while (!stack.isEmpty()&&count>0){
            deleted= stack.pop();
            count--;
        }
        if (stack.isEmpty()){
            return deleted.next;
        }
        else {
            stack.peek().next = deleted.next;
            return head;
        }

    }

    /**
     * 删除重复元素：只保留一个相同元素
     * 删除重复元素，核心条件是比较当前元素和下一个元素的值，因此循环条件需要写 cur.netxt!=null,需要保障下一个元素非空
     * @param head
     * @return
     */
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
     * 删除重复元素，只保留不重复的元素:内部二次循环需要找到一个和next 元素不同的元素，然后接入到cur后面
     * 说白了 需要站在第一个位置(cur) 比较cur.next 和cur.next.next的值是否相等，然后在循环往后找 和cur.next 值不相同的元素
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
