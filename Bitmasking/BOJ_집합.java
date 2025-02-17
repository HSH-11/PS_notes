import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * 입력의 크기가 최대 3,000,000줄까지 될 수 있으므로 입출력 최적화 필요
 * 집합의 원소가 1~20개로 한정되어 있다? => 비트 마스킹을 이용
 * 각 숫자 x에 대해 (x-1)번째 비트를 이용하여 포함 여부 관리
 * 모든 연산이 O(1) 비트연산으로 처리
 * M번의 연산을 처리하는 데 O(M) 소요
 */
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int M = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		int set = 0;

		for (int i = 0; i < M; i++) {
			String cmd = br.readLine();
			if (cmd.startsWith("add")) {
				int x = Integer.parseInt(cmd.split(" ")[1]);
				set |= (1 << (x - 1));
			} else if (cmd.startsWith("remove")) {
				int x = Integer.parseInt(cmd.split(" ")[1]);
				set &= ~(1 << (x - 1));
			} else if (cmd.startsWith("check")) {
				int x = Integer.parseInt(cmd.split(" ")[1]);

				if ((set & (1 << (x - 1))) != 0) {
					sb.append(1).append("\n");
				} else {
					sb.append(0).append("\n");
				}
			} else if (cmd.startsWith("toggle")) {
				int x = Integer.parseInt(cmd.split(" ")[1]);
				if ((set & (1 << (x - 1))) != 0) {
					set &= ~(1 << (x - 1));
				} else {
					set |= (1 << (x - 1));
				}
			} else if (cmd.equals("all")) {//20비트를 모두 1로 설정
				set = (1 << 20) - 1;
			} else if (cmd.equals("empty")) {
				set = 0;
			}

		}
		System.out.println(sb.toString());

	}

}