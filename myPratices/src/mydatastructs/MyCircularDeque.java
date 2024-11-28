package mydatastructs;

/**
 * 循环双端队列
 */
class MyCircularDeque {

    private  Node head,tail;
    private int size=0,capatity=0;
    public MyCircularDeque(int k) {
        this.capatity=k;
        head=new Node();
        tail=new Node();
        head.next=tail;
        tail.pre=head;
    }
    
    public boolean insertFront(int value) {
       if (isFull()){
           return  false;
       }

       Node node=new Node();
       node.value=value;
       node.pre=head;
       node.next=head.next;
       head.next.pre=node;
       head.next=node;
       size++;
       return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()){
            return false;
        }
        //先建立 新节点指向尾节点、尾的前一个节点，然后在设置另外两个节点对他的指向
        Node node=new Node();
        node.value=value;
        node.next=tail;
        node.pre=tail.pre;
        tail.pre.next=node;
        tail.pre=node;
        size++;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()){
            return false;
        }
       Node next= head.next;
        next.next.pre=head;
        head.next=next.next;
        //释放链接
        next.pre=null;
        next.next=null;
        size--;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()){
            return false;
        }

        Node node= tail.pre;
        node.pre.next=tail;
        tail.pre=node.pre;
        size--;
        return true;
    }
    
    public int getFront() {
        if (isEmpty()){
            return -1;
        }
        return head.next.value;
    }
    
    public int getRear() {
        if (isEmpty()){
            return -1;
        }
        return tail.pre.value;
    }
    
    public boolean isEmpty() {
        return size==0;
    }
    
    public boolean isFull() {
        return size==capatity;
    }


    class Node{
        int value;
         Node next;
        Node  pre;
    }
}

