class Trie {

    public static void main(String[] args) {
        Trie trie=new Trie();
        trie.insert("abcd");
        System.out.println(trie.search("abcd"));
        System.out.println(trie.startsWith("d"));
    }
    private TrieNode root=null;
    /** Initialize your data structure here. */
    public Trie() {
        root=new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode tmp=root;
        for (int i=0;i<word.length();i++){
            int idx=word.charAt(i)-'0';
            if (tmp.childs[idx]==null){
                tmp.childs[idx]=new TrieNode();
            }
            tmp=tmp.childs[idx];
        }
        tmp.end=true;
        tmp.value=word;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode tmp = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - '0';
            if (tmp.childs[idx] == null) {
                return false;
            }
            tmp = tmp.childs[idx];
        }
        return tmp.end;//或者判断tmp是否有值
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode tmp = root;
        for (int i = 0; i < prefix.length(); i++) {
            int idx = prefix.charAt(i) - '0';
            if (tmp.childs[idx] == null) {
                return false;
            }
            tmp = tmp.childs[idx];
        }
        return tmp != null;
    }

    static class TrieNode{

        public boolean end;
        public String value;
        public TrieNode[] childs;
         public TrieNode(){
            childs=new TrieNode[256];
         }
    }
}