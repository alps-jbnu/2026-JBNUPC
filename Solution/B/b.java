import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainJava {
    static int digitSum26(int n) {
        int s = 0;
        while (n > 0) {
            s += n % 26;
            n /= 26;
        }
        return s;
    }

    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; (long) i * i <= n; ++i)
            if (n % i == 0) return false;
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int cnt = 0;
        for (int n = a; n <= b; n++) {
            int s = digitSum26(n);
            if (isPrime(s) && n % s == 0) cnt++;
        }
        System.out.println(cnt);
    }
}
