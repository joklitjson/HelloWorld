package linkedList;

import java.util.ArrayList;
import java.util.List;

public class LinkedTest {

    public int[] reverseBookList(ListNode head) {
        List<Integer> list=new ArrayList<>();
        dfs(head,list);
        int [] result=new int[list.size()];

        for (int i=0;i<list.size();i++){
            result[i]=list.get(0);
        }
        return result;
    }

    private void  dfs(ListNode head, List<Integer> list){
        if (head==null){
            return;
        }
        dfs(head.next,list);

        list.add(head.val);
    }
}
