import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/* 문제 해결 과정
이 문제는 일반적인 다익스트라와는 다르게 격자 형태로 인접한 네 방향으로 이동하면서 최단거리를 갱신해야 한다.
우선 순위 큐를 이용한 다익스트라 알고리즘을 적용하여 현재 위치에서 가장 적은 비용으로 이동할 수 있는 노드부터 탐색
*/
public class Main {

	static int N;
	static final int INF = Integer.MAX_VALUE;
	static int[][] map; // 도둑 루피의 비용을 저장할 배열
	static PriorityQueue<Node> pq;
	static int[][] dist; // 최단거리 배열 정의
	// 상,하,좌,우
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static class Node implements Comparable<Node> { // 노드 객체는 y,x,cost를 저장하고 비용 기준으로 정렬된다
		int y;
		int x;
		int weight;

		public Node(int y,int x, int weight) {

			this.y = y;
			this.x = x;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.weight - o.weight;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int cnt = 0;
		while (true) {
			N = Integer.parseInt(br.readLine());

			if (N == 0)
				return;

			map = new int[N][N];
			dist = new int[N][N];

			// map 및 최단비용 거리 초기화(INF)
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					dist[i][j] = INF;
				}
			}

			dist[0][0] = map[0][0]; // 시작점의 비용 초기화

			dijkstra();
			
			cnt++;
			System.out.println("Problem "+cnt+": "+ dist[N-1][N-1]); // 도착지까지 도달하면 최단거리 출력

		}

	}

	static void dijkstra() {
		pq = new PriorityQueue<Node>();
		pq.offer(new Node(0,0,map[0][0]));
		
		while(!pq.isEmpty()) {
			Node node = pq.poll();
			int curr_y = node.y;
			int curr_x = node.x;
			int weight = node.weight;
			
			if (weight > dist[curr_y][curr_x]) continue; //이미 weight가 크면 최단거리를 갱신할 수 없음 
			
			// 상하좌우 탐색하며 dist 갱신
			for (int i = 0; i < 4; i++) { 
				int ny = curr_y + dy[i];
				int nx = curr_x + dx[i];
				
				if (nx<0 || ny<0 || nx>=N || ny>=N) continue; //map 이탈 방지
				
				if (dist[ny][nx] > dist[curr_y][curr_x] + map[ny][nx]) { // 최소경로 갱신
					dist[ny][nx] = dist[curr_y][curr_x] + map[ny][nx];
					pq.offer(new Node(ny,nx,dist[ny][nx]));
				}
			}
		}	
	}
}