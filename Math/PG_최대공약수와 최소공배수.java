// 1. 최대공약수(GCD) 구하기: 유클리드 호제법
// 두 수 a, b (a > b)가 있을 때, a를 b로 나눈 나머지를 r이라고 하면:
// "a와 b의 최대공약수는 b와 r의 최대공약수와 같다"는 원리.
// 이 과정을 나머지가 0이 될 때까지 반복하면, 그때 나누는 수가 바로 최대공약수.

// 2. 최소공배수(LCM) 구하기
// 두 수 a, b의 곱은 (최대공약수X최소공배수)와 같다는 성질을 이용.
// 공식: LCM = (a * b) / GCD

class Solution {
    public int[] solution(int n, int m) {
        // 최대공약수 구하기
        int gcd = getGCD(n,m);
        
        // 최소공배수 구하기
        int lcm = (n * m) / gcd;
        
        return new int[] {gcd, lcm};
    }
    
    public int getGCD(int a, int b) {
        if (b == 0) return a;
        return getGCD(b, a % b);
    }
}