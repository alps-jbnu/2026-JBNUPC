#include <iostream>

bool isPrime(int n) {
    if (n < 2) return false;
    for (int i = 2; i * i <= n; ++i)
        if (n % i == 0) return false;
    return true;
}

int digitSum26(int n) {
    int s = 0;
    while (n > 0) {
        s += n % 26;
        n /= 26;
    }
    return s;
}

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);

    int a, b;
    std::cin >> a >> b;

    int cnt = 0;
    for (int n = a; n <= b; ++n) {
        int s = digitSum26(n);
        if (isPrime(s) && n % s == 0) ++cnt;
    }

    std::cout << cnt << '\n';
    return 0;
}
