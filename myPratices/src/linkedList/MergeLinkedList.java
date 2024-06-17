package linkedList;

import java.util.PriorityQueue;

public class MergeLinkedList {

    ListNode mergeLists(ListNode lists1, ListNode lists2) {
        ListNode dummyNode = new ListNode();
        ListNode p1 = lists1, p2 = lists2, p = dummyNode;
        while (p1 != null && p2 != null) {
            if (p1.val > p2.val) {
                p.next = p2;
                p2 = p2.next;
            } else {
                p.next = p1;
                p1 = p1.next;
            }
        }

        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }
        return dummyNode.next;
    }

    ListNode mergeKLists(ListNode[] lists) {

        ListNode dummy = new ListNode();
        ListNode p = dummy;
        //小跟堆
        PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>((a, b) -> a.val - b.val);
        for (ListNode listNode : lists) {
            pq.add(listNode);
        }

        while (!pq.isEmpty()) {
            ListNode t = pq.poll();
            p.next = t;
            p = p.next;
            //再次把他的下一个节点加入进去
            if (t.next != null) {
                pq.offer(t.next);
            }

        }

        return dummy.next;
    }

    ListNode middleNode(ListNode head) {
        //快慢指针
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        //慢指针指向中点
        return slow;
    }

    //快慢指针相交这表示有环
    boolean hasCycle(ListNode head) {
        ListNode fast = head, slow = head;

        while (fast != null&&fast.next != null  ) {
            fast = fast.next.next;
            slow = slow.next;

            //有环的话，肯定会相交的
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    ListNode detectCycle(ListNode head) {


        ListNode fast = head, slow = head;

        while (slow != null&&fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                break;
            }
        }

        //遇到了空指针 说明没有环
        if (fast.next == null || fast == null) {
            return null;
        }

        //再次让slow从头走，再次相遇则是环的起点
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    }
