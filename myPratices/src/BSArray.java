import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.cnblogs.com/bournet/p/4058998.html
 * 折半枚举（双向搜索）
 * 各有n个整数的四个数列A、B、C、D。要从每个数列中各取一个数，使四个数的和为0。求出这样组合的个数。
 * 折半枚举的思想来源于双向搜索,主要解决的就是当问题规模较大时,无法枚举所有元素的组合,但能枚举一半元素的组合.
 */
public class BSArray {
    static final int MAX_N = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        int[] B = new int[n];
        int[] C = new int[n];
        int[] D = new int[n];

        for (int i = 0; i < n * 4; i++) {
            switch (i / n) {
                case 0:
                    A[i % n] = scanner.nextInt();
                    break;
                case 1:
                    B[i % n] = scanner.nextInt();
                    break;
                case 2:
                    C[i % n] = scanner.nextInt();
                    break;
                case 3:
                    D[i % n] = scanner.nextInt();
                    break;
                default:
                    break;
            }
        }
        solve(A, B, C, D, n);
        scanner.close();
    }

    /**
     * 从4个数列中选择共有n4种情况，全部判断一遍不可行。不过将它们对半分成AB和CD再考虑，就可以快速解决了。
     * 从2个数列中选择的话只有n2种组合，所以可以进行枚举。先从A、B中取出a、b后，为使总和为0需要从C、D中取出c + d = -(a + b)。
     * 因此先将从C、D中取数字的n2种方法全都枚举出来，将这些和排序，进行二分搜索。这个算法复杂度为O(n2logn)。
     * @param A
     * @param B
     * @param C
     * @param D
     * @param n
     */
    public static void solve(int[] A, int[] B, int[] C, int[] D, int n) {
        int[] CD = new int[n * n];
        // 枚举从 C 和 D 中取出数字的所有方法
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                CD[i * n + j] = C[i] + D[j];
            }
        }
        Arrays.sort(CD);
        long res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cd = -(A[i] + B[j]);
                // 取出 C 和 D 中和为 cd 的部分
//                在CD集合中找到 左右下表，相减就能得到区间元素的个数
                res += upperBound(CD, cd) - lowerBound(CD, cd);
            }
        }
        System.out.println(res);
    }

    public static int lowerBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static int upperBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}    