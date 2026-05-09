import sys
input = sys.stdin.readline

def main():
    n, k = map(int, input().split())
    x = list(map(int, input().split()))
    x.sort()

    ans = 0
    i = 0
    while i < n:
        cover = x[i] + 2 * k
        ans += 1
        i += 1
        while i < n and x[i] <= cover:
            i += 1

    print(ans)

main()