package mydatastructs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 底层用 Trie 树实现的键值映射
// 键为 String 类型，值为类型 V
class TrieMap<V> {

    private static  int R=256;

    int size;
    private TrieNode<V> root;//父节点

    static class TrieNode<V>{
        //节点的值
        public V val;
        //子数组
        public  TrieNode<V> [] child=new TrieNode[R];
    }

    /**
     * 工具数组：查找元素的节点
     * @param node
     * @param key
     * @return
     */
    TrieNode<V> getNode(TrieNode<V> node,String key) {
        TrieNode<V> p = node;

        //查找某个节点
        for (int i = 0; i < key.length(); i++) {
            if (p == null) {
                return null;
            }
            int c = key.charAt(i);
            p = p.child[c];
        }
        return p;
    }
    /***** 增/改 *****/

    // 在 Map 中添加 key
    public void put(String key, V val) {
        root= putHer(root, 0, key, val);
        size++;
    }

    private TrieNode<V>  putHer(TrieNode<V> node, int idx, String key, V val){
        if (node==null){
            node=new TrieNode();
        }
        if (idx==key.length()){
            // key 的路径已插入完成，将值 val 存入节点
            node.val=val;
            return node;
        }
        char c= key.charAt(idx);

        node.child[c]=putHer(node.child[c],idx+1,key,val);
        return node;
    }

    /***** 删 *****/

    // 删除键 key 以及对应的值,如果当前值设置空之后，他的孩子节点也都是null的，则把这个节点也删除
    public void remove(String key){
        //类似二叉树的后续遍历，只有他的子节点不存在值了，才把子节点设置成空
        if (!containsKey(key)){
            return;
        }
        remove(root,0,key);
        size--;
    }

    private TrieNode<V> remove(TrieNode<V> node,int idx,String key) {
        if (node == null) {
            return null;
        }
        if (idx == key.length()) {
            //设置他的值为空
            node.val = null;
        } else {
            //删除他的子节点
            node.child[idx] = remove(node.child[idx + 1], idx + 1, key);
        }
        if (node.val != null) {
            // 如果该 TireNode 存储着 val，不需要被清理
            return node;
        }
        //check下是否子节点都是空
        for (char i = 0; i < R; i++) {
            if (node.child[i] != null) {
                return node;
            }
        }

        // 既没有存储 val，也没有后缀树枝，则该节点需要被清理
        return null;
    }

    /***** 查 *****/

    // 搜索 key 对应的值，不存在则返回 null
    // get("the") -> 4
    // get("tha") -> null
    public V get(String key){

        TrieNode<V> node= getNode(root,key);
        //node要存在，而且他的值也要有
        if (node==null||node.val==null){
            return null;
        }
        return node.val;
    }

    // 判断 key 是否存在在 Map 中
    // containsKey("tea") -> false
    // containsKey("team") -> true
    public boolean containsKey(String key) {
        return get(key) != null;
    }

    // 在 Map 的所有键中搜索 query 的最短前缀
    // shortestPrefixOf("themxyz") -> "the"
    public String shortestPrefixOf(String query) {
        TrieNode<V> p = root;
        for (int i = 0; i < query.length(); i++) {
            if (p == null) {
                return null;
            }
            //遇到有值的就返回
            if (p.val != null) {
                return query.substring(0, i);
            }
            int c = query.charAt(i);
            p = p.child[c];
        }

        //刚好找到了他本身
        if (p!=null&&p.val != null) {
            return query;
        }
        //找到的节点没有值
        return null;
    }

    // 在 Map 的所有键中搜索 query 的最长前缀
    // longestPrefixOf("themxyz") -> "them"
    public String longestPrefixOf(String query){
        TrieNode<V> p=root;
        int maxLenth=0;
        for (int i=0;i<query.length();i++) {
            if (p == null) {
                break;
            }
            //遇到一个字符串,继续向下查找
            if (p.val != null) {
                maxLenth = i;
            }
            int c = query.charAt(i);
            //向下查找
            p = p.child[c];
        }
        //可能是他自己
        if (p!=null&& p.val!=null){
            return query;
        }
        //截取字符串
        return query.substring(0,maxLenth);
    }

