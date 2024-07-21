package mydatastructs;

import java.util.Arrays;

/**
 * 使用数组实现的trie数
 *https://leetcode.cn/problems/implement-trie-prefix-tree/solutions/721110/gong-shui-san-xie-yi-ti-shuang-jie-er-we-esm9/
 * 一个朴素的想法是直接使用「二维数组」来实现 Trie 树。
 *
 * 使用二维数组 trie[] 来存储我们所有的单词字符。
 * 使用 index 来自增记录我们到底用了多少个格子（相当于给被用到格子进行编号）。
 * 使用 count[] 数组记录某个格子被「被标记为结尾的次数」（当 idx 编号的格子被标记了 n 次，则有 cnt[idx]=n）
 *
 */
class TrieArray {
    // 以下 static 成员独一份，被创建的多个 Trie 共用
    static int N = 10; // 直接设置为十万级
    static int[][] trie = new int[N][26];
    static int[] count = new int[N];
    static int index = 0;

    // 在构造方法中完成重置 static 成员数组的操作
    // 这样做的目的是为减少 new 操作（无论有多少测试数据，上述 static 成员只会被 new 一次）
    public TrieArray() {
        for (int row = index; row >= 0; row--) {
            Arrays.fill(trie[row], 0);
        }
        Arrays.fill(count, 0);
        index = 0;
    }
    
    public void insert(String s) {
        int p = 0;
        for (int i = 0; i < s.length(); i++) {
            int u = s.charAt(i) - 'a';
            if (trie[p][u] == 0) {
                trie[p][u] = ++index;
            }
            p = trie[p][u];
        }
        count[p]++;
    }
    
    public boolean search(String s) {
        int p = 0;
        for (int i = 0; i < s.length(); i++) {
            int u = s.charAt(i) - 'a';
            if (trie[p][u] == 0) {
                return false;
            }
            p = trie[p][u];
        }
        return count[p] != 0;
    }
    
    public boolean startsWith(String s) {
        int p = 0;
        for (int i = 0; i < s.length(); i++) {
            int u = s.charAt(i) - 'a';
            if (trie[p][u] == 0) return false;
            p = trie[p][u];
        }
        return true;
    }

    public static void main(String[] args) {
        TrieArray trieArray=new TrieArray();
        trieArray.insert("ab");

        trieArray.insert("abc");

        trieArray.search("df");
    }
}