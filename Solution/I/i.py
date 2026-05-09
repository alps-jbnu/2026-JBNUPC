import sys

class D:
    def __init__(self, T=None):
        if T is None:
            self.T = [0] * 10
        else:
            self.T = T[:]

e = D([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])

def add(d1, d2):
    res = D()
    for i in range(10):
        res.T[i] = d2.T[d1.T[i]]
    return res

class Seg:
    def __init__(self, A, op, e):
        self.op = op
        self.e = e
        self.n = len(A)
        self.S = [e for _ in range(2 * self.n)]
        for i in range(self.n):
            self.S[self.n + i] = A[i]
        for i in range(self.n - 1, 0, -1):
            self.S[i] = self.op(self.S[i << 1], self.S[i << 1 | 1])

    def query(self, left, right):
        left += self.n
        right += self.n + 1
        resl = self.e
        resr = self.e
        while left < right:
            if left & 1:
                resl = self.op(resl, self.S[left])
                left += 1
            if right & 1:
                right -= 1
                resr = self.op(self.S[right], resr)
            left >>= 1
            right >>= 1
        return self.op(resl, resr)

    def update(self, index, newvalue):
        i = index + self.n
        self.S[i] = newvalue
        i >>= 1
        while i:
            self.S[i] = self.op(self.S[i << 1], self.S[i << 1 | 1])
            i >>= 1

def main():
    input = sys.stdin.readline
    n, q = map(int, input().split())

    drones = []
    for _ in range(n):
        d = D(e.T)
        d1, d2 = map(int, input().split())
        d.T[d1], d.T[d2] = d.T[d2], d.T[d1]
        drones.append(d)

    S = Seg(drones, add, e)
    out = []

    for _ in range(q):
        line = input().split()
        qtype = int(line[0])

        if qtype == 1:
            a = int(line[1]) - 1
            b = int(line[2]) - 1
            drones[a], drones[b] = drones[b], drones[a]
            S.update(a, drones[a])
            S.update(b, drones[b])
        else:
            l = int(line[1]) - 1
            r = int(line[2]) - 1
            x = int(line[3])
            F = S.query(l, r)
            out.append(str(F.T[x]))

    sys.stdout.write("\n".join(out))

if __name__ == "__main__":
    main()