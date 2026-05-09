import java.io.*;
import java.util.*;

public class Main {
    // 입력을 빠르게 받기 위한 FastReader 클래스
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    String line = br.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    return null;
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }
        long nextLong() { return Long.parseLong(next()); }
    }

    public static void main(String[] args) {
        FastReader fr = new FastReader();
        
        String nStr = fr.next();
        if (nStr == null) return;
        
        int N = Integer.parseInt(nStr);
        int M = fr.nextInt();

        // 가치가 0이 되지 않는 최대 수확 횟수는 20번 (10^6 < 2^20)
        int effM = Math.min(M, 20);
        
        // N-M개의 당근을 건너뛸 수 있으므로, 
        // 처음 effM개의 당근을 수확할 수 있는 배열의 최대 길이 제한
        int limit = N - M + effM;

        // DP 배열 초기화
        long[] dp = new long[effM + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int i = 0; i < N; i++) {
            long v = fr.nextLong();

            // 한계점(limit)을 넘어가면 첫 effM개 안에 포함될 수 없음
            if (i < limit) {
                int maxJ = Math.min(i + 1, effM);
                // 뒤에서부터 순회하여 0/1 배낭 문제(Knapsack) 로직 적용
                for (int j = maxJ; j >= 1; j--) {
                    if (dp[j - 1] >= 0) {
                        int shift = j - 1;
                        long gain = (shift >= 20) ? 0 : (v >> shift);
                        
                        if (dp[j - 1] + gain > dp[j]) {
                            dp[j] = dp[j - 1] + gain;
                        }
                    }
                }
            }
        }

        // 정확히 M개를 수확했을 때의 최대 가치
        System.out.println(dp[effM]);
    }
}