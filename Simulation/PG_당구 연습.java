class Solution {
    public long[] solution(int m, int n, int startX, int startY, int[][] balls) {
        long[] answer = new long[balls.length];
               
        // 대칭 이동해서 거리 구하기
        // 목표 공을 맞히기 전에 벽에 먼저 부딪혀야 함.
        int idx = 0;
        for (int[] ball : balls) {
            long minDistance = Long.MAX_VALUE;
            int targetX = ball[0];
            int targetY = ball[1];
            
            // 1. 왼쪽 벽에 맞히기 (목표 공의 x좌표를 0기준으로 대칭 이동)
            // 목표 공이 좌우 일직선에 있으면서 목표 공이 내 공보다 왼쪽에 있으면 벽을 맞히기 전에 먼저 때리므로 무효
            if (!(startY == targetY && startX > targetX)) {
                minDistance = Math.min(minDistance, getDist(startX, startY, -targetX, targetY));
            }
            // 2. 오른쪽 벽에 맞히기
            if (!(startY == targetY && startX < targetX)) {
                minDistance = Math.min(minDistance, getDist(startX, startY, 2 * m - targetX, targetY));
            }
            // 3. 위쪽 벽에 맞히기
            if (!(startX == targetX && startY < targetY)) {
                minDistance = Math.min(minDistance, getDist(startX, startY, targetX, 2 * n - targetY));
            }
            // 4. 아래쪽 벽에 맞히기 
            if (!(startX == targetX && startY > targetY)) {
                minDistance = Math.min(minDistance, getDist(startX, startY, targetX, -targetY));
            }
            answer[idx] = minDistance;
            idx++;
        }
        
        return answer;
    }
    
    private long getDist(long sx, long sy, long tx, long ty) {
        long dx = sx - tx;
        long dy = sy - ty;
        return dx * dx + dy * dy;
    }
}