package basestructs;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    public static void main(String[] args) {
        LRUCache lruCache=new LRUCache(2);
        lruCache.put(1,1);
        lruCache.put(2,2);
        System.out.println(lruCache.get(1));
        lruCache.put(3,3);
        System.out.println(lruCache.get(2));
        lruCache.put(4,4);
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));
    }
    public Integer size;

    Node head;
    Node tail;
    Map<Integer,Node> map=new HashMap<>();

    public LRUCache(int capacity) {
        this.size=capacity;
        head=new Node(-1,-1);

        tail=new Node(-1,-1);

        head.next=tail;
        tail.pre=head;
    }
    
    public int get(int key) {
        Node node=  map.get(key);
        if (node!=null){
            node.pre.next=node.next;
            node.next.pre=node.pre;
            moveToHead(node);
            return node.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        Node node=  map.get(key);
        if (node!=null){
            //移除
            node.pre.next=node.next;
            node.next.pre=node.pre;
            node.value=value;

            moveToHead(node);
        }
        else {
            //插入到头节点
            node = new Node(key, value);
            map.put(key, node);

            moveToHead(node);

            if (map.size() >size) {
                //淘汰末尾元素
                Node pre= tail.pre;
                map.remove(pre.key);
                Node prePre = tail.pre.pre;
                prePre.next = tail;
                tail.pre = prePre;
            }
        }
    }

    private void  moveToHead( Node node){
        //获取next 元素
        Node next = head.next;
        head.next = node;
        node.pre = head;

        next.pre = node;
        node.next = next;
    }

    static  class Node{
        public Integer key;
        public Integer value;

        public Node( Integer key, Integer value){
            this.key=key;
            this.value=value;
        }
        public Node pre;
        public Node next;
    }
}
