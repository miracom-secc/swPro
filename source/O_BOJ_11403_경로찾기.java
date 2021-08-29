package Floyd;

import java.io.*;
import java.util.*;

/*가중치 없는 방향 그래프 G가 주어졌을 때, 모든 정점 (i, j)에 대해서, i에서 j로 가는 경로가 있는지 없는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 정점의 개수 N (1 ≤ N ≤ 100)이 주어진다. 둘째 줄부터 N개 줄에는 그래프의 인접 행렬이 주어진다. 
i번째 줄의 j번째 숫자가 1인 경우에는 i에서 j로 가는 간선이 존재한다는 뜻이고, 0인 경우는 없다는 뜻이다. i번째 줄의 i번째 숫자는 항상 0이다.

출력
총 N개의 줄에 걸쳐서 문제의 정답을 인접행렬 형식으로 출력한다. 정점 i에서 j로 가는 경로가 있으면 i번째 줄의 j번째 숫자를 1로, 없으면 0으로 출력해야 한다.
*/

public class O_BOJ_11403_경로찾기 {
	
	static int n;
	static int map[][];
	//static int fw[][];
	//static int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		
		map = new int[n+1][n+1];
		
		// 플로이드 와샬 테이블 입력
		for(int i=1; i<=n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}			
		}
		
		// 플로이드 와샬 수행
		floyd();
		
		// 출력
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}

	}

	
	static void floyd() {
		for(int mid =1; mid <=n ; mid++) { // 경유지.
			for(int i=1; i<=n; i++) {  // 출발지
				for(int j=1; j<=n; j++) { // 종료점
					if(map[i][mid] ==1 && map[mid][j] ==1) {  //i-mid, mid-j 가 1이라는건 mid 를 통해서 둘사이 연결이 된다는 것
						map[i][j] = 1;
					}	
				}			
			}
		}
		
	}

}
