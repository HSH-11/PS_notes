import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Deque;
import java.util.StringTokenizer;
/*
 * <조건 및 구현>
 * 1.한 칸에 물고기 최대 1마리
 * 2.처음 아기 상어의 크기는 2이며, 아기 상어는 1초에 상하좌우로 한 칸씩 이동함
 * 3.아기 상어는 자신의 크기보다 큰 물고기가 있는 칸은 지나갈 수 없고, 나머지 칸은 모두 지날 수 있음
 * 4.아기 상어는 자기보다 작은 크기의 물고기만 먹을 수 있음
 * 5.아기 상어의 이동은 1초, 먹는 시간 고려 x
 * 6.먹으면 빈칸
 * 7.아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때마다 크기가 1 증가
 * <추가>
 * 더 이상 먹을 물고기가 공간에 없으면 엄마에게 도움 요청
 * 1마리면 그거 먹고, 더 많으면 거리가 가장 가까운 것
 * 거리: 아기 상어가 있는 칸에서 물고기가 있는 칸의 최소거리 
 * <BFS >
 * 가장 가까운 물고기 먼저
 * 가장 위쪽
 * 가장 왼쪽
 * <목표>
 * 아기 상어가 몇 초 동안 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아 먹을 수 있는 지
 */

public class Main {
    static int N, sharkSize = 2, cnt = 0;
    static int[][] map;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    
    static class Fish {
        int x, y, dist;

        Fish(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        StringTokenizer st;
        int startX = -1, startY = -1;
        
        for (int i = 0; i < N; i++) {
        	st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 9) { // 아기 상어 위치
                    startX = i;
                    startY = j;
                    map[i][j] = 0;
                }
            }
        }
        
        System.out.println(bfs(startX, startY)); 
    }

    public static int bfs(int startX, int startY) {
        Deque<Fish> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];
        queue.offer(new Fish(startX, startY, 0));
        visited[startX][startY] = true;

        int totalTime = 0;

        while (!queue.isEmpty()) {
            List<Fish> eatableFishes = new ArrayList<>();
            int size = queue.size();
            
            while (size-- > 0) {
                Fish current = queue.poll();
                for (int i = 0; i < 4; i++) {
                    int nx = current.x + dx[i];
                    int ny = current.y + dy[i];
                    if (nx >= 0 && ny >= 0 && nx < N && ny < N && !visited[nx][ny] && map[nx][ny] <= sharkSize) {
                        //아직 방문 X, 사이즈가 현재 아기 상어보다 작다면 
                    	visited[nx][ny] = true;
                        if (map[nx][ny] == 0 || map[nx][ny] == sharkSize) {
                        // 이동 가능한 경우 bfs 큐에 추가
                            queue.offer(new Fish(nx, ny, current.dist + 1));
                        } else if (map[nx][ny] < sharkSize && map[nx][ny] > 0) {
                        	// 이동 중에 자기보다 작은 먹을 물고기 찾음
                            eatableFishes.add(new Fish(nx, ny, current.dist + 1));
                        }
                    }
                }
            }
            //먹을 수 있는 물고기가 있다면 거리가 가까운 물고기부터 먹도록
            if (!eatableFishes.isEmpty()) {
                Collections.sort(eatableFishes, (f1, f2) -> {
                    if (f1.dist == f2.dist) {
                        if (f1.x == f2.x) {
                            return f1.y - f2.y;
                        }
                        return f1.x - f2.x;
                    }
                    return f1.dist - f2.dist;
                });

                Fish fishToEat = eatableFishes.get(0);
                map[fishToEat.x][fishToEat.y] = 0;
                totalTime += fishToEat.dist;
                cnt++;

                if (cnt == sharkSize) {
                    sharkSize++;
                    cnt = 0;
                }
                
                //아기 상어가 물고리를 먹은 후의 상태 초기화
                //큐를 비워야 아기 상어가 물고기를 먹은 후의 새로운 상태에서 다시 탐색을 시작
                //방문 기록 역시 초기화
                //새로운 위치에서 탐색을 시작하도록 queue에 추가(아기 상어가 먹은 위치에서 다시 시작)
                queue.clear();
                visited = new boolean[N][N];
                queue.offer(new Fish(fishToEat.x, fishToEat.y, 0));
                visited[fishToEat.x][fishToEat.y] = true;
            }

            if (queue.isEmpty() && eatableFishes.isEmpty()) {
                break;
            }
        }

        return totalTime;
    }
}