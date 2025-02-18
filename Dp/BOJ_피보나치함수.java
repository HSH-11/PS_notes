import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;
/*
DP를 활용하여 0,1호출 횟수 재활용
*/

public class Main {
	private static int[] zero_cnt;
	private static int[] one_cnt;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		zero_cnt = new int[41];
		one_cnt = new int[41];
		
		zero_cnt[0] = 1;
		zero_cnt[1] = 0;
		one_cnt[0] = 0;
		one_cnt[1] = 1;
		
		for (int i = 2; i < 41; i++) {
			zero_cnt[i] = zero_cnt[i-2]+zero_cnt[i-1];
			one_cnt[i] = one_cnt[i-2]+one_cnt[i-1];
		}
		
		for (int i = 0; i < T; i++) {
			int n = Integer.parseInt(br.readLine());
			System.out.println(zero_cnt[n]+" "+one_cnt[n]);
			
		}	
	}	
}