package mydatastructs;

import java.util.Arrays;

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
            if (trie[p][u] == 0) return false;
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
        trieArray.insert("abcdf");

        trieArray.insert("wert");

        trieArray.search("df");
    }
}