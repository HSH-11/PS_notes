// 에라토스테네스의 체는 '특정 범위 내의 소수'를 판정하는 데에만 효율적

// 에라토스테네스의 체 원리:
// 1. 2부터 n까지 숫자를 쭉 쓴다.
// 2. 아직 지워지지 않은 수 중 가장 작은 수(2)를 찾는다. 얘는 소수.
// 3. 그 수의 배수들을 모두 지웁니다. (2의 배수: 4, 6, 8... 제거)
// 4. 다음 지워지지 않은 수(3)를 찾고, 그 배수들을 다 지운다.
// 5. 이 과정을 sqrt{n}까지만 반복하면 남은 숫자들은 모두 소수.

class Solution {
    public int solution(int n) {
        // 0부터 n까지 포함해야 하므로 크기는 n+1
        boolean[] isPrime = new boolean[n + 1];
        
        // 처음엔 모두 소수라고 가정 (true로 채우기)
        for (int i = 2; i <= n; i++) isPrime[i] = true;

        // 에라토스테네스의 체 실행
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                // i의 배수들을 모두 false로 변경
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) count++;
        }
        return count;
    }
}