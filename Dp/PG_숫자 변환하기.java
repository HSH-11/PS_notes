import java.util.*;

class Solution {
    public int solution(int x, int y, int n) {
        if (x == y) return 0;
        
        // 숫자 i를 만들기 위한 최소 연산 횟수
        int[] dp = new int[y + 1];
        Arrays.fill(dp, 1000001);
        dp[x] = 0;
        
        for (int i = x; i <= y; i++) {
            if (dp[i] == 1000001) continue;
            
            if (i + n <= y) {
                dp[i + n] = Math.min(dp[i] + 1, dp[i + n]);
            }
            
            if (i * 2 <= y) {
                dp[i * 2] = Math.min(dp[i] + 1, dp[i * 2]);
            }
            
            if (i * 3 <= y) {
                dp[i * 3] = Math.min(dp[i * 3], dp[i] + 1);
            }
        }

        return dp[y] == 1000001 ? -1 : dp[y];
    }
}