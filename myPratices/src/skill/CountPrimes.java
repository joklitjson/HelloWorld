package skill;

import java.util.Arrays;

public class CountPrimes {

    public static void main(String[] args) {
        System.out.println(new CountPrimes().countPrimes(10));
    }
    //计算素数的个数
    //循环遍历，逐个检查
    int countPrimes(int n) {

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrimes(i)) {
                System.out.println(i);
                count++;
            }
        }
        return count;
    }

    /**
     * 高效算法
     *
     * 埃拉托斯特尼筛法 Sieve of Eratosthenes 算法
     * @param n
     * @return
     */
    int countPrimes2(int n) {
        boolean [] primes=new boolean[n];
        Arrays.fill(primes,true);

        for (int i=2;i<n;i++){
            //i的k倍数 都不是素数
            if (primes[i]){
                for (int j=2*i;j<n;j+=i){
                    primes[j]=false;
                }
            }
        }
        //统计个数
        int count=0;
        for (int i=2;i<n;i++){
            if (primes[i]){
                count++;
            }
        }

        return count;
    }

    //判断k是否是素数
    private boolean isPrimes(int k) {
        for (int j = 2; j*j <= k; j++) {
            if (k % j == 0) {
                // 有其他整除因子
                return false;
            }
        }
        return true;
    }
}
