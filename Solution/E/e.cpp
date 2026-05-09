#include <bits/stdc++.h>
using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int N, M;
    if (!(cin >> N >> M)) return 0;

    // 가치가 0이 되지 않는 최대 수확 횟수는 20번 (10^6 < 2^20)
    int effM = min(M, 20);
    
    // N-M개의 당근을 건너뛸 수 있으므로, 
    // 처음 effM개의 당근을 수확할 수 있는 배열의 최대 길이 제한
    int limit = N - M + effM;

    vector<long long> dp(effM + 1, -1);
    dp[0] = 0;

    for (int i = 0; i < N; i++) {
        long long v;
        cin >> v;

        if (i < limit) {
            int maxJ = min(i + 1, effM);
            for (int j = maxJ; j >= 1; j--) {
                if (dp[j - 1] >= 0) {
                    int shift = j - 1;
                    long long gain = (shift >= 20) ? 0 : (v >> shift);
                    
                    if (dp[j - 1] + gain > dp[j]) {
                        dp[j] = dp[j - 1] + gain;
                    }
                }
            }
        }
    }

    // 정확히 M개를 수확했을 때의 최대 가치
    cout << dp[effM] << "\n";

    return 0;
}