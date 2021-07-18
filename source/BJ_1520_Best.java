package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
 * 
지도의 제일 왼쪽 위 지점 -> 제일 오른쪽 아래 지점까지 항상 내리막길로만 이동하는 경로의 개수

입력
첫째 줄에는 지도의 세로의 크기 M과 가로의 크기 N이 빈칸을 사이에 두고 주어진다. 
이어 다음 M개 줄에 걸쳐 한 줄에 N개씩 위에서부터 차례로 각 지점의 높이가 빈 칸을 사이에 두고 주어진다.
M과 N은 각각 500이하의 자연수이고, 각 지점의 높이는 10000이하의 자연수이다.

출력
첫째 줄에 이동 가능한 경로의 수 H를 출력한다. 모든 입력에 대하여 H는 10억 이하의 음이 아닌 정수이다.

4 5
50 45 37 32 30
35 50 40 20 25
30 30 25 17 28
27 24 22 15 10

3


DFS 로만 풀면 시간초과 -> DFS + DP 이전에 해당 좌표까지 계산된 값을 기억해두고, 해당 좌표 재방문시 해당값을 리턴해주어 반복작업을 피함.
*/
public class BJ_1520_Best {

	static int map[][];
	static long chk[][]; // 현재 위치 ~ 끝점까지 가는 경로의 갯수 저장
	
	static int m, n;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		m = Integer.parseInt(st.nextToken()); // row
		n = Integer.parseInt(st.nextToken()); // col
		
		map = new int[m+1][n+1];
		chk = new long[m+1][n+1];
		
		// 방문 여부 및 경로갯수 저장용 초기화
		for(int i=1; i<=m ; i++) {
			for(int j=1; j<=n; j++) {
				chk[i][j] = -1;
			}		
		}
		
		for(int i=1; i<=m ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}		
		}
		
		long ans = find(1,1);	// 	시작점
/*
chk 배열
 3  2  2  2  1 
 1 -1 -1  1  1 
 1 -1 -1  1 -1 
 1  1  1  1 -1 
*/		
		bw.write(ans+"\n");
		
		bw.flush();
		bw.close();
		br.close();
		
	}

	static long find(int x, int y) {

		int de =1;
		
		int dx[] = {-1, 0, 1, 0}; // 상, 좌, 하, 우
		int dy[] = { 0,-1, 0, 1}; // 상, 좌, 하, 우
		
		// 1. 끝점에 도달한 경우 
		if(x == m && y == n) return 1;
		
		// 2. 이미 계산이 끝난 경우 - 계산된 경로의 수를 return
		if(chk[x][y] != -1) return chk[x][y]; 
		
		chk[x][y] = 0; // 앞으로 탐색 시작할 위치, 0으로 초기화. (방문했다!)
		
		// 3. 경로 탐색 및 갯수 저장
		for(int i=0; i< 4 ; i++) {
			
			int next_x = x + dx[i];
			int next_y = y + dy[i];
			
			// 범위를 벗어난 경우, continue;
			if(next_x <1 || next_x> m || next_y < 1 || next_y > n) continue;

			// 더 낮은 높이의 경우에만 이동!
			if(map[x][y] > map[next_x][next_y]) 
				chk[x][y] += find(next_x, next_y); // 현재의 좌표에 다음 좌표~ m,n 까지의 경로 저장.			
			
		}
		return chk[x][y];
	}
	
}
