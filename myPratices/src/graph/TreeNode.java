package graph;

public class TreeNode {

    public int val;
    private int data;

    public  TreeNode left;
    public  TreeNode right;

    public TreeNode(){

    }
    public TreeNode(int data){
        this.data=data;
        this.val=data;
    }
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
