package tree;

import datastrucs.TreeNode;

import java.util.*;

/**
 * 序列化、反序列化二叉树：可以使用
 * 前序遍历：第一个节点是跟阶段，然后再构造左子树，然后再构造右子树
 * 后续遍历：最后一个节点是跟节点，因此需要先构造跟节点，然后再构造右子树，在构造左子树
 * 中序遍历：不行，因为跟节点在中间，无法确定跟节点的位置
 * 层序遍历：按层次生成字符串，然后反序列化时，在按层序列化 构造他的左右孩子
 */
public class SerialOrDeserial {

    static String NULL = "#";

    static String SEP = ",";

    /* 主函数，将字符串序列化为二叉树结构 */
    String serialize(TreeNode root) {
        // 将字符串转化成列表
        StringBuffer stringBuffer = new StringBuffer();
        serialize(root, stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * 序列化左右节点
     *
     * @param root
     * @param stringBuffer
     */
    void serialize(TreeNode root, StringBuffer stringBuffer) {
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
        String[] arr = data.split(SEP);
        List<String> queue = new LinkedList<>();
        for (String str : arr) {
            queue.add(str);
        }
        return deserialize(queue);
    }

    /**
     * 使用分解写法
     *
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
        treeNode.left = deserialize(queue);
        treeNode.right = deserialize(queue);

        return treeNode;
    }

    /**
     * 层序遍历 实现二叉树的序列化和反序列化
     *
     * @param root
     * @return
     */
    String serialize2(TreeNode root) {
        // 将字符串转化成列表
        StringBuffer stringBuffer = new StringBuffer();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        //层序遍历生成 序列化
        while (!queue.isEmpty()) {
            TreeNode treeNode = queue.poll();
            if (treeNode == null) {
                stringBuffer.append(NULL).append(SEP);
                continue;
            }
            stringBuffer.append(treeNode.val).append(SEP);
            queue.offer(treeNode.left);
            queue.offer(treeNode.right);
        }


        return stringBuffer.toString();
    }

    TreeNode deserialize2(String data) {
        String[] arr = data.split(SEP);
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode treeNode = new TreeNode();
        treeNode.val = Integer.valueOf(arr[0]);
        queue.offer(treeNode);
        for (int i = 1; i < arr.length; i++) {
            //计算当前数据的左右子节点
            TreeNode parent = queue.poll();
            if (i < arr.length) {
                String left = arr[i++];
                //zuo
                if (left.equals(NULL)) {
                    parent.left = null;
                } else {
                    TreeNode leftNode = new TreeNode();
                    leftNode.val = Integer.valueOf(left);
                    parent.left = leftNode;
                    queue.offer(leftNode);
                }
            }

            if (i < arr.length) {
                String right = arr[i++];
                if (right.equals(NULL)) {
                    parent.right = null;
                } else {
                    TreeNode rightNode = new TreeNode();
                    rightNode.val = Integer.valueOf(right);
                    parent.right = rightNode;
                    queue.offer(rightNode);
                }
            }
        }
        return treeNode;
    }

    Map<String, Integer> treeStrMap = new HashMap<>();
    List<TreeNode> result = new ArrayList<>();

    List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        traverse(root);
        return result;
    }

    /**
     * 使用分解法？：求左右子树的 子字符串
     *
     * @param node
     * @return
     */
    private String traverse(TreeNode node) {
        if (node == null) {
            return "#";
        }

        String left = traverse(node.left);
        String right = traverse(node.right);

        //构建当前根的 子树结构
        String curStr = left + "," + right + "," + node.val;

        int fre = treeStrMap.getOrDefault(curStr, 0);
        if (fre == 1) {
            result.add(node);
        }
        treeStrMap.put(curStr, fre + 1);

        return curStr;
    }
}
