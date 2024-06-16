package array;

import java.util.Arrays;

//差分数组
public class Difference {

    int[] differ;
    public Difference() {}
    public Difference(int[] nums) {
        differ = new int[nums.length];
        differ[0] = nums[0];
        //构造差分数组
        for (int i = 1; i < nums.length; i++) {
            differ[i] = nums[i] - nums[i - 1];
        }
    }

    public void increase(int i, int j, int val) {

        differ[i] += val;

        //j后面的数 应该减去value,注意数组是否越界
        if (j + 1 < differ.length) {
            differ[j + 1] -= val;
        }
    }

    public int[] getResult() {

        int[] result = new int[differ.length];

        result[0] = differ[0];
        for (int i = 1; i < differ.length; i++) {
//            当前元素==前一个元素+差分的值
            result[i] = result[i - 1] + differ[i];
        }

        return result;
    }

    //    区间的加法
    int[] getModifiedArray(int length, int[][] updates) {
        int[] arr = new int[length];
        Difference difference = new Difference(arr);

        for (int[] ope : updates) {
            difference.increase(ope[0], ope[1], ope[2]);
        }

        return difference.getResult();
    }


    //航班预定，在i~j个航班预定了k个位置，返回全部预定结果
    int[] corpFlightBookings(int[][] bookings, int n){

        Difference difference=new Difference(new int[n]);

        for (int [] book:bookings){
            difference.increase(book[0],book[1],book[2]);
        }
        int [] resut=difference.getResult();
        return resut;
    }


    //公交乘车，车站最多1000个站
    boolean carPooling(int[][] trips, int capacity) {

        Difference difference = new Difference(new int[1001]);

        for (int[] trip : trips) {
            int count = trip[0];
            int from = trip[1];
            int end = trip[1] - 1;//一直加到下车的前一站

            difference.increase(from, end, count);
        }

        int[] res = difference.getResult();
        for (int val : res) {
            if (val > capacity) {
                return false;
            }
        }

        return true;
    }

        public static void main(String[] args) {
        Difference difference = new Difference(new int[2]);

        int[] result = difference.getModifiedArray(5, new int[][]{{1, 3, 2}, {2, 4, 3}, {0, 2, -2}});
        System.out.println("差分数组result===" + Arrays.toString(result));

        result = difference.corpFlightBookings(new int[][]{{1, 2, 10}, {2, 3, 20}, {2, 5, 25}}, 5+1);
        System.out.println("航班预定result===" + Arrays.toString(result));
    }
}
