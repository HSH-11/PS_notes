// 수열이 오름차순으로 정렬되어 있다"는 아주 중요한 조건이 있음
// 이럴 때는 투 포인터를 사용하여 O(N)으로 끝낼 수 있다.

class Solution {
    public int[] solution(int[] sequence, int k) {
        int n = sequence.length;
        int left = 0;
        int right = 0;
        int sum = 0;
        
        int minLen = Integer.MAX_VALUE;
        int[] answer = new int[2];
        
        while (right < n) {
            sum += sequence[right];
            
            // sum이 k보다 크면 left를 옮겨서 줄임
            while (sum > k && left <= right) {
                sum -= sequence[left];
                left++;
            }
            
            if (sum == k) {
                int currLen = right - left + 1;
                if (currLen < minLen) {
                    minLen = currLen;
                    answer[0] = left;
                    answer[1] = right;
                }
            }
            
            right++;
        }
        
        return answer;
    }
}