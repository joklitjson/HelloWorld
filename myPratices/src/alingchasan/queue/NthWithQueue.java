package alingchasan.queue;

import java.util.*;

/**
 * 使用优先级队列 处理 第k位的数字:
 * 多路归并：多个指针的数字节点 存放在堆中，每次取一个最小值，然后在放入堆中，这样反复的计算（多个链表的合并）
 * 通用方案（堆）：类似数组链接的合并，把每个指针头加入到有序列表中，然后去除最小的指针，然后在把下一个指针放在队列中，这样就能获取到k个最小值
 */
public class NthWithQueue {

    public static void main(String[] args) {
        NthWithQueue test=new NthWithQueue();

//        System.out.println(Arrays.toString(test.kthSmallestPrimeFraction(new int[]{1,2,3,5},3)));

        test.kSum(new int[]{4,2,-1,-7},5);
    }
    /**
     * 264. 丑数 II
     * 方案：使用小跟堆，把第一个最小的数字放在队列，然后每次取一个最小的数字，让这个最小的数字 和 2、3、5在进行乘机，然后加入到队列中（如果已经入队列，可以不在加入）
     * 重复此过程，则就能获取到最小的n个丑数
     * 每次取出堆顶元素 x，则 x 是堆中最小的丑数，由于 2x,3x,5x 也是丑数，因此将 2x,3x,5x 加入堆。[注意去重]
     * @param n
     * @return
     */
  public   static int nthUglyNumber(int n) {

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

    /**
     *方案二：使用多路归并的方式
     * 由丑数×2所得的有序序列：1×2、2×2、3×2、4×2、5×2、6×2、8×2...
     * 由丑数×3所得的有序序列：1×3、2×3、3×3、4×3、5×3、6×3、8×3...
     * 由丑数×5所得的有序序列：1×5、2×5、3×5、4×5、5×5、6×5、8×5...
     *
     * 使用三个指针：依次把三个指针的 结果进行比较
     * 把这三个队列的值 依次加入到结果列中,m
     * @param n
     * @return
     */
    public   static int nthUglyNumber2(int n) {
        //使用 n+1的长度，第一个是空的
        int ans[]=new int[n+1];
        ans[1]=1;//第一个

        int index=2;//计算第二位的值

        for (int idx2=1,idx3=1,idx5=1;index<=n;index++){
            int p2=2*ans[idx2];

            int p3=3*ans[idx3];

            int p5=5*ans[idx5];
            //三个指针的最小值
            int min=Math.min(p2,Math.min(p3,p5));

            //这里需要使用if ，不能使用else if 因为 他们的结果可能相等
            if (min==p2){
                idx2++;
            }

            if (min==p3){
                idx3++;
            }

            if (min==p5){
                idx5++;
            }
            ans[index]=min;
        }
        return ans[n];
    }


    /**
     * 378. 有序矩阵中第 K 小的元素
     * 方案一：把二维转换成一维，然后在排序，在计算值(缺点：没利用数组)
     * 方案二：使用多路归并：把每一行数组 单独独立出来，然后放在小跟堆中，每次取最小的一个，然后在放在堆中，这样执行k-1次，则堆顶的就是第k小了
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        int m=matrix.length,n=matrix[0].length;
        //小跟堆
        PriorityQueue<int[]> minQueue=new PriorityQueue<>((a,b)->a[0]-b[0]);

        for (int i=0;i<matrix.length;i++){
            //三元组：值|行|列
            minQueue.offer(new int[]{matrix[i][0],i,0});
        }
        while (k>1) {
            int[] p = minQueue.poll();
            //不是最后一列：则还能继续入多列
            if (p[2]!=n-1){
                p[2]++;
                p[0]=matrix[p[1]][p[2]];//取下一个值
                minQueue.offer(p);
            }

            k--;
        }
        return minQueue.peek()[0];
    }


    /**
     * 373. 查找和最小的 K 对数字
     * 方案分析：使用小跟堆获取最小值，假设当前最小值的坐标是(i,j)，那么我们只需要把（i,j+1）放在堆中即可(不把i+1,j 放进去，防止重复)
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {

//        List<List<Integer>> ans = new ArrayList<>(k);
//        PriorityQueue<int[]> minQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
//        //初始化:把第一个数组的每一个数组都跟 第二个数组的第一个数字配对
//        for (int i = 0; i < Math.min(nums1.length, k); i++) {
//            minQueue.offer(new int[]{nums1[i] + nums2[0], i, 0});
//        }
//
//        while (ans.size() < k) {
//            int[] p = minQueue.poll();
//            int i = p[1];
//            int j = p[2];
//            List<Integer> tmp = new ArrayList<>();
//            tmp.add(nums1[i]);
//            tmp.add(nums2[j]);
//            ans.add(tmp);
//            if (j + 1 < nums2.length) {
//                minQueue.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
//            }
//        }
//        return ans;

        // 方案二：可以在遍历的过程中 逐个加入元素
        List<List<Integer>> ans = new ArrayList<>(k);
        PriorityQueue<int[]> minQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        minQueue.offer(new int[]{nums1[0]+nums2[0],0,0});
        while (ans.size() < k) {
            int[] p = minQueue.poll();
            int i = p[1];
            int j = p[2];
            List<Integer> tmp = new ArrayList<>();
            tmp.add(nums1[i]);
            tmp.add(nums2[j]);
            ans.add(tmp);
            //加入i的下一个元素和 j=0的配对（i滚动的向后加入队列）
            if (j==0&&i + 1 < nums1.length) {
                minQueue.offer(new int[]{nums1[i+1] + nums2[0], i+1, 0});
            }

            //只需要加入(i,j+1)个元素
            if (j + 1 < nums2.length) {
                minQueue.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }
        return ans;
    }


    /**
     * 2386. 找出数组的第 K 大和
     * 堆贪心
     * 分析：数组有正、有负，因此 第一最大值 就是所有整数的和，第二最大值 就是sum-(第一最小值)，第三最大值sum-(第二最小值)
     * 因此：问题转换成了 求 数组的第k-1个最小值的子序列的和，我们把数组中的负数先转成正数，然后在进行排序(递增)，在使用优先级队列 每次获取子序列的最小
     * 值，然后在生成下一个子序列，在获取
     * @param nums
     * @param k
     * @return
     */
    public long kSum(int[] nums, int k) {

        int n = nums.length;
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                sum += nums[i];
            } else {
                //翻转
                nums[i] = -nums[i];
            }
        }

