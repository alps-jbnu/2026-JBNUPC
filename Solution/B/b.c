#include <stdio.h>

int digitSum26(int n) {
    int s = 0;
    while (n > 0) {
        s += n % 26;
        n /= 26;
    }
    return s;
}

int isPrime(int n) {
    if (n < 2) return 0;
    for (int i = 2; i * i <= n; ++i)
        if (n % i == 0) return 0;
    return 1;
}

int main(void) {
    int a, b, cnt = 0;
    scanf("%d %d", &a, &b);
    for (int n = a; n <= b; ++n) {
        int s = digitSum26(n);
        if (isPrime(s) && n % s == 0) ++cnt;
    }
    printf("%d\n", cnt);
    return 0;
}
