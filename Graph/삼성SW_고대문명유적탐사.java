import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*<문제 분석>
 * 유적지 5x5 격자 형태
 * 유물 조각은 총 7가지
 * [1] 탐사 진행
 * -3x3 격자 선택
 * -선택된 격자는 시계방향으로 90,180,270 중 하나의 각도 만큼 회전 진행
 * -회전 목표(정렬 기준)
 * 1-1. 유물 1차 획득 가치 최대화
 * 1-2. 회전한 각도가 가장 작은 방법 선택
 * 1-3. 회전 중심 좌표 열이 가장 작은 구간->열이 같다면 행이 가장 작은 구간
 * [2] 유물 획득
 * #유물 1차 획득
 * -상하좌우로 인접한 같은 종류의 유물은 서로 연결되어 있으며,
 * -3개 이상 연속하면 조각이 모여 유물이 되고 사라짐
 * 	->유적의 벽면에 써있는 숫자로 채워짐
 * 	(1)열 번호가 작은 순으로 조각이 생김
 * 	(2)열 번호가 같다면 행 번호가 큰 순서로 조각이 생김
 * 	(3)이후 남은 숫자부터 다시 순서대로 사용가능
 * -유물의 가치는 모인 조각의 개수와 같음
 * #유물 연쇄 획득
 * -새로 채우고 난 뒤에도 유물 탐사를 통해 유물 획득
 * [3] 탐사 반복
 * -탐사진행~유물 연쇄 획득까지가 1턴이며 총 K번 턴에 걸쳐 진행
 * -각 턴마다 획득한 유물의 가치의 총합을 출력
 * -K번 진행 안하여도 유물이 더 이상 어떠한 방법으로 못 찾으면 탐사 즉시 종료
 *  =>종료되는 턴에는 아무것도 출력X
 * <의사 코드>
 * 1.초기화
 * -격자판 상태 저장
 * -회전 및 제거 연산을 위한 임시 배열
 * -8방향 및 4방향 이동을 위한 배열 설정
 * -숫자를 채우기 위한 queue 선언
 * 2.시뮬레이션 진행(k번)
 * -최적의 회전 정보 초기화
 * -9개의 중심점과 회전 각도에 대해 반복
 * -가장 높은 점수 (동점일 경우 우선 순위 기준) best 갱신
 * -best를 적용하여 회전 후 연쇄 제거 시작
 * :빈칸을 채우고 bfs를 실행하여 제거할 수 있는 그룹을 찾고 점수 계산
 * :제거할 그룹이 없을 때 까지 반복
 * :결과 원본에 반영
*/

public class Main {

	// 격자판의 크기
	static final int SIZE = 5;
	// 유물 탐사 반복 횟수 및 벽면에 적힌 유물 조각의 개수
	static int K, M;
	// 현재 격자판의 상태를 저장하는 배열
	static int[][] map = new int[SIZE][SIZE];
	// 회전 및 제거 연산을 시뮬레이션하기 위한 임시 배열
	static int[][] copyMap = new int[SIZE][SIZE];
	// 상하좌우(BFS 탐색에 사용)
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	// 8방향 이동을 위한 방향 배열(회전 연산에 사용)
	// 중심점을 기준으로 좌측 위부터 반시계 방향
	static int[] dc = { -1, -1, -1, 0, 1, 1, 1, 0 };
	static int[] dr = { -1, 0, 1, 1, 1, 0, -1, -1 };
	// 제거된 숫자를 채우기 위한 새로운 숫자들을 저장하는 큐
	static Queue<Integer> q;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		K = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		q = new LinkedList<Integer>();
		
		for (int i = 0; i < SIZE; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < SIZE; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 벽에 적힌 숫자를 저장할 큐
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			q.offer(Integer.parseInt(st.nextToken()));
		}

		// K번의 시뮬레션 진행
		for (int i = 0; i < K; i++) {
			RotateInfo best = null; // 최고의 회전을 저장
			// 모든 가능한 회전 중심점과 각도에 대해 시뮬레이션
			for (int r = 1; r <= 3; r++) {
				for (int c = 1; c <= 3; c++) {
					for (int angle = 1; angle <= 3; angle++) {
						RotateInfo candidate = process(r, c, angle);
						// 최적의 회전 정보 갱신 (점수가 높은 순, 각도/열/행이 작은 순)
						// 반환값이 음수이면 candidate가 우선 순위가 더 높음
						if (best == null || candidate.compareTo(best) < 0) {
							best = candidate;
						}
					}
				}
			}

			int sum = best.score;
			if (sum == 0)
				break; // 제거할 수 있는 그룹이 없으면 종료

			// 최적의 회전을 실행하고 연쇄 제거 진행
			// copyMap 상태는 인접 유물이 제거된 상태
			process(best.row, best.column, best.angle);
			while (true) {
				fill(); // 빈칸 채우기
				int curScore = bfs();
				if (curScore == 0)
					break;
				sum += curScore;
			}
			sb.append(sum).append(" ");
			updateMap();

		}
		System.out.println(sb);

	}

	// 임시 맵 상태를 실제 맵에 반영
	static void updateMap() {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				map[r][c] = copyMap[r][c];
			}
		}
	}

	// 주어진 위치에서의 특정 각도 회전 후 제거 점수를 계산
