#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const long long INF = 4000000000000000000LL;

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int N;
    int M;

    cin >> N >> M;

    vector<long long> cost(N);
    vector< vector<bool> > can(N, vector<bool>(M + 1, false));

    for (int i = 0; i < N; i++)
    {
        int k;
        cin >> cost[i] >> k;

        for (int j = 0; j < k; j++)
        {
            int t;
            cin >> t;

            if (t >= 1 && t <= M)
            {
                can[i][t] = true;
            }
        }
    }

    vector< vector<int> > maxR(N, vector<int>(M + 1, 0));

    for (int i = 0; i < N; i++)
    {
        for (int s = 1; s <= M; s++)
        {
            if (!can[i][s])
            {
                maxR[i][s] = 0;
            }
            else
            {
                int r = s;

                while (r + 1 <= M && can[i][r + 1])
                {
                    r++;
                }

                maxR[i][s] = r;
            }
        }
    }

    vector< vector<int> > startPerson(M + 2);
    vector< vector<int> > startEnd(M + 2);

    for (int s = 1; s <= M; s++)
    {
        for (int i = 0; i < N; i++)
        {
            if (maxR[i][s] >= s)
            {
                startPerson[s].push_back(i);
                startEnd[s].push_back(maxR[i][s]);
            }
        }
    }

    int S = 1 << N;
    int P = M + 2;

    vector<long long> dp((long long)S * P, INF);

    dp[0 * P + 1] = 0;

    for (int mask = 0; mask < S; mask++)
    {
        for (int pos = 1; pos <= M; pos++)
        {
            long long cur = dp[mask * P + pos];

            if (cur == INF)
            {
                continue;
            }

            int sz = startPerson[pos].size();

            for (int idx = 0; idx < sz; idx++)
            {
                int i = startPerson[pos][idx];
                int r = startEnd[pos][idx];

                if ((mask & (1 << i)) != 0)
                {
                    continue;
                }

                int nmask = mask | (1 << i);
                int npos = r + 1;

                long long newCost = cur + cost[i];
                long long& ref = dp[nmask * P + npos];

                if (newCost < ref)
                {
                    ref = newCost;
                }
            }
        }
    }

    long long ans = INF;

    for (int mask = 0; mask < S; mask++)
    {
        long long val = dp[mask * P + (M + 1)];

        if (val < ans)
        {
            ans = val;
        }
    }

    if (ans == INF)
    {
        cout << -1 << "\n";
    }
    else
    {
        cout << ans << "\n";
    }

    return 0;
}