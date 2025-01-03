package alingchasan;

import java.util.*;

/**
 * 栈题目：可以使用栈进行模拟、也可以使用数组、StringBuilder进行模拟
 *
 * 判断括号的有效性：可以使用 计数法、模拟栈
 */
public class DStack {

    public static void main(String[] args) {
//        buildArray(new int[]{1,3},3);
//        System.out.println(simplifyPath("/home/user/Documents/../Pictures"));
//        System.out.println(clearStars("aaba*"));
//        System.out.println(Arrays.toString(exclusiveTime(1,Arrays.asList("0:start:0","0:start:1","0:start:2","0:end:3","0:end:4","0:end:5"))));

//        DinnerPlates dinnerPlates=new DinnerPlates(2);
//        dinnerPlates.push(1);
//        dinnerPlates.push(2);
//        dinnerPlates.push(3);
//        dinnerPlates.push(4);
//        dinnerPlates.push(5);
//        System.out.println(dinnerPlates.popAtStack(0));
//
//        dinnerPlates.push(20);
//        dinnerPlates.push(21);
//
//        System.out.println(dinnerPlates.popAtStack(0));
//        System.out.println(dinnerPlates.popAtStack(2));
//
//        System.out.println(dinnerPlates.pop());
//        System.out.println(dinnerPlates.pop());
//        System.out.println(dinnerPlates.pop());
//        System.out.println(dinnerPlates.pop());
//        System.out.println(dinnerPlates.pop());

        DinnerPlates dinnerPlates=new DinnerPlates(1);

        dinnerPlates.push(1);
        dinnerPlates.push(2);
        System.out.println(dinnerPlates.popAtStack(1));
        System.out.println(dinnerPlates.pop());

        dinnerPlates.push(1);
        dinnerPlates.push(2);

        System.out.println(dinnerPlates.pop());
        System.out.println(dinnerPlates.pop());

//        System.out.println(isValid("aabcbc"));

        System.out.println(removeDuplicates("deeedbbcccbdaa",3));
        System.out.println("countCollisions=="+countCollisions("SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR"));

//        System.out.println("asteroidCollision=="+Arrays.toString(asteroidCollision(new int[]{10,2,-5})));

//        System.out.println("survivedRobotsHealths"+Arrays.toString(survivedRobotsHealths(new int[]{3,5,2,6},new int[]{10,10,15,12},"RLRL").toArray())) ;

//        System.out.println(scoreOfParentheses("(()(()))"));
        System.out.println(clumsy(4));
        System.out.println(clumsy2(4));

//        System.out.println(new DStack().calculate("(31+11*23)"));
//        System.out.println(new DStack().calculate("2-4-(8+2-6+(8+4-(1)+8-10))"));

//        System.out.println(new DStack().countOfAtoms("Be32"));

        System.out.println(parseBoolExpr("!(&(f,t))"));

    }



    /**
     * 1441. 用栈操作构建数组
     * 解决方案:遍历记录前一个值，然后计算前一个值和当前遍历的值之间差了多少个数字，然后在向其中插入 n个 push pop,操作
     * @param target
     * @param n
     * @return
     */
    public static List<String> buildArray(int[] target, int n) {
        int pre = 0;
        List<String> result = new ArrayList<>();
        for (int value : target) {

            //两者之间的差值进行插入然后在弹出
            for (int i = pre+1; i < value; i++) {
                System.out.println("loop"+i);
                result.add("Push");
                result.add("Pop");
                System.out.println(Arrays.toString(result.toArray()));
            }
            //插入当前数字
            result.add("Push");
            pre=value;
        }
        return result;
    }

