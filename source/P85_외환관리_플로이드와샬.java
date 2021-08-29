package Floyd;

import java.io.*;
import java.util.*;

/* n(통화종류)개의 노드와 m(통화교환정보)개의 간선으로 이루어진 그래프의 최단경로를 구하는 문제
 * 협약비용 k를 사용유무에 따른 비용을 고려
 * 
 * 풀이 순서
 * 1. 플로이드 워셜 초기화.=> 최대값
 * 	 1) K 를 사용하지 않는 경우 : dist[i][j][0] = INF
 *   2) K 를 사용하는 경우	   : dist[i][j][1] = INF
 * 2. 교환 비용 정보 입력
 * 	 1) K 를 사용하지 않는 경우 : dist[i][j][0] = z (교환비용)  
 *   2) K 를 사용하는 경우	   : dist[i][j][1] = k (협약비용)  
 * 3. 플로이드 워셜 수행
 *   1) K 를 사용하지 않는 경우 : min(dist[x][y][0], dist[x][mid][0]+dist[mid][y][0])
 *   2) K 를 사용하는 경우	   : min(dist[x][y][1], dist[x][mid][1]+dist[mid][y][0], dist[x][mid][0]+dist[mid][y][1])
 * 4. 통화 발생 비용 계산
 *   1) min(dist[x][y][0], dist[x][y][1])
 */
/*
3
5 6 5 5
1 2 2
3 2 2
3 4 3
1 3 6
5 4 4
5 1 4
1 4
5 3
3 1
4 2
2 5
5 6 5 -5
1 2 2
3 2 2
3 4 3
1 3 6
5 4 4
5 1 4
1 4
5 3
3 1
4 2
2 5
5 4 3 1
1 2 1
2 3 2
3 4 1
4 5 2
1 2
1 3
3 5 
*/
// 

public class P85_외환관리_플로이드와샬 {
	
	static int n; // 통화종류의 수
	static int m; // 교환비용 정보개수
	static int d; // 업무일수
	static int k; // 협약비용
	
	static long dist[][][]; 
	static long INF = 1000000*10000000;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		int t = Integer.parseInt(br.readLine());
		for(int tc =1; tc <=t ; tc ++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			
			/* 1. 플로이드 워셜 초기화 */
			dist = new long[n+1][n+1][2];
			for(int i=1; i<=n; i++) {
				for(int j=1; j<=n; j++) {
					// 1) dist[i][j][0] : K 를 사용하지 않는 경우
					dist[i][j][0] =INF;
					// 2) dist[i][j][1] : K 를 사용하는 경우  
					dist[i][j][1] =INF;
				}
			}
			
			/* 2. 교환 비용 정보 입력 */
			for(int i=0; i<m ; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken()); // 통화 x
				int y = Integer.parseInt(st.nextToken()); // 통화 y
				int z = Integer.parseInt(st.nextToken()); // 비용 z
				
				// 1)  K 를 사용하지 않는 경우 세팅
				dist[x][y][0] = z;
				dist[y][x][0] = z;
				// 2)  K 를 사용하는 경우 세팅
				dist[x][y][1] = k;
				dist[y][x][1] = k;				
			}
			
			/*플로이드 수행 전 dist 배열의 값
			 	 - 		2/5 	6/5		 -		4/5 
			 	2/5 	 -		2/5 	 - 		 - 
			 	6/5 	2/5 	 - 		3/5 	 - 
			 	 -		 - 		3/5 	 - 		4/5 
			 	4/5 	 - 		 - 		4/5 	 - 
			 */
			
			/* 3. 플로이드워셜 수행*/
			for(int mid=1; mid<=n; mid++) { // 중간 경유지
				for(int i=1; i<=n; i++) {
					for(int j=1; j<=n; j++) {
						// 1) K 를 사용하지 않는 경우
						// 	- min(현재까지 저장된 최단 거리, 현재의 경유지(mid)를 거쳤을 때의 거리)
						dist[i][j][0] = Math.min(dist[i][j][0], dist[i][mid][0]+dist[mid][j][0]); 
						// 2) K 를 사용한 경우
						// 	- k 는 하루에 한번만 사용가능  => min(i->경유지 에 k 를 쓴경우, 경유지->j 에 k를 쓴 경우)
						long dmin = Math.min(dist[i][mid][1]+dist[mid][j][0], dist[i][mid][0]+dist[mid][j][1]);  
						dist[i][j][1] = Math.min(dist[i][j][1], dmin);
					}
				}
			}
			
			/* 플로이드 수행후 dist 배열의 값
				4/7 	2/5 	4/5 	7/8 	4/5 
				2/5 	4/7 	2/5 	5/7 	6/7 
				4/5 	2/5 	4/7 	3/5 	7/8 
				7/8 	5/7 	3/5 	6/8 	4/5 
				4/5 	6/7 	7/8 	4/5 	8/9 
			*/

			/* 4. 통화 발생 비용 계산 */
			long ans=0;
			for(int i=0; i<d ; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken()); // 통화 x
				int y = Integer.parseInt(st.nextToken()); // 통화 y
				
				// x,y 통화의 최소 교환비용
				ans += Math.min(dist[x][y][0], dist[x][y][1]);
			}
			bw.write("#"+tc+" "+ans+"\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}

}
