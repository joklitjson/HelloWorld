package fenzhi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiffWaysToCompute {

    public static void main(String[] args) {
        System.out.println(new DiffWaysToCompute().diffWaysToCompute("1+2*3-4*5"));
    }

    // 计算所有加括号的结果
    // 把表达式按字符串中的运算符号进行分开，分别计算坐标的接口，和右边的结果，然后在把左右的结果集合
    //按运算法进行运算，则可以得到一个大的运算结果
   public List<Integer> diffWaysToCompute(String input){

       List<Integer> res=new ArrayList<>();
       for(int i=0;i<input.length();i++){
           char c= input.charAt(i);
           if (c=='+'||c=='-'||c=='*'){
               /******** 分*************/
               List<Integer> leftRes=diffWaysToCompute(input.substring(0,i));
               /******** 分*************/
               List<Integer> rightRes=diffWaysToCompute(input.substring(i+1));

               /******** 治(合并结果)*************/
               for (int a:leftRes){
                   for (int b:rightRes){
                       if (c=='+'){
                           res.add(a+b);
                       }
                       else if (c=='-'){
                           res.add(a-b);
                       }
                       else if (c=='*'){
                           res.add(a*b);
                       }
                   }
               }
           }
       }
//       base case 分割的子运算结果只有一个数字，没有运算符
       if (res.isEmpty()){
           res.add(Integer.valueOf(input.trim()));
       }
       return res;
    }


    /**
     * LCR 121. 寻找目标值 - 二维数组
     * 解决方案：原题从左到右是递增的，从上到下也是递增的，但是我们发现一个规律左下角和右上角：向两边的元素的规律是相反的：一个递增一个递减
     * 于是我们从左下角开始搜索二维区间
     * if(plant[i][j]>target) i--;
     * if(plant[i][j])<target;j++
     * @param plants
     * @param target
     * @return
     */
    public boolean findTargetIn2DPlants(int[][] plants, int target) {
        int m = plants.length;
        if (m==0){
            return false;
        }
        int n=plants[0].length;

        int i = plants.length - 1, j = 0;

        while (i >= 0 && j < n) {
            if (plants[i][j] < target) {
                j++;
            }
            else if (plants[i][j] > target) {
                i--;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * LCR 120. 寻找文件副本
     * set
     * @param documents
     * @return
     */
    public int findRepeatDocument(int[] documents) {

        Set<Integer> set = new HashSet<>();

        for (int val : documents) {
            if (set.contains(val)) {
                return val;
            }
            set.add(val);
        }

        return -1;
    }
}
