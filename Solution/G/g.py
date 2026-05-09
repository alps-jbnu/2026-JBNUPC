import sys

def solve():
    input = sys.stdin.readline
    N, M = map(int, input().split())

    cost = [0] * N
    can = [[False] * (M + 2) for _ in range(N)]

    for i in range(N):
        data = list(map(int, input().split()))
        cost[i] = data[0]
        k = data[1]
        for t in data[2:2 + k]:
            can[i][t] = True

    # maxR[i][s] = 지원자 i가 s부터 시작해서 맡을 수 있는 최대 연속 끝 번호
    maxR = [[0] * (M + 2) for _ in range(N)]
    for i in range(N):
        r = 0
        # 각 시작점마다 while 돌면 비효율적이므로, M이 작아도 깔끔하게 직접 계산
        for s in range(1, M + 1):
            if not can[i][s]:
                continue
            r = s
            while r + 1 <= M and can[i][r + 1]:
                r += 1
            maxR[i][s] = r

    S = 1 << N

    # sum_cost[mask] = 해당 mask의 총 비용
    sum_cost = [0] * S
    for mask in range(1, S):
        b = mask & -mask
        i = b.bit_length() - 1
        sum_cost[mask] = sum_cost[mask ^ b] + cost[i]

    # reach[mask] = mask를 사용해서 1..reach[mask]까지 덮을 수 있는 최대 위치
    reach = [-1] * S
    reach[0] = 0

    ans = None

    for mask in range(S):
        p = reach[mask]
        if p < 0:
            continue

        if p == M:
            cur_cost = sum_cost[mask]
            if ans is None or cur_cost < ans:
                ans = cur_cost
            continue

        # 현재 비용이 이미 ans 이상이면 더 볼 필요 없음
        if ans is not None and sum_cost[mask] >= ans:
            continue

        nxt_task = p + 1
        bit = 1
        for i in range(N):
            if mask & bit:
                bit <<= 1
                continue

            r = maxR[i][nxt_task]
            if r >= nxt_task:
                nmask = mask | bit
                if r > reach[nmask]:
                    reach[nmask] = r

            bit <<= 1

    print(-1 if ans is None else ans)


if __name__ == "__main__":
    solve()
