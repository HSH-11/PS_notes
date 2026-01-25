import java.util.*;
// 메모리 최적화 (배열 활용)

// 코딩테스트에서 입력값이 '알파벳 소문자'로 제한되어 있다면, 
// `HashMap` 대신 크기 26인 정수 배열을 사용하는 것이 메모리와 속도 면에서 훨씬 유리

class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        int[] lastPos = new int[26];
        Arrays.fill(lastPos, -1);
        
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            answer[i] = lastPos[idx] == -1 ? -1 : i -lastPos[idx];
            lastPos[idx] = i;
        }
        
        return answer;
    }
}