    // 搜索所有前缀为 prefix 的键
    // keysWithPrefix("th") -> ["that", "the", "them"]
    public List<String> keysWithPrefix(String prefix) {
        //1、先查找到节点
        // 2在向节点下面使用深度遍历查找 需要使用回溯算法查找
        List<String> res = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        TrieNode<V> node = getNode(root, prefix);
        traver(node, stringBuilder, res);
        return res;
    }

    private void  traver(TrieNode<V>  node,StringBuilder stringBuilder, List<String> res){
        if (node==null){
            return;
        }
        //找到一个
        if (node.val!=null){
            res.add(stringBuilder.toString());
        }
        for (char i=0;i<R;i++) {
            stringBuilder.append(i);
            TrieNode<V> child = node.child[i];
            //这个节点存在值，则向下查找
            if (child != null) {
                traver(child, stringBuilder, res);
            }
            //删除最后一个
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }

    // 判断是和否存在前缀为 prefix 的键
    // hasKeyWithPrefix("tha") -> true
    // hasKeyWithPrefix("apple") -> false
    public boolean hasKeyWithPrefix(String prefix) {
        //1、偷懒的写法：让找一个就行,
//        return !keysWithPrefix(prefix).isEmpty();
        //2、再次定义一个递归函数
        TrieNode<V> node = getNode(root, prefix);
//        return hasKeyPrefixTrave(node);
        return node != null;
    }

    public boolean hasKeyPrefixTrave(TrieNode<V> node){
        if (node==null){
            return false;
        }
        if (node.val!=null){
            return true;
        }

        for (char c=0;c<R;c++){
            boolean result=hasKeyPrefixTrave(node.child[c]);
            //寻找他的子节点 只要存在一个就行
            if (result){
                return true;
            }
        }
        //他的子节点都找了，也没找到
        return false;
    }

    // 通配符 . 匹配任意字符，搜索所有匹配的键
    // keysWithPattern("t.a.") -> ["team", "that"]
    public List<String> keysWithPattern(String pattern) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> res = new ArrayList<>();
        travePatten(root, 0,pattern,stringBuilder, res);
        return res;
    }

    private void travePatten(TrieNode<V> node,int index,String pattern, StringBuilder stringBuilder,List<String> res){
        if (node==null){
            return;
        }
        //长度
        if (index==pattern.length()) {
            if (node.val != null) {
                res.add(stringBuilder.toString());
            }
            return;
        }
        char c=pattern.charAt(index);
        if (c=='.'){
            //.匹配任意值
            for (char t=0;t<R;t++) {
//                node.child[t]
                stringBuilder.append(t);
                travePatten(node.child[t], index + 1, pattern, stringBuilder, res);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
        else {
            stringBuilder.append(c);
            travePatten(node.child[c],index+1,pattern,stringBuilder,res );
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
    }

    // 通配符 . 匹配任意字符，判断是否存在匹配的键
    // hasKeyWithPattern(".ip") -> true
    // hasKeyWithPattern(".i") -> false
    public boolean hasKeyWithPattern(String pattern){
        return hasKeyWithPatternTrave(root,0,pattern);
    }

    private boolean hasKeyWithPatternTrave(TrieNode<V> node, int idx, String pattern) {
        if (node == null) {
            return false;
        }
        //达到长度了
        if (idx == pattern.length() ) {
            return node.val != null;
        }

        char c = pattern.charAt(idx);
        if (c == '.') {
            for (char t = 0; t < R; t++) {
                //子元素 只要找到一个即可
                if (hasKeyWithPatternTrave(node.child[t], idx + 1, pattern)) {
                    return true;
                }
            }
        } else {
            return hasKeyWithPatternTrave(node.child[c], idx + 1, pattern);
        }

        return false;
    }

    // 返回 Map 中键值对的数量
    public int size() {
        return size;
    }

//    public static void main(String[] args) {
//        TrieMap<Integer> map=new TrieMap<>();
//
//        map.put("abc",1);
//        map.put("ab",2);
//        map.put("aap",1);
//        map.put("acp",1);
//
//        System.out.println(map.containsKey("abec"));
//        System.out.println(map.get("ab"));
//        System.out.println("keysWithPrefix=ab==="+Arrays.toString(map.keysWithPrefix("a").toArray()));
//        System.out.println(Arrays.toString(map.keysWithPattern("a.p").toArray()));
//    }
}