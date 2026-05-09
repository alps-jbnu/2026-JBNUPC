import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String line1 = br.readLine();
        if (line1 == null) return;
        st = new StringTokenizer(line1);
        
        int n = Integer.parseInt(st.nextToken());
        long m = Long.parseLong(st.nextToken());

        String line2 = br.readLine();
        if (line2 == null) return;
        st = new StringTokenizer(line2);
        
        long x1 = Long.parseLong(st.nextToken());
        long y1 = Long.parseLong(st.nextToken());
        long x2 = Long.parseLong(st.nextToken());
        long y2 = Long.parseLong(st.nextToken());

        long totalScore = 0;

        // Iterate through the area (Adjust 1-indexed to 0-indexed)
        for (long i = x1; i <= x2; i++) {
            for (long j = y1; j <= y2; j++) {
                if (getLayer(n, i - 1, j - 1) <= m) {
                    totalScore++;
                }
            }
        }

        System.out.println(totalScore);
    }

    public static long getLayer(int n, long x, long y) {
        if (n == 0) return 1;

        long mid = 1L << (n - 1);
        long area = mid * mid;

        // 1. Top-Left: Layer Group 1
        if (x < mid && y >= mid) {
            return getLayer(n - 1, x, (mid - 1) - (y - mid));
        }
        // 2. Top-Right: Layer Group 2
        else if (x >= mid && y >= mid) {
            long nx = (mid - 1) - (x - mid);
            long ny = (mid - 1) - (y - mid);
            return area + getLayer(n - 1, nx, ny);
        }
        // 3. Bottom-Right: Layer Group 3
        else if (x >= mid && y < mid) {
            long nx = (mid - 1) - (x - mid);
            long ny = y;
            return 2 * area + getLayer(n - 1, nx, ny);
        }
        // 4. Bottom-Left: Layer Group 4 (Bottom floor)
        else {
            return 3 * area + getLayer(n - 1, x, y);
        }
    }
}