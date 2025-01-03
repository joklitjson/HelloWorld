package mydatastructs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

public class Trie {

    TrieMap<Integer> trieMap=new TrieMap<>();
    //插入 word 并记录插入次数
    public void insert(String word) {
        Integer fre = trieMap.get(word);
        if (fre == null) {
            fre = 0;
        }
        //评率加一
        trieMap.put(word, fre + 1);
    }

    // 查询 word 插入的次数
    public int countWordsEqualTo(String word) {
        return trieMap.get(word);
    }
    // 累加前缀为 prefix 的键的插入次数总和
    public int countWordsStartingWith(String prefix) {

        return trieMap.keysWithPrefix(prefix).size();
    }

    // word 的插入次数减一
    public void erase(String word) {
        Integer fre = trieMap.get(word);
        if (fre==1){
            trieMap.remove(word);
        }
        else {
            trieMap.put(word,--fre);
        }
    }

    ///677 kv映射，返回对应的所有前缀key的value和
    public void insert(String key, int val) {
        //直接覆盖
        trieMap.put(key,val);
    }

    public int sum(String prefix) {
       List<String> keys= trieMap.keysWithPrefix(prefix);
       int sum=0;
       for (String key:keys){
           sum+=trieMap.get(key);
       }
       return sum;
    }
    public static void main(String[] args) {
//        ["cat","bat","rat"], sentence =
        List<String> dictionary= Arrays.asList("cat","bat","rat");
        System.out.println(new Trie().replaceWords(dictionary,"the cattle was rattled by the battery"));

        System.out.println(new Trie().replaceWords2(dictionary,"the cattle was rattled by the battery"));
    }
    /**
     * 使用trie树
     * @param dictionary
     * @param sentence
     * @return
     */
    public String replaceWords(List<String> dictionary, String sentence) {

        TrieSet  trieSet=new TrieSet();
        for (String str:dictionary){
            trieSet.add(str);
        }
        String words[]=sentence.split(" ");

        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<words.length;i++){
            String word=words[i];
            String prefix= trieSet.shortestPrefixOf(word);
            //找到了最短前缀
            if (prefix!=null){
                stringBuilder.append(prefix);
            }
            else {
                stringBuilder.append(word);
            }
            if (i!=words.length-1){
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public String replaceWords2(List<String> dictionary, String sentence) {

        String[] words = sentence.split(" ");

        //先进行词根排序
        dictionary.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        //遍历单词，然后在遍历 词根
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (String sub : dictionary) {
                if (word.startsWith(sub)) {
                    words[i] = sub;
                    break;
                }
            }
        }

        return String.join(" ", words);
    }
}
