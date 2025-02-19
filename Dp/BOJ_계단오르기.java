import java.io.BufferedReader;
import java.io.InputStreamReader;

/*<문제 분석>
 * 계단 한 번에 1칸 or 2칸 씩만 이동 가능
 * 마지막 도착 계단은 반드시 밟아야함
 * 누적 점수 최대
 * 마지막 계단을 밟는 경우:
 * -직전 칸에서 올라오는 경우
 * -두 칸 전에서 올라오는 경우
 * <의사 코드>
 * N이 1이면 arr[1]을 출력하고 종료
 * DP 배열 초기화
 * - dp[1] = arr[1]  (첫 번째 계단을 오르는 경우)
 * - dp[2] = arr[1] + arr[2] (두 번째 계단까지 오르는 경우)
 * - dp[3] = max(arr[1], arr[2]) + arr[3] (세 번째 계단까지 오르는 경우)
 * N 이 4부터
 * - dp[i] = max(dp[i-2], dp[i-3] + arr[i-1]) + arr[i]
 * 직전 칸에서 올라오는 경우는 직전 칸의 직전 칸에서 올라오면 안되므로 dp[i-3]에서 arr[i-1]를 거쳐야함
 */
public class Main {

	static int N;

	static int[] dp;
	static int[] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		dp = new int[N + 1];
		arr = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		if (N == 1) {
			System.out.println(arr[1]);
			return;
		}

		dp[1] = arr[1];
		dp[2] = arr[1] + arr[2];
		if (N == 2) {
            System.out.println(dp[2]);
            return; 
        }
		dp[3] = Math.max(arr[1], arr[2]) + arr[3];

		for (int i = 4; i <= N; i++) {
			dp[i] = Math.max(dp[i - 2], dp[i - 3] + arr[i - 1]) + arr[i];
		}

		System.out.println(dp[N]);

	}

}