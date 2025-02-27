package alingchasan.queue;

import javafx.util.Pair;

import java.util.*;

/**
 * 懒惰删除堆(其实就是延迟删除)：未来获取数据流中的最值，我们使用了堆，但是我们有可能还会更改、删除堆中元素，如果直接删除的话 效率比较低，因此我们使用
 * 另一个集合(通常是map)：存放元素当前的最新值，在获取堆的最值时，在跟 delayHeap中的元素进行比较，看看当前元素的值是否是最新的，如果不是则丢弃,继续获取最值
 *
 * 在某种情况下 可以使用有序集合 TreeMap代替 PrioryQueue
 *
 */
public class DelayDeleteHeap {

    public static void main(String[] args) {
        DelayDeleteHeap delayDeleteHeap=new DelayDeleteHeap();

        delayDeleteHeap.mostFrequentIDs(new int[]{7,7},new int[]{3,5});

        Map<Integer,Integer> mm=new HashMap<>();

    }
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


        /**
         * 2353. 设计食物评分系统
         * 按烹饪方式进行分组，分组内在按评分进行排序(使用有序集合),然后在使用一个Map 记录每种是食物的当前评分（用于准确顶计算）
         * 1、使用map<name,TreeMap> 方式分组，修改的时候在更新里面的内容
         * 2、使用map、+优先级队列的方式，记录分组内的评分
         */
        class FoodRatings {


            //rate|cuiness
           Map<String,String[]> foodsMap =new HashMap<>();

            private Map<String,TreeSet<String[]>> fooddsCuisineMap=new HashMap<>();

            public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
                for (int i=0;i<foods.length;i++){
                    if (!fooddsCuisineMap.containsKey(cuisines[i])){
                        //1、自定义排序方式
                        fooddsCuisineMap.put(cuisines[i],new  TreeSet<String[]>(new Comparator<String[]>() {
                            //按评分降序、名称升序排列
                            @Override
                            public int compare(String[] o1, String[] o2) {
                                int rate0=Integer.valueOf(o1[0]);
                                int rate1=Integer.valueOf(o2[0]);
                                if (rate0!=rate1){
                                    return rate1-rate0;
                                }
                                //按自然排序
                                return o1[1].compareTo(o2[1]);
                            }
                        }));
                    }


                    //评分、名字
                    fooddsCuisineMap.get(cuisines[i]).add(new String[]{ratings[i]+"",foods[i]});

                    //评分、类型
                    foodsMap.put(foods[i],new String[]{ratings[i]+"",cuisines[i]});
                }
            }

            /**
             * 直接进行更新
             * @param food
             * @param newRating
             */
            public void changeRating(String food, int newRating) {
               String [] pair= foodsMap.get(food);
               if (pair==null){
                   return;
               }


                fooddsCuisineMap.get(pair[1]).remove(new String[]{pair[0],food});
                fooddsCuisineMap.get(pair[1]).add(new String[]{newRating+"",food});

                pair[0]=newRating+"";

                //添加

                foodsMap.put(food,pair);
            }

            // 返回指定烹饪方式 cuisine 下评分最高的食物的名字。如果存在并列，返回 字典序较小 的名字。
            public String highestRated(String cuisine) {
                //获取最高的评分
                return fooddsCuisineMap.get(cuisine).first()[1];
            }
        }

        /**
         * 使用惰性堆删除
         */
        class FoodRatings2 {
            //rate|cuiness
            Map<String,Pair<Integer,String>> foodsMap =new HashMap<>();

            private Map<String,PriorityQueue<Pair<Integer,String>>> fooddsCuisineMap=new HashMap<>();

            public FoodRatings2(String[] foods, String[] cuisines, int[] ratings) {
                for (int i=0;i<foods.length;i++){
                    if (!fooddsCuisineMap.containsKey(cuisines[i])){
                        //1、自定义排序方式
                        fooddsCuisineMap.put(cuisines[i],new PriorityQueue<Pair<Integer,String>>(new Comparator<Pair<Integer, String>>() {
                            @Override
                            public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                                //评分降序排列，名字升序排列
                                if (Objects.equals(o1.getKey(),o2.getKey())){
                                    return o2.getKey()-o1.getKey();
                                }
                                return o1.getValue().compareTo(o2.getValue());
                            }
                        }));
                    }
                    //评分、名字
                    fooddsCuisineMap.get(cuisines[i]).add(new Pair<>(ratings[i],foods[i]));

                    //评分、类型
                    foodsMap.put(foods[i],new Pair<>(ratings[i],cuisines[i]));
                }
            }

            /**
             * 直接进行更新
             * @param food
             * @param newRating
             */
            public void changeRating(String food, int newRating) {
                Pair<Integer,String>  pair= foodsMap.get(food);
                if (pair==null){
                    return;
                }

                //记录最新的值
                foodsMap.put(food,new Pair<>(newRating,pair.getValue()));

                //评分、名字
                fooddsCuisineMap.get(pair.getValue()).add(new Pair<>(newRating,food));
            }

            // 返回指定烹饪方式 cuisine 下评分最高的食物的名字。如果存在并列，返回 字典序较小 的名字。
            public String highestRated(String cuisine) {
                PriorityQueue<Pair<Integer, String>> pp = fooddsCuisineMap.get(cuisine);
                String name = "";
                while (!pp.isEmpty()) {
                    int rat = foodsMap.get(pp.peek().getValue()).getKey();
                    //堆顶元素 不是最新值
                    if (rat != pp.peek().getKey()) {
                        pp.poll();
                    } else {
                        name = pp.peek().getValue();
                        break;
                    }
                }
                //获取最高的评分
                return name;
            }
        }


    }


    /**
     *3092. 最高频率的 ID
     *方案一：map+懒删除堆
     * 方案二：hash+ 有序集合(频率是键，元素值是value)
     * @param nums
     * @param freq
     * @return
     */
    public long[] mostFrequentIDs(int[] nums, int[] freq) {

        //频率|元素值
        //设置大根堆
        PriorityQueue<long[]> queue = new PriorityQueue<long[]>((a, b) -> (int) (b[0] - a[0]));

        Map<Long, Long> numToFre = new HashMap<>();
        long[] ans = new long[freq.length];

        for (int i = 0; i < freq.length; i++) {

            long currentFre = numToFre.getOrDefault((Long.valueOf( nums[i])), 0L);
            currentFre += freq[i];
            currentFre=Math.max(currentFre,0);
            numToFre.put((long) nums[i], currentFre);
            //加入进去:不删除旧的数据
            queue.offer(new long[]{currentFre, nums[i]});
            //判断当前堆顶的元素的频率是否 和他真正的频率一直
            while (queue.peek()[0]!=numToFre.get(queue.peek()[1])) {
                queue.poll();
            }
            ans[i] = queue.peek()[0];
        }

        TreeMap<Long,Integer> freToNum=new TreeMap<>();

        for (int i = 0; i < freq.length; i++) {
            int num=nums[i];
            int fr=freq[i];
            long currentFre = numToFre.getOrDefault((Long.valueOf(num)), 0L);
            currentFre += fr;
            currentFre=Math.max(currentFre,0);

            //移除之前的
            if (numToFre.containsKey(num)){
                freToNum.remove((Long) numToFre.get(num));
            }
            //更新
            numToFre.put((long) num,currentFre);
            freToNum.put(currentFre,num);
            ans[i] = freToNum.lastKey();
        }
        return ans;
    }


}
