/**
 * 技巧：遍历二维数组，有时候需要设置vis数组记录是否被访问，可以使用原数组，把值设置成负数、0之类的表示已经被访问了
 * 技巧二：求一个数组中所有元素左边和右边的最大值，只需要正序遍历一次，在倒序遍历一次即可
 */
public class Skill {

    public static void main(String[] args) {
        getFirstMaxAndSecondMax();
    }

    /**
     * 获取一维数组的 前两个最大值或前两个最小值
     */
    public static void getFirstMaxAndSecondMax() {
        int[] array = {11, 2, 0,99,77,3, 4, 5};
        int firstMax = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > firstMax) {
                secondMax = firstMax;
                firstMax = array[i];
            } else if (array[i] > secondMax && array[i] != firstMax) {
                secondMax = array[i];
            }
        }

        System.out.println("第一大的值: " + firstMax);
        System.out.println("第二大的值: " + secondMax);
    }

    /**
     * 求一个数组的左边和右边的最大值
     * @param arr
     * @return
     */
    public static int[][] calculateMax(int[] arr) {
        int n = arr.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // 计算左边最大值数组
        leftMax[0] = arr[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], arr[i]);
        }

        // 计算右边最大值数组
        rightMax[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], arr[i]);
        }

        return new int[][]{leftMax, rightMax};
    }
}
