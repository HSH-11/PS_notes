package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

// 이분 탐색으로 범위를 좁혀 나가자
// 높이가 최대인 나무와 0을 범위로 설정해 두고 중간값을 기준으로 범위를 조절
// 높이 H마다 잘린 나무의 길이 총합이 M과 일치하는 지 확인
//	잘린 나무의 길이 총합이 M보다 큰 경우 H를 높이자 (low = mid + 1)
//  반대의 상황: H를 낮추자 (high = mid - 1)

public class Main {

	static int N, M;
	static int[] nums;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		nums = new int[N];
		
		st = new StringTokenizer(br.readLine());
		int max = 0;
		for (int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
			max = Math.max(max, nums[i]);
		}
		
		int low = 0, high = max, result = 0;
		while (low <= high) {
			int mid = (low + high) / 2;
			long total = 0;
			
			for (int num : nums) {
				if (num > mid) total += num - mid;
			}
			
			if (total >= M) {
				result = mid; // 최적의 절단기 높이 갱신
				low = mid + 1;
			}else {
				high = mid - 1; // 나무 부족 -> 절단기 낮추기
			}
		}
		
		System.out.println(result);
		
		
	}

	
}