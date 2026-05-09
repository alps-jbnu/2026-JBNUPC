import java.io.*;
import java.util.*;

public class Main {
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
    }

    static int reflectedIndex(long pos, int length) {
        long x = (pos - 1) % (2L * length);
        if (x < length) {
            return (int) x;
        }
        return (int) (2L * length - 1 - x);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int N = fs.nextInt();
        int M = fs.nextInt();

        int[][] paper = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                paper[i][j] = fs.nextInt();
            }
        }

        int Q = fs.nextInt();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < Q; i++) {
            long r = fs.nextInt();
            long c = fs.nextInt();

            int rr = reflectedIndex(r, N);
            int cc = reflectedIndex(c, M);

            sb.append(paper[rr][cc]).append('\n');
        }

        System.out.print(sb);
    }
}
