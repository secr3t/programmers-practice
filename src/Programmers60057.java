import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by secr3t on 2020/10/20 at 17:21
 *
 * Email : dev.secr3t@gmail.com
 * https://programmers.co.kr/learn/courses/30/lessons/60057?language=java
 */
/*
s	result
"aabbaccc"	7
"ababcdcdababcdcd"	9
"abcabcdede"	8
"abcabcabcabcdededededede"	14
"xababcdcdababcdcd"	17
 */
public class Programmers60057 {

    public static void main(String[] args) {
        Programmers60057 p = new Programmers60057();
        Map<String, Integer> tc = Map.of("aabbaccc", 7,
            "ababcdcdababcdcd", 9,
            "abcabcdede", 8,
            "abcabcabcabcdededededede", 14,
            "xababcdcdababcdcd", 17);

        tc.forEach((s, answer) ->
            System.out.printf("s : %s, answer : %d, my-solution : %d\n", s, answer, p.solution(s)));

    }

    public int solution(String s) {
        int len = s.length();
        int max = len / 2;

        List<String> compressed = new ArrayList<>();

        for (int i = 1; i <= max; i++) {
            final StringBuilder sb = new StringBuilder();
            int cnt = 1;
            String curr = "";
            for (int j = i; j < len; j += i) {
                final String prev = s.substring(j-i, j);
                curr = s.substring(j, Math.min(i + j, len));
                if (prev.equals(curr)) {
                    cnt++;
                } else if (cnt > 1) {
                    sb.append(cnt)
                        .append(prev);
                    cnt = 1;
                } else {
                    sb.append(prev);
                    cnt = 1;
                }
            }
            if(cnt > 1) {
                sb.append(cnt);
            }
            sb.append(curr);
            compressed.add(sb.toString());
        }

        return compressed.stream().map(String::length).min(Integer::compareTo).orElse(len);
    }
}
