import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * N이 홀수일 경우에는 절대 채울 수 없다
 * <점화식 도출>
 * dp[i] = dp[i-2]*3: i가 2이상일 때, 이전 i-2의 방법에서 3*2크기를 추가하는 방식이 3가지 있음
 * 추가적으로, 각 dp[i]값에 대해 dp[i-j]*2를 더하는 경우가 있는데, 
 * 이는 이미 채운 dp[i-j]부분을 기반으로 남은 공간을 채우는 다른 방식을 의미
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] dp = new int[N+1];
		
		//N이 홀수인 경우 타일을 채울 수 없음
		if (N % 2 == 1) {
			System.out.println("0");
			return;
		}
		
		dp[0] = 1;
		dp[2] = 3;
		
		// N = 4: N=2를 두번 이용해서 만들 수 있는 방법 + 새로운 방식
		// N = 6: dp[4]*3 + dp[2]*2(dp[2]를 채운 뒤, 나머지 공간을 새로운 방식으로 채울 수 있음) + 2(새로운 패턴)
		// 기존 dp[4]를 확장 + 기존 dp[2]를 확장 + dp[6]에서만 등장하는 새로운 패턴
		for (int i = 4; i <= N; i += 2) {
			dp[i] = dp[i-2]*dp[2];
			for (int j = 4; j <= i; j+=2) {
				dp[i] += dp[i-j]*2;
			}
		}
		System.out.println(dp[N]);

	}

}