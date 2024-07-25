package leetcode;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Ugly {

    public static void main(String[] args) {
        System.out.println(nthUglyNumber(11));
        System.out.println(nthUglyNumber2(11));

        System.out.println(nthSuperUglyNumber(11, new int[]{2, 3, 5}));
        System.out.println(nthSuperUglyNumber2(11, new int[]{2, 3, 5}));
    }

    /**
     * 263
     * 判断一个数是否是丑数
     * 包含质因数2、3和5的正整数。
     *
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

    //

    /**
     * 求第n个丑数，其实就是求多个链表的合并
     *
     * [不能进行简单的相加]
     * @param n
     * @return
     */
    static int nthUglyNumber(int n) {

        int ans[] = new int[n + 1];
        int cur = 1;

        // 可以理解为三个有序链表的头节点的值
        int product2 = 1, product3 = 1, product5 = 1;

        //头节点指针
        int p2 = 1, p3 = 1, p5 = 1;

        while (cur <= n) {
            int min = Math.min(Math.min(product2, product3), product5);
            ans[cur++] = min;

            //合p1 p2 p3三个链表
            if (min == product2) {
                product2 = 2 * ans[p2];
                p2++;
            }
            if (min == product3) {
                product3 = 3 * ans[p3];
                p3++;
            }
            if (min == product5) {
                product5 = 5 * ans[p5];
                p5++;
            }
        }
//        System.out.println(Arrays.toString(ans));
        return ans[n];
    }

    /**
     * 每次取出堆顶元素 x，则 x 是堆中最小的丑数，由于 2x,3x,5x 也是丑数，因此将 2x,3x,5x 加入堆。[注意去重]
     * @param n
     * @return
     */
    static int nthUglyNumber2(int n) {

        int[] factors = {2, 3, 5};
        HashSet see = new HashSet();
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(1L);
        Long ugly = 0L;
        for (int i = 0; i < n; i++) {
            ugly = priorityQueue.poll();
            for (int fac : factors) {
                Long value = ugly * fac;
                if (see.add(value)) {
                    priorityQueue.offer((long) value);
                }
            }
        }

        //获取顶部元素
        return ugly.intValue();
    }


    //n个质数的丑数
    static int nthSuperUglyNumber(int n, int[] primes) {
        //优先级队列存放三元组 prime,i,p：质数，当前质数的n个元素，p第n个元素
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // 优先级队列按照节点的值排序
                return o1[0] - o2[0];
            }
        });

        for (int val : primes) {
            queue.add(new int[]{1, val, 1});
        }
        int[] ugls = new int[n + 1];
        int p = 1;
        while (p <= n) {
            int[] min = queue.poll();
            int product = min[0];
            int prime = min[1];
            int index = min[2];

            //判断是否重复
            if (product != ugls[p - 1]) {
                ugls[p] = product;
                p++;
            }
            // 生成下一个节点加入优先级队列
            //prime * (ugls[index]) 取上一次这个质数的指针指向对应的元素，然后再乘以prime，表示下一个链表的节点
            queue.add(new int[]{prime * (ugls[index]), prime, index + 1});
        }

        return ugls[n];
    }

    static int nthSuperUglyNumber2(int n, int[] primes) {
        //优先级亿元 prime
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // 优先级队列按照节点的值排序
                return o1[0] - o2[0];
            }
        });

        for (int val : primes) {
            queue.add(new int[]{val, val});
        }
        int[] ugls = new int[n + 1];
        int p = 0;
        ugls[p++] = 1;
        while (p <= n) {
            int[] min = queue.poll();
            int product = min[0];
            int prime = min[1];

            //判断是否重复
            if (product != ugls[p - 1]) {
                ugls[p++] = product;
            }
            // 生成下一个节点加入优先级队列
            //prime * (ugls[index]) 取上一次这个质数的指针指向对应的元素，然后再乘以prime，表示下一个链表的节点
            queue.add(new int[]{prime + product, prime});
        }

        return ugls[n - 1];
    }

    public int nthUglyNumber(int n, int a, int b, int c) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // 优先级队列按照节点的值排序
                return o1[0] - o2[0];
            }
        });

        queue.add(new int[]{a, a});
        queue.add(new int[]{b, b});
        queue.add(new int[]{c, c});


        int p = 1;//没有了1，因此此处设置成1，下面有个p-1的比较

        long minValue = -666;

        while (p <= n) {
            int[] min = queue.poll();
            int product = min[0];
            int prime = min[1];
            minValue=Math.min(minValue,product);

            // 生成下一个节点加入优先级队列
            //prime * (ugls[index]) 取上一次这个质数的指针指向对应的元素，然后再乘以prime，表示下一个链表的节点
            queue.add(new int[]{prime + product, prime});
        }
        return (int) minValue;
    }
}
