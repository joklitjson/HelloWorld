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
//            向后移动
            p = p.next;
        }

//        直接连接后面的有序的链表
        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }
        return dummyNode.next;
    }

    /**
     * 合并k个有序链表
     * 还可以使用归并合并：https://leetcode.cn/problems/vvXgSW/solutions/1412116/he-bing-pai-xu-lian-biao-by-leetcode-sol-w1zb/
     *
     * @param lists
     * @return
     */
    ListNode mergeKLists(ListNode[] lists) {

        if (lists==null||lists.length==0){
            return null;
        }
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

    //使用归并的思想:自顶向下
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        merge(lists, 0, lists.length - 1);

        return lists[0];
    }

    //使用归并思想：自底向上
    public ListNode mergeKLists3(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }

        int n=lists.length,grap=1;

        //
        for (;grap<n;grap=grap*2){
            // 基本分区合并(随着间隔的成倍增长，一一合并，二二合并，四四合并...)
            for (int l=0;l<n-grap;l+=2*grap){
//                grap=1:[0,1]、【2、3】【4、5】合并
//                grap=2:>>>[0,2],[4,6]合并
                System.out.println("grap="+grap+"; range=["+l+","+(l+grap)+"]");
               lists[l]=merge(lists[l],lists[l+grap]);
           }
        }
        //如果还有剩余 需要再次合并
        return grap<n-1? merge(lists[0],lists[grap]):lists[0];
    }

    /**
     * 自顶向下的合并
     * @param lists
     * @param left
     * @param right
     */
    public void merge(ListNode[] lists,int left,int right) {
        if (left >= right) {
            return;
        }
        int middle = (left + right) / 2;
        merge(lists, left, middle);
        merge(lists, middle + 1, right);

        //合并在一起：
//        因为每次合并都是把两个链表(归并中的两部分的第一个节点)合并，然后在放在左侧链表中，因此这里的右侧节点是 middle+1
        lists[left] = merge(lists[left], lists[middle + 1]);
    }

    //合并思想
    public ListNode merge(ListNode node1, ListNode node2) {
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


        /**
         * 查找链表的中间节点
         *
         * @param head
         * @return
         */
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

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            //有环的话，肯定会相交的
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断链表是否有环：如果slow==fast 表示有环，如果slow=null 或者fast.next==null,表示链表没有环
     * 然后在把慢指针指向头节点，再次让快慢指针一步步的向后移动，相遇就是环的起点
     * @param head
     * @return
     */
    ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (slow != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                break;
            }
        }

        //遇到了空指针 说明没有环
        if (fast == null || fast.next == null) {
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
