package linkedList;

import java.util.Random;

//使用快慢指针，找到中间节点，然后在把左右两部分进行排序，在合并
public class SortList {

    public static void main(String[] args) {

        ListNode dummy=new ListNode(-1);
        ListNode cus=dummy;
        Random random=new Random(100);
        for (int i=0;i<10;i++){
            cus.next=new ListNode(random.nextInt(100));

            System.out.print(cus.next.val+" ");
            cus=cus.next;
        }
        System.out.println();
        ListNode sorted= sortList(dummy.next);
        cus=sorted;
        while (cus!=null){
            System.out.print(cus.val+" ");
            cus=cus.next;
        }
        System.out.println();

    }

    public static ListNode sortList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNode middle = splitList(head);
        ListNode left = sortList(head);
        ListNode right = sortList(middle);

        return merge(left, right);
    }

    //找到链表的中点
    private static ListNode splitList(ListNode head) {
        // 为链表增加一个虚拟节点
        // 当 fast 达到尾端时，slow 刚好指向前半段链表的最后一个节点
        ListNode virtualHead = new ListNode(-1);
        virtualHead.next=head;
        ListNode fast = virtualHead, slow = virtualHead;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode result = slow.next;
        slow.next = null;
        return result;
    }


    //合并思想
    public static ListNode merge(ListNode node1, ListNode node2) {
        ListNode dummy=new ListNode(-1);
        ListNode cur=dummy;

        while (node1!=null&&node2!=null){
            if (node1.val<node2.val){
                cur.next=node1;
                node1=node1.next;
            }
            else {
                cur.next=node2;
                node2=node2.next;
            }
            //移动游标
            cur=cur.next;
        }

        if (node1!=null){
            cur.next=node1;
        }

        if (node2!=null){
            cur.next=node2;
        }
        return dummy.next;
    }

}
