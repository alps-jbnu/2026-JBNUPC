# G. 태스크 기반 고용 설계

## 메타

- 태그: `bitmask_dp`
- 출제진 의도: Platinum Hard
- 출제자: 홍채운
- 에디토리얼 작성자: 홍채운

## 풀이

- 전체 배정은 $[1,M]$을 여러 연속 구간으로 나누는 문제이다.
- $maxR[i][s]$를 지원자 $i$가 $s$번 태스크부터 연속으로 맡을 수 있는 가장 오른쪽 태스크 번호라 하자.
- $maxR[i][s]<s$이면 지원자 $i$는 $s$번 태스크에서 시작할 수 없다.
- 모든 $i,s$에 대해 $maxR[i][s]$를 전처리할 수 있다.
- $costSum[mask]$를 $mask$에 포함된 지원자들의 비용 합이라 하자.
- $reach[mask]$를 $mask$에 포함된 지원자를 모두 한 번씩 사용해 $1$번부터 연속으로 배정할 수 있는 가장 오른쪽 태스크 번호라 하자.
- 초기값은 $reach[0]=0$이다.
- 도달하지 못한 $mask$는 건너뛰고, 현재 $p=reach[mask]$라 하자.
- 아직 고용하지 않은 지원자 $i$에 대해 $maxR[i][p+1]\ge p+1$이면 다음과 같이 갱신한다.

$$
reach[mask\mid(1\ll i)]
=
\max\left(reach[mask\mid(1\ll i)], maxR[i][p+1]\right)
$$

- 같은 $mask$에서는 더 큰 $reach$만 남기면 충분하다.
- 답은 $reach[mask]=M$인 $mask$ 중 $costSum[mask]$의 최솟값이다.
- 가능한 $mask$가 없다면 $-1$을 출력한다.

## 복잡도

- 시간복잡도: $\mathcal{O}(2^N N+NM^2)$
- 공간복잡도: $\mathcal{O}(2^N+NM)$