        //排序
        Arrays.sort(nums);

        PriorityQueue<long[]> minQueue = new PriorityQueue<long[]>((a, b) -> (int) (a[0] - b[0]));

        minQueue.offer(new long[]{0, 0});
        System.out.println("寻找最小子序列的和-----"+Arrays.toString(nums));

//        while (k-- > 1) {
//            long[] p = minQueue.poll();
//            long t = p[0];
//            int i = (int) p[1];
//            System.out.println("当前最小和是"+t+";index="+i);
//        满足子序列每个元素选或不选的要求
//            if (i < n) {
//                minQueue.offer(new long[] { t + nums[i ], i + 1 });// 只往序列中添加元素
//                if (i > 0) {
//                    minQueue.offer(new long[] { t + nums[i] - nums[i -1], i + 1 });// 去掉序列中倒数第二大的元素,添加下一个
//                }
//            }
//        }


        long ret=0;
        for (int j = 2; j <= k; j++) {
            long[] arr = minQueue.poll();
            long t = arr[0];
            int i = (int) arr[1];
            System.out.println("当前最小和是"+t+";index="+i);
            ret = t;
            if (i == n - 1) {
                continue;
            }
            minQueue.offer(new long[]{t + nums[i + 1], i + 1});
            minQueue.offer(new long[]{t - nums[i] + nums[i + 1], i + 1});
        }

        //减去第k小的子序列的和
        return sum - minQueue.peek()[0];
    }

    /**
     * 786. 第 K 个最小的质数分数
     * 方案：优先级队列，把每个数 和第一个数字进行 配对：当做n-1条链表 进行合并
     * @param arr
     * @param k
     * @return
     */
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {

        //把除法的比较 转换成乘法比较
        PriorityQueue<int[]> minQueue = new PriorityQueue<>((a, b) -> arr[a[0]] * arr[b[1]] - arr[b[0]] * arr[a[1]]);

        //默认填充(i,j) 到后面裂变 只能(i+1,j)

        //把每个数 和第一个数字进行 配对：当做n-1条链表 进行合并
        for (int i = 1; i < arr.length; i++) {
            minQueue.offer(new int[]{0, i});
        }
        while (k-- > 1) {
            int[] p = minQueue.peek();
            System.out.println(arr[p[0]]+","+arr[p[1]]);
            minQueue.poll();
            if (p[0]+1<p[1]){
                p[0]++;//向后一个更大的转变
                minQueue.offer(p);
            }
        }

        return new int[]{arr[minQueue.peek()[0]],arr[minQueue.peek()[1]]};
    }
}
