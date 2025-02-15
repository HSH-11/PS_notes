import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class BOJ_최소스패닝트리_크루스칼 {
    static int V, E;  // 정점의 개수 V(1≤V≤100,000)와 간선의 개수 E(1≤E≤200,000)
    static long sum; // long :  V-1 개의 간선의 가중치를 합
    static int[] parent;
    static Edge[] edges;  // 간선 리스트
    // 방문 체크 자료구조 없음.
    
    public static void main(String[] args) throws Exception{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
                
        parent = new int[V+1];
        edges = new Edge[E];
        
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            
            edges[i] = new Edge(v1, v2, c);
        }
        
        // 풀이
        sum = 0;
        
        // 간선을 최소 비용 기준으로 정렬 - 부담되는 부분
        Arrays.sort( edges, (e1, e2) -> e1.c - e2.c );
        // 
        makeSet();
         
        int cnt = 0; // 간선의 수
        for (int i = 0; i < E; i++) {  // 작은 것 부터!!
            Edge edge = edges[i];       
            // union 안에서 두 부모가 같아서 합칠 필요가 없으면 return false 그렇지 않으면 true 를 return 하도록 변경
            if( union( edge.v1, edge.v2 ) ) {
                cnt++;
//                  System.out.println(edge);
                sum += edge.c;
                if( cnt == V - 1) break; // 간선 수가 정점보다 하나 적으면 ( 모두 연결 )
            }
        }
        
        System.out.println(sum);
    }
    
    static void makeSet() {
        for (int i = 1; i <= V; i++) {
            parent[i] = i;
        }
    }
    
    static int findSet(int x) {
        if( parent[x] == x ) return x;
        else return parent[x] = findSet(parent[x]);
    }
    
    // 서로소이면 합치고 true 리턴하도록
    static boolean union(int x, int y) {
        int px = findSet(x);
        int py = findSet(y);
        
        if ( py == px ) return false;
        
        if( px < py ) parent[py] = px;
        else parent[px] = py;
        
        return true;
    }
    
    // 간선 중심이므로 간선이 두 정점과 비용 정보를 가지고 있다.
    static class Edge{
        
        int v1, v2, c;
        
        Edge(int v1, int v2, int c) {
            this.v1 = v1; this.v2 = v2; this.c = c;
        }
//      @Override
//      public String toString() {
//          return "Edge [v1=" + v1 + ", v2=" + v2 + ", c=" + c + "]";
//      }
        
        
    }
}