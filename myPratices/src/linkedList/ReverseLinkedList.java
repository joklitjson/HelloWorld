package linkedList;

public class ReverseLinkedList {


    /**
     * 递归法反转链表：
     *
     * @param head
     * @return
     */
    ListNode reverse(ListNode head) {
        if (head.next == null) {
            //如果只有一个元素，则不需要进行翻转了
            return head;
        }
        ListNode last = reverse(head.next);
        head.next.next = head;//让head的下一个元素指向head(先成环，在切断)
        head.next = null;//把header的下一个元素设置成空
//        last 一直是原链表的最后一个元素
        return last;
    }

    //反转链表前n个节点
    ListNode successor=null;//第n个节点的后记节点（用于连接后续的节点）
    ListNode reverseN(ListNode head, int n) {
        //找到了第N个节点
        if (n==1){
            //只有一个节点了，所以不在尽心翻转了
            successor=head.next;
            return head;
        }
        ListNode last=reverseN(head.next,n-1);
        head.next.next=head;
        head.next=successor;//让他的后续节点只想后面的节点，把链表串起来
        return last;
    }



    //翻转链表的m~n个节点
    ListNode reverseBetween(ListNode head, int m, int n) {
        //找到了第M个节点，然后从M节点往后翻转前n个元素既可
        if (m == 1) {
//            base-case
            return reverseN(head, n);
        }
        //此方法就是为了递归到m节点
        head.next= reverseBetween(head.next, m - 1, n - 1);
        return head;
    }

    ListNode reverseBetween2(ListNode head, int m, int n) {

        int start=m;
        ListNode pre=null,current=head;
        //1、先找到第m个节点，以及他的前驱节点，在向后翻转
        while (start!=1){
            pre=current;
            current=current.next;
            start--;
        }
        ListNode reversed=   reverseN(current,n-m+1);
        pre.next=reversed;
        return head;
    }


    //使用迭代法翻转链表
    ListNode reverseIterator(ListNode head) {
        // pre current next
        ListNode pre=null,curr=head,next=null;
        while (curr!=null){
            next=curr.next;
            curr.next=pre;//指向前一个元素，起到翻转的效果
            pre=curr;// 指针向后移动
            curr=next;//指针向后移动
        }
//        返回反转后的头节点
        return pre;
    }

    //翻转headd到end区间的元素[head,end)包含前者，不包含后者
    ListNode reverse(ListNode head,ListNode end) {
        // pre current next
        ListNode pre=null,curr=head,next=null;
        while (curr!=end){
            next=curr.next;
            curr.next=pre;//指向前一个元素，起到翻转的效果
            pre=curr;// 指针向后移动
            curr=next;//指针向后移动
        }
//        返回反转后的头节点
        return pre;
    }
    // 其实就是k个元素一翻转，然后在前后连接起来
    ListNode reverseKGroup(ListNode head, int k) {

        ListNode a=head,b=head;

        for (int i=0;i<k;i++){
            if (b==null){
                //不足k个元素，直接返回
               return a;
            }
            b=b.next;
        }
        //翻转[a,b)之前的元素
        ListNode newHead= reverse(a,b);

        //在翻转接下来的袁术,接在a节点的后面
        a.next= reverseKGroup(b,k);
        return newHead;
    }

    ListNode left=null;
    boolean res=true;

    //回文链表
    boolean isPalindrome(ListNode head) {
        left=head;
        traver(head);
        return res;
    }

    private boolean traver(ListNode right) {
        if (right == null) {
            return true;
        }
        traver(right.next);
        res = res && right.val == left.val;
        left = left.next;
        return res;
    }

//    双指针法
    boolean isPalindrome2(ListNode head) {

        ListNode fast=head,slow=head;
        while (fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }

        //奇数个节点
        if (fast!=null){
            slow=slow.next;//向后一定一位
        }
        //然后在翻转slow后的节点
        ListNode right= reverseIterator(slow);
        ListNode left=head;
        while (right!=null){//此时必须是right!=null。因为right则的长度较短，中间元素在left那边
            if (left.val!=right.val){
                return false;
            }
            left=left.next;
            right=right.next;
        }
        //此时还可以再把right链表翻转回来
        return true;
    }
        public static void main(String[] args) {
            ListNode dummy = new ListNode(-1);
            ListNode p = dummy;
            for (int i = 0; i < 11; i++) {
                p.next = new ListNode(i + 1);
                p = p.next;
            }

            ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
//        ListNode head=reverseLinkedList.reverse(dummy.next);
//        ListNode head=reverseLinkedList.reverseN(dummy.next,3);
//            ListNode head = reverseLinkedList.reverseBetween(dummy.next, 2, 4);
            ListNode head = reverseLinkedList.reverseBetween2(dummy.next, 2, 5);
//            ListNode head = reverseLinkedList.reverseKGroup(dummy.next, 3);
            p = head;
            System.out.print("反转后的链表顺序：");
            while (p != null) {
                System.out.print(p.val + " ");
                p = p.next;
            }
            System.out.println();
        }
}
