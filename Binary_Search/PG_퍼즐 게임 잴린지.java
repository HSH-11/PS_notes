// 조건을 만족하는 가장 작은 숙련도를 구하라", "가장 긴 길이를 구하라"와 같이 어떤 값의 경계선을 찾는 질문 -> 이분 탐색 고려
// 일반적인 for문으로 하나씩 확인해서는 절대 시간 내에 풀 수 없는 수치라면 -> 이분 탐색 고려
// 숙련도가 오를수록 소요 시간은 줄어들음 -> 이분탐색
class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int low = 1;
        int high = 100000;
        int answer = high;
        
        while (low <= high) {
            int mid = (low + high) / 2;
            
            if (isPossible(diffs, times, limit, mid)) {
                answer = mid; // 가능하면 숙련도 낮추기
                high = mid - 1;
            }else {
                low = mid + 1; // 불가능하면 숙련도 높이기
            }
        }
        
        return answer;
    }
    
    private boolean isPossible(int[] diffs, int[] times, long limit, int level) {
        long total = 0;
        for (int i = 0; i < diffs.length; i++) {
            if (diffs[i] <= level) {
                total += times[i];
            } else {
                long diffCount = diffs[i] - level;
                
                long prevTime = (i > 0) ? times[i-1] : 0;
                total += diffCount * (times[i] + prevTime) + times[i];
            }
            
            if (total > limit) return false; // 중간에 이미 넘어가면 바로 실패 반환
        }
        return true;
    }
}