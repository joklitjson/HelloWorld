package linkedList;

import java.util.List;

public class ReorderList {

    //重排链表
    //1、先找到中间节点，然后再翻转后半部分，然后再合并两个链表
    public void reorderList(ListNode head) {
        ListNode mid=  getMid(head);

        ListNode next= mid.next;
        mid.next=null;//切断关联
        ListNode reverse= reverse(next);
        merge(head,reverse);
    }

    private void merge(ListNode l1,ListNode l2) {
        ListNode l1_tmp;
        ListNode l2_tmp;
        while (l1 != null && l2 != null) {
            l1_tmp = l1.next;
            l2_tmp = l2.next;

            //l1指向l2
            l1.next = l2;
            l1 = l1_tmp;

            //l2指向l1的下一个元素
            l2.next = l1;
            l2 = l2_tmp;
        }
    }
    private ListNode reverse(ListNode node) {
        ListNode pre = null, current = node, next;
        while (current != null) {
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        return pre;
    }

    private ListNode getMid(ListNode head) {

        ListNode fast = head, slow = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }
}
