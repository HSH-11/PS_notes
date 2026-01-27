import java.util.*;

// 인덱스를 저장하자
// 값을 저장하면 나중에 정답 배열의 어느 칸에 값을 채워야 할지 알 수 없으므로, index를 stack에 넣기.

// 시간 복잡도 O(N)
// numbers의 길이가 100만 이므로 이중 for문 불가
// 각 인덱스를 스택에 한 번 들어가고 나오게 끔 설계

class Solution {
    public int[] solution(int[] numbers) {
        int n = numbers.length;
        int[] answer = new int[n];
        
        Arrays.fill(answer, -1);
        
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            while(!stack.isEmpty() && numbers[stack.peek()] < numbers[i]) {
                int index = stack.pop();
                answer[index] = numbers[i];
            }
            
            stack.push(i);
        }
        return answer;
    }
}