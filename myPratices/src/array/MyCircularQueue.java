package array;

/**
 * 处理队满和队空有两种方法
 * 1.少用一个空间元素，即队列空间大小为Maxsize时，有Maxsize-1个元素就认为是队满
 * 2.单独设置一个标识符以便于区别队列是否为空状态。
 */
public class MyCircularQueue {

    private int capacity;
    private int [] element=null;

//    队列头指针
    private int front;

    //队列尾指针
    private int rear;
    public MyCircularQueue(int size){
        this.capacity=size;
        element=new int[size];

        front=0;
        rear=0;
    }

    public boolean enEnque(int value){
        if (isFull()){
            return false;
        }
        element[rear]=value;
        rear=(rear+1)%capacity;

        return true;
    }

    public int dequeue(){
        if (isEmpty()){
            return -1;
        }
        int value= element[front];
        front=(front+1)%capacity;
        return value;
    }

    public boolean isFull(){

//        1.少用一个空间元素，即队列空间大小为Maxsize时，有Maxsize-1个元素就认为是队满
        //尾指针加1 是到队头，
        return (rear+1)%capacity==front;
    }

    public boolean isEmpty(){
        if (front==rear){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        MyCircularQueue myCircularQueue=new MyCircularQueue(3);

        System.out.println("是否插入成功："+myCircularQueue.enEnque(1));
        System.out.println("是否插入成功："+myCircularQueue.enEnque(2));
        System.out.println("是否插入成功："+myCircularQueue.enEnque(3));
        System.out.println("是否插入成功："+myCircularQueue.enEnque(4));
    }

}
