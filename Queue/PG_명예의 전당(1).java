import java.util.*;

// "실시간 순위 관리"나 "상위 K개 추출" 같은 키워드가 보인다면 PriorityQueue

class Solution {
    public int[] solution(int k, int[] score) {
        int[] answer = new int[score.length];
        
        // 최솟값이 맨 앞
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for (int i = 0; i < score.length; i++) {
            pq.add(score[i]);
            
            // 큐의 크기가 k를 넘어가면 가장 작은 값 제거
            if (pq.size() > k) {
                pq.poll();
            }
            
            answer[i] = pq.peek();
        }
        return answer;
    }
}