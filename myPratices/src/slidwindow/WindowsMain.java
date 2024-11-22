package slidwindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WindowsMain {

    public static void main(String[] args) {
        System.out.println(new WindowsMain().dismantlingAction1("dbascDdad"));
    }
    /**
     * LCR 167. 招式拆解 I
     * 求连续不重复的子串的最大长度
     * 1、滑动窗口 +双指针
     * 2、滑动窗口+hash
     * @param arr
     * @return
     */
    public int dismantlingAction(String arr) {
        int left = 0, right = 0, n = arr.length();
        Set<Character> set = new HashSet<>();
        int maxLength = 0;
        while (right < n) {
            char c = arr.charAt(right);
            right++;
            while (set.contains(c)) {
                set.remove(arr.charAt(left));
                left++;

            }
            set.add(c);
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }

    public int dismantlingAction1(String arr) {
        int left = -1;//一定要设置成-1
        int right = 0, n = arr.length();
        Map<Character,Integer> map = new HashMap<>();
        int maxLength = 0;
        while (right < n) {
            char c = arr.charAt(right);
            //获取左指针
            if (map.containsKey(c)){
                left=Math.max(left,map.get(c));
            }
            map.put(c,right);
            maxLength = Math.max(maxLength, right - left);
            right++;
        }
        return maxLength;
    }

}
