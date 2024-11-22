package bit;

import java.util.Arrays;

/**
 * 位运算总结
 * ^异或运算：同一个比特位相同是0，相异是1，结论：相同的数异或的结果是0，不相同的数异或结果是非零
 * | 或运算：有一个1就是1，全是0则是0
 */
public class BitMan {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new BitMan().sockCollocation(new int[]{4, 5, 2, 4, 6, 6})));
    }
    /**
     * LCR 177. 撞色搭配
     * 一组数中只有两个元素是一个，其他元素都是有两个，求这两个元素是什么
     * 1、使用异或运算：求出一个结果result
     * 2、求出 resut二进制中的第一个1，这个1表明 需要求出的那两个数在此位置上bit位是不同的， 因此可以使用这个数字把 数组 分成两拨，同时这两个数字也会分成在两组中
     * 3、再次使用异或进行分组：
     * @param sockets
     * @return
     */
    public int[] sockCollocation(int[] sockets) {

        int result = 0;
        for (int value : sockets) {
            result ^= value;
        }

        int i = 1;
        while (i < 32) {
            //获取第一个非零的位置
            if (((1 << i) & result) > 0) {
                break;
            }
            i++;
        }

        //使用第 i位进行分组
        int a = 0, b = 0;
        for (int value : sockets) {
            //把元素进行了分组
            if (((value >> i) & 1) > 0) {
                a ^= value;
            } else {
                b ^= value;
            }
        }
        return new int[]{a, b};
    }


    /**
     *LCR 178. 训练计划 VI
     * 其实还是位运算：数组中每个数 有3个一样的数，只有一个数是单个：
     * 1、我们把所有的数转成 二进制进行上下对齐，
     * 2、然后再把相同位的数字相加
     * 3、在进行按3取余：因为相同的数a有三个，因此在某个二进制位上的1的个数肯定是3的倍数，所以他们的和 取余之后肯定是单个数字在二进制位上的和
     * @param actions
     * @return
     */
    public int trainingPlan(int[] actions) {
        int count[] = new int[32];
        for (int value : actions) {
            for (int i = 0; i < 32; i++) {
                count[i] += value & 1;//更新第i位 1的个数
                value = value >> 1;//继续比较下一位
            }
        }

        //从高位开始
        int ans = 0;
        for (int i = 31; i >= 0; i--) {
            ans = ans << 1;//进行进位
            ans |= count[i] % 3;// 恢复第i位上的数字，和之前的拼接在一起
        }
        return ans;
    }
}
