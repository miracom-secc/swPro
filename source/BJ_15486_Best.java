package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * DP 배열
 * 
 * DP : 상담했을 때 얻을 수 있는 최대이익을 날짜별로 저장
 * max : 최대 이익
 * 
 1  2  3  4   5   6  7      (일)
 ------------------------------------------------
 3  5  1  1   2   4  2      (걸리는 시간)
 10 20 10 20  15  40 200    (이익) 
 ------------------------------------------------
[0, 0, 0, 10, 0 , 0, 0 , 0 ] max = 0
[0, 0, 0, 10, 0 , 0, 20, 0 ] max = 0
[0, 0, 0, 10, 0 , 0, 20, 0 ] max = 0
[0, 0, 0, 10, 30, 0, 20, 0 ] max = 10 (1일차)
[0, 0, 0, 10, 30, 0, 45, 0 ] max = 30 (1일차+4일차)
[0, 0, 0, 10, 30, 0, 45, 0 ] max = 30 (1일차+4일차)
[0, 0, 0, 10, 30, 0, 45, 0 ] max = 45 (1일차+4일차+5일차) 
[0, 0, 0, 10, 30, 0, 45, 45] max = 45 (1일차+4일차+5일차)


 1  2  3  4  5   6   7   8   9   10      (일)
 ------------------------------------------------
 1  1  1  1  1   1   1   1   1    1      (걸리는 시간)
 1  2  3  4  5   6   7   8   9   10      (이익)
 ------------------------------------------------
[0, 1, 0, 0, 0 , 0 , 0 , 0 , 0 , 0 , 0 ] max = 0
[0, 1, 3, 0, 0 , 0 , 0 , 0 , 0 , 0 , 0 ] max = 1
[0, 1, 3, 6, 0 , 0 , 0 , 0 , 0 , 0 , 0 ] max = 3
[0, 1, 3, 6, 10, 0 , 0 , 0 , 0 , 0 , 0 ] max = 6
[0, 1, 3, 6, 10, 15, 0 , 0 , 0 , 0 , 0 ] max = 10
[0, 1, 3, 6, 10, 15, 21, 0 , 0 , 0 , 0 ] max = 15
[0, 1, 3, 6, 10, 15, 21, 28, 0 , 0 , 0 ] max = 21
[0, 1, 3, 6, 10, 15, 21, 28, 36, 0 , 0 ] max = 28
[0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 0 ] max = 36
[0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55] max = 45
[0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55] max = 55
 */
public class BJ_15486_Best {

	public static void main(String[] args) throws Exception{
		//퇴사2 DP
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());

		int[] dp = new int[N+2];
		int[] t = new int[N+2];
		int[] p = new int[N+2];
		int max = Integer.MIN_VALUE;
		
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			t[i] = Integer.parseInt(st.nextToken()); // 날짜
			p[i] = Integer.parseInt(st.nextToken()); // 금액
		}
		
		for(int i=1;i<=N+1;i++) {
			max = Math.max(max, dp[i]); // 최대 이익

			if(i+t[i] <= N+1)
				dp[i+t[i]] = Math.max(max+p[i], dp[i+t[i]]); // 미래의 날짜에 값 저장
			
		}
		System.out.println(max);
	}
}