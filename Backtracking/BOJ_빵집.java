import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/* <목표>
 * 모든 가능한 경로를 탐색하는 것이 아니라, 연결 가능한 최대 개수만 찾으면 됨
 * <특징>
 * 한 경로가 성공하면 더 이상 다른 경로를 찾을 필요가 없음
 * 첫 번째로 발견한 경로를 바로 선택
 * <탐색 방식>
 * DFS를 사용하여 오른쪽 위 -> 오른쪽 -> 오른쪽 아래 순서로 탐색
 * 방문 처리를 되돌릴 팔요없음(가장 먼저 오른쪽 끝에 도착한 경로가 정답이므로 더 이상 다른 경로를 찾을 필요가 없음)
 * */
public class Main {
	
	static char[][] map;
	//오른쪽,오른쪽 위,오른쪽 아래
	static int[] dy = {-1,0,1}; 
	static int R,C,count;
	

	public static void main(String[] args) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    StringTokenizer st = new StringTokenizer(br.readLine());
	    R = Integer.parseInt(st.nextToken());
	    C = Integer.parseInt(st.nextToken());
	    map = new char[R][C];

	    for (int i = 0; i < R; i++) {
	        String line = br.readLine();
	        for (int j = 0; j < C; j++) {
	            	map[i][j] = line.charAt(j);               
	        }
	    }
	    
	    count = 0;
	    for (int i = 0; i < R; i++) {
	    	if(dfs(i, 0)) {
	    		count++;
	    	}
	    }
	    
	    System.out.println(count);
	       
	}

	static boolean dfs(int y, int x) {
	    
		if (x == C - 1) return true; 

	    for (int i = 0; i < 3; i++) {
	    	int ny = y + dy[i];
	    	int nx = x + 1;
	    	
	    	if (ny >= 0 && ny < R && nx < C && map[ny][nx] == '.') {
	    		map[ny][nx] = 'x';
	    		if(dfs(ny, nx)) {
	    			return true;
	    		}
	    	}
	    	
	    }
	    return false;
	}

}