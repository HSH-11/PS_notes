import java.util.*;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        // 시간대별 좌표 빈도 저장: Map<시간, Map<좌표문자열,빈도>
        Map<Integer, Map<String, Integer>> timeMap = new HashMap<>();
        
        for (int[] route : routes) {
            int currentTime = 0;
            
            for (int i = 0; i < route.length-1; i++) {
                int[] start = points[route[i] - 1];
                int[] end = points[route[i+1] - 1];
                
                int r = start[0];
                int c = start[1];
                
                // (중요) 시작점과 끝점의 중복 처리
                if (i == 0) {
                    record(timeMap, currentTime++, r, c);
                }
                
                // r좌쵸가 변하는 이동을 c좌표가 변하는 이동보다 먼저
                // 1,4 -> 6,4
                while (r != end[0]) {
                    r += (r < end[0]) ? 1 : -1;
                    record(timeMap, currentTime++, r, c);
                }
                
                while (c != end[1]) {
                    c += (c < end[1]) ? 1 : -1;
                    record(timeMap, currentTime++, r, c);
                }
            }
        }
        
        int answer = 0;
        for (int t : timeMap.keySet()) {
            Map<String, Integer> posMap = timeMap.get(t);
            for (int count : posMap.values()) {
                if (count >= 2) answer++;
            }
        }
        return answer;
    }
    
    private void record(Map<Integer, Map<String, Integer>> timeMap, int t, int r, int c) {
        timeMap.putIfAbsent(t, new HashMap<>());
        String pos = r + "," + c;
        timeMap.get(t).put(pos, timeMap.get(t).getOrDefault(pos,0) + 1);
    }
}