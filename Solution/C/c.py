import sys


def reflected_index(pos: int, length: int) -> int:
    x = (pos - 1) % (2 * length)
    if x < length:
        return x
    return 2 * length - 1 - x


def solve() -> None:
    data = list(map(int, sys.stdin.buffer.read().split()))
    idx = 0

    N = data[idx]
    idx += 1
    M = data[idx]
    idx += 1

    paper = []
    for _ in range(N):
        row = data[idx:idx + M]
        idx += M
        paper.append(row)

    Q = data[idx]
    idx += 1

    out = []
    for _ in range(Q):
        r = data[idx]
        idx += 1
        c = data[idx]
        idx += 1

        rr = reflected_index(r, N)
        cc = reflected_index(c, M)
        out.append(str(paper[rr][cc]))

    sys.stdout.write("\n".join(out))


if __name__ == "__main__":
    solve()
