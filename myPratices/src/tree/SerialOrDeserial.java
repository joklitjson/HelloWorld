package tree;

import datastrucs.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 序列化、反序列化二叉树：可以使用
 * 前序遍历：第一个节点是跟阶段，然后再构造左子树，然后再构造右子树
 * 后续遍历：最后一个节点是跟节点，因此需要先构造跟节点，然后再构造右子树，在构造左子树
 * 中序遍历：不行，因为跟节点在中间，无法确定跟节点的位置
 */
public class SerialOrDeserial {

    static String NULL="#";

    static String SEP=",";

    /* 主函数，将字符串序列化为二叉树结构 */
     String serialize(TreeNode root) {
         // 将字符串转化成列表
         StringBuffer stringBuffer = new StringBuffer();
         serialize(root, stringBuffer);
         return stringBuffer.toString();
     }

    /**
     * 序列化左右节点
     * @param root
     * @param stringBuffer
     */
    void serialize(TreeNode root,StringBuffer stringBuffer) {
        if (root == null) {
            stringBuffer.append(NULL).append(SEP);
            return;
        }
        stringBuffer.append(root.val).append(SEP);
        serialize(root.left, stringBuffer);
        serialize(root.right, stringBuffer);
    }

    /* 主函数，将字符串反序列化为二叉树结构 */
    TreeNode deserialize(String data) {
        String [] arr= data.split(SEP);
        List<String> queue=new LinkedList<>();
        for (String str:arr){
            queue.add(str);
        }
        return deserialize(queue);
    }

    /**
     * 使用分解写法
     * @param queue
     * @return
     */
    TreeNode deserialize(List<String> queue) {
        if (queue.isEmpty()) {
            return null;
        }
        String peek = queue.remove(0);
        if (NULL.equals(peek)) {
            return null;
        }
        TreeNode treeNode = new TreeNode();
        treeNode.setData(Integer.valueOf(peek));
        treeNode.left = deserialize( queue);
        treeNode.right = deserialize(queue);

        return treeNode;
    }
}
