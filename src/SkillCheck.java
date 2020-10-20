import java.util.ArrayList;
import java.util.List;

/**
 * Created by secr3t on 2020/10/20 at 14:23
 *
 * Email : dev.secr3t@gmail.com
 */
public class SkillCheck {

    public static void main(String[] args) {
        new SkillCheck().solution(2);
    }

    public int[][] solution(int n) {
        int[][] answer;
        List<Integer[]> a = new ArrayList<>();
        hanoi(n, 1, 2, 3, a);
        answer = new int[a.size()][2];
        for (int i = 0; i < a.size(); i++) {
            answer[i][0] = a.get(i)[0];
            answer[i][1] = a.get(i)[1];
        }
        return answer;
    }

    public void hanoi(int n, int start, int via, int to, List<Integer[]> a) {
        if (n == 1) {
            move(1, start, to, a);
        } else {
            hanoi(n-1, start, to, via, a);
            move(n, start, to, a);
            hanoi(n-1, via, start, to, a);
        }
    }

    public void move(int n, int start, int to, List<Integer[]> a) {
        a.add(new Integer[] {start, to});
    }
}
