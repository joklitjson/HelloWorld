package mydatastructs;

public class WordDictionary {

    TrieSet trieSet=new TrieSet();
    public WordDictionary() {

    }

    public void addWord(String word) {
        trieSet.add(word);
    }

    public boolean search(String word) {
        return trieSet.hasKeyWithPattern(word);
    }
}
