package interview;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TrainingPlan {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(trainingPlan(new int[]{1,2,3,4,5})));
        System.out.println(Arrays.toString(trainingPlan2(new int[]{1,2,3,4,5})));
    }

    public static int[] trainingPlan2(int[] actions) {
        if (actions.length<=1){
            return actions;
        }
//        int tm ;
//        //双指针法，i从左向右寻找偶数，j从右向左寻找奇数，然后在交换
//        int i = 0, j = actions.length - 1, n = actions.length;
//        while (i < j) {
//            while (i < j && (actions[i] % 2 == 1)) {
//                i++;
//            }
//
//            while (i < j && (actions[j] % 2 == 0)) {
//                j--;
//            }
//          tm= actions[i];
//            actions[i] = actions[j];
//            actions[j] = tm;
//        }
//        return actions;

        //不能保证顺序的稳定性
        int i = 0, j = actions.length - 1, tmp;
        while(i < j) {
            while(i < j && (actions[i] & 1) == 1) i++;
            while(i < j && (actions[j] & 1) == 0) j--;
            tmp = actions[i];
            actions[i] = actions[j];
            actions[j] = tmp;
        }
        return actions;
    }
    public static int[] trainingPlan(int[] actions) {
        if (actions.length <= 1) {
            return actions;
        }
        int n = actions.length;
        int[] result = new int[n];
        int idx = 0;
        for (int i=0;i<n;i++){
            if (actions[i]%2==1){
                result[idx++]=actions[i];
            }
        }
        for (int i=0;i<n;i++){
            if (actions[i]%2==0){
                result[idx++]=actions[i];
            }
        }
        return result;
    }
}
