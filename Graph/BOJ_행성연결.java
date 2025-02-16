import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
 * 제국 내 모든 행성을 유지 비용이 최소화가 되도록 연결
 * 임의의 정점(행성)에서 시작하여 방문한 정점을 기준으로 가장 낮은 가중치의 간선을 선택
 * 모든 정점이 연결될 때까지 반복
 * MST가 완성되면 총 비용 출력
 * --> 최소 스패닝 트리(프림,크루스칼 활용)
 * 시간 복잡도: N개의 정점을 모두 탐색하며 우선순위 큐를 사용하여 삽입과 삭제가 O(log N)을 가지므로 =>O(N^2logN)
 */
public class Main {

	static int N;
	static int[][] adjmatrix;
	static boolean[] visit;
	static long sum;
	
	//우선순위 큐를 활용하여 최소 비용 간선 선택
	static PriorityQueue<Vertex> pq = new PriorityQueue<>((e1,e2)-> Integer.compare(e1.cost, e2.cost));
	
	static class Vertex {
		int vertex;
		int cost;
		
		Vertex(int vertex, int cost){
			this.vertex = vertex;
			this.cost = cost;
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		adjmatrix = new int[N][N];
		
		
		//인접 배열 초기화
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				adjmatrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		

		visit = new boolean[N];
		sum = 0;
		
		pq.add(new Vertex(0,0));
		
		while(!pq.isEmpty()) {
			Vertex curr = pq.poll();
			int node = curr.vertex;
			int cost = curr.cost;
			
			if(visit[node]) continue;
			//MST 구성 과정
			//큐에서 꺼낸 정점이 방문되지 않았다면 방문 표시
			visit[node] = true;
			sum += cost;
			//해당 정점과 연결된 모든 정점들을 확인하고, 방문하지 않은 정점이면 큐에 추가
			//큐에서 계속 최소 비용의 간선을 뽑으며 진행
			for (int nextNode = 0; nextNode < N; nextNode++) {
				if (node != nextNode && !visit[nextNode] && adjmatrix[node][nextNode] != 0) {
					pq.add(new Vertex(nextNode,adjmatrix[node][nextNode]));
				}
			}
		}
		
		System.out.println(sum);
		
		

	}
	
}