//	* @param angle 회전 각도 (1: 90도, 2: 180도, 3: 270도)
	// @return 회전 정보와 획득 점수를 포함한 RotateInfo 객체
	static RotateInfo process(int r, int c, int angle) {
		rotate(r, c, angle);
		int score = bfs();
		return new RotateInfo(score, angle, c, r);
	}

	static void rotate(int r, int c, int angle) {
		// 현재 맵 상태를 임시 맵에 복사
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		// 8방향의 칸들을 angle에 따라 회전
		for (int i = 0; i < 8; i++) {
			int tnr = r + dr[i];
			int tnc = c + dc[i];
			// 현재 배열은 반시계 방향으로 구성
			// 90도 = 중심점을 기준으로 반시계방향 두칸 전의 요소를 가져온다
			// 180도 = 네칸 전
			// 270도 = 여섯칸 전
			// 8칸을 돌면서 angle에 2를 곱한 값에 현재 i에서 더해주면 좌표를 알 수 있음
			// 모듈러 연산을 통해 반복되는 원령 리스트 처리
			int nr = r + dr[(i + angle * 2) % 8];
			int nc = c + dc[(i + angle * 2) % 8];
			copyMap[tnr][tnc] = map[nr][nc];
		}
	}

//	BFS로 같은 숫자가 3개 이상 연속된 그룹 찾아 제거
	// @return 제거된 칸의 개수 (점수)
	static int bfs() {
		// 방문 여부를 체크할 배열
		boolean[][] visit = new boolean[SIZE][SIZE];
		int score = 0;
		// 각 위치마다 bfs를 돌려 연속한 유물 체크
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				// bfs 진행을 위한 큐
				Queue<int[]> queue = new LinkedList<int[]>();
				// 같은 값을 발견한 좌표 저장
				List<int[]> group = new ArrayList<>();
				if (!visit[y][x]) {
					queue.offer(new int[] { y, x });
					group.add(new int[] { y, x });
					visit[y][x] = true;
				}

				// BFS로 인접한 같은 숫자 찾기
				while (!queue.isEmpty()) {
					int[] current = queue.poll();
					int r = current[0];
					int c = current[1];
					for (int i = 0; i < 4; i++) {
						int nr = r + dy[i];
						int nc = c + dx[i];
						// 격자판 유효 체크
						if (nr < 0 || nr >= SIZE || nc < 0 || nc >= SIZE)
							continue;
						// 같은 숫자 인지 체크
						if (copyMap[nr][nc] != copyMap[r][c])
							continue;
						// 방문 여부 체크
						if (visit[nr][nc])
							continue;

						visit[nr][nc] = true;
						queue.add(new int[] { nr, nc });
						group.add(new int[] { nr, nc });
					}
				}
				// 그룹 크기가 3이상이면 제거 처리, 동시에 점수를 계산해서 반환
				if (group.size() >= 3) {
					for (int[] axis : group) {
						copyMap[axis[0]][axis[1]] = 0;
						score++;
					}
				}
			}
		}
		return score;
	}

	static void fill() {
		for (int c = 0; c < SIZE; c++) {
			for (int r = SIZE - 1; r >= 0; r--) {
				if (copyMap[r][c] == 0) {
					copyMap[r][c] = q.poll();
				}
			}
		}
	}

}

// 회전 정보를 저장하고 비교하기 위한 클래스
// 점수가 높은 순, 각도가 작은 순, 열이 작은 순, 행이 작은 순
class RotateInfo implements Comparable<RotateInfo> {
	int score;
	int angle;
	int column; // 회전 중심점의 열 좌표
	int row; // 회전 중심점의 행 좌표

	public RotateInfo(int score, int angle, int column, int row) {
		this.score = score;
		this.angle = angle;
		this.column = column;
		this.row = row;
	}

	@Override
	public int compareTo(RotateInfo o) {
		int diff;
		// 점수 내림차 순
		// 음수이면 앞에 있는게 뒤에거보다 작음
		if ((diff = o.score - this.score) != 0)
			return diff;
		// 각도가 오름차 순
		if ((diff = this.angle - o.angle) != 0)
			return diff;
		// 열 좌표 오름차 순
		if ((diff = this.column - o.column) != 0)
			return diff;
		// 행 좌표 오름차 순
		return this.row - o.row;
	}

}