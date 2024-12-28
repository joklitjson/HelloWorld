package alingchasan;

import java.util.Stack;

/**
 * 1472. 设计浏览器历史记录
 * 右两种方案：
 * 方案一：使用双栈的思想，左边的栈记录的是历史游览记录和当前浏览页面，右边的栈记录的是前进中的页面，如果使用了visit方法，则清空右边前进的栈
 *
 * 方案二：使用数组模拟栈：使用cur、top指针，代表当前指针位置和top的指针指向，然后就是操作指针的移动问题了
 *
 */
class BrowserHistory {

//    private Stack<String> s1=new Stack<>();
//    private Stack<String> s2=new Stack<>();
//    public BrowserHistory(String homepage) {
//        visit(homepage);
//    }
//
//    public void visit(String url) {
//        //放在左边和清空右边
//        s1.push(url);
//        s2.clear();
//    }
//
//    public String back(int steps) {
//        //获取最大回退步骤
//        steps=Math.min(s1.size(),steps);
//        while (steps>0&&s1.size()>1){
//            s2.push(s1.pop());
//            steps--;
//        }
//        return s1.peek();
//    }
//
//    public String forward(int steps) {
//
//        steps=Math.min(steps,s2.size());
//        while (steps>0&&s2.size()>0){
//            //把前进栈中的元素放在当前访问栈中
//            s1.push(s2.pop());
//            steps--;
//        }
//        return s1.peek();
//    }



    String [] history;
    int cur=-1;//当前指针指向
    int top=-1;//top指针指向
    public BrowserHistory(String homepage) {
        history=new String[5001];//最大长度
        visit(homepage);
    }

    public void visit(String url) {
        cur++;
        history[cur]=url;
        top=cur;
        top++;
    }

    /**
     * 后退
     * @param steps
     * @return
     */
    public String back(int steps) {
       if (steps>cur){
           steps=cur;
       }
       //回退指针
       cur=cur-steps;
       return history[cur];
    }

    public String forward(int steps) {

        //判断前进的部署是否超过了 top指针
        steps = Math.min(steps, top - cur - 1);
        cur = cur + steps;
        return history[cur];
    }
}