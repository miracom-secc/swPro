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

public class BJ_9655_Best {

	static int N;
	static int[] dp;

	public static void main(String[] args) throws Exception {
		BJ_9655_Best t = new BJ_9655_Best();
		long startTime = System.currentTimeMillis();
		/* ========================================= */
		// 알고리즘을 해당 쿼리 안에 넣어 작성한 알고리즘의 성능을 계산할 수 있다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    //BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));

	    try {
	    	N = Integer.parseInt(br.readLine());
	    	
	    	String rslt = (N%2 == 0) ? "CY" : "SK";
	    	System.out.println(rslt);
	    	br.close();
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		/* ========================================= */
		long endTime = System.currentTimeMillis();
		//System.out.println("수행속도 : "+(endTime - startTime));
	    
	}
}
