import java.util.*;

class Solution {
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    static int r, c;

    public int solution(String[] board) {
        r = board.length;
        c = board[0].length();
        
        int startR = 0;
        int startC = 0;
        
        char[][] grid = new char[r][c];
        boolean[][] visited = new boolean[r][c];
        
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                grid[i][j] = board[i].charAt(j);
                if (grid[i][j] == 'R') {
                    startR = i;
                    startC = j;
                }
            }
        }
        
        return bfs(startR, startC, grid, visited);
    }
    
    private int bfs(int y, int x, char[][] grid, boolean[][] visited) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[] {y, x, 0});
        visited[y][x] = true;
        
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            int curY = pos[0];
            int curX = pos[1];
            int count = pos[2];
            
            // 현재 위치가 목표 지점(G)인지 확인
            if (grid[curY][curX] == 'G') {
                return count;
            }
            
            for (int i = 0; i < 4; i++) {
                int ny = curY;
                int nx = curX;
                
                // 해당 방향으로 장애물이나 벽을 만날 때까지 계속 미끄러짐
                while (true) {
                    int nextY = ny + dy[i];
                    int nextX = nx + dx[i];
                    
                    // 범위를 벗어나거나 장애물('D')을 만나면 멈춤
                    if (nextY < 0 || nextY >= r || nextX < 0 || nextX >= c || grid[nextY][nextX] == 'D') {
                        break;
                    }
                    
                    ny = nextY;
                    nx = nextX;
                }
                
                // 미끄러져서 멈춘 위치가 처음 가보는 곳일 때만 큐에 추가
                if (!visited[ny][nx]) {
                    visited[ny][nx] = true;
                    q.add(new int[] {ny, nx, count + 1});
                }
            }
        }
        
        // 목표 지점에 도달할 수 없는 경우
        return -1;
    }
}