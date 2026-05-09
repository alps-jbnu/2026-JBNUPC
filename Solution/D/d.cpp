#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;

int main() {

    int n, k;
    cin >> n >> k;

    long long arr[100005];
    for (int i = 0; i < n; i++) cin >> arr[i];
    sort(arr, arr+n);

    int ans = 0;
    int i = 0;
    while (i < n) {
        long long cover = arr[i] + 2 * k;
        ans++;
        i++;
        while (i < n && arr[i] <= cover) i++;
    }

    cout << ans << "\n";
    return 0;
}