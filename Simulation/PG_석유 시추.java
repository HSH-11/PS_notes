import java.util.*;

// "덩어리 번호 매기기"
// 전체 탐색(딱 한 번): BFS를 통해 석유 덩어리를 찾으면, 그 덩어리에 속한 모든 칸에 ID를 부여하고,
// 그 덩어리의 전체 크기를 Map에 저장

class Solution {
    static int n, m;
    static int[][] groupMap; // 각 칸이 어떤 덩어리(ID)에 속하는지 저장
    static Map<Integer, Integer> groupSize = new HashMap<>(); // ID별 석유량 저장
    static boolean[][] visited;
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    
    public int solution(int[][] land) {
        n = land.length;
        m = land[0].length;
        groupMap = new int[n][m];
        visited = new boolean[n][m];
        int groupId = 1;
        
        // 덩어리 ID 부과 및 크기 계산
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (land[i][j] == 1 && !visited[i][j]) {
                    int size = bfs(i,j,groupId,land);
                    groupSize.put(groupId, size);
                    groupId++;
                }
            }
        }
        
        // 각 열마다 시추했을 때 얻는 석유량 계산
        int maxOil = 0;
        for (int j = 0; j < m; j++) {
            int currOil = 0;
            Set<Integer> groups = new HashSet<>();
            
            for (int i = 0; i < n; i++) {
                if (groupMap[i][j] > 0) {
                    groups.add(groupMap[i][j]);
                }
            }
            
            for (int id : groups) {
                currOil += groupSize.get(id);
            }
            
            maxOil = Math.max(maxOil, currOil);
        }
        
        return maxOil;
    }
    
    private int bfs(int r, int c, int id, int[][] land) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{r, c});
        visited[r][c] = true;
        groupMap[r][c] = id;
        int count = 1;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dy[d];
                int nc = cur[1] + dx[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    if (land[nr][nc] == 1 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        groupMap[nr][nc] = id;
                        q.add(new int[]{nr, nc});
                        count++;
                    }
                }
            }
        }
        return count;
    }
}