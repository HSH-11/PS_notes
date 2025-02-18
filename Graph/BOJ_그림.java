import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
<문제 분석>
1끼리 연결된 부분을 찾는 것이므로 BFS나 DFS 활용
<해결 전략>
방문여부 체크를 통한 중복 방문 방지
BFS탐색을 통해 1인 곳은 방문하여 크기 구하기
<시간 복잡도>
O(N*M)
*/

public class Main {
	static int N, M;
	static int map[][];
	static boolean[][] visited;
	static int max_count, max_area;
	static int[] dx = { 0, 0, -1, 1 };
	static int[] dy = { -1, 1, 0, 0 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		visited = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 1 && !visited[i][j]) {
					max_count++;
					max_area = Math.max(max_area, bfs(i, j));
				}
			}
		}

		System.out.println(max_count);
		System.out.println(max_area);
	}

	public static int bfs(int y, int x) {
		Queue<int[]> queue = new ArrayDeque<int[]>();
		queue.add(new int[] { y, x });
		visited[y][x] = true;

		int area = 1;

		while (!queue.isEmpty()) {
			int[] curr = queue.poll();

			for (int i = 0; i < 4; i++) {
				int nx = curr[1] + dx[i];
				int ny = curr[0] + dy[i];

				if (nx >= 0 && nx < M && ny >= 0 && ny < N) {
					if (!visited[ny][nx] && map[ny][nx] == 1) {
						area++;
						visited[ny][nx] = true;
						queue.add(new int[] { ny, nx });
					}
				}
			}
		}

		return area;
	}
}