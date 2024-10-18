package dp;

public class MinDistance {

    public static void main(String[] args) {
        System.out.println(minDistance("rad","apple"));
        System.out.println(dpFuncation("le","apple",1,4));
    }


    static  int dpFuncation(String s1,String s2,int left,int right){
        if (left<0){
            return right+1;
        }
        if (right<0){
            return left+1;
        }

        if (s1.charAt(left)==s2.charAt(right)){
            return dpFuncation(s1,s2,left-1,right-1);
        }
        else {
            return min(dpFuncation(s1,s2,left-1,right),//删除
                    dpFuncation(s1,s2,left-1,right-1),//替换
                    dpFuncation(s1,s2,left,right-1))+1;//插入
        }
    }

    static int min(int a,int b,int c) {
        return Math.min(a, Math.min(a, b));
    }
    public static int minDistance(String s1,String s2){

        int m=s1.length(),n=s2.length();
        //定义dp数组，函数：s[i]的场地，变成s2[j]的长度需要的操作步骤
        //基本case s1长度==0时，需要变成s2任意长度 需要的的步骤就是s2[j]的长度
        //base case s2.length()==2时，s1的任意长度字符变成s2的长度就是s1的长度

//        dp[i][j] 代表 word1 中前 i 个字符，变换到 word2 中前 j 个字符，最短需要操作的次数
        int [][]dp=new int[m+1][n+1];
        //两则都是
        dp[0][0]=0;
        for (int i=0;i<=m;i++){
            dp[i][0]=i;
        }

        for (int j=0;j<=n;j++){
            dp[0][j]=j;
        }

        //先假设 前面的数据已经求出来了
        for (int i=1;i<=m;i++){
            for (int j=1;j<=n;j++){
                if (s1.charAt(i-1)==s2.charAt(j-1)){
                    //相等 就直接比较下一个原始
                    dp[i][j]=dp[i-1][j-1];
                }
                else{
                    //比较这三者的最小值
                    dp[i][j]=Math.min(dp[i-1][j],Math.min(dp[i][j-1],dp[i-1][j-1]))+1;
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
//    public static  int
}
