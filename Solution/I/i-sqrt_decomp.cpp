#include <iostream>
#include <vector>

using namespace std;

template <typename T> struct seg {
  int n;
  int bucket_size;
  vector<T> A;
  vector<T> buckets;
  T (*op)(T, T);
  T e;
  seg(const vector<T> &A, T (*op)(T, T), T e) : op(op), e(e), A(A) {
    n = A.size();
    bucket_size = 400;
    buckets = vector<T>(n / bucket_size+1);
    for (int i = 0; i < buckets.size(); i++)
      bucket_refresh(i);
  }

  void bucket_refresh(int i) {
    buckets[i] = e;
    for (int j = bucket_size * i; j < bucket_size * (i + 1) && j < n; j++) {
      buckets[i] = op(buckets[i], A[j]);
    }
  }

  T query(int left, int right) {
    T ret = e;

    while (left % bucket_size != 0 && left != right) {
      ret = op(ret, A[left]);
      left++;
    }
    while (left + bucket_size <= right) {
      ret = op(ret, buckets[left / bucket_size]);
      left += bucket_size;
    }
    while (left <= right) {
      ret = op(ret, A[left]);
      left++;
    }
    return ret;
  }
  void update(int index, T newvalue) {
    A[index] = newvalue;
    bucket_refresh(index / bucket_size);
  }
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
  for (auto &d : drones) {
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