package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 문제 조건 
 * - 바다 위에 있는 모든 섬을 다리로 연결 
 * - 섬은 1*1의 땅이 상하좌우로 붙어 있는 덩어리 
 * - 다리는 바다에만 일직선으로 건설 가능, 길이는 2이상
 * 
 * 문제 풀이 
 * 1. 입력받은 지도의 정보로부터 섬을 식별 (0은 바다, 1은 땅) 
 * 2. 건설가능한 다리 찾기 (길이정보 저장) 
 * 3. 모든 섬을 연결하는 다리 길이의 최솟값 구하기 
 * 
 * 필요 변수
 * - map : 지도 정보 입력
 * - island : 각 섬에 번호를 붙여 저장 (섬 식별하기)
 * - parents : 섬 연결시 사이클이 발생하지 않도록 연결정보 저장 
 */

public class BJ_17472_Best{  // 백준 17472
	static int N,M;
	static int[][] map, island;
	static int[] dy = {-1,1,0,0}; // 상, 하, 좌, 우 
	static int[] dx = {0,0,-1,1};
	static int[] parents;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		island = new int[N][M];
		
		Queue<int[]> conn = new LinkedList<>(); // 바다와 인접한 땅의 정보(좌표 및 바다를 바라보는 방향)를 저장하는 리스트 
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine().trim());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 1:땅, 0:바다
			}
		}
		
		// 섬에 번호를 붙여 식별하기 
		int idx = 1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j] > 0 && island[i][j]==0) {
					findIsland(conn, i, j, idx);
					idx++;
				}
			}
		}
		
		ArrayList<int[]> edges = new ArrayList<>(); // 다리 정보를 저장하는 리스트 
		
		while(!conn.isEmpty()) {
			int[] n = conn.poll();
			
			int toY = n[0]+dy[n[2]];  
			int toX = n[1]+dx[n[2]];

			int dis = 1;
			
			while(true) {
				toY += dy[n[2]];
				toX += dx[n[2]];
				
				if(!isRange(toY,toX)) break;
				if(map[toY][toX] > 0 ) { // 섬에 도달했는데
					if(island[toY][toX] != island[n[0]][n[1]] && dis>=2) { // 다른 섬을 만나면? + 거리는 2이상
						edges.add(new int[] {island[n[0]][n[1]], island[toY][toX], dis}); // 도로 추가
					}
					break;
				}
				dis++;
			}
		}

		// 거리 오름차순 정렬
		Collections.sort(edges, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return o1[2]-o2[2];
			}
		});
		
		// 모든 섬을 최소길이의 다리로 연결하기
		int ans = 0;
		int cnt = 1;
		
		parents = new int[idx];
		for(int i=1; i<idx; i++) {
			parents[i] = i;
		}
		
		for(int i=0; i<edges.size(); i++) {
			int[] edge = edges.get(i);
			int left = findParents(edge[0]);
			int right = findParents(edge[1]);
			if(left != right) {
				parents[right] = left;
				ans += edge[2];
				cnt++;
			}
			if(cnt==idx-1) break;
		}
		if(cnt!=idx-1) ans=-1;
		
		System.out.println(ans==0? -1: ans);
	}
	
	
	static int findParents(int x) {
		if(parents[x] == x) return x;
		
		return parents[x] = findParents(parents[x]);
	}
	
	static void findIsland(Queue<int[]> conn, int y, int x, int idx) {
		Queue<int[]> land = new LinkedList<>();

		land.add(new int[] {y,x});
		island[y][x] = idx;
		
		while(!land.isEmpty()) {
			int[] n = land.poll();
			
			for(int k=0; k<4; k++) {
				int toY = n[0] + dy[k];
				int toX = n[1] + dx[k];
				
				if(!isRange(toY,toX) || island[toY][toX] > 0) continue;  // 범위 밖이거나 이미 섬으로 파악한 지역이면 건너뛰기
				
				
				if(map[toY][toX] == 0) {  // 바다와 인접해 있으면 큐에 넣어주고 건너뛰기
					conn.add(new int[] {n[0], n[1], k}); // 현재 좌표, 바다를 바라보는 방향
					continue;
				}
				
				island[toY][toX] = idx;
				land.add(new int[] {toY, toX});
			}
		}
	}
	
	static boolean isRange(int y , int x ) {
		if(y<0 || y>=N || x<0 || x>=M) return false;
		return true;
	}
}
