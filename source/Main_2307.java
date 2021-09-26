package STUDY_26;

import java.util.*;
import java.io.*;

/*
[문제풀이]
- 용의자가 도시에 진입하는 지점은  항상 1번
- 용의자가  도시를 빠져 나가기 위하여 최종적으로 도달해야하는 지점은 항상 N번 지점
- 각 도시 지점 간을 이동하는 시간은 항상 양의 정수

- 용의자는 검문을 피해서 가장 빨리 도시를 빠져나가고자 함 
- 경찰은 적절한 도로를 선택하여 이 용의자들의 탈출시간을 최대한 지연시키고자 함
=> 다익스트라

1. 용의자) 도시를 빠져나가는 최단경로 및 최단시간 구하기
2. 경찰) 용의자가 이동하는 최단경로에 포함되는 도로를 하나씩 검문하기
    - 지연되는 시간 구하기
    - 용의자가 탈출하지 못하면 -1출력

*/
public class Main_2307 {
	static int N,M;
	static int MAX = 1000 * 10000 + 1;
	static ArrayList<int[]>[] adj;
	static int[] path, dist;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList[N+1]; // 도시 연결 정보
		path = new int[N+1];	 // 용의자가 이동한 경로
		dist = new int[N+1];     // 도시를 탈출하는 최소 시간
		
		for(int i=1; i<N+1; i++) {
			adj[i] = new ArrayList<>();
			path[i] = MAX;
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			
			adj[a].add(new int[] {b,t});
			adj[b].add(new int[] {a,t});
		}
		
		path[1] = 0;
		int escape = dijkstra(-1,-1, false);
		
		int idx = N;
		int ANS = 0;
		
		while(idx != 0) {
			if(path[idx] == MAX) {
				ANS = -1;
				break;
			}
			
			int delay = dijkstra(idx, path[idx], true);
			
			if(delay == -1) { // 도시를 빠져나가지 못하는 경우
				ANS = -1;
				break;
			}
			if(delay >= dist[N]) { // 지연된 시간 계산
				ANS = Math.max(ANS, delay - escape);
			}
			
			idx = path[idx];
		}
		
		System.out.println(ANS);
	}
	
	static int dijkstra(int x, int y, boolean police) {
		PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1[1], o2[1]);
			}
		});
		
		Arrays.fill(dist, MAX);
		
		pq.add(new int[] {1,0});
		dist[1] = 0;
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			
			if(cur[0] == N) return cur[1];
			
			for(int[] next : adj[cur[0]]) {
				if(police && ((cur[0]==x && next[0]==y) || (cur[0]==y && next[0]==x))) continue; // 검문중인 도로
				
				if(dist[next[0]] > dist[cur[0]]+next[1]) {
					dist[next[0]] = dist[cur[0]]+next[1];
					
					if(!police) path[next[0]] = cur[0]; // 용의자 경로 저장
					
					pq.add(new int[] {next[0], dist[next[0]]});
				}
			}
		}
		
		return -1;
	}
}
