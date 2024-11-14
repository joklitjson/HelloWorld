package basestructs;

import java.util.HashMap;
import java.util.Map;

/**
 * LCR 031. LRU 缓存
 * 定义两个子方法：moveToHead、removeNode 在插入、查询中可以复用这些代码
 */
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
            removeNode(node);
            moveToHead(node);
            return node.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        Node node=  map.get(key);
        if (node!=null){
            //移除
            node.value=value;
            removeNode(node);
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
                removeNode(pre);
            }
        }
    }

    private void removeNode(Node node){
        node.pre.next=node.next;
        node.next.pre=node.pre;
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
