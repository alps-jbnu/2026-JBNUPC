#include <iostream>

int main()
{
    int ary[200][200];
    int n, m, q;

    std::cin >> n >> m;

    for (int y = 0; y < n; y++)
    {
        for (int x = 0; x < m; x++)
        {
            std::cin >> ary[y][x];
        }
    }

    std::cin >> q;

    while (q--)
    {
        int r, c;
        std::cin >> r >> c;

        int idx_r = (r - 1) % (2 * n);
        int idx_c = (c - 1) % (2 * m);

        if (idx_r >= n)
        {
            idx_r = (2 * n - 1) - idx_r;
        }
        if (idx_c >= m)
        {
            idx_c = (2 * m - 1) - idx_c;
        }

        std::cout << ary[idx_r][idx_c] << '\n';
    }

    return 0;
}