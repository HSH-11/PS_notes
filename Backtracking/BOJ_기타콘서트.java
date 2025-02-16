import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* <문제 정의 및 해결 방법>
 * 특정 사람이 연주할 수 있는 기타는 비트마스크 형태로 주어짐
 * 여러 사람이 협력해서 최소의 기타 개수로 모든 곡을 연주
 * 비트마스크 값을 이용하여 연주 가능한 기타 집합 나타냄
 * 완전탐색 + 최소 인원 찾기 (Brute Force)
 * => 부분집합을 생성하여 각 조합이 모든 기타를 연주할 수 있는 지 확인
 * => 그 중 가장 적은 기타 수를 가지는 조합 선택
 */
public class Main {

	static int N, M, maxSongs;
	static int minGuitar = Integer.MAX_VALUE;
	static long[] guitars;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		guitars = new long[N];

		// 인접 배열 초기화
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			st.nextToken(); // 기타 이름 무시
			char[] guitarYN = st.nextToken().toCharArray();		
			// M=3이며 2,3번 가능=>110(2)로 표현
			for (int j = 0; j < M; j++) {
				if (guitarYN[j] == 'Y') {
					guitars[i] |= (1L << j);
				}
			}
		}

		
		findComb(0, 0L, 0);
		
		if (minGuitar == 0) {
			minGuitar = -1;
		}
		
		System.out.println(minGuitar);

	}

	// 백트래킹을 사용하여 가능한 모든 기타 조합 탐색
//	idx:현재 탐색 중인 기타의 index
//	selectCount: 현재까지 선택한 기타의 개수
//	playedMask: 현재까지 선택한 기타들이 연주 가능한 곡을 비트마스크로 표현
	static void findComb(int idx, long guitarMask, int selected) {
		int songCount = Long.bitCount(guitarMask); // 연주 가능한 곡 개수 확인
		
		//현재 최대값은 같지만, 사용한 기타의 수가 더 작을 때
        if(songCount == maxSongs && selected < minGuitar) {
            minGuitar = selected;
        }
        //현재 최대값보다 더 많은 곡을 칠 수 있을 때
        if(songCount > maxSongs) {
            minGuitar = selected;
            maxSongs = songCount;
        }
        //모든 곡을 칠 때, 모든 기타를 확인했을 때
        if(songCount == M || idx == N){
            return;
        }
		
		
		findComb(idx + 1,  guitarMask | guitars[idx], selected+1); // 기타 선택
		findComb(idx + 1, guitarMask, selected); // 기타 선택하지 않음
	}

}