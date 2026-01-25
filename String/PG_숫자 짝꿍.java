import java.util.*;

// 예외 상황 처리

// startsWith("0") : "000" 같은 상황을 한 번에 "0"으로 치환할 수 있음
class Solution {
    public String solution(String X, String Y) {
        StringBuilder sb = new StringBuilder();
             
        // 1. 숫자 등장 횟수 배열 저장 (크기 10)
        int[] countX = new int[10];
        int[] countY = new int[10];
        
        for (int i = 0; i < X.length(); i++) {
            countX[X.charAt(i) - '0']++;
        }
        
        for (int i = 0; i < Y.length(); i++) {
            countY[Y.charAt(i) - '0']++;
        }
        
        // 가장 큰 수를 만들어야 하므로 9부터 0까지 거꾸로 확인
        for (int i = 9; i >= 0; i--) {
            if (countX[i] != 0 && countY[i] != 0) {
                int cnt = Math.min(countX[i],countY[i]);
                for (int j = 0; j < cnt; j++) {
                    sb.append(i);
                } 
            }
        }
    
        String answer = sb.toString();
        
        if (answer.equals("")) return "-1";
        
        if (answer.startsWith("0")) return "0";
    
        return answer;
    }
}