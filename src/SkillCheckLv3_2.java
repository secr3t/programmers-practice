import java.util.ArrayList;
import java.util.List;

/**
 * Created by secr3t on 2020/10/20 at 14:44
 *
 * Email : dev.secr3t@gmail.com
 */
public class SkillCheckLv3_2 {

    public static void main(String[] args) {
        final int a = new SkillCheckLv3_2().solution("hit", "cog", new String[] {
            "hot", "dot", "dog", "lot", "log", "cog"
        });
        System.out.println(a);
    }

    public int solution(String begin, String target, String[] words) {
        if(!hasTarget(target, words)) {
            return 0;
        }

        List<Integer> answers = new ArrayList<>();
        List<List<String>> ss = new ArrayList<>();
        int len = words.length;
        for(int i = 0; i < len; i++) {
            if (canConvert(begin, words[i])) {
                boolean[] v = new boolean[len];
                v[i] = true;
                List<String> s = new ArrayList<>();
                s.add(words[i]);
                if (target.equals(words[i])) {
                    return 1;
                }
                dfs(words[i], v, words, 1, answers, target, s, ss);
            }
        }

        return ss.stream().map(List::size).min(Integer::compareTo).orElse(0);
    }

    public void dfs(String start, boolean[] v, String[] words, int cnt, List<Integer> a, String target,
        List<String> s, List<List<String>> ss) {
        for (int i = 0, len = v.length; i < len; i++) {
            if(!v[i] && canConvert(start, words[i])) {
                boolean[] vv = v.clone();
                vv[i] = true;
                int ccnt = cnt + 1;
                List<String> s2 = new ArrayList<>(s);
                s2.add(words[i]);
                if(words[i].equals(target)) {
                    a.add(ccnt);
                    ss.add(s2);
                    return;
                }
                dfs(words[i], vv, words, ccnt, a, target, s2, ss);
            }
        }
    }

    public boolean hasTarget(String target, String[] words) {
        for (String word : words) {
            if(target.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public boolean canConvert(String from, String to) {
        int len = from.length();
        int cnt = 0;
        for (int i = 0; i < len; i++) {
            if (from.charAt(i) != to.charAt(i)) {
                cnt++;
            }

            if (cnt > 1) {
                return false;
            }
        }

        return cnt == 1;
    }
}
