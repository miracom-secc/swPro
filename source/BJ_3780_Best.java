package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 네트워크 연결
 * 1) N개의 기업 : 1~N까지 배열에 각각 저장 
 * 2) 명령어 수행
 *    E I   : 기업 I와 I의 센터까지의 거리 출력
 *            -> I부터 I의 센터(최상위 부모 노드)까지 가는데 거치는 기업들이 있음
 *               각 기업들간의 거리를 누적하여 저장 필요 
 *    I I J : 센터 I를 기업 J에 연결 
 *            -> J의 센터(최상위 부모 노드)로 통합, 
 *               기업 I와 J를 잇는 라인의 길이는 |I – J|(mod 1000)
 *               
 * 필요한 배열 
 *  - parents : 각 기업(배열 인덱스)의 센터(부모 노드) 정보를 저장
 *  - arrLen : 각 기업(배열 인덱스)으로부터 센터까지의 거리의 누적합을 저장
 *  
 * 특이사항
 *  print로 출력시 1476ms, BufferedWriter로 출력시 568ms
 *  - E명령어 최대 200000개, 테스트케이스 수는 정해지지 않음
 *    => 만약 tc = 20000이라면? 최대 1000 * 2000000 = 2억
 */

public class BJ_3780_Best { // 백준 3780
	static int N, M, len;
	static int[] parents, arrLen;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		StringBuilder sb =new StringBuilder();
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int tc=1; tc<=T; tc++) {
			N = Integer.parseInt(br.readLine().trim());
			
			parents = new int[N+1];
			arrLen = new int[N+1];
			
			for(int i=1; i<N+1; i++) {
				parents[i] = i;
			}
			
			while(true) {
				st = new StringTokenizer(br.readLine());
				
				char cmd = st.nextToken().charAt(0);
				
				if(cmd == 'E') {
					int X = Integer.parseInt(st.nextToken());
					findParent(X);
					sb.append(arrLen[X]).append("\n");
				}
				else if (cmd == 'I'){
					int X = Integer.parseInt(st.nextToken());
					int Y = Integer.parseInt(st.nextToken());
					
					union(X, Y);
				}
				else break;
			}
		}
		bw.write(sb.toString());
		bw.close();
		br.close();
	}
	
	static int findParent(int x) {
		if(parents[x] == x) return x;
		
		int pX = findParent(parents[x]);
		arrLen[x] += arrLen[parents[x]];
		
		return parents[x] = pX;
	}
	
	static void union(int x, int y) {
		parents[x] = y;
		arrLen[x] = (Math.abs(parents[x] - x)%1000);
	}
}