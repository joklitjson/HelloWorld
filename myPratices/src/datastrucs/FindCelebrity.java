package datastrucs;

import java.util.LinkedList;
import java.util.List;

public class FindCelebrity {

    // 可以直接调用，能够返回 i 是否认识 j
    boolean knows(int i, int j){

        return true;
    }
    //1暴力破解
    // 请你实现：返回「名人」的编号
    int findCelebrity(int n) {
        for (int cand=0;cand<n;cand++){
            int other;
            for (other=0;other<n;other++){
                if (cand==other){
                    continue;
                }
                //候选人认识其他人或者 其他人不认识候选者，则跳过
                if (!knows(other,cand)||knows(cand,other)){
                    continue;
                }
            }
            //说明以上的内层循环都走完了，满足条件
            if (other==n){
                return cand;
            }
        }

        //没有人认识
        return -1;
    }

    //翻转思路，使用排除法，排除非候选人的人员:候选人认识其他人或其他人不认识候选者，需要淘汰候选者
    int findCelebrity2(int n) {
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            queue.add(i);
        }

        //逐个淘汰
        while (queue.size() >= 2) {
            int cand = queue.removeFirst();
            int other = queue.removeFirst();

            if (knows(cand, other) || !knows(other, cand)) {
                //淘汰cand,添加other,此时other可能是名人
                queue.add(other);
            } else {
                queue.add(cand);
            }
        }
        //最后队列只剩一个元素，再次遍历下 验证下是否是的
        int cand = queue.removeFirst();

        for (int other = 0; other < n; other++) {
            if (knows(cand, other) || !knows(other, cand)) {
                return -1;
            }
        }
        return cand;
    }

    int findCelebrity3(int n) {
        //先假设0号是名人
        int cand = 0;
        for (int other = 1; other < n; other++) {
            if (!knows(other, cand) || knows(cand, other)) {
                //此种情况下cand 不可能是名人了，因此把other设置成名人
                cand = other;
            } else {
                //cand 还可能是名人，先不动
            }
        }

        //再次验证下 cand是否是名人
        for (int other = 0; other < n; other++) {
            if (cand == other) continue;
            if (knows(cand, other) || !knows(other, cand)) {
                return -1;
            }
        }
        return cand;
    }
}
