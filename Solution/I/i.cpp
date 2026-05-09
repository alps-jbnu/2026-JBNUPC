#include <iostream>
#include <vector>

using namespace std;

template <typename T>
struct seg {
    int n;
    vector<T> S;
    T (*op)(T, T);
    T e;
    seg(const vector<T>& A, T (*op)(T, T), T e) : op(op), e(e) {
        n = A.size();
        S.resize(4 * n);
        init(A, 0, n - 1, 1);
    }
    T init(const vector<T>& A, int left, int right, int node) {
        if (left == right) {
            return S[node] = A[left];
        }
        int mid = (left + right) / 2;
        return S[node] = op(init(A, left, mid, node * 2),
                            init(A, mid + 1, right, node * 2 + 1));
    }

    T query(int left, int right, int node, int nodeleft, int noderight) {
        if (right < nodeleft || noderight < left) return e;
        if (left <= nodeleft && noderight <= right) return S[node];

        int mid = (nodeleft + noderight) / 2;
        return op(query(left, right, node * 2, nodeleft, mid),
                  query(left, right, node * 2 + 1, mid + 1, noderight));
    }
    T query(int left, int right) { return query(left, right, 1, 0, n - 1); }
    void update(int index, T newvalue, int node, int nodeleft, int noderight) {
        if (index < nodeleft || noderight < index) return;
        if (nodeleft == noderight) {
            S[node] = newvalue;
            return;
        }
        int mid = (nodeleft + noderight) / 2;
        update(index, newvalue, node * 2, nodeleft, mid);
        update(index, newvalue, node * 2 + 1, mid + 1, noderight);
        S[node] = op(S[node * 2], S[node * 2 + 1]);
    }
    void update(int index, T newvalue) { update(index, newvalue, 1, 0, n - 1); }
};

struct D {
    int T[10];
};

const auto e = D({0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

D add(D d1, D d2) {
    D res;
    for (int i = 0; i < 10; i++) {
        res.T[i] = d2.T[d1.T[i]];
    }
    return res;
};

int main() {
    int n, q;
    cin >> n >> q;
    vector<D> drones(n);
    for (auto& d : drones) {
        d = e;
        int d1, d2;
        cin >> d1 >> d2;
        swap(d.T[d1], d.T[d2]);
    }
    seg<D> S(drones, add, e);
    for (int i = 0; i < q; i++) {
        int qtype;
        cin >> qtype;
        if (qtype == 1) {
            int a, b;
            cin >> a >> b;
            a--;
            b--;
            swap(drones[a], drones[b]);
            S.update(a, drones[a]);
            S.update(b, drones[b]);
        } else {
            int l, r, x;
            cin >> l >> r >> x;
            l--;
            r--;
            auto F = S.query(l, r);
            cout << F.T[x] << '\n';
        }
    }
}