import java.util.*;

// 핵심 아이디어:
// A의 흔적을 DP 인덱스로 사용하고,
// 해당 A흔적에서 만들 수 있는 "B의 최소 누적 흔적"을 저장한다.
// 모든 물건을 순회하며 A가 훔칠지, B가 훔칠지를 선택하는 배낭형 DP.

class Solution {
    public int solution(int[][] info, int n, int m) {

        // dp[j] : A의 흔적이 j일 때, 만들 수 있는 B의 최소 누적 흔적
        int[] dp = new int[n];

        int INF = 1_000_000;
        Arrays.fill(dp, INF);

        // 시작 상태: 아무것도 훔치지 않았을 때
        dp[0] = 0;

        // 모든 물건을 하나씩 처리
        for (int[] item : info) {
            int aCost = item[0]; // A가 훔치면 증가하는 흔적
            int bCost = item[1]; // B가 훔치면 증가하는 흔적

            // 다음 단계 DP
            int[] nextDp = new int[n];
            Arrays.fill(nextDp, INF);

            // 현재 가능한 모든 A 흔적 상태 순회
            for (int j = 0; j < n; j++) {
                if (dp[j] == INF) continue; // 만들 수 없는 상태

                // 1) A가 이 물건을 훔치는 경우
                // A 흔적 증가, B 흔적 변화 없음
                if (j + aCost < n) {
                    nextDp[j + aCost] = Math.min(nextDp[j + aCost], dp[j]);
                }

                // 2) B가 이 물건을 훔치는 경우
                // A 흔적 그대로, B 흔적 증가
                if (dp[j] + bCost < m) {
                    nextDp[j] = Math.min(nextDp[j], dp[j] + bCost);
                }
            }

            // 다음 단계로 이동
            dp = nextDp;
        }

        // 모든 물건을 처리한 후
        // B의 흔적이 m 미만인 상태 중
        // 가장 작은 A 흔적(j)을 찾는다.
        for (int j = 0; j < n; j++) {
            if (dp[j] < m) return j;
        }

        // 가능한 경우가 없다면
        return -1;
    }
}