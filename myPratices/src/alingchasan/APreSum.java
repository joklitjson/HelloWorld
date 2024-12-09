package alingchasan;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 性质 1：从左到右累加 d 中的元素，可以得到数组 a。
 *
 * 性质 2：如下两个操作是等价的。
 *
 * 把 a 的子数组 a[i],a[i+1],…,a[j] 都加上 x。
 * 把 d[i] 增加 x，把 d[j+1] 减少 x。
 * 利用性质 2，我们只需要 O(1) 的时间就可以完成对 a 的子数组的操作。最后利用性质 1 从差分数组复原出数组 a。
 *
 * 注：也可以这样理解，d[i] 表示把下标 ≥i 的数都加上 d[i]。
 */
public class APreSum {

    public static void main(String[] args) {
        System.out.println(numberOfSubarrays(new int[]{1,1,2,1,1},3));
    }
    /**
     * 1248. 统计「优美子数组」
     * 个连续子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     *
     * 请返回这个数组中 「优美子数组」 的数目。
     * 解决方案：定义一个前缀奇数和的数组，然后向右遍历，维护左边的索引
     * @param nums
     * @param k
     * @return
     */
    public static int numberOfSubarrays(int[] nums, int k) {

        // 数组 prefixCnt 的下标是前缀和（即当前奇数的个数），值是前缀和的个数。
        int[] prefixCnt = new int[nums.length + 1];
        prefixCnt[0]=1;//
        int evenCnt=0;
        int ans=0;
        for (int i=0;i<nums.length;i++){
            if (nums[i]%2==1){
                evenCnt++;
            }
            prefixCnt[evenCnt]++;//记录左侧前缀和等于 enentCnt的子数组的个数
            if (evenCnt >= k) {
                ans += prefixCnt[evenCnt - k];
            }
        }
        return ans;
    }
}