    /**
     * 844. 比较含退格的字符串
     * 解决方案：可以使用栈模拟，删除退格键之前的字符，然后在比较两则
     * @param s
     * @param t
     * @return
     */
    public boolean backspaceCompare(String s, String t) {
        String ss = removeBackSpace(s);

        String tt = removeBackSpace(t);
        return Objects.equals(ss, tt);
    }
    private String removeBackSpace(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        if (s == null || s.length() == 0) {
            return s;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '#') {
                //删除
                if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 682. 棒球比赛
     * 解决方式：使用栈 直接计算，遇到计算符号 则进行弹出计算，然后在入栈
     * @param operations
     * @return
     */
    public int calPoints(String[] operations) {
        Stack<Integer> stack = new Stack<>();
        //遍历
        for (String str : operations) {
            if (str.equals("+")) {
                Integer num1 = stack.pop();
                Integer num2 = stack.pop();
                stack.push(num2);
                stack.push(num1);
                stack.push(num1 + num2);
            } else if (str.equals("D")) {
                Integer num1 = stack.peek();
                stack.push(num1 * 2);
            } else if (str.equals("C")) {
                stack.pop();
            } else {
                stack.push(Integer.valueOf(str));
            }
        }
        int score = 0;
        while (!stack.isEmpty()) {
            score += stack.pop();
        }
        return score;
    }

    /**
     * 2390. 从字符串中移除星号
     * 使用栈模拟，然后遇到星号 在移除前一个
     * @param s
     * @return
     */
    public String removeStars(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                if (stringBuilder.length() > 0) {
                    //通过设置长度，也可以起到删除的左右
                    stringBuilder.setLength(stringBuilder.length()-1);
//                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 946. 验证栈序列
     *方案一：：使用辅助栈 遍历pushed的数组，然后向栈中push，然后在判断 poped的当前元素是否和栈顶元素一致：一致就弹出，否则继续入栈     * @param pushed
     *方案二： 也可以使用数组模拟栈
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {

//        Stack<Integer> stack = new Stack<>();
//        int popIdx = 0;
//        for (int i = 0; i < pushed.length; i++) {
//            stack.push(pushed[i]);
//            //判断栈顶数据和当前需要的栈顶数据是否一样
//            while (!stack.isEmpty() && stack.peek().equals(popped[popIdx])) {
//                stack.pop();
//                popIdx++;
//            }
//        }
//        return stack.isEmpty();

        int stack[] = new int[pushed.length];
        int stackTop = -1;
        int poppedTop = 0;
        for (int val : pushed) {
            stack[++stackTop] = val;
            //实现出栈,继续比较下一个
            while (stackTop > -1 && stack[stackTop] == popped[poppedTop]) {
                poppedTop++;
                stackTop--;
            }
        }
        //栈为空
        return stackTop == -1;
    }

    /**
     * 71. 简化路径
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {

        String[] pathList = path.split("/");
        StringBuilder stringBuilder = new StringBuilder();

        Stack<String> stack = new Stack<>();
        for (String val : pathList) {
            if (val.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (val.equals(".") || val.length() == 0) {
                continue;
            } else {
                stack.push(val);
            }
        }
        //为空的话 值返回根目录
        if (stack.isEmpty()) {
            stringBuilder.append("/");
        } else {
            //弹出栈元素，然后在使用头插法：实现翻转
            while (!stack.isEmpty()) {
                stringBuilder.insert(0, "/" + stack.pop());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 3170. 删除星号以后字典序最小的字符串
     * 分析：为了让字典序尽量小，相比去掉前面的字母，去掉后面的字母更好
     * 使用26个数组模拟栈，每个栈存放 该字母所在的下标，然后遇到*，则删除第一个非空的栈顶下标(去掉最后一个字母)，
     * 然后在把所有的下标进行排序，在拼接的字符串中
     * 方案二：也可以把*前面一个字符设置成*，然后在使用一次遍历 就能删除所有的*
     * @param s
     * @return
     */
    public static String clearStars(String s) {

        char [] ss=s.toCharArray();
        List<Integer>[] stackList = new List[26];
        Arrays.setAll(stackList, i -> new ArrayList<>());//为每个栈 设置一个集合

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                //移除最近的一个最小字符
                for (List<Integer> list : stackList) {
                    if (!list.isEmpty()) {
                        ss[list.get(list.size() - 1)]='*';//设置成*
                        list.remove(list.size() - 1);
                        break;
                    }
                }
            } else {
                //存放下标
                stackList[s.charAt(i) - 'a'].add(i);
            }
        }
        //2、把所有的下标进行排序然后在输出
//        List<Integer> idxList = new ArrayList<>();
//        for (List<Integer> list : stackList) {
//            if (!list.isEmpty()) {
//                idxList.addAll(list);
//            }
//        }
//
//        Collections.sort(idxList);
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < idxList.size(); i++) {
//            stringBuilder.append(s.charAt(idxList.get(i)));
//        }

        StringBuilder stringBuilder = new StringBuilder();
        for (char c:ss){
            if (c!='*'){
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 636. 函数的独占时间
     * 使用模拟栈：遍历logs然后入栈，如果栈顶和当前元素时一对，则出栈，记录当前函数的运行时间，然后在更新栈顶下的函数的运行开始时间
     * @param n
     * @param logs
     * @return
     */
    public static int[] exclusiveTime(int n, List<String> logs) {
        Stack<Integer[]> stack = new Stack<>();
        int[] funTime = new int[n];

        for (String log : logs) {
            String currentFunLog[] = log.split(":");
            Integer fun=Integer.valueOf(currentFunLog[0]);
            String action=(currentFunLog[1]);
            Integer time=Integer.valueOf(currentFunLog[2]);
            if (action.equals("start")) {
                if (!stack.empty()){
                    //计算上一次函数的运行时间
                    funTime[stack.peek()[0]]+=time-stack.peek()[1];
                }
                stack.push(new Integer[]{fun,time});
            } else {
                //弹出元素计算时间
                Integer[] before = stack.pop();
                //记录当前函数的执行时间
                funTime[fun] +=time - before[1] + 1;
                //更新
                if (!stack.isEmpty()) {
                    //更新之前的函数的执行时间
                    stack.peek()[1]=time+1;
                }
            }
        }
        return funTime;
    }

    /**
     * 1381. 设计一个支持增量操作的栈
     * 方案：记录增量操作在第几个元素上，然后弹出的时候再加上这个增量。弹出后把这个增量 传递给他的下一个元素
     */
    class CustomStack {

        private int [] stack;
        private int [] incre;//记录某个位置上的增量
        int  top=-1;
        public CustomStack(int maxSize) {
            stack=new int[maxSize];
            incre=new int[maxSize];
        }

        public void push(int x) {
            if (top>=stack.length-1){
                return;
            }
            stack[++top]=x;
        }

        public int pop() {
            if (top<0){
                return -1;
            }

            int x = stack[top];
            x += incre[top];
            //往下传递
            if (top-1>=0){
                incre[top - 1] += incre[top];
            }
            incre[top] = 0;//复位
            top--;
            return x;
        }

        public void increment(int k, int val) {
            int min = Math.min(k-1, top);
            if (min>=0){
                //表示min位置上的元素加上val
                incre[min] += val;
            }
        }
    }

    /**
     * 895. 最大频率栈
     * 最大频率栈：
     *
     */
    class FreqStack {

        //数字对应的频率
        Map<Integer,Integer> numToFre=new HashMap<>();
        //频率对应的数字集合
        Map<Integer,Stack<Integer>> freToNums=new HashMap<>();
        int maxFre=0;
        public FreqStack() {
        }

        public void push(int val) {
            //1、更急元素的频率
            int fre=numToFre.getOrDefault(val,0);
            fre++;
            numToFre.put(val,fre);
            maxFre=Math.max(maxFre,fre);

            //更新对应的频率集合
            Stack<Integer> stack= freToNums.get(fre);
            if (stack==null){
                stack=new Stack<>();
                freToNums.put(fre,stack);
            }
            stack.push(val);
        }

        public int pop() {
            //获取最大频率的数字
            while (maxFre > 0) {
                Stack<Integer> stack = freToNums.get(maxFre);
                if (stack!=null&&!stack.isEmpty()) {
                    int value = stack.pop();
                    if (stack.isEmpty()) {
                        maxFre--;
                    }
                    //更新元素对应的频率
                    numToFre.put(value,numToFre.get(value)-1);
                    return value;
                } else {
                    maxFre--;
                }
            }
            return -1;
        }
    }


    /**
     * 2434. 使用机器人打印字典序最小的字符串
     * 解决方案：使用栈+贪心，如果某个字符都比后面的字符小，则可以进行打印了，因为后面找不到比他在小的字符了
     * 先计算每个字符的个数，然后遍历的同时 在计算第一个个数不为0的字符，比较他们大小，如果大于当前字符则可以从这里打印了
     * @param s
     * @return
     */
    public String robotWithString(String s) {

        int cnt[] = new int[26];
        Stack<Character> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        //统计个数
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
        }

        int minIdx = 0;

        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']--;

            //找到第一个不为0的字符，表示的是当前字符后面的最小字符
            while (minIdx <25 && minIdx == 0) {
                minIdx++;
            }

            stack.push(s.charAt(i));

            //当前栈顶字符 小于后续字符
            while (!stack.isEmpty() && stack.peek() - 'a' <= minIdx) {
                stringBuilder.append(stack.pop());
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 2589. 完成所有任务的最少时间
     * 贪心+排序
     * 方案：先按照end 进行排序，然后遍历每个task，在统计 每个task 窗口能已经运行的程序数量，如果不足，则从右相左的时间执行程序
     * 为啥设置从右相左？因为后面的任务 可能利用最右侧的时间任务
     * @param tasks
     * @return
     */
    public int findMinimumTime(int[][] tasks) {

        Arrays.sort(tasks,(a,b)->a[1]-b[1]);
        int taskExe[]=new int[2001];//创建一个时间轴：记录某个时间是否有任务执行
        int ans=0;//执行任务需要的时间
        for (int [] task:tasks){

            int start=task[0];
            int end=task[1];
            int duration=task[2];
            for (int i=start;i<=end;i++){
                // 如果当前位置有任务执行，则可以把一个任务量放在这个时间执行
                duration-=taskExe[i];
            }
            //如果还有剩余任务，则优先放在右侧执行
            for (int i=end;i>=start&&duration>0;i--){
                //表示没任务执行:在次数放置一个任务
                if (taskExe[i]==0){
                    taskExe[i]=1;
                    duration--;
                    ans++;
                }
            }
        }
        return ans;
    }


    /**
     * 2696. 删除子串后的字符串最小长度
     * 临项消除：
     * @param s
     * @return
     */
    public int minLength(String s) {
        Stack<Character> stack=new Stack<>();
        for (int i=0;i<s.length();i++){
            if (stack.isEmpty()){
                stack.push(s.charAt(i));
            }
            else {
                if ((stack.peek()=='A'&&s.charAt(i)=='B')||(stack.peek()=='C'&&s.charAt(i)=='D')){
                    stack.pop();
                }
                else {
                    stack.push(s.charAt(i));
                }
            }
        }
        return stack.size();
    }

    /**
     * 1047. 删除字符串中的所有相邻重复项
     * 方案：使用栈  比较栈顶元素和当前元素，此处可以使用模拟栈
     * @param s
     * @return
     */
    public String removeDuplicates(String s) {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<s.length();i++){
            char c=s.charAt(i);
            //相同则移除
            if (stringBuilder.length()>0&&stringBuilder.charAt(stringBuilder.length()-1)==c){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
            else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 整理字符串：1544. 整理字符串
     * 其实就是删除 相邻的一大一小的字符
     * @param s
     * @return
     */
    public String makeGood(String s) {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<s.length();i++){
            char c=s.charAt(i);
            //相同则移除
            if (stringBuilder.length()>0&&stringBuilder.charAt(stringBuilder.length()-1)!=c
            &&Character.toLowerCase(stringBuilder.charAt(stringBuilder.length()-1))==Character.toLowerCase(c)){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
            else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 1003. 检查替换后的词是否有效
     * 愿意：空字符 "" 通过插入 字符 n、个 abd 变成了目标字符s，判断字符s是否是插入了n个字符abc 变成的，
     * 我们可以反过来思考：字符s 是否可以通过删除 临近的abc，然后变成空字符
     * @param s
     * @return
     */
    public  static boolean isValid(String s) {

        //使用数组模拟栈
        char [] cc=new char[s.length()];
        int topIdx=-1;
        for (int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if (topIdx-1>=0){
                //判断最近三个字符是否相等？如何判断n个字符呢？使用一个循环就行了
                if (c=='c'&&cc[topIdx]=='b'&&cc[topIdx-1]=='a'){
                    topIdx=topIdx-2;
                }
                else {
                    cc[++topIdx]=c;
                }
            }
            else {
                cc[++topIdx]=c;
            }
        }
        //判断数组是否为空
        return topIdx==-1;
    }

    /**
     * 1209. 删除字符串中的所有相邻重复项 II
     * 使用模拟栈的方式 遍历字符，然后当 已经遍历的长度大于k时，我们在判断长度k的字符串是否都相等
     * @param s
     * @param k
     * @return
     */
    public static String removeDuplicates(String s, int k) {
        int idx=-1;
        char [] cc=new char[s.length()];
        for (int i=0;i<s.length();i++){
            char c=s.charAt(i);
            cc[++idx]=c;
            if (idx>=k-1){
                int j=idx-k+1;//判断[j,idx]之间的元素
                //从idx 往前判断k个字符是否相等
                while (j<idx&&cc[j]==c){
                    j++;
                }
                //删除这几个字符
                if (j==idx){
                    //重置游标
                    idx=idx-k;
                }
            }
        }

        if (idx==-1){
            return "";
        }
        return new String(cc,0,idx+1);
    }


    /**
     * 2211. 统计道路上的碰撞次数
     * 使用栈：分情况讨论
     * @param directions
     * @return
     */
    public static int countCollisions(String directions) {

        int ans = 0;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < directions.length(); i++) {
            char c = directions.charAt(i);

            if (c == 'L') {
                if (stack.isEmpty()) {
                    continue;//肯定不会碰撞，不用添加了
                }
                if (stack.peek() == 'S') {
                    ans++;
//                        stack.push('S');//加不加都行，因为此时栈顶已经存在了stop 远山
                } else if (stack.peek() == 'R') {
                    ans++;//当前会加1,下面的第一个while 循环也会+1
                    while (!stack.isEmpty() && stack.peek() == 'R') {
                        stack.pop();
                        ans++;
                    }
                    //碰撞之后就停止了
                    stack.push('S');
                } else {
                    //左边是向左的，没关系可以继续添加
//                        stack.push(c);
                }
            } else if (c == 'S') {
                while (!stack.isEmpty() && stack.peek() == 'R') {
                    stack.pop();
                    ans++;
                }
                stack.push(c);
            } else if (c == 'R') {
                stack.push(c);
            }
        }
        return ans;
    }


    /**
     * 735. 小行星碰撞
     * 使用模拟栈：向向则爆炸，保留质量大的，如果两个质量相同则 都爆炸
     * @param asteroids
     * @return
     */
    public static int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stack = new Stack<>();

        for (int aster : asteroids) {
            if (stack.isEmpty()) {
                stack.push(aster);
            } else {
                //相向而行
                if (stack.peek() > 0 && aster < 0) {
                    boolean needPush=true;//判断当前的星星是否需要加入
                    while (!stack.isEmpty()&&stack.peek() > 0 && aster < 0) {
                        if (stack.peek() == Math.abs(aster)) {
                            stack.pop();
                            needPush=false;
                            break;
                        } else if (stack.peek() < Math.abs(aster)) {
                            //把栈顶的星星 消灭
                            stack.pop();
                        } else {
                            //当前元素较小,不需要入栈：消灭当前星星
                            needPush=false;
                            break;
                        }
                    }
                    if (needPush){
                        stack.push(aster);
                    }
                } else {
                    //他俩方向不会碰撞
                    stack.push(aster);
                }
            }
        }
        if (stack.isEmpty()) {
            return new int[0];
        }
        //输出答案
        int[] ans = new int[stack.size()];
        int idx = ans.length - 1;
        while (idx >= 0 && !stack.isEmpty()) {
            ans[idx--] = stack.pop();
        }
        return ans;
    }

    /**
     * 2197. 替换数组中的非互质数
     * 使用栈处理
     * @param nums
     * @return
     */
    public List<Integer> replaceNonCoprimes(int[] nums) {

        List<Integer> ans = new ArrayList<>();

        for (int num : nums) {
            ans.add(num);
            while (ans.size() >1) {
                //判断这两个数是否互质:
                int num1 = ans.get(ans.size() - 1);
                int num2 = ans.get(ans.size() - 2);
                int g = gcd(num1, num2);
                if (g != 1) {
                    //非互质的情况下 删除他们俩，然后在加入他俩的最小公倍数
                    ans.remove(ans.size() - 1);
                    ans.remove(ans.size() - 1);
                    ans.add(num1 / g * num2);
                } else {
                    break;
                }
            }
        }
        return ans;
    }

    int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * 2216. 美化数组的最少删除数
     * @param nums
     * @return
     */
    public int minDeletion(int[] nums) {
        return 0;
    }


    /**
     * 2751. 机器人碰撞
     *
     * 先按位置进行排序，然后在使用模拟栈的方式：让每个机器人进栈后的情况
     * @param positions
     * @param healths
     * @param directions
     * @return
     */
    public static List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {

        int [][] robots=new int[positions.length][4];//构造一个4元组：机器人id、位置、健康度、方向
        for (int i=1;i<=positions.length;i++){
            robots[i-1]=new int[]{i,positions[i-1],healths[i-1],directions.charAt(i-1)=='R'?1:-1};
        }
        //然后在按照位置排序
        Arrays.sort(robots,(a,b)->a[1]-b[1]);

        Stack<int[]> stack=new Stack<>();

        //这里类似星球碰撞
        for (int[] robot:robots){
            //判断是否需要添加
            boolean needAdd=true;
            while (needAdd&&!stack.isEmpty()&&stack.peek()[3]>0&&robot[3]<0){
                if (stack.peek()[2]==robot[2]){
                    //健康度相同则进行都移除
                    stack.pop();
                    needAdd=false;
                    break;
                }
                else  if (stack.peek()[2]<robot[2]){
                    //当前健康度比较大
                    stack.pop();
                    robot[2]=robot[2]-1;
                }
                else {
                    //当前健康度比较小
                    stack.peek()[2]--;
                    needAdd=false;
                    break;
                }
            }
           if (needAdd){
               stack.push(robot);
           }
        }

        int [][]result=new int[stack.size()][4];
        int i=0;
        while (!stack.isEmpty()){
            result[i++]=stack.pop();
        }
        //按编号排序
        Arrays.sort(result,(a,b)->a[0]-b[0]);
        List<Integer> ans=new ArrayList<>();
        for (int [] robot:result) {
            ans.add(robot[2]);
        }

        return ans;
    }


    /**
     * 921. 使括号有效的最少添加
     * 将[有效括号] 转换成 [分值有效性]的数学判定,遇到 ( +1, ) -1,遍历过程中  如果小于零 则需要补充 abs(score) 个左括号
     * 遍历结束：还需要再补充 score个右括号(以上过程一直在补左括号，因此肯定是左括号要大于右括号的)
     * @param s
     * @return
     */
    public int minAddToMakeValid(String s) {
        int score = 0;
        int ans = 0;
        //
        for (int i = 0; i < s.length(); i++) {
            score += s.charAt(i) == '(' ? 1 : -1;
            if (score < 0) {
                //补充 左括号
                ans += Math.abs(score);
                score=0;
            }
        }
        //补充右括号
        ans += score;
        return ans;
    }

    /**
     * 1614. 括号的最大嵌套深度
     * 使用分数法：遇到（ +1，遇到 ） -1,遍历过程 中 求最大值
     * @param s
     * @return
     */
    public int maxDepth(String s) {
        int score = 0, maxDep = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                score++;
            } else if (s.charAt(i) == ')') {
                score--;
            }
            maxDep = Math.max(maxDep, score);
        }
        return maxDep;
    }

    /**
     * 1190. 反转每对括号间的子串
     * 解决方案：遇到（ 需要把临时字符串 插入栈中，遇到 ），则翻转临时字符串中的字符，然后再把栈顶元素弹出 拼接到字符串头部
     * @param s
     * @return
     */
    public String reverseParentheses(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                //把字符 暂存在栈中
                stack.push(stringBuilder.toString());
                stringBuilder.setLength(0);
            } else if (c == ')') {
                //翻转当前端的字符
                stringBuilder.reverse();
                //把之前暂存的字符放在当前字符头部? 这样做的目的主要是为了下次遇到 )字符进行再次翻转
                stringBuilder.insert(0, stack.pop());
            } else {
                //存在临时字符中
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 1963. 使字符串平衡的最小交换次数
     * 使用贪心的思想：题目中 [ 、]数量相等，因此我们可以使用计数的方式 计算，如果 遇到)时 cnt==0，则说明此事需要和后面的一个]进行交换
     * @param s
     * @return
     */
    public int minSwaps(String s) {

        int balance = 0, ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {
                balance++;
            } else if (c == ']') {
                if (balance > 0) {
                    balance--;
                } else {
//                    此时balance==0，这里需要进行和后面的]进行一次交换
                    balance++;
                    ans++;

                }
            }
        }
        //类似这段代码
//        Deque<Character> stack = new ArrayDeque<>();
//        for (char c : chars) {
//            if (c == '[') {
//                stack.push(c);
//            } else {
//                if (stack.isEmpty()) {
//                    res++;
//                    stack.push('[');
//                } else {
//                    stack.pop();
//                }
//            }
//        }
        return ans;
    }


    /**
     * 678. 有效的括号字符串
     * 方案：使用两个栈，第一个栈存储左括号的下表，第二个存储*的下表，遍历中间在进行对冲
     * 遍历结束后，判断他们两个是否有值，总之就是需要把stack栈的元素消耗完成
     * @param s
     * @return
     */
    public boolean checkValidString(String s) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack1.push(i);
            }
           else if (c == '*') {
                stack2.push(i);
            }
            else if (c == ')') {
                if (stack1.isEmpty()) {
                    if (!stack2.isEmpty()) {
                        //使用一个*当做 (,对冲 当前的 )
                        stack2.pop();
                    } else {
                        //已经不平衡了
                        return false;
                    }
                } else {
                    stack1.pop();
                }
            }
        }
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            int leftIdx = stack1.pop();
            int startIdx = stack2.pop();
            //*号在 （的左边，因此不能进行对冲，所以返回false
            if (startIdx < leftIdx) {
                return false;
            }
        }
        // *浩已经对冲完了，再看看(是否为空
        return stack1.isEmpty();
    }


