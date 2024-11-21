import java.util.HashMap;
import java.util.Map;

public class MapSkill {

    /**
     * LCR 169. 招式拆解 IIs
     * 返回第一个 只出现一次的元素
     * 可以使用map<key,Bool> 表示是否包含一个字符的map
     * 优化点：可以使用有序的map：在第二次遍历时 只需要遍历map 就可以了，不需要再次遍历原先的集合
     *
     * 方案二：可以使用 26个字母的数组 记录元素个数
     * @param arr
     * @return
     */
    public char dismantlingAction(String arr) {
        Map<Character, Boolean> mapOne = new HashMap<>();
//        Map<Character, Boolean> mapOne = new LinkedHashMap<>();

        for (char c : arr.toCharArray()) {
            mapOne.put(c, !mapOne.containsKey(c));
        }

        for (char c : arr.toCharArray()) {
            if (mapOne.get(c)) {
                return c;
            }
        }

//        //使用有序的map 可以只需要遍历当前map 就可以了
//        for (Map.Entry<Character, Boolean> entry: mapOne.entrySet()){
//            if (entry.getValue()){
//                return entry.getKey();
//            }
//        }
        return ' ';
    }
}
