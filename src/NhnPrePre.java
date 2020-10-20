import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by secr3t on 2020/10/19 at 18:19
 *
 * Email : dev.secr3t@gmail.com
 */
public class NhnPrePre {
    private static int[] X = {-1,0,1,0};
    private static int[] Y = {0,1,0,-1};



    private static void solution(int sizeOfMatrix, int[][] matrix) {
        final ArrayList<Integer> areas = new ArrayList<>();
        boolean[][] v = new boolean[sizeOfMatrix][sizeOfMatrix];

        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[i].length; j++) {
                if(matrix[i][j] == 1) {
                    areas.add(dfs(i,j,v,matrix, 1));
                }
            }
        }

        System.out.println(areas.size());
        System.out.println(areas.stream().sorted().map(String::valueOf).collect(
            Collectors.joining(" ")));
    }

    private static int dfs(int dx, int dy, boolean[][]v, int[][]matrix, int cnt) {
        v[dx][dy] = true;
        matrix[dx][dy] = 0;

        for(int i=0; i<4; i++) {
            int nextX = dx + X[i];
            int nextY = dy + Y[i];
            if(nextX <0 || nextY<0 || nextX>=v.length || nextY>=v.length) {continue;}
            if(v[nextX][nextY]){continue;}
            if(matrix[nextX][nextY] == 0) {v[nextX][nextY] = true; continue;}

            cnt = dfs(nextX,nextY,v,matrix, cnt);
            cnt++;
        }
        return cnt;
    }

    private static class InputData {
        int sizeOfMatrix;
        int[][] matrix;
    }

    private static InputData processStdin() {
        InputData inputData = new InputData();

        try (Scanner scanner = new Scanner(System.in)) {
            inputData.sizeOfMatrix = Integer.parseInt(scanner.nextLine().replaceAll("\\s+", ""));

            inputData.matrix = new int[inputData.sizeOfMatrix][inputData.sizeOfMatrix];
            for (int i = 0; i < inputData.sizeOfMatrix; i++) {
                String[] buf = scanner.nextLine().trim().replaceAll("\\s+", " ").split(" ");
                for (int j = 0; j < inputData.sizeOfMatrix; j++) {
                    inputData.matrix[i][j] = Integer.parseInt(buf[j]);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return inputData;
    }

    public static void main(String[] args) throws Exception {
        InputData inputData = processStdin();

        solution(inputData.sizeOfMatrix, inputData.matrix);
    }
}
