import java.io.*;
import java.util.*;

// 문제 정의
// 0과 1로 이루어진 N * N의 이진 영상에서 모든 값이 같으면 그 값만 출력하고 , 
// 다르면 4등분하여 각 영역을 압축한 결과를 ()안에 출력하는 압축된 문자열을 구하라

// 문제 해결 전략
// 분할 정복(재귀) 활용
// 주어진 영역의 값이 모두 같으면 그 값 출력
// 그렇지 않으면 해당 영역을 4등분해서 재귀적으로 압축 수행

// Pseudocode
// function compress(x, y, size):
//    if 모든 (x, y) ~ (x+size, y+size) 영역의 값이 같다면:
//        sb에 해당 값 추가
//        return
//
//    // 다르면 4분할 후 괄호로 묶어서 재귀 호출
//    sb에 '(' 추가
//
//    half = size / 2
//
//    compress(x, y, half)              // 왼쪽 위
//    compress(x, y + half, half)       // 오른쪽 위
//    compress(x + half, y, half)       // 왼쪽 아래
//    compress(x + half, y + half, half)// 오른쪽 아래
//
//    sb에 ')' 추가

public class Main {

	static int[][] arr;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		arr = new int[N][N];

		for (int i = 0; i < N; i++) {
			String st = br.readLine();
			for (int j = 0; j < N; j++) {
				int num = st.charAt(j) - '0';
				arr[i][j] = num;
			}
		}
		
		// (0,0)부터 N*N 크기 압축 시작
		compress(0,0,N);
		System.out.println(sb);

	}
	
	// 주어진 영역이 모두 같은 값인지 확인하고 같다면 해당 값을 기록
	// 아니라면 4분할하여 재귀적으로 처리
	static void compress(int x, int y, int size) {
		
		int pivot =  arr[x][y];
		boolean isSame =  true;
		
		for (int i = x; i < x + size; i++) {
			for (int j = y; j < y + size; j++) {
				if (arr[i][j] != pivot) {
					isSame = false;
					break;
				}
			}
			if (!isSame) break;
		}
		
		// 같은 값으로만 구성되어 있으면 해당 값만 추가
		if (isSame) {
			sb.append(pivot);
			return;
		}
		
		// 다르면 괄호 열고 4분할
		sb.append("(");
		int n = size / 2;
		compress(x,y,n); //좌상
		compress(x,y+n,n); //우상
		compress(x+n,y,n); //좌하
		compress(x+n,y+n,n); //우하
		sb.append(")");
	}

}
