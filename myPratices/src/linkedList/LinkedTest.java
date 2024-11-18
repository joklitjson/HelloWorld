package linkedList;

import java.util.*;

public class LinkedTest {

    public int[] reverseBookList(ListNode head) {
        List<Integer> list=new ArrayList<>();
        dfs(head,list);
        int [] result=new int[list.size()];

        for (int i=0;i<list.size();i++){
            result[i]=list.get(0);
        }
        return result;
    }

    private void  dfs(ListNode head, List<Integer> list){
        if (head==null){
            return;
        }
        dfs(head.next,list);

        list.add(head.val);
    }


    /**
     * LCR 028. 扁平化多级双向链表
     * 方案一：使用栈 设置两个指针  pre、current 指针，如果有child节点，则把next此节点入队列，先遍历child 节点
     * 方案二：一遇到child，我们就立即处理当前child层，将其塞进上一级的链表，不用使用辅助栈，
     * @param head
     * @return
     */
    public Node flatten(Node head) {
        Stack<Node> nodeDeque=new Stack<>();
        Node pre=null,curr=head;
        while (curr!=null||!nodeDeque.isEmpty()){
            if (curr==null){
                curr=nodeDeque.pop();
            }
            if (curr.child!=null){
                if (curr.next!=null){
                    nodeDeque.push(curr.next);
                }

                //连接 curr和child 节点
                curr.next=curr.child;
                curr.child.prev=curr;
                curr.child=null;
            }
            //用于连接 之前放入队列的next 节点
            if (pre!=null){
                pre.next=curr;
                curr.prev=pre;
            }
            pre=curr;
            curr=curr.next;
        }
        return head;
    }

    public Node flatten2(Node head) {
        return null;
    }

    public Node insert(Node head, int insertVal) {
        Node  node=new Node();
        node.val=insertVal;
        if (head==null){
            return node;
        }
        if (head.next==head){
            node.next=head.next;
            head.next=node;
            return head;
        }

        Node cur=head;
        while (cur!=null){
            //递增趋势
            if (cur.val<=cur.next.val&& insertVal>=cur.val&&insertVal<=cur.next.val){

                node.next=cur.next;
                cur.next=node;
                break;
            }
            //递减趋势
            if (cur.val>cur.next.val){
                if ( insertVal<=cur.next.val||insertVal>cur.val){
                    node.next=cur.next;
                    cur.next=node;
                    break;
                }

            }
            cur=cur.next;
        }
        return head;
    }

    public  static Node insert2(Node head, int insertVal) {
        Node node = new Node(insertVal);
        if (head == null) {
            node.next = node;
            return node;
        }
        if (head.next == head) {
            head.next = node;
            node.next = head;
            return head;
        }
        Node curr = head, next = head.next;
        while (next != head) {
            if (insertVal >= curr.val && insertVal <= next.val) {
                break;
            }
            if (curr.val > next.val) {
                if (insertVal > curr.val || insertVal < next.val) {
                    break;
                }
            }
            curr = curr.next;
            next = next.next;
        }
        curr.next = node;
        node.next = next;
        return head;
    }

    public static void main(String[] args) {
        Node head1=new Node(3);

        Node head2=new Node(3);

        Node head3=new Node(3);

        head1.next=head2;
        head2.next=head3;
        head3.next=head1;

        Node hh=  insert2(head1,0);
        System.out.println(hh);
    }


    /**
     * LCR 142. 训练计划 IV
     *合并有序链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode trainningPlan(ListNode l1, ListNode l2) {

        ListNode dummyHeader = new ListNode(-1);
        ListNode current = dummyHeader;
        ListNode p1 = l1, p2 = l2;
        while (p1 != null && p2 != null) {

            if (p1.val < p2.val) {
                current.next = p1;
                p1 = p1.next;
            } else {
                current.next = p2;
                p2 = p2.next;
            }
            current=current.next;
        }
        if (p1 != null) {
            current.next = p1;
        }
        if (p2 != null) {
            current.next = p2;
        }
        return dummyHeader.next;
    }

}
