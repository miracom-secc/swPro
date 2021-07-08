package swPro.source;

import java.io.*;
import java.util.*;


/*
 * 비트마스킹 연산
 * 1) num | (1<<i)
 * 		num에 값 추가
 * 		ex) 8 | (1<<2) = 1000 | 0100 = 1100(4번, 3번 발전소 On 상태)
 * 
 * 2) num & (1<<i)
 * 		num과 값이 같은 번호만 반환
 * 		ex) 8 & (1<<3) = 1000 & 1000 = 1000(4번 발전소만 켜진상태)
 * 			12 & (1<<3) = 1100 & 1000 = 1000 반환
 * 			따라서, 12 & (1<<i) == (1<<i) -> i는 현재 상태와 일치하는 값이라는 뜻(4번,3번 발전소 on)
 * 
 * 
 * 풀이
 * 1) 초기에 발전기의 상태를 비트값으로 표현
 *    ex) 3개의 발전소가 고장나 있지 않은 상태
 *    		10011
 *    		01110
 *    		00111
 * 2) 작동하는 발전기의 갯수가 P와 일치할때까지 재귀호출 탐색
 * 3) 최소값 memorization
 */

public class BJ_1102_Best {
	static int[] dp;
	static int[][] cost;
	static int N;
	static int P;
	static int init = 123456789;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());	//발전소 개수
		cost = new int[N][N];					//재시작 비용(행렬 형태로 입력이 주어지므로 2차원 배열)			
		dp = new int[1<<N];						//비트값으로 표현하기 위해 발전소 갯수^N
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				cost[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		Arrays.fill(dp, -1);	//고치는것이 불가능할때 -1출력해야 하므로 -1로 초기화

		
		String[] status = br.readLine().split("");	
		P = Integer.parseInt(br.readLine());	//적어도 P개 이상의 발전소는 고장이 나면 안됨.
		
		int powerStatus = 0;	//현재 켜져있는 발전기 상태(비트마스크) : YNN -> 001(2) = 1 
		int cnt = 0;	//현재 켜져있는 발전기 갯수
		for(int i=0; i<status.length; i++) {
			if(status[i].equals("Y")) {
				powerStatus = powerStatus | (1<<i);
				cnt++;
			}
		}
		int anwser = powerplant(cnt,powerStatus);

		System.out.println(anwser == init? -1 : anwser);		
	}
	
	static int powerplant(int cnt, int powerStatus) {
		if(cnt >= P ) return 0;		//비용이 0일때, 발전기 상태 NYN, P=1이면, 비용은 0
		if(dp[powerStatus] != -1) return dp[powerStatus];
		
		dp[powerStatus] = init; 
		
		for(int i=0; i<N; i++) {
			// pNum의 발전소가 켜져있을 때
			if((powerStatus &(1<<i)) == (1<<i)) {
				for(int j=0; j<N; j++) {
					// 같은 번호의 발전소인 경우 || j도 켜져있는 경우 스킵 
					if((i==j) || (powerStatus&(1<<j)) == (1<<j)) continue;
					
					//최소값 구하기
					dp[powerStatus] = Math.min(dp[powerStatus], powerplant(cnt+1, powerStatus|(1<<j)) + cost[i][j]);	// pNum|1<<고장난 발전기 + cost[가동중발전기][고장난발전기]
				}
				
			}
		}
		return dp[powerStatus];
	}
}