    /**
     * 32. 最长有效括号
     * 我们始终保持栈底元素为当前已经遍历过的元素中「最后一个没有被匹配的右括号的下标」，这样的做法主要是考虑了边界条件的处理，栈里其他元素维护左括号的下标：
     * 因此遇到 (时需要向栈中加入当前的 索引，遇到)时判断当前栈是否为空，如果是空 则新开辟一个新的开始，否则就是拿当前的i减去栈顶的元素：计算当前最大长度
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {

        int max = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);//表示左边不匹配的 ）的索引是-1位
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);//开启新的开始
                } else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }


    /**
     * 1190. 反转每对括号间的子串
     * 先预处理 左右括号的对应位置，然后在进行遍历，以及遇到括号进行方向遍历翻转
     * @param s
     * @return
     */
    public String reverseParentheses2(String s) {

        int[] pair = new int[s.length()];//记录每个括号的对应 括号的位置
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                int j = stack.pop();
                //记录对应位置
                pair[i] = j;
                pair[j] = i;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        //进行遍历
        int idx = 0;
        int step = 1;//步长的正负 代表了遍历的方向
        while (idx < s.length()) {
            char c = s.charAt(idx);
            if (c == '(' || c == ')') {
                idx = pair[idx];//
                step = -step;//翻转方向
            } else {
                stringBuilder.append(c);
            }
            //增加步长
            idx += step;
        }
        return stringBuilder.toString();
    }


