package leetcode;

public class TreeToDoublyList {

    public static void main(String[] args) {
        Node node=new Node(4);
        node.left=new Node(2);
        node.right=new Node(5);
        node.left.left=new Node(1);
        node.left.right=new Node(3);


        Node value= new   TreeToDoublyList().treeToDoublyList(node);
        System.out.println(value);
    }
    Node pre;
    Node root;
    // 使用递归写法
    public Node treeToDoublyList(Node nodel) {
        this.pre=null;
        this.root=null;
         dfs(nodel);
        //返回根节点
        return this.root;
    }

    public Node dfs(Node nodel) {

        //递归写法
        if (nodel == null) {
            return null;
        }
        dfs(nodel.left);

        // 中序位置
        if ( this.root == null) {
            this.root = nodel;
        }
        //前一个节点存在，则构建前后关系
        if (pre != null) {
            pre.right = nodel;
            nodel.left = pre;
        }
        //移动pre指针到当前元素
        pre = nodel;

        dfs(nodel.right);

        //返回根节点
        return this.root;
    }
}

// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};

