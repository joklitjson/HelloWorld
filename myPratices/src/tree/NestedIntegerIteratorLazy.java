package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NestedIntegerIteratorLazy implements Iterator<Integer> {

    LinkedList<NestedInteger> list=new LinkedList<>();

    /**
     * 用于迭代 List<NestedInteger>  集合
     * 缺点：一次性加载，还没迭代就把数据初始化好了，最后的处理方式是懒加载
     * @param list
     */
    public NestedIntegerIteratorLazy(List<NestedInteger> list){
        this.list.addAll(list);
    }

    /**
     * 以这种方法，符合迭代器惰性求值的特性，是比较好的解法，建议拿小本本记下来！
     * @return
     */
    @Override
    public boolean hasNext() {
        while (!list.isEmpty()&&!list.get(0).isInteger()){
            //拆解第一个元素的孩子,类似于深度遍历，找第一个元素的子元素的元素，直到找到孩子为止
            List<NestedInteger> first= list.remove(0).getList();
            for (int i=first.size()-1;i>=0;i--){
                list.addFirst(first.get(0));
            }
        }
        return !list.isEmpty();
    }

    @Override
    public Integer next() {
        // hasNext 方法保证了第一个元素一定是整数类型
        return list.remove(0).getInteger();
    }


}