    /**
     * 856. 括号的分数
     * 方案：每次遇到 ( 增加一个0进去，遇到 ) 在看栈顶的元素是否是0，如果是0，则是 ()形式，如果不是零 ，则是(A)形式 需要2*A,最后在累加就行
     * 简单的栈应用不简单，利用先进后出的性质，可以让栈中永远多一个元素，用来存储最后的答案，
     * 栈顶记录答案，实时修改。更像是个数学问题，栈顶记录的是层数，遇到第一个反括号的时候，表示来到了最里面一层。
     * @param s
     * @return
     */
    public static int scoreOfParentheses(String s) {
        Stack<Integer> stack=new Stack<>();
        stack.push(0);//添加一个0
        for (int i=0;i<s.length();i++){
            if (s.charAt(i)=='('){
                stack.push(0);
            }
            else {
                if (stack.peek()==0){
                    stack.pop();//弹出之前的(
                    stack.push(stack.pop()+1);
                }
                else {
                    //乘以两倍
                    int result=stack.pop()*2;
//                    将其新得分累加到栈顶元素上
                    stack.push(stack.pop()+result);//同时进行累加之前的结果
                }
            }
        }
        return stack.peek();
    }


    /**
     * 1249. 移除无效的括号
     * 方案一：使用模拟栈，匹配括号，把匹配的括号直接弹出，然后栈中剩下的就是 未匹配的括号 则可以移除
     * 方案二：使用计数器代替栈，遍历途中发现 balance 小于0，则抛弃当前的 ),结束后 如果balance 不等于0，则倒序删除多余(
     * @param s
     * @return
     */
    public String minRemoveToMakeValid(String s) {
//        Stack<Integer> stack = new Stack<>();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (c == '(' || c == ')') {
//                if (stack.isEmpty()) {
//                    stack.push(i);
//                } else {
//                    if (s.charAt(stack.peek()) == '(' && c == ')') {
//                        stack.pop();
//                    } else {
//                        stack.push(i);
//                    }
//                }
//            }
//        }
//        //记录需要删除的索引id
//        Set<Integer> removeIdx = new HashSet<>();
//        while (!stack.isEmpty()) {
//            removeIdx.add(stack.pop());
//        }
//        //遍历元素
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//            if (!removeIdx.contains(i)) {
//                stringBuilder.append(s.charAt(i));
//            }
//        }
//        return stringBuilder.toString();


        int balance=0;
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if (c=='('){
                balance++;
                stringBuilder.append(c);
            }
            else if (c==')'){
                balance--;
                if (balance<0){
//                    )字符太多了 ，需要删除,因此当前的 )就不需要再加入到答案中了
                    balance=0;
                }
                else {
                    stringBuilder.append(c);
                }
            }
            else {
                //正常的字符
                stringBuilder.append(c);
            }
        }

