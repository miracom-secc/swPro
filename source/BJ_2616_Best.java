package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
7
35 40 50 10 30 45 60
2
 */

public class BJ_2616_Best {
	
	static int N, K, arr[];
	static int dp[][], sum[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		
		arr = new int[N+1]; 
		sum = new int[N+1];
		dp = new int[4][N+1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i=1; i<=N; i++) {
			int temp = Integer.parseInt(st.nextToken());
			 arr[i] = temp;
			 sum[i] = sum[i-1] + arr[i];
		}
		
		K = Integer.parseInt(br.readLine());

		for (int i=1; i<=3; i++) {
			int start = i * K; // 시작 인덱스
			for (int j=1; j<=N; j++) {
				if (j < start) continue;
				// i번째 기관차가 j까지 고를 수 있는 최대값(dp[i][j)
				// j-1번째 칸의 최대값(dp[i][j-1])과 현재인덱스와 i-1번 기관차의 승객합(dp[i-1][j-K]+(sum[j]-sum[j-K]))의 최대값
				dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j-K]+(sum[j]-sum[j-K]));
			}
		}
		
		bw.write(dp[3][N]+"");
		
		br.close();
		bw.close();
	}

}
