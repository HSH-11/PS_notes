import java.util.*;

// 그리디 전략
// "지금 당장 요격해야 하는 미시알이 있다면, 최대한 늦게 쏴서 다음에 오는 미사일도 같이 맞기를 노린다"

class Solution {
    public int solution(int[][] targets) {
        // 끝나는 지점(e)를 기준으로 오름차순 정렬        
        Arrays.sort(targets, (a,b) -> {
            if (a[1] == b[1]) return a[0]- b[0];
            return a[1] - b[1];
        });
        
        int answer = 0;
        // 요격 미사일 발사 위치
        double lastShot = -1.0;
        
        for (int[] target : targets) {
            int s = target[0];
            int e = target[1];
            
            // 현재 미사일의 시작점이 마지막 요격 지점보다 뒤에 있으면 새로 발사
            if (s >= lastShot) {
                // 가장 효율적인 요격 지점은 현재 미사일의 끝점 직전
                answer++;
                lastShot = e;
            }
        }
        return answer;
    }
}