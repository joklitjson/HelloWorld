/**
 * 技巧：遍历二维数组，有时候需要设置vis数组记录是否被访问，可以使用原数组，把值设置成负数、0之类的表示已经被访问了
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
}
