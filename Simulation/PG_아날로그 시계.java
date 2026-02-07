class Solution {
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int answer = 0;
     
        // 모든 시간을 '0시 0분 0초' 기준의 '초'로 변환
        int start = h1 * 3600 + m1 * 60 + s1;
        int end = h2 * 3600 + m2 * 60 + s2;
        
        // 시작 시점에 이미 겹침
        if (isOverlap(start)) answer++;
        
        // 1초 단위로 탐색하며 그 사이의(i초 ~ i+1초)에 추월이 발생하는지 확인
        for (int i = start; i < end; i++) {
            double sNow = (i * 6) % 360;
            double mNow = (i * 0.1) % 360;
            double hNow = (i * (1.0 / 120)) % 360;
            
            double sNext = ((i+1) * 6) % 360;
            double mNext = ((i+1) * 0.1) % 360;
            double hNext = ((i+1) * (1.0 / 120)) % 360;
            
            // 다음 각도가 0도라면 비교를 위해 360도로 변환
            if (sNext == 0) sNext = 360;
            if (mNext == 0) mNext = 360;
            if (hNext == 0) hNext = 360;
            
            // 초침이 시침 추월?
            boolean hPass = sNow < hNow && sNext >= hNext;
            // 초침이 분침 추월?
            boolean mPass = sNow < mNow && sNext >= mNext;
            
            if (hPass && mPass) {
                // 시침과 분침을 동시에 추월하는 경우 (12시 정각)
                if (hNext == mNext) answer++;
                else answer += 2;
            } else if (hPass || mPass) {
                answer++;
            }
            
        }

        return answer;
    }
    
    private boolean isOverlap(int totalSecond) {
        double sAngle = (totalSecond * 6) % 360; // 초침은 1초에 6도
        double mAngle = (totalSecond * 0.1) % 360; // 분침은 1초에 (360 / 3600)도
        double hAngle = (totalSecond * (1.0 / 120)) % 360; // 시침은 1초에 (360 / (12 * 3600))도
        
        return sAngle == mAngle || sAngle == hAngle;
    }
}