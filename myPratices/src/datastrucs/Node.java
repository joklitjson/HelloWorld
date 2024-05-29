package datastrucs;

public class Node {

    public int val;
    private int data;

    public Node parent;
    public Node left;
    public Node right;

    public Node(){

    }
    public Node(int data){
        this.data=data;
        this.val=data;
    }
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
