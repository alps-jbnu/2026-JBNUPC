import java.io.*;
import java.util.*;

public class Main {
    static final long INF = (long) 4e18;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int N = fs.nextInt();
        int M = fs.nextInt();

        long[] cost = new long[N];
        boolean[][] can = new boolean[N][M + 1];

        for (int i = 0; i < N; i++) {
            cost[i] = fs.nextLong();
            int k = fs.nextInt();
            for (int j = 0; j < k; j++) {
                int t = fs.nextInt();
                can[i][t] = true;
            }
        }

        int[][] maxR = new int[N][M + 1];
        for (int i = 0; i < N; i++) {
            for (int s = 1; s <= M; s++) {
                if (!can[i][s]) continue;
                int r = s;
                while (r + 1 <= M && can[i][r + 1]) r++;
                maxR[i][s] = r;
            }
        }

        ArrayList<int[]>[] startList = new ArrayList[M + 2];
        for (int i = 0; i <= M + 1; i++) startList[i] = new ArrayList<>();

        for (int s = 1; s <= M; s++) {
            for (int i = 0; i < N; i++) {
                if (maxR[i][s] >= s) {
                    startList[s].add(new int[]{i, maxR[i][s]});
                }
            }
        }

        int S = 1 << N;
        int P = M + 2;

        long[] dp = new long[S * P];
        Arrays.fill(dp, INF);
        dp[1] = 0;

        for (int mask = 0; mask < S; mask++) {
            int base = mask * P;
            for (int pos = 1; pos <= M; pos++) {
                long cur = dp[base + pos];
                if (cur == INF) continue;

                for (int[] pair : startList[pos]) {
                    int i = pair[0];
                    int r = pair[1];

                    if ((mask & (1 << i)) != 0) continue;

                    int nmask = mask | (1 << i);
                    int npos = r + 1;
                    int idx = nmask * P + npos;

                    long nextCost = cur + cost[i];
                    if (nextCost < dp[idx]) {
                        dp[idx] = nextCost;
                    }
                }
            }
        }

        long ans = INF;
        for (int mask = 0; mask < S; mask++) {
            ans = Math.min(ans, dp[mask * P + (M + 1)]);
        }

        System.out.println(ans == INF ? -1 : ans);
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return sign == 1 ? val : -val;
        }
    }
}
