import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int Q = scanner.nextInt();

        List<List<Integer>> adj = new ArrayList<>(N + 1);
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }
        boolean[] hasSelfLoop = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            if (u == v) {
                hasSelfLoop[u] = true;
            } else {
                adj.get(u).add(v);
                adj.get(v).add(u);
            }
        }

        boolean[] inS = new boolean[N + 1];
        int[] color = new int[N + 1]; // 0: uncolored, 1/2: bipartite colors

        while (Q-- > 0) {
            int k = scanner.nextInt();
            List<Integer> S = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                int node = scanner.nextInt();
                S.add(node);
                inS[node] = true;
            }

            boolean ok = true;

            // self-loop check
            for (int v : S) {
                if (hasSelfLoop[v]) {
                    ok = false;
                    break;
                }
            }

            // bipartite check on induced subgraph
            if (ok) {
                Queue<Integer> q = new LinkedList<>();
                for (int start : S) {
                    if (color[start] != 0) continue;
                    color[start] = 1;
                    q.add(start);

                    while (!q.isEmpty() && ok) {
                        int cur = q.poll();
                        for (int nxt : adj.get(cur)) {
                            if (!inS[nxt]) continue; // only edges inside S matter
                            if (color[nxt] == 0) {
                                color[nxt] = 3 - color[cur];
                                q.add(nxt);
                            } else if (color[nxt] == color[cur]) {
                                ok = false;
                                break;
                            }
                        }
                    }
                    if (!ok) break;
                }
            }

            System.out.println(ok ? "YES" : "NO");

            // reset marks for next query
            for (int v : S) {
                inS[v] = false;
                color[v] = 0;
            }
        }

        scanner.close();
    }
}

