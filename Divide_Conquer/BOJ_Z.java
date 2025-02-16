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
 * 분할 정복을 활용하여 Z모양을 탐색
 * 4등분하여 위치 찾기-목표 위치(r,c)가 어느 사분면에 속하는지 확인
 * 목표 위치가 속하지 않는 사분면은 탐색하지 않고 건너뛴다
 * N = 0이 될 때까지 재귀적으로 호출하여 정답을 찾는다
 * 사분면을 건너뛸 때마다 해당 사분면의 크기만큼 카운트를 증가
 * 한번의 재귀 호출마다 문제의 크기가 1/4로 줄어드므로, 시간 복잡도 O(logN)
 */
public class Main {

	static int N,r,c,ans;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		N = (int) Math.pow(2, N); //2^N 크기로 변환(배열 크기)
		
		z(0,0);
		
		System.out.println(ans);
		

	}
	static void z(int y, int x) {
		
		//종료 조건
		if (N == 1) return;
		
		N /= 2; //4등분할 크기
		
		//r,c의 사분면 위치 탐색
		if (r < y + N && c < x + N) { // 1사분면
			z(y,x);
		}else if(r < y + N && c >= x + N) { // 2사분면
			ans += N * N * 1;
			z(y,x + N);
		}else if(r >= y + N && c < y + N) {// 3사분면
			ans += N * N * 2;
			z(y+N,x);
		}else {// 4사분면
			ans += N * N * 3;
			z(y+N,x+N);
		}
		
	}
}
