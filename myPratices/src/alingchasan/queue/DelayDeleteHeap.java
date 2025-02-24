package alingchasan.queue;

import java.util.*;

/**
 * 懒惰删除堆(其实就是延迟删除)：未来获取数据流中的最值，我们使用了堆，但是我们有可能还会更改、删除堆中元素，如果直接删除的话 效率比较低，因此我们使用
 * 另一个集合(通常是map)：存放元素当前的最新值，在获取堆的最值时，在跟 delayHeap中的元素进行比较，看看当前元素的值是否是最新的，如果不是则丢弃,继续获取最值
 *
 */
public class DelayDeleteHeap {

    /**
     * 2349. 设计数字容器系统
     * 使用有序集合+保持正确值(延迟更新、删除)
     */
    class NumberContainers {

        //下标对应的 真实分数
        Map<Integer,Integer> indexValueMap=new HashMap<>();

        //分数对应的 所有下标：是有序的
        Map<Integer, TreeSet<Integer>> numberToIndexMap=new HashMap<>();

        public NumberContainers() {

        }

        public void change(int index, int number) {
            indexValueMap.put(index,number);

            if (!numberToIndexMap.containsKey(number)){
                numberToIndexMap.put(number,new TreeSet<Integer>());
            }

            //加入到对应的里面
            numberToIndexMap.get(number).add(index);
        }


        /**
         * 使用延迟删除
         * @param number
         * @return
         */
        public int find(int number) {
            if (!numberToIndexMap.containsKey(number)) {
                return -1;
            }
            TreeSet<Integer> treeSet = numberToIndexMap.get(number);


            //看看第一个下标对应的值 是否和 number 相等
            while (!treeSet.isEmpty() && indexValueMap.get(treeSet.first()) != number) {
                treeSet.pollFirst();
            }
            return treeSet.size() == 0 ? -1 : treeSet.first();
        }
    }


}
