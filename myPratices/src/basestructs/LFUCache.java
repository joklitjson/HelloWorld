package basestructs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

class LFUCache {
    //kv映射
    private Map<Integer, Integer> keyValueMap = new HashMap<>();

    //key到频率的映射
    private Map<Integer, Integer> keyToFreMap = new HashMap<>();
    //频率到key的映射
    Map<Integer, LinkedHashSet<Integer>> freToKeyMap = new LinkedHashMap<>();
    int capacity;
    int minFre = 0;//最小频率的key

    // 构造容量为 capacity 的缓存
    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    // 在缓存中查询 key
    public int get(int key) {
        Integer value = keyValueMap.get(key);
        if (value == null) {
            return -1;
        }
        increaseKeyFre(key);
        return value;
    }

    // 将 key 和 val 存入缓存
    public void put(int key, int val) {


        //如果key已经存在，则修改key对应的频率
        if (keyValueMap.containsKey(key)){
            keyValueMap.put(key,val);
            increaseKeyFre(key);
            return;
        }

        //淘汰频率最低的key集合中的最后一个数据
        if (capacity == keyValueMap.size()) {
            removeMinFreKey();
        }
        keyValueMap.put(key, val);
        keyToFreMap.put(key, 1);
        minFre = 1;
        //插入到当前频率表中
        LinkedHashSet linkedList = freToKeyMap.getOrDefault(minFre, new LinkedHashSet<>());
        linkedList.add(key);
        freToKeyMap.put(minFre, linkedList);
    }


    private void  removeMinFreKey(){
        LinkedHashSet<Integer> minFreKeys = freToKeyMap.get(minFre);
        Integer removedKey = minFreKeys.iterator().next();
        minFreKeys.remove(removedKey);
        //把集合删除
        if (minFreKeys.isEmpty()) {
            freToKeyMap.remove(minFre);
        }

        //更新kv表
        keyValueMap.remove(removedKey);
        keyToFreMap.remove(removedKey);
    }


    /**
     * 增加key的频率
     */
    private void  increaseKeyFre(Integer key){
        int freq= keyToFreMap.get(key);
        LinkedHashSet<Integer> keysOfFre = freToKeyMap.get(freq);
        keysOfFre.remove((Integer) key);
        //删除当前频率对应的key 列表
        if (keysOfFre.isEmpty()) {
            freToKeyMap.remove(freq);
            // 如果这个 freq 恰好是 minFreq，更新 minFreq
            if (freq == this.minFre) {
                this.minFre++;
            }
        }

        //更新频率
        int currentFre =freq + 1;
        keyToFreMap.put(key, currentFre);

        // 插入到新的频率集合中
        LinkedHashSet linkedList = freToKeyMap.getOrDefault(currentFre, new LinkedHashSet<>());
        linkedList.add(key);
        freToKeyMap.put(currentFre, linkedList);
    }

    public static void main(String[] args) {
//[[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]

        LFUCache lfuCache=new LFUCache(2);

        lfuCache.put(1,1);
        lfuCache.put(2,2);
        lfuCache.get(1);
        lfuCache.put(3,3);//移除2
        lfuCache.get(2);

        lfuCache.get(3);//3、1 都是2
        lfuCache.put(4,4);//移除1
        System.out.println(lfuCache.get(1));
    }
}