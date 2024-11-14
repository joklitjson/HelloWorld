package linkedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 快慢指针 寻找中间节点
     * @param head
     * @return
     */
    private ListNode getMid(ListNode head) {

        ListNode fast = head, slow = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode l1 = headA, l2 = headB;
        while (l1 != l2) {
            if (l1 != null) {
                l1 = l1.next;
            } else {
                l1 = headB;
            }

            if (l2 != null) {
                l2 = l2.next;
            } else {
                l2 = headA;
            }
        }

        return l1;
    }

    //把所有的节点复制一份，然后存放在map中
    public Node copyRandomList(Node head) {
        HashMap<Node,Node> map=new HashMap<>();
        Node cur=head;
        while (cur!=null) {
            Node copy = new Node(cur.val);
            map.put(cur, copy);
            cur = cur.next;
        }

        Node copyHead = new Node(-1);
         cur=head;
        Node copyHeadCur=copyHead;
        while (cur!=null) {
            Node copy = map.get(cur);
            copyHeadCur.next=copy;
            if (cur.random!=null){
                Node copyRandom = map.get(cur.random);
                copy.random=copyRandom;
            }
            cur = cur.next;
            copyHeadCur=copyHeadCur.next;
        }
        return copyHead.next;
    }

    /**
     * 1、在每个节点中间插入复制的节点：
     * 2、再次遍历老节点，如果有随机节点，在设置该节点的复制节点的随机节点
     * 3、翻个链表，返回复制的节点
     * @param head
     * @return
     */
    public Node copyRandomList2(Node head) {
        Node cur=head,next=null;
        while (cur!=null){
            next=cur.next;
            //copy的节点加入到当前节点和下一个节点之间
            Node copy = new Node(cur.val);
            cur.next=copy;
            copy.next=next;
            cur=next;

        }

        // 2. 构建各新节点的 random 指向
        cur = head;
        while(cur != null) {
            if(cur.random != null)
                cur.next.random = cur.random.next;
            cur = cur.next.next;
        }

        //原->coy->原
        Node copyHead=new Node(-1);
        Node point=copyHead;
        cur=head;
//        3、分割链表
        while (cur!=null){
            Node  orgNext=  cur.next.next;//原链表的next节点
            Node copy=  cur.next;
            point.next=copy;
            copy.next=null;
            point=copy;
//            cur.next=orgNext;//回复原链表
            cur=orgNext;
        }
        return copyHead.next;
    }

    /**
     *  分割链表：给定一个链表和一个值，以该值为界把链表分割成两部分且原始相对位置不变。
     *
     * 方案一：创建两个队列
     * 方案二：头插法：把小于x的值直接插入到头部，(jdk hashmap 扩容就是采用的此方法，但是有死循环问题，目前1.8已经修改)
     * 方案三：递归排序，小于的都排在前面，大于的都排在后面
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode larget = new ListNode(-1), small = new ListNode(-1);
        ListNode cur = head, next = null;
        ListNode p1 = larget, p2 = small;
        while (cur != null) {
//            next=cur.next;
            if (cur.val >= x) {
                p1.next = cur;
                p1 = p1.next;
            } else {
                p2.next = cur;
                p2 = p2.next;
            }
            cur = cur.next;
        }
        p1.next = null;
        p2.next = larget.next;
        return small.next;
    }

    //头插法：相对位置发生了改变
    //可以采用尾插法：元素的相对位置可以保持不变
    public ListNode partition2(ListNode head, int x) {
        ListNode dummy=new ListNode(-1);
        ListNode cur=dummy;

        while (head!=null&&head.next!=null){
            if (head.next.val<x){
                ListNode next= head.next;
                //插入dummy节点之后
                next.next=dummy.next;
                dummy.next=next;
                //调到下一个的下一个节点
                head=head.next.next;
            }
            else {
                head=head.next;
            }
        }
        return dummy.next;
    }

    //添加一个头指针，方便判断第一个指针是否是要被删除的元素
    public ListNode deleteNode(ListNode head, int val) {
        ListNode dumy=new ListNode(-1);
        dumy.next=head;
        ListNode cur=dumy;
        while (cur!=null&&cur.next!=null){
            if (val==cur.next.val){
                cur.next=cur.next.next;
            }
            else {
                cur=cur.next;
            }
        }

        return dumy.next;
    }

    public ListNode detectCycle(ListNode head) {
        ListNode fast=head,slow=head;

        //双指针
        while (fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;

            if (fast == slow) {
                break;
            }
        }
        //没有环，fast到了尽头
        if (fast==null||fast.next==null){
            return null;
        }

        //让slow或fast从头开始走，他俩再次 相遇则在交点处
        slow=head;
        while (fast!=slow){
            fast=fast.next;
            slow=slow.next;
        }
        return fast;
    }

    /**
     * 1、双指针算法：定义奇数指针(因为奇数在前) 把遇到的奇数插入前面，然后一个游标一直向后一定
     * 2、分离奇偶链表，然后在把他俩合并在一起
     * @param head
     * @return
     */
    public static ListNode oddEvenList(ListNode head) {

        if(head==null){
            return null;
        }
        //奇数指针
        ListNode odd=head,cur=head;
        ListNode oddhead=odd;
        int i=2;
        while (cur.next!=null){
            if (i%2==1){
                ListNode next=   cur.next;
                cur.next=next.next;
                //把next 插入到odd后面
                next.next=oddhead.next;
                oddhead.next=next;
                oddhead=oddhead.next;
            }
            else {
                //向后移动指针
                cur=cur.next;
            }
            i++;
        }
        return odd;
    }

    public static void main(String[] args) {
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        for (int i = 0; i < 11; i++) {
            p.next = new ListNode(i + 1);
            p = p.next;
        }

        oddEvenList(dummy.next);
    }
}

class Node {
    int val;
    Node next;
    Node random;
    public Node prev;
    public Node child;
    public Node() {}
    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
