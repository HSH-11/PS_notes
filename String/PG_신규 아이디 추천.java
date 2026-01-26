// 정규표현식 문제
class Solution {
    public String solution(String new_id) {      
        // 1단계
        String s = new_id.toLowerCase();
        
        // 2단계
        // [^ : 뒤에 조건들에 해당하지 않는 것을 선택
        // \\- : 하이픈은 특수기호가 아닌 문자'-'그 자체 표기
        s = s.replaceAll("[^a-z0-9\\-_.]","");
        
        // 3단계
        // {2,} : 2번 이상 반복
        s = s.replaceAll("\\.{2,}",".");
        
        // 4단계
        // 대괄호 밖에서 ^는 시작을 의미
        s = s.replaceAll("^\\.|\\.$","");
        
        // 5단계
        if (s.isEmpty()) s = "a";
        
        // 6단계
        if (s.length() >= 16) {
            s = s.substring(0,15).replaceAll("\\.$","");
        }
        
        // 7단계
        while (s.length() <= 2) {
            s += s.charAt(s.length()-1);
        }
        
        return s;
    }
}