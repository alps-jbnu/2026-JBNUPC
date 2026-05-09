import sys


def digit_sum_26(n):
    s = 0
    while n > 0:
        s += n % 26
        n //= 26
    return s


def is_prime(n):
    if n < 2:
        return False
    i = 2
    while i * i <= n:
        if n % i == 0:
            return False
        i += 1
    return True


def main():
    a, b = map(int, sys.stdin.buffer.read().split())
    cnt = 0
    for n in range(a, b + 1):
        s = digit_sum_26(n)
        if is_prime(s) and n % s == 0:
            cnt += 1
    sys.stdout.write(f"{cnt}\n")


main()
