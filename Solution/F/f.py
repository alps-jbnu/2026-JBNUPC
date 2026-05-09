import sys
 
def get_layer(n, x, y):
    """
    (x, y) 좌표가 몇 번째 층인지 반환 (0-indexed 좌표계 기준)
    n: 남은 접기 횟수
    x, y: 현재 영역에서의 상대 좌표
    """
    if n == 0:
        return 1
    
    mid = 1 << (n - 1)
    area = mid * mid  # 한 사분면의 크기 (층 수)
 
    # 1. 좌상단 (Top-Left): 1층 구역
    if x < mid and y >= mid:
        # y축 반전 발생
        return get_layer(n - 1, x, (mid - 1) - (y - mid))
    
    # 2. 우상단 (Top-Right): 2층 구역
    elif x >= mid and y >= mid:
        # x축, y축 모두 반전 발생
        nx = (mid - 1) - (x - mid)
        ny = (mid - 1) - (y - mid)
        return area + get_layer(n - 1, nx, ny)
    
    # 3. 우하단 (Bottom-Right): 3층 구역
    elif x >= mid and y < mid:
        # x축 반전 발생
        nx = (mid - 1) - (x - mid)
        ny = y
        return 2 * area + get_layer(n - 1, nx, ny)
    
    # 4. 좌하단 (Bottom-Left): 4층 구역 (가장 바닥)
    else:
        return 3 * area + get_layer(n - 1, x, y)
 
def solve():
    # 빠른 입력을 위해 sys.stdin 사용
    input_data = sys.stdin.read().split()
    if not input_data:
        return
    
    n = int(input_data[0])
    m = int(input_data[1])
    x1, y1, x2, y2 = map(int, input_data[2:])
 
    total_score = 0
    
    # 영역 내의 모든 좌표를 순회 (1-indexed를 0-indexed로 변환)
    for i in range(x1, x2 + 1):
        for j in range(y1, y2 + 1):
            if get_layer(n, i - 1, j - 1) <= m:
                total_score += 1
                
    print(total_score)
 
if __name__ == "__main__":
    solve()
