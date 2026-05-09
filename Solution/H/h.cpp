#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, M, Q;
    cin >> N >> M >> Q;

    vector<vector<int>> adj(N + 1);
    vector<char> hasSelfLoop(N + 1, 0);

    for (int i = 0; i < M; i++) {
        int u, v;
        cin >> u >> v;
        if (u == v) {
            hasSelfLoop[u] = 1;
        } else {
            adj[u].push_back(v);
            adj[v].push_back(u);
        }
    }

    vector<char> inS(N + 1, 0);
    vector<int> color(N + 1, 0); // 0: uncolored, 1/2: bipartite colors

    while (Q--) {
        int k;
        cin >> k;
        vector<int> S(k);
        for (int i = 0; i < k; i++) {
            cin >> S[i];
            inS[S[i]] = 1;
        }

        bool ok = true;

        // self-loop check
        for (int v : S) {
            if (hasSelfLoop[v]) { ok = false; break; }
        }

        // bipartite check on induced subgraph
        if (ok) {
            queue<int> q;
            for (int start : S) {
                if (color[start] != 0) continue;
                color[start] = 1;
                q.push(start);

                while (!q.empty() && ok) {
                    int cur = q.front(); q.pop();
                    for (int nxt : adj[cur]) {
                        if (!inS[nxt]) continue; // only edges inside S matter
                        if (color[nxt] == 0) {
                            color[nxt] = 3 - color[cur];
                            q.push(nxt);
                        } else if (color[nxt] == color[cur]) {
                            ok = false;
                            break;
                        }
                    }
                }
                if (!ok) break;
            }
        }

        cout << (ok ? "YES\n" : "NO\n");

        // reset marks for next query
        for (int v : S) {
            inS[v] = 0;
            color[v] = 0;
        }
    }

    return 0;
}