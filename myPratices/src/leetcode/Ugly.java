package leetcode;

public class Ugly {

    public static void main(String[] args) {
        System.out.println( nthUglyNumber(10));
    }
    /**
     * 263
     * 判断一个数是否是丑数
     * 包含质因数2、3和5的正整数。
     * @param n
     * @return
     */
    boolean isUgly(int n) {

        if (n < 0) {
            return false;
        }
        // 如果 n 是丑数，分解因子应该只有 2, 3, 5
        while (n % 2 == 0) {
            n = n / 2;
        }

        while (n % 3 == 0) {
            n = n / 3;
        }

        while (n % 5 == 0) {
            n = n / 5;
        }

        return n == 1;
    }

    //求第n个丑数，其实就是求多个链表的合并
     static int nthUglyNumber(int n) {

        int ans[] = new int[n + 1];
        int cur = 1;

         // 可以理解为三个有序链表的头节点的值
         int product2 = 1, product3 = 1, product5 = 1;

         //头节点指针
        int p2= 1, p3 = 1, p5 = 1;

        while (cur <= n) {
            int min = Math.min(Math.min(product2, product3), product5);
            ans[cur++] = min;

            //合p1 p2 p3三个链表
            if (min == product2) {
                product2= 2*ans[p2];
                p2++;
            }
            if (min == product3) {
                product3= 3*ans[p3];
                p3++;
            }
            if (min == product5) {
                product5= 5*ans[p5];
                p5++;
            }
        }

        return ans[n];
    }
}
