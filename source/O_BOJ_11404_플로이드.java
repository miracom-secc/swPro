package Floyd;

import java.io.*;
import java.util.*;

public class O_BOJ_11404_플로이드 {
	static int n,m;
	static long map[][];
	static long INF = 99999998;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		n = Integer.parseInt(br.readLine());
		map = new long [n+1][n+1];
		
		for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                map[i][j] = (i==j) ? 0 : INF;
            }
        }
		
		m = Integer.parseInt(br.readLine());
		StringTokenizer st;

		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			map[u][v] = Math.min(map[u][v], w); // 해당 지점에 이미 값이 들어있는 경우 가중치 체크
		}

		floyd();	
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=1; i<=n; i++) { 
			for(int j=1; j<=n; j++) {
				long num = map[i][j] == INF ? 0: map[i][j] ;
				sb.append(num +" ");
			}
			sb.append("\n");
		}
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		br.close();
		
	}

	static void floyd() {

		for(int k =1; k <=n ; k++) { // 경유지.
			for(int i=1; i<=n; i++) { // 시작점
				for(int j=1; j<=n; j++) { // 도착점
					map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
				}			
			}
		}		
	}

}
