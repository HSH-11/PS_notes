import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/* 
**백트래킹(Backtracking)**을 활용하여 퀸을 배치하면서 유효한 경우만 탐색한다.
arr[y] = i 를 통해 y번째 행에 i번째 열에 퀸을 배치한다.
check(row) 함수를 통해 같은 열이나 대각선에 다른 퀸이 있는지 검사한다.
유효한 경우에만 다음 행(y+1)으로 재귀 호출을 진행한다.
y == N까지 도달하면 모든 퀸이 배치된 것이므로 경우의 수를 증가시킨다.
*/
public class Main {

	static int N,count;
	static int[] arr;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		arr = new int[N];
		
		nQueen(0);
		System.out.println(count);
			
	}
	
	static void nQueen(int y) {
		
		if (y == N) {
            //모든 행에 퀸을 배치했다면 경우의 수 증가
			count++;
			return;
		}
		
        //현재 행에 대해 각 열에 퀸을 배치하는 경우 탐색
		for (int i = 0; i < N; i++) {
			arr[y] = i;
			if (check(y)) { 
				nQueen(y+1);
			}
		}
	}
	
	static boolean check(int row) {
		for (int i = 0; i < row; i++) {
			if (arr[i] == arr[row]) {
				return false;
			}
			
            //대각선 확인 방법
			if(Math.abs(row-i) == Math.abs(arr[row]-arr[i])) {
				return false;
			}
		}
		return true;
	}

}