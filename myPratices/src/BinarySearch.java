public class BinarySearch {

    public static void main(String[] args) {
        System.out.println("binarySearch result=="+binarySearch(new int[]{2,5,9,12,43,66,89},4));

        System.out.println("left_bound(小于target元素的数量) result=="+left_bound(new int[]{2,5,12,12,12,66,89},1));

        System.out.println("right_bound(小于等于target元素最大值索引) result=="+right_bound(new int[]{2,5,12,12,12,66,89},1));
    }

    public static int binarySearch(int [] nums,int target){
        int left=0,right=nums.length-1;
        while (left<=right){
            int middle=left+(right-left)/2;
            if (nums[middle]==target){
                return middle;
            }
            else if (nums[middle]<target){
                left=middle+1;
            }
            else if (nums[middle]>target){
                right=middle-1;
            }
        }

        //没有查找到
        return -1;
    }
    public static int left_bound(int [] nums,int target){

        //搜索区间[0,length) 左闭右开
        int left=0,right=nums.length;//注意
        while (left<right){//注意
            int middle=left+(right-left)/2;
            if (nums[middle]==target){
                right=middle;//收缩右边界，由于循环区间while 不含有右边界，因此middle的位置已经被查找过了，
//                 所以这里设置成right=middl ,不会被再次查找
            }
            else if(nums[middle]>target){
                right=middle;//同上
            }
            else if (nums[middle]<target){
                left=middle+1;
            }
        }

        //***********************
        //如果需要返回-1
//        if (left==nums.length){
//            return -1;
//        }
//        return nums[left]==target?left:-1;
        //******************
        //取值范围[0,num.length] 表示小于target元素的数量
        return left;
    }

    public static int right_bound(int [] nums,int target) {

        int left = 0, right = nums.length;

        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] == target) {
                left = middle + 1;//注意：收缩左边界
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle;
            }
        }
//        left 取值范围[0,nums.length]
        //因为left 总是+1,因此总是位于目标值的右侧
        return left - 1;//注意

//       *************************************
        //如果想返回-1
//        if (left == 0) {
//            return -1;
//        }
//        return nums[left - 1] == target ? left - 1 : -1;
        //********************************
    }
}
