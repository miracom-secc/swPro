package swPro.source;

import java.io.*;
import java.util.*;

/*
 * dist[i][j] : i번째 행성까지 j개의 워프패킷을 가지고 도착했을 때의 시간
 *  	- dist[4][0] : 4번째 행성까지 0개의 워프패킷을 가지고 도달한 시간을 의미
 * adj : 행성간의 연결정보 저장
 * MAX_VALUE : 시작행성부터 도착행성까지 도달하는데 걸리는 시간의 최댓값+1
 * 		- 최댓값 : 행성 최대 10만개, 행성 간 여행시간 최대 10만 = 10만*10만
 * 
 */
public class Main_인터스텔라 {
	static int N,M,K,FROM,TO; // N: [2:100,000] , M:[1:200,000], K:[0:2]
	static long ANS;	// 출력할 값은 32비트 정수 변수로 표현할 수 있는 범위를 넘을 수 있으므로 long사용
	static long[][] dist;
	static ArrayList<Node>[] adj;
	static long MAX_VALUE = 100000*100000+1;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int TC = Integer.parseInt(br.readLine().trim());
		
		for(int tc=1; tc<=TC; tc++) {
			st = new StringTokenizer(br.readLine().trim());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			// 변수 초기화 
			adj = new ArrayList[N+1];
			dist = new long[N+1][K+1];
			ANS = MAX_VALUE;
			
			for(int i=1; i<N+1; i++) {
				adj[i] = new ArrayList<>();	
				Arrays.fill(dist[i], MAX_VALUE); 
			}
			
			// 행성간 연결정보 입력
			for(int i=0; i<M; i++) {
				st = new StringTokenizer(br.readLine().trim());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken()); // [1:100,000]
				
				adj[a].add(new Node(b,cost,0));
				adj[b].add(new Node(a,cost,0));
			}
			
			// 시작위치, 도착위치 입력
			st = new StringTokenizer(br.readLine().trim());
			FROM = Integer.parseInt(st.nextToken());
			TO = Integer.parseInt(st.nextToken());
			
			// 걸리는 시간 계산
			dijkstra();
			
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	
	static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		pq.add(new Node(FROM, 0, K)); // 시작행성(FROM)에서 K의 워프패킷을 가지고 출발
		Arrays.fill(dist[FROM], 0);
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			if(n.planet == TO) { // 도착하면 최솟값 출력
				ANS = Math.min(ANS, dist[TO][n.k]);
				break;
			}
			
			if(dist[n.planet][n.k] < n.cost) continue;
			
			for(Node next: adj[n.planet]) {
				// 일단 이동
				if(dist[next.planet][n.k] > dist[n.planet][n.k]+next.cost) {
					dist[next.planet][n.k] = dist[n.planet][n.k]+next.cost;
					pq.add(new Node(next.planet, dist[next.planet][n.k], n.k));
				}
				
				// 워프패킷 사용해서 이동
				if(n.k>0 && dist[next.planet][n.k-1] > dist[n.planet][n.k]+1) {
					dist[next.planet][n.k-1] = dist[n.planet][n.k]+1;
					pq.add(new Node(next.planet, dist[next.planet][n.k-1], n.k-1));
				}
			}
		}
	}
	
	static class Node implements Comparable<Node>{
		int planet;
		long cost;
		int k;
		
		public Node(int planet, long cost, int k) {
			this.planet = planet;
			this.cost = cost;
			this.k = k;
		}

		@Override
		public int compareTo(Node o) {
			// 시간을 기준으로 오름차순 정렬
			return Long.compare(this.cost, o.cost);
		}
	}
}
