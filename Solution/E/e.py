import sys

def solve():
    # 입력을 이터레이터로 처리하여 메모리와 속도 개선
    input_data = sys.stdin.read().split()
    if not input_data:
        return
    
    it = iter(input_data)
    N = int(next(it))
    M = int(next(it))
    
    # 가치가 0이 되지 않는 최대 수확 횟수는 20번 (10^6 < 2^20)
    effM = min(M, 20)
    
    # N-M개의 당근을 건너뛸 수 있으므로, 
    # 처음 effM개의 당근을 수확할 수 있는 최대 인덱스 제한
    limit_index = N - M + effM
    
    # DP 배열 초기화
    dp = [-1] * (effM + 1)
    dp[0] = 0
    
    # 당근 가치들 읽기
    for i in range(N):
        v = int(next(it))
        
        if i < limit_index:
            current_limit = min(i + 1, effM)
            for j in range(current_limit, 0, -1):
                if dp[j - 1] >= 0:
                    shift = j - 1
                    gain = 0 if shift >= 20 else (v >> shift)
                    
                    new_val = dp[j - 1] + gain
                    if new_val > dp[j]:
                        dp[j] = new_val
    
    # 정확히 M개를 수확했을 때의 최대 가치
    print(dp[effM])

if __name__ == '__main__':
    solve()