package leetcode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DivideRedPackageTest {

    public static void main(String[] args) {
        List<Integer> result=divideRedPackage(1000,5);

        for (int value:result){
            System.out.println("抢到的红包金额："+ BigDecimal.valueOf(value).divide(new BigDecimal(100)));
        }
    }

    /**
     * 二倍均值法:次抢到的金额=随机区间[0.01，m/n×2-0.01]元
     * @param amount
     * @param n
     * @return
     */
    public static List<Integer> divideRedPackage(int amount,int n){

        int resAmount=amount;
        int resPeople=n;

        List<Integer> result=new ArrayList<>();
        Random random=new Random();
        for (int i=0;i<n-1;i++){
            int redPackage=random.nextInt(resAmount*2/resPeople-1)+1;
            resAmount-=redPackage;
            resPeople--;
            result.add(redPackage);
        }
        result.add(resAmount);
        return result;
    }
}
