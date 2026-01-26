class Solution {
    public String solution(String phone_number) {
        // 뒤에 최소 4개의 문자가 남아있는 모든 문자 1개
        // . : 임의의 문자 하나를 의미
        // (?=조건) : 전방탐색
        // 현재 위치 뒤에 조건이 맞으면 OK
        // .{4} : 임의의 문자가 정확히 4개
        // -> 뒤에 4글자 이상 남아있는 문자들만 매칭됨.
        return phone_number.replaceAll(".(?=.{4})","*");
    }
}