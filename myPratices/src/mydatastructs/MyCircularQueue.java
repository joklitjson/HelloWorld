package mydatastructs;

/**
 * 使用链表实现循环队列
 */
class MyCircularQueue {

    private Node head,tail;
    int capatity;
    private int size=0;
    public MyCircularQueue(int k) {
        this.capatity=k;
    }
    
    public boolean enQueue(int value) {
        if (isFull()){
            return false;
        }
        Node node=new Node();
        node.value=value;
        if (head==null){
            //头尾节点都指向当前节点
            head=node;
            tail=node;
        }
        else {
            tail.next=node;
            tail=tail.next;
        }
        size++;
        return true;
    }

    /**
     * 出队列
     * @return
     */
    public boolean deQueue() {
        if (isEmpty()){
            return false;
        }
        head=head.next;
        size--;
        return true;
    }
    
    public int Front() {
        if (isEmpty()){
            return -1;
        }
        return head.value;
    }
    
    public int Rear() {
        if (isEmpty()){
            return -1;
        }
        return tail.value;
    }
    
    public boolean isEmpty() {
        return size==0;
    }
    
    public boolean isFull() {
        return size==capatity;
    }
}

class Node{
    int value;
    Node next;
}
