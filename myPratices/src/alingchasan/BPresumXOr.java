package alingchasan;


import java.util.*;

/**
 *
 * 前缀亦或者
 */
public class BPresumXOr {

    public static void main(String[] args) {
        System.out.println(wonderfulSubstrings("aba"));
    }
    /**
     * 1310. 子数组异或查询
     * 方案：先进行预处理：获得亦或数组，然后再便利查询，亦或两个段
     * @param arr
     * @param queries
     * @return
     */
    public int[] xorQueries(int[] arr, int[][] queries) {
        int n=arr.length,m=queries.length;
        int [] xorArr=new int[n+1];
        xorArr[0]=0;//填充第一个
        for (int i=1;i<=n;i++){
            xorArr[i]=xorArr[i-1]^arr[i-1];
        }
        int [] ans=new int[m];
        for (int i=0;i<m;i++){
            int start=queries[i][0];
            int end=queries[i][1];
            ans[i]=xorArr[end+1]^xorArr[start];//亦或的结果是包含两个闭区间的数字[start,end]
        }
        return ans;
    }

    /**
     * 1177. 构建回文串检测
     * 方案：构建二维数组前缀 个数数据sum[i][j] j=26，表示前i个数组中 每个字符出现的次数
     * 然后在进行询问统计，统计个数是奇数的字符数量m，把这m个不同字符变换成回文字符串，则需要 把其中一半的字符 替换成另一半的字符
     * 因此问题转换成：m/2<=k 的问题,就是需要的操作次数是m/2次
     *
     * 优化方案：由于只关心每种字母出现次数的奇偶性，所以不需要在前缀和中存储每种字母的出现次数，
     * 只需要保存每种字母出现次数的奇偶性。
     * 为方便计算，用 0 表示出现偶数次，用 1 表示出现奇数次。
     *
     *
     * @param s
     * @param queries
     * @return
     */
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {

        int m=s.length();
        int [][] sumCnt=new int[m+1][26];//统计元素个数

        //预处理
        for (int i=0;i<s.length();i++){
            int num=s.charAt(i)-'a';
            sumCnt[i+1]=sumCnt[i].clone();//把上一级的字符统计结果基础过来
            sumCnt[i+1][num]++;//更新当前字符的个数
        }
        List<Boolean> ans=new ArrayList<>();
        for (int[] row:queries){
            int star=row[0];
            int end=row[1];
            int k=row[2];//最多操作k次

            int diff=0;
            for (int i=0;i<26;i++){
                //左右都是闭区间[start,end]因此这里sumCnt[end+1] end需要+1：
                diff+=(sumCnt[end+1][i]-sumCnt[star][i])%2;//奇数+1，偶数加0
            }
            ans.add((diff/2)<=k);
        }

        return ans;
    }

    /**
     * 只需要关心每个字符的奇偶性，不需要关注个数
     * 奇偶性：%2或者使用^亦或判断
     * 异或可以视作「不进位加法（减法）」或者「模 2 剩余系中的加法（减法）」，所以也常常用来解决一些和奇偶性有关的问题。
     * @param s
     * @param queries
     * @return
     */
    public List<Boolean> canMakePaliQueries2(String s, int[][] queries) {

        int m=s.length();
        int [][] sumCnt=new int[m+1][26];//计算某个字符的奇偶性

        //预处理
        for (int i=0;i<s.length();i++){
            int num=s.charAt(i)-'a';
            sumCnt[i+1]=sumCnt[i].clone();//把上一级的字符统计结果基础过来
            sumCnt[i+1][num]++;//更新当前字符的个数
            sumCnt[i+1][num]=sumCnt[i+1][num]%2;//判断奇偶性
//            由于异或运算满足 1 和 0 的结果是 1，而 0 和 0，以及 1 和 1 的结果都是 0，所以可以用异或替换上面的减法。
//            sumCnt[i+1][num]^=1;
        }
        List<Boolean> ans=new ArrayList<>();
        for (int[] row:queries){
            int star=row[0];
            int end=row[1];
            int k=row[2];//最多操作k次

            int diff=0;//计算字符不同的元素个数
            for (int i=0;i<26;i++) {
                //左右都是闭区间[start,end]因此这里sumCnt[end+1] end需要+1：
                //v2字符不同则表示存在一个需要替换的字符：使用亦或优化
                diff += (sumCnt[end + 1][i] != sumCnt[star][i]) ? 1 : 0;
                //v3优化：亦或优化:他俩的范围都是在0、1之间，因此如果不同则结果是1，否则是0
//                diff += sumCnt[end + 1][i] ^ sumCnt[star][i];
            }
            ans.add((diff/2)<=k);
        }
        return ans;
    }

