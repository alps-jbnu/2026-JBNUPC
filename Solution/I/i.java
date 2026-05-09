import java.io.*;
import java.util.*;

public class Main {
    static class D {
        int[] T = new int[10];

        D() {
        }

        D(int[] arr) {
            System.arraycopy(arr, 0, T, 0, 10);
        }

        D copy() {
            return new D(T);
        }
    }

    interface Op<T> {
        T apply(T a, T b);
    }

    static class Seg<T> {
        int n;
        Object[] S;
        Op<T> op;
        T e;

        Seg(T[] A, Op<T> op, T e) {
            this.op = op;
            this.e = e;
            n = A.length;
            S = new Object[4 * n];
            init(A, 0, n - 1, 1);
        }

        @SuppressWarnings("unchecked")
        T get(int idx) {
            return (T) S[idx];
        }

        T init(T[] A, int left, int right, int node) {
            if (left == right) {
                return (T) (S[node] = A[left]);
            }
            int mid = (left + right) / 2;
            return (T) (S[node] = op.apply(init(A, left, mid, node * 2),
                    init(A, mid + 1, right, node * 2 + 1)));
        }

        T query(int left, int right, int node, int nodeleft, int noderight) {
            if (right < nodeleft || noderight < left) return e;
            if (left <= nodeleft && noderight <= right) return get(node);

            int mid = (nodeleft + noderight) / 2;
            return op.apply(query(left, right, node * 2, nodeleft, mid),
                    query(left, right, node * 2 + 1, mid + 1, noderight));
        }

        T query(int left, int right) {
            return query(left, right, 1, 0, n - 1);
        }

        void update(int index, T newvalue, int node, int nodeleft, int noderight) {
            if (index < nodeleft || noderight < index) return;
            if (nodeleft == noderight) {
                S[node] = newvalue;
                return;
            }
            int mid = (nodeleft + noderight) / 2;
            update(index, newvalue, node * 2, nodeleft, mid);
            update(index, newvalue, node * 2 + 1, mid + 1, noderight);
            S[node] = op.apply(get(node * 2), get(node * 2 + 1));
        }

        void update(int index, T newvalue) {
            update(index, newvalue, 1, 0, n - 1);
        }
    }

    static final D e = new D(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

    static D add(D d1, D d2) {
        D res = new D();
        for (int i = 0; i < 10; i++) {
            res.T[i] = d2.T[d1.T[i]];
        }
        return res;
    }

    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    static void swap(D[] a, int i, int j) {
        D t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int n = fs.nextInt();
        int q = fs.nextInt();

        D[] drones = new D[n];
        for (int i = 0; i < n; i++) {
            D d = e.copy();
            int d1 = fs.nextInt();
            int d2 = fs.nextInt();
            swap(d.T, d1, d2);
            drones[i] = d;
        }

        Seg<D> S = new Seg<>(drones, Main::add, e);

        for (int i = 0; i < q; i++) {
            int qtype = fs.nextInt();
            if (qtype == 1) {
                int a = fs.nextInt() - 1;
                int b = fs.nextInt() - 1;
                swap(drones, a, b);
                S.update(a, drones[a]);
                S.update(b, drones[b]);
            } else {
                int l = fs.nextInt() - 1;
                int r = fs.nextInt() - 1;
                int x = fs.nextInt();
                D F = S.query(l, r);
                out.append(F.T[x]).append('\n');
            }
        }

        System.out.print(out);
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
    }
}

