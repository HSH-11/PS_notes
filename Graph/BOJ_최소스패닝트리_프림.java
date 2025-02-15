import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class BJ_최소스패닝트리_프림 {
    static int V, E;
    static long sum; // long :  V-1 개의 간선의 가중치를 합
    static List<List<Vertex>> adjList; // 인접 리스트
    static boolean[] visit; // 방문 체크
    
    // 현재 고려 대상의 간선 중 비용이 가장 작은 것을 선택
    static PriorityQueue<Vertex> pqueue = new PriorityQueue<Vertex>( (e1, e2) -> e1.c - e2.c );
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        
        adjList = new ArrayList<>();
        
        for (int i = 0; i <= V; i++) { // List 의 1차 구조를 먼저 만든다. 0 dummy
            adjList.add(new ArrayList<Vertex>());
        }
        visit = new boolean[V+1]; // 0 dummy
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            // 방향이 없으므로 양쪽 다 가능
            adjList.get(v1).add( new Vertex(v2, c));
            adjList.get(v2).add( new Vertex(v1, c));
        }
        
        // 풀이
        sum = 0;
        pqueue.clear();
        
        prim();
        System.out.println(sum);
    }
    
    // pqueue 에 들어가는 건 현재 방문 가능한 모든 정점
    static void prim() {
        // first vertex
        int cnt = 1;
        visit[1] = true;
        // 시작정점으로부터 갈 수 있는 모든 정점을 넣는다. 
        // 이 후, 최소 비용 정점을 선택해서 또 그곳부터 갈 수 있는 모든 정점을 고려한다. 
        // => pqueue 가 알아서 이전 미방문 정점 포함 최소 비용 정점을 찾아준다.
        pqueue.addAll(adjList.get(1)); 
        
        while(! pqueue.isEmpty() ) {
            
            Vertex vertex = pqueue.poll();
            if( visit[vertex.v] ) continue; // edge 가 연결할 정점이 이미 방문한 것이면 skip
            
            visit[vertex.v] = true;
            sum += vertex.c;
            
            cnt++;
            if( cnt == V ) break; // 정점의 수만큼 선택
            
            // 그냥 전체 놓기
//          pqueue.addAll(adjList.get(edge.v)); 
            // 골라서 넣기
            for (Vertex v : adjList.get(vertex.v)) {
                if( !visit[v.v] ) pqueue.add(v);
            }
        }
    }
    // 정점의 자료구조에 종속된 간선은 다른 정점과 비용정보만 관리
    static class Vertex{        
        int v;
        int c;
        
        public Vertex(int v, int c) {
            this.v = v;
            this.c = c;
        }
    }
}







