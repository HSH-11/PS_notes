import java.util.regex.*;

// Matcher.group()

// `mapper.find()`가 성공할 때마다 정규식은 `10D*` 같은 덩어리를 찾음. 
// 이때:
// - `group(0)`: 전체 문자열 (`10D*`)
// - `group(1)`: 첫 번째 괄호 내용 (`10`)
// - `group(2)`: 두 번째 괄호 내용 (`D`)
// - `group(3)`: 세 번째 괄호 내용 (`*`)

class Solution {
    public int solution(String dartResult) {
        int[] score = new int[3];
        int i = 0;
        
        // Pattern과 Matcher를 사용한 파싱
        Pattern pattern = Pattern.compile("(\\d+)([SDT])([*#]?)");
        Matcher mapper = pattern.matcher(dartResult);
        
        while(mapper.find()) {
            int n = Integer.parseInt(mapper.group(1)); // 점수
            String bonus = mapper.group(2); // 보너스
            String option = mapper.group(3); // 옵션
            
            // 그룹별 로직
            
            score[i] = (int) Math.pow(n, bonus.equals("S") ? 1 : bonus.equals("D") ? 2 : 3);
            
            if (option.equals("*")) {
                score[i] *= 2;
                if (i > 0) score[i-1] *= 2;
            } else if (option.equals("#")) {
                score[i] *= -1;
            }
            i++;
        }
        
        return score[0]+score[1]+score[2];
    }
}