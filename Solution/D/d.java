import java.io.*;
import java.util.*;

public class Solution_AC {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        long[] x = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            x[i] = Long.parseLong(st.nextToken());
        }
        Arrays.sort(x);

        int ans = 0;
        int i = 0;
        while (i < n) {
            long cover = x[i] + 2 * k;
            ans++;
            i++;
            while (i < n && x[i] <= cover) i++;
        }

        System.out.println(ans);
    }
}