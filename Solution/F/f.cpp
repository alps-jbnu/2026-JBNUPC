#include <bits/stdc++.h>
using namespace std;
//뒤집힘 로직 -> (접힌 너비 길이) - (상대 좌표) -> (mid-1) - (y-mid)

long long getLayer(int n, long long x, long long y) {
    if (n == 0) return 1;

    long long mid = 1LL << (n - 1);
    long long area = mid * mid; // 한 사분면이 가진 층의 개수

    // 1. 왼쪽 위 (Top-Left): 위쪽 절반이므로 y축 반전 발생
    if (x < mid && y >= mid) {
        return getLayer(n - 1, x, (mid - 1) - (y - mid));
    } 
    // 2. 오른쪽 위 (Top-Right): 가로 접기로 x축 반전 + 세로 접기로 y축 반전
    else if (x >= mid && y >= mid) {
        long long nx = (mid - 1) - (x - mid); // x 상대좌표 뒤집기
        long long ny = (mid - 1) - (y - mid); // y 상대좌표 뒤집기
        return area + getLayer(n - 1, nx, ny);
    } 
    // 3. 오른쪽 아래 (Bottom-Right): 가로 접기로 x축 반전, 아래쪽이므로 y축은 그대로
    else if (x >= mid && y < mid) {
        long long nx = (mid - 1) - (x - mid); // x 상대좌표 뒤집기
        long long ny = y;                     // y는 이미 상대좌표이며 반전 없음
        return 2 * area + getLayer(n - 1, nx, ny);
    } 
    // 4. 왼쪽 아래 (Bottom-Left): 아무 반전 없음 (가장 바닥에 고정된 기준점)
    else {
        return 3 * area + getLayer(n - 1, x, y);
    }
}


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n,m; cin>>n>>m;

    long long x1, y1, x2, y2;
    cin >> x1 >> y1 >> x2 >> y2;


    long long totalScore = 0;

    // 영역 내의 모든 좌표를 순회하며 층수 확인
    for (long long i = x1; i <= x2; ++i) {
        for (long long j = y1; j <= y2; ++j) {
            // 문제의 좌표는 1-indexed이므로 getLayer 호출 시 -1 해줌
            if (getLayer(n, i - 1, j - 1) <= m) {
                totalScore++;
            }
        }
    }

    cout << totalScore << endl;

    return 0;
}