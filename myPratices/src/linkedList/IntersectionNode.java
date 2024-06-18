package linkedList;


import org.w3c.dom.NodeList;

public class IntersectionNode {
    public static void main(String[] args) {
        ListNode nodeList1=new ListNode(-1);
        ListNode p1=nodeList1;

        ListNode nodeList2=new ListNode(-1);
        ListNode p2=nodeList2;
        for (int i=0;i<2;i++){
            ListNode node=new ListNode(i);
            p1.next=node;
            p1=node;
        }

        for (int i=3;i<6;i++){
            ListNode node=new ListNode(i);
            p2.next=node;
            p2=node;
        }

        ListNode pp= getIntersectionNode(nodeList1.next,nodeList2.next);
        System.out.println(pp);
    }
    public  static ListNode getIntersectionNode(ListNode nodeList1, ListNode nodeList2){
        {
            ListNode p1 = nodeList1, p2 = nodeList2;

            while (p1 != p2) {
                if (p1 == null) {
                    p1 = nodeList2;
                } else {
                    p1 = p1.next;
                }

                if (p2 == null) {
                    p2 = nodeList1;
                } else {
                    p2 = p2.next;
                }
            }

            return p1;
        }

    }
}