    /**
     * 1371. 每个元音包含偶数次的最长子字符串
     * 思路分析：
     * 1、开始我们想枚举每个子串，然后再计算每个子串中的元音字符个数，但是这种复杂度太高了
     * 2、我们可以利用前缀和数组，sumCnt[i][j] ：表示前i个字符中，每个元音出现的次数(j的长度是5，因为是5个字符)，再次使用两个指针遍历子串，能快速求出他们之间的元音字符的差距
     * 2.1 我们可以不用记录次数：可以记录元素的【奇偶性】，5个元素，使用5个二进制位存储
     * sum(sumCnt[p][j]-sumcnt[p][j])：求最大值 则会就很想我们遍历求一维数组某个操作中 求最大值：遍历右边，维护左边的最小值，则能求出当前位置结尾的最大值
     * 3、之前我们是使用map，但是此题我们可以使用数组：存每个状态的最早的索引i。同时把每个状态
     * 本质是考察二进制表示奇偶性以及对异或运算的理解
     * @param s
     * @return
     */
    public int findTheLongestSubstring(String s) {

        int[] pos = new int[1 << 5];//存储左侧status的索引数组，类似于map
        Arrays.fill(pos, -1);//默认值都是-1
        pos[0] = 0; //设置第一个值
        int ans = 0;
        int status = 0;//亦或的结果值

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a') {
                status ^= 1 << 0;
            } else if (c == 'e') {
                status ^= 1 << 1;//设置第二位上的奇偶性
            } else if (c == 'i') {
                status ^= 1 << 2;//设置第二位上的奇偶性
            } else if (c == 'o') {
                status ^= 1 << 3;//设置第二位上的奇偶性
            } else if (c == 'u') {
                status ^= 1 << 4;//设置第二位上的奇偶性
            }
            if (pos[status] >= 0) {
                //表示左侧遍历过的子数组中 存在这种状态，那么这期间的所有元音则都是
                ans = Math.max(ans, i - pos[status] + 1);
            } else {
                pos[status] = i + 1;//记录 前i个元素的前缀亦或结果 的索引
            }
        }
        return ans;
    }

    /**
     * 1542. 找出最长的超赞子字符串：
     * 子串内容可以组成一个回文串：有两种情况：某一个子串内的元素的个数都是 偶数 因此该子串的亦或结果是0.或者该子串的亦或结果 只有一个二进制位是1，表示只有一个字符是奇数，其他都是偶数
     *
     * 前缀亦或+状态压缩：状态压缩 就是n位数的状态压缩到一个二进制中
     * 回文字符串有奇数和偶数之分
     * 偶数：就是两个值相等
     * 奇数:sum[j]^sum[i]=1<X 表示：当前亦或值 和之前的一个亦或值的结果等于 奇数
     *
     * 总结：其实就是把前n个字符的奇偶性 压缩在一个二进制的数字中，然后在看看已经遍历的左侧是否有这样的子串 或者左侧 有比当前子串多一个字符的子串
     * @param s
     * @return
     */
    public int longestAwesome(String s) {

        Map<Integer, Integer> prefix = new HashMap<>();
        prefix.put(0, -1);//表示亦或结果是0的元素的最左侧下标是-1
        int ans = 0;
        int sequence = 0;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - '0';
            sequence ^= 1 << idx;//记录二进制 第idx位 上面的奇偶性
            if (prefix.containsKey(sequence)) {
                ans = Math.max(ans, i - prefix.get(sequence));
            } else {
                //把当前位置的状态记录下来
                prefix.put(sequence, i);
            }
            //遍历是奇数的情况下
            for (int j = 0; j < 10; j++) {
                if (prefix.containsKey(sequence ^ (1 << j))) {
                    ans = Math.max(ans, i - prefix.get(sequence ^ (1 << j)));
                }
            }
        }
        return ans;
    }

    /**
     * 1915. 最美子字符串的数目
     * 如果某个字符串中 至多一个 字母出现 奇数 次，则称其为 最美 字符串。
     * 解决方案：使用前缀和+状态压缩法：记录每种状态出现的次数
     * @param word
     * @return
     */
    public static long wonderfulSubstrings(String word) {
        Map<Integer, Integer> prefix = new HashMap<>();//记录某种状态出现的次数：也可以使用数组
        prefix.put(0,1);//当前这个意思表示：亦或状态是0的结果有一个，类似前缀和的s[0]=0;
        long ans = 0L;
        int sum = 0;
//        十个小写英文字母组成（'a' 到 'j'）:需要压缩在一个数字中
        for (int i = 0; i < word.length(); i++) {
            int digit = word.charAt(i) - 'a';
            sum ^= 1 << digit;//亦或当前结果

            if (prefix.containsKey(sum)) {
                ans += prefix.get(sum);
            }
            //遍历是否还有一个奇数
            for (int j = 0; j < 10; j++) {
                if (prefix.containsKey(sum ^ (1 << j))) {
                    ans += prefix.get(sum ^ (1 << j));
                }
            }
            //记录前缀和是sum的子数组的个数
            prefix.put(sum, prefix.getOrDefault(sum, 0) + 1);
        }

        return ans;
    }
}
