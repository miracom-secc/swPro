package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 
시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
1 초	128 MB	7785	5097	4495	66.890%

▶ 문제
돌 게임은 두 명이서 즐기는 재밌는 게임이다.
탁자 위에 돌 N개가 있다. 상근이와 창영이는 턴을 번갈아가면서 돌을 가져가며, 돌은 1개 또는 3개 가져갈 수 있다. 마지막 돌을 가져가는 사람이 게임을 이기게 된다.
두 사람이 완벽하게 게임을 했을 때, 이기는 사람을 구하는 프로그램을 작성하시오. 게임은 상근이가 먼저 시작한다.

▶ 입력
첫째 줄에 N이 주어진다. (1 ≤ N ≤ 1000)

▶ 출력
상근이가 게임을 이기면 SK를, 창영이가 게임을 이기면 CY을 출력한다.

 */

public class BJ_9655 {

	static int N;
	static int[] dp;

	public static void main(String[] args) throws Exception {
		BJ_9655 t = new BJ_9655();
		long startTime = System.currentTimeMillis();
		/* ========================================= */
		// 알고리즘을 해당 쿼리 안에 넣어 작성한 알고리즘의 성능을 계산할 수 있다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    //BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));

	    dp = new int[1001];
	    Arrays.fill(dp, -1);
	    dp[0] = 1;
	    	
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	N = Integer.parseInt(st.nextToken());
	    
    	// 점화식 : pick(n) = n개의 돌이 있을 경우 상근이가 이길 수 있는 지 여부
    	dp[N] = pick(N-1) | pick(N-3);

	    System.out.println(dp[N] == 1 ? "SK" : "CY");
		
		
		/* ========================================= */
		long endTime = System.currentTimeMillis();
		//System.out.println("수행속도 : "+(endTime - startTime));
	    
	}
	
	/*
	 * 상근이가 이길 수 있는지 여부를 return
	 * 이 게임은은 결국 상근이가 이길 수 있는지 없는지만 판단하면 된다.
	 */
	static int pick(int n) {
		//기저사례 1 : 더이상 뽑을 것이 없으면 승리
		if (n == 0) return dp[0];
		
		//기저사례2 : 0보다 작으면 실패(잘못 가져간 경우 예 : 2개 남았는데 3개 가져간 경우 잘못 가져간 걸로 실패를 리턴)
		if (n < 0) return 0;
		
		int ret = dp[n];
		if (ret == -1) {
			int[] cyPick = {1,3}; //창영이가 뽑을 수 있는 돌의 갯수
			int winSk = 0;
			
			//창영이가 뽑고 난 뒤 상근이가 이길 수 있는지 여부를 다시 체크
			for (int i : cyPick) {
				winSk = winSk | (pick(n-i-1) | pick(n-i-3));
			}
			
			dp[n] = winSk;
		}
		
		return dp[n];
	}

}
