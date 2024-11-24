package binary;

public class SingleNonDuplicate {

    /**
     * 有序数组中的单一元素
     * 方案零：直接使用map 统计数组个数:但是缺少了利用数组有序性的条件
     * 方案一：直接遍历数组，判断当前元素和他下一个元素是否相等
     * 方案二：遍历数组，使用亦或，最终的数字就是那个 单一元素
     * 方案三：使用二分法：正常情况下成对元素中的第一个所对应的下标必然是偶数，成对元素中的第二个所对应的下标必然是奇数。
     * 但是由于单一元素破坏了这个规律，因此单一元素左边的元素还保持此规律，但是单一元素右边的原则 则变成了相反的情况
     * 因此我们判断 middle 是奇数还是偶数，然后在判断是属于那种规律，在决定middle 是属于x的左边还是右边，在搜紧left、right的值
     * @param nums
     * @return
     */
    public int singleNonDuplicate(int[] nums) {
   int n = nums.length;
//        for (int i=0;i< n-1;i+=2){
//            if (nums[i]!=nums[i+1]){
//                return nums[i];
//            }
//        }
//        return nums[n-1];

        //方案二：使用亦或的方法
//        int result=0;
//        for (int value:nums){
//            result^=value;
//        }
//        return result;

        //方案三：二分法

        int l = 0, r = n - 1;

        while (l < r) {
            int middle = (r - l) / 2 + l;
//            if (middle % 2 == 0) {
//                //说明当前 可能在左边，因此判断下是否符合以上规律
//                if (middle + 1 < n && nums[middle] == nums[middle + 1]) {
//                    l = middle + 1;
//                } else {
//                    //
//                    r = middle;
//                }
//            } else {
//                //奇数位置，判断他的左边是否和他相同，如果相同，则说明当前位置符合规律一
//                if (middle - 1 >= 0 && nums[middle] == nums[middle - 1]) {
//                    l = middle + 1;
//                } else {
//                    r = middle;
//                }
//            }

            //优化点：使用^1，则是表示把这个数字进行+1或-1，偶数情况下加一，奇数情况下减一，(与1按位亦或只影响最后一位)
            if (nums[middle] == nums[middle ^ 1]) {
                l = middle + 1;
            } else {
                r = middle;
            }
        }
        return nums[r];
    }
}