        int idx=stringBuilder.length()-1;
        //删除多余的 (
        while (balance!=0){
            //需要倒序删除
            if (stringBuilder.charAt(idx)=='('){
                balance--;
                stringBuilder.deleteCharAt(idx);
            }
            idx--;
        }
        return stringBuilder.toString();
    }


    /**
     * 1006. 笨阶乘
     * 解决方案：优先计算乘除，然后在加减。如果是乘除 则弹出栈顶元素和当前元素计算，然后在入栈，如果是减则，弹出栈顶元素在取反，在入栈。最后把占中所有的元素进行累加
     *
     * @param n
     * @return
     */
    public static int clumsy(int n) {
        Stack<Integer> stack = new Stack<>();
        stack.push(n);
        n--;
        int operator = 0;//代表* / + -
        while (n > 0) {
            if (operator == 0) {
                stack.push(stack.pop() * n);
            } else if (operator == 1) {
                stack.push(stack.pop() / n);
            } else if (operator == 2) {
                stack.push(n);
            } else if (operator == 3) {
                stack.push(-n);
            }
            n--;
            //在进行循环
            operator = ++operator % 4;
        }
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }


    /**
     * 使用通用模板：双栈的结构，一个存数字 一个存运算符号,【只有「栈内运算符」比「当前运算符」优先级高/同等，才进行运算】
     * https://leetcode.cn/problems/clumsy-factorial/solutions/693194/gong-shui-san-xie-tong-yong-biao-da-shi-nngfp/
     * @param n
     * @return
     */
    public static int clumsy2(int n) {

        char opts[] = new char[]{'*', '/', '+', '-'};
        Stack<Integer> numberStack = new Stack<>();
        Stack<Character> optStack = new Stack<>();

        //定义运算法优先级
        Map<Character, Integer> map = new HashMap<>();
        map.put('*', 2);
        map.put('/', 2);
        map.put('+', 1);
        map.put('-', 1);

        for (int i = n, j = 0; i > 0; i--, j++) {
            numberStack.push(i);
            char op = opts[j%4];
            //栈内运算法 大于后面的运算法
            while (!optStack.isEmpty() && map.get(optStack.peek()) >= map.get(op)) {
                calc(numberStack, optStack);
            }
            if (i != 1) {
                optStack.push(op);
            }
        }

        //如果栈内还有元素 没算完 继续算
        while (!optStack.isEmpty()) {
            calc(numberStack, optStack);
        }
        return numberStack.peek();
    }

    private static void  calc(  Stack<Integer> numberStack,Stack<Character> optStack){
        int b=numberStack.pop();
        int a=numberStack.pop();
        Character character=  optStack.pop();
        if (character.equals('*')){
            numberStack.push(a*b);
        }
        else if (character.equals('/')){
            numberStack.push(a/b);
        }
        else if (character.equals('+')){
            numberStack.push(a+b);
        }
        else if (character.equals('-')){
            numberStack.push(a-b);
        }
    }


    /**
     * 224. 基本计算器
     * 可以使用双栈，然后把数字和运算法分别统计在两个栈中，类似逆波兰计算法
     * @param s
     * @return
     */
    public int calculate(String s) {

        //定义优先级
        Map<Character, Integer> map = new HashMap(){{
            put('-', 1);
            put('+', 1);
            put('*', 2);
            put('/', 2);
            put('%', 2);
            put('^', 3);
        }};

        s=s.replace(" ","");
        Stack<Integer> numberStack=new Stack<>();

        // 例如 -1 + 2 情况
        numberStack.push(0);//防止第一个是-号或者其他符号位
        Stack<Character> optStack=new Stack<>();

        int n=s.length();
        for (int i=0;i<n;i++){
            char c=s.charAt(i);
            if (c=='('){
                optStack.push(c);
            }
            else if (Character.isDigit(c)){
                //是数字
                int j=i;
                //获取完整的数字
                int num=0;
                while (j<n&&Character.isDigit(s.charAt(j))){
                    num=num*10+(s.charAt(j)-'0');
                    j++;
                }
                numberStack.push(num);
                i=j-1;//需要返回到最后一个数字的位置

            }
            else if (c==')'){
                while (!optStack.isEmpty()){
                    if (optStack.peek()=='('){
                        //弹出这个 (括号
                        optStack.pop();
                        break;
                    }
                    else {
                        calc2(numberStack,optStack);
                    }
                }
            }
            else {
                // 为防止 () 内出现的首个字符为运算符，将 (- 替换为 (0-，(+ 替换为 (0+
//                防止连续两个运算符号比如：(-  -(  *(
                if (i>0&&(s.charAt(i-1)=='('||s.charAt(i-1)=='+'||s.charAt(i-1)=='-')){
                    numberStack.push(0);
                }

                //如果当前栈顶优先级高于当前优先级 则可以进行计算
                while (!optStack.isEmpty()&&optStack.peek()!='('){
                    char preOpt=optStack.peek();
                    if (map.get(preOpt)>=map.get(c)){
                        calc2(numberStack,optStack);
                    }
                    else {
                        break;
                    }
                }
                //把当前符号进入
                optStack.push(c);

            }
        }

        //还有多余的运算符
        while (!optStack.isEmpty()){
            calc2(numberStack,optStack);
        }
        return numberStack.peek();
    }

    private void  calc2( Stack<Integer> numberStack, Stack<Character> optStack){
        if (numberStack.size()<2||optStack.isEmpty()){
            return;
        }
        int b=numberStack.pop();
        int a=numberStack.pop();
        Character opt=optStack.pop();

        int result=0;
        if (opt=='*'){
            result=a*b;
        }
        else if (opt=='/'){
            result=a/b;
        }
        else if (opt=='%'){
            result=a%b;
        }
        else if (opt=='^'){
            result= (int) Math.pow(a,b);
        }
        else if (opt=='+'){
            result=a+b;
        }
        else if (opt=='-'){
            result=a-b;
        }
        numberStack.push(result);

    }


    /**
     * 726. 原子的数量
     * 理清楚概念：什么是原子？一个大写字母后面跟着n个小写字符。
     * 在遍历字符串：解析原子符号，并且记录原子字符串的数量。同时在结合 （）运算，因此使用栈和hash的方式
     * @param formula
     * @return
     */
    public String countOfAtoms(String formula) {



        Stack<Map<String, Integer>> stack = new Stack<>();
        stack.push(new HashMap<>());
        int i = 0, n = formula.length();
        while (i < n) {
            char c = formula.charAt(i);
            if (c == '(') {
                stack.push(new HashMap<>());
                i++;
            } else if (c == ')') {
                //结束符合

                //读取数字
                int num = 0;
                if (i + 1 < n && Character.isDigit(formula.charAt(i+1))) {
                    i++;
                    while (i < n && Character.isDigit(formula.charAt(i))) {
                        num = num * 10 + (formula.charAt(i++) - '0');
                    }
                } else {
                    num = 1;
                    i++;
                }
                Map<String, Integer> popedMap = stack.pop();
                Map<String, Integer> toMap = stack.peek();

                //把当前结果加入到之前的结果中
                for (Map.Entry<String, Integer> entry : popedMap.entrySet()) {
                    String atom = entry.getKey();
                    toMap.put(atom, toMap.getOrDefault(atom, 0) + entry.getValue() * num);
                }

            } else {
                //是字符
                //读取完整字符
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(c);
                i++;
                while (i < n && Character.isLowerCase(formula.charAt(i))) {
                    stringBuilder.append(formula.charAt(i++));
                }

                //读取数字
                int num = 0;
                if (i<n&&Character.isDigit(formula.charAt(i))) {
                    while (i < n && Character.isDigit(formula.charAt(i))) {
                        num = num * 10 + (formula.charAt(i++) - '0');
                    }
                } else {
                    num = 1;
                }

                //计算个数
                Map<String, Integer> cuurentMap = stack.peek();
                cuurentMap.put(stringBuilder.toString(), cuurentMap.getOrDefault(stringBuilder.toString(), 0) + num);
            }
        }

        //在进行字典序输出

        TreeMap<String, Integer> treeMap = new TreeMap(stack.peek());

        StringBuilder ans = new StringBuilder();
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            String atom = entry.getKey();
            ans.append(atom);
            if (entry.getValue() > 1) {
                ans.append(entry.getValue());
            }
        }
        return ans.toString();
    }


    /**
     * 1106. 解析布尔表达式
     * 我们依旧使用双栈解决：把 t、f当做数字，& | ！当做运算符
     * @param expression
     * @return
     */
    public static boolean parseBoolExpr(String expression) {

        Stack<Character> numStack = new Stack<>();

        Stack<Character> opttack = new Stack<>();
        int n = expression.length();
        for (int i = 0; i < n; i++) {
            char c = expression.charAt(i);

            if (c == 't' || c == 'f') {
                numStack.push(c);
            } else if (c == '|' || c == '&' || c == '!') {
                opttack.add(c);
            } else if (c == ',') {
                continue;
            } else if (c == '(') {
                numStack.push('(');
            } else if (c == ')') {
                //需要计算子结果

                //括号外的运算符
                char currentOp = opttack.pop();
                char curr = ' ';//假设是当前元素
                while (!numStack.isEmpty() && numStack.peek() != '(') {
                    char top = numStack.pop();
                    //这个意思就是 当括号内只有一个元素时，结果是当前元素或者是内部括号已经计算出了结果
                    curr = curr == ' ' ? top : calc(curr, top, currentOp);
                }
                numStack.pop();//弹出 （
                if (currentOp == '!') {
                    //进行取反运算
                    numStack.push(curr == 't' ? 'f' : 't');
                } else {
                    //直接入到数字坑里
                    numStack.push(curr);
                }
            }
        }

        return numStack.peek() == 't';
    }
    private  static char calc(char a,char b,char opt) {
        boolean x = a == 't';
        boolean y = b == 't';
        boolean result = opt == '|' ? x | y : x & y;
        return result ? 't' : 'f';
    }
    /**
     * 1172. 餐盘栈
     * 解决方案：记录pop的下标，在记录左侧非空栈的下标，
     */
    static class DinnerPlates {

        private int cap=0;
        private int topIdx=0;
        private Map<Integer,Stack<Integer>> map=new HashMap<>();
        TreeSet<Integer> notFullStackIdx=new TreeSet<>();
        public DinnerPlates(int capacity) {
            this.cap=capacity;
        }

        public void push(int val) {
            if (!notFullStackIdx.isEmpty()){
                int idx= notFullStackIdx.first();
                map.get(idx).add(val);
                //移除;
                if (map.get(idx).size()==cap){
                    notFullStackIdx.pollFirst();
                }

            }
            else {
                Stack<Integer> stack=  map.get(topIdx);
                if (stack==null){
                    stack=new Stack<>();
                    map.put(topIdx,stack);
                }
                stack.push(val);
                //增加下标
                if (stack.size()==cap){
                    topIdx++;
                }
            }
        }

        public int pop() {
            while (topIdx >= 0) {
                Stack<Integer> stack = map.get(topIdx);
                if (stack == null || stack.isEmpty()) {
                    if (topIdx == 0) {
                        return -1;
                    }
                    notFullStackIdx.remove(topIdx);
                    topIdx--;
                    continue;
                }

                int value = stack.pop();
                if (stack.isEmpty()) {
                    if (topIdx==0){
                        notFullStackIdx.clear();
                    }
                    else {
                        notFullStackIdx.remove(topIdx);
                        topIdx--;
                    }

                }
                return value;
            }
            return -1;
        }

        public int popAtStack(int index) {
            Stack<Integer> stack = map.get(index);
            if (stack == null || stack.isEmpty()) {
                return -1;
            }
            //更新不满的栈ID
            int value = stack.pop();
            notFullStackIdx.add(index);
            return value;
        }
    }
}

