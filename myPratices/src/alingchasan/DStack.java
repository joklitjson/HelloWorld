package alingchasan;

import org.omg.CORBA.INTERNAL;

import java.util.*;

/**
 * 栈题目：可以使用栈进行模拟、也可以使用数组、StringBuilder进行模拟
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

        System.out.println("asteroidCollision=="+Arrays.toString(asteroidCollision(new int[]{10,2,-5})));
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
