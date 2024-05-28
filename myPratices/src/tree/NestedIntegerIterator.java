package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NestedIntegerIterator implements Iterator<Integer> {

    List<Integer> result=new ArrayList<>();
    Iterator<Integer> iterator=null;

    /**
     * 用于迭代 List<NestedInteger>  集合
     * 缺点：一次性加载，还没迭代就把数据初始化好了，最后的处理方式是懒加载
     * @param list
     */
    public NestedIntegerIterator(List<NestedInteger> list){
        traseve(list);
        iterator=result.iterator();
    }
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Integer next() {
        return iterator.next();
    }

    private void  traseve(List<NestedInteger> list){
        for (NestedInteger nestedInteger:list){
            if (nestedInteger.isInteger()){
                result.add(nestedInteger.getInteger());
            }
            else {
                traseve(nestedInteger.getList());
            }
        }
    }
}
