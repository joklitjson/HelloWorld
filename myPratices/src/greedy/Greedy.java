package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Greedy {

    public static void main(String[] args) {
        System.out.println(getMaxValue(new int[]{9,1,2,44,23,60,12}));
    }
    /**
     * 双指针 求数组最大值:从两侧进入比较
     * 也可以求最小值
     * @param A
     * @return
     */
    public static int getMaxValue(int[] A/*输入保证非空*/) {
        final int N = A == null ? 0 : A.length;
        int i = 0, j = N - 1;
        while (i < j) {
            if (A[i] > A[j]) {
                j--;
            } else {
                i++;
            }
        }
        return A[i];
    }

    /**
     * 分隔字符串，让相同字符的分在一个分段中
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        int[] lastPos = new int[26];
        List<Integer> ans = new ArrayList<>();
        //记录每个字符的最后位置
        for (int i = 0; i < s.length(); i++) {
            lastPos[s.charAt(i) - 'a'] = i;
        }

        // 记录start、end区间

        int start = 0, end = 0, i = 0;
        while (i < s.length()) {
            end = Math.max(end, lastPos[s.charAt(i) - 'a']);
            //当前位置i 是区间[strat,end]区间内所有字符的最右侧区间
            if (i == end) {
                ans.add(end - start + 1);
                //新开启一个区间
                start = end + 1;
            }
            i++;
        }
        return ans;
    }

    /**
     * 环形加油站
     * 使用贪心算法
     * @param G
     * @param C
     * @return
     */
    int canCompleteCircuit(int[] G, int[] C) {

        int left = 0,total=0;
        int start = 0;//先从第一个点开始,
        int ans = 0;
//        如果当前加油站补充的油小于当前行程需要的油，则需要新开起点

        for (; start < G.length; start++) {
            int add = G[start];
            int cost = C[start];
            total+=add-cost;
            if (left + add >= cost) {
                left = left + add - cost;
            } else {
                //从下个起点开始重新 计算 是否满足条件
                left = 0;
                ans = start + 1;
            }
        }
        return total >= 0 ? ans : -1;
    }

    /**
     * 611. 有效三角形的个数
     * 方案1：先排序，然后在循环数组，定义为i，然后在第二个循环中定义j=i+1， 在 j,n的范围内 二分查找 arr[i]+arr[j] 的最小左边界k,然后 n-k 就是有效的数量
     * 方案二：排序+双指针 类似方案一，我们可以把他取名叫做三指针问题,先排序，然后在进行两次循环，第二个数和第三个数 我们使用指针查找，一直找到合适的数字
     *
     * @param nums
     * @return
     */
    public int triangleNumber(int[] nums) {

        Arrays.sort(nums);
        int n=nums.length;
        int ans=0;
        for (int i=0;i<n;i++){
            for (int j=i+1;j<n;j++){
                //最左侧二分查找
                int left=j+1,right=n-1, k=left;
                while (left<=right){
                    int middle=(left+right)/2;
                    if (nums[middle]<nums[i]+nums[j]){
                        k=middle;
                        left=middle+1;
                    }
                    else {
                        right=middle-1;
                    }
                }
                //这区间的都是答案
                ans+=k-j;
            }
        }

        //排序+双指针
        for (int i=0;i<n;i++){
            for (int j=i+1;j<n;j++) {
                int k = j + 1;
                //寻找到满足条件的k
                while (k + 1 < n && nums[k + 1] < nums[i] + nums[j]) {
                    k++;
                }
                //这区间的都是正确值
                ans += Math.max(k - j, 0);
            }
        }

        return ans;

    }

}
