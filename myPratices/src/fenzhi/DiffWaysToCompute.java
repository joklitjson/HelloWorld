package fenzhi;

import java.util.ArrayList;
import java.util.List;

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


}
