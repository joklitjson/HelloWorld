package leetcode;

public class RemoveKDigitsTest {

    public static void main(String[] args) {
        System.out.println(removeKDigits("123",2));
    }
    public static String removeKDigits(String num,int k) {
        if (num.length()<=k){
            return "0";
        }
        String copy = num;


        // 删除一个数字，如果后面的数字小于这个数，则可以删除，因为可以使用更小的数接替自己的位置，所以可以更小

        while (k > 0) {
            if (copy.startsWith("0")){
                copy=copy.substring(1);
            }
            if (copy.length()<=k){
                return "0";
            }
            int i=0;
            //优化点1：事实上，我们应该停留在上一次删除的位置继续进行比较，而不是再次从头开始遍历。
            while (i<=copy.length()-2){
                if (Integer.valueOf(copy.charAt(i)) > Integer.valueOf(copy.charAt(i+1))){
                    break;
                }
                i++;
            }
            copy = copy.substring(0, i) + copy.substring(i+1);
            k--;
        }
        return copy;
    }
}
