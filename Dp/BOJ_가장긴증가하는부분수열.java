import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
<의사 코드>
#1. DP
1. N을 입력받는다.
2. 길이가 N인 배열 arr을 입력받는다.
3. 길이가 N인 dp 배열을 1로 초기화한다. (각 원소는 최소 자기 자신만으로 길이 1인 LIS 가능)

4. for i = 1 to N-1:
      for j = 0 to i-1:
          if arr[j] < arr[i]:  # 이전 값이 현재 값보다 작다면
              dp[i] = max(dp[i], dp[j] + 1)  # 기존 값과 (이전 값의 LIS 길이 + 1) 중 큰 값 선택

5. dp 배열의 최댓값을 출력한다. (가장 긴 증가하는 부분 수열의 길이)

#2. 이분 탐색 (ArrayList + binartSearch 활용)
1. N을 입력받는다.
2. 길이가 N인 배열 arr을 입력받는다.
3. 빈 리스트를 생성한다.
4. for i = 0 to N-1:
      if list가 비어있거나 arr[i]가 list의 마지막 원소보다 크다면:
          list에 arr[i] 추가
      else:
          list에서 arr[i]가 들어갈 위치를 이분 탐색으로 찾고, 해당 위치 값을 arr[i]로 갱신
5. list의 길이를 출력한다. (가장 긴 증가하는 부분 수열의 길이)

 * */

public class Main {

	static int N;
	static int[] dp, arr;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		arr = new int[N];
		dp = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.fill(dp, 1); // 각 원소 자체로 LIS 길이는 최소 1

		int maxLength = 1;
		for (int i = 1; i < N; i++) {
			for (int j = 0; j < i; j++) {
				if (arr[j] < arr[i]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			maxLength = Math.max(maxLength, dp[i]);
		}

		System.out.println(maxLength);

//		<이분 탐색>
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		int N = Integer.parseInt(br.readLine());
//		int[] arr = new int[N];
//		StringTokenizer st = new StringTokenizer(br.readLine());
//
//		for (int i = 0; i < N; i++) {
//			arr[i] = Integer.parseInt(st.nextToken());
//		}
//
//		ArrayList<Integer> lis = new ArrayList<>();
//		lis.add(arr[0]);
//
//		for (int i = 1; i < N; i++) {
//			if (arr[i] > lis.get(lis.size() - 1)) {
//				lis.add(arr[i]); // 증가하는 경우 추가
//			} else {
//				int idx = Collections.binarySearch(lis, arr[i]);
//				if (idx < 0)
//					idx = -idx - 1;
//				lis.set(idx, arr[i]); // 적절한 위치에 갱신
//			}
//		}
//
//		System.out.println(lis.size());
	}
}