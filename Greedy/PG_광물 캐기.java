import java.util.*;

// "한 번 들면 5개를 연속으로 캐야 한다"는 제약 조건 덕분에 5개 단위의 덩어리 가치가 고정됨. 
// 그래서 덩어리들을 가치 순으로 정렬하는 그리디 방식이 훨씬 효율적이고 빠르게 동작

class Solution {
    public int solution(int[] picks, String[] minerals) {
        int answer = 0;
        
        // 캘 수 있는 최대 광물 수 계산
        int totalPicks = Arrays.stream(picks).sum();
        int maxMinerals = Math.min(minerals.length, totalPicks * 5);
        
        // 5개씩 묶어서 그룹별 피로도 계산
        List<int[]> groups = new ArrayList<>();
        for (int i = 0; i < maxMinerals; i += 5) {
            int dia = 0, iron = 0, stone = 0;
            
            for (int j = i; j < i + 5 && j < maxMinerals; j++) {
                String m = minerals[j];
                
                dia += 1;
                iron += (m.equals("diamond") ? 5 : 1);
                stone += (m.equals("diamond") ? 25 : (m.equals("iron") ? 5 : 1));
                
            }
            groups.add(new int[] {dia, iron, stone});
        }
        
        // 돌 곡괭이로 캤을 때 피로도가 큰 그룹일수록 다이아가 많으므로 이때 좋은 곡괭이 써야함
        Collections.sort(groups, (a, b) -> b[2] - a[2]);
        
        for (int[] group : groups) {
            if (picks[0] > 0) { // 다이아
                answer += group[0];
                picks[0]--;
            } else if (picks[1] > 0) { // 철
                answer += group[1];
                picks[1]--;
            } else if (picks[2] > 0) { // 돌
                answer += group[2];
                picks[2]--;
            } else {
                // 곡괭이 다 씀
                break;
            }
        }
                
        return answer;
    }
}