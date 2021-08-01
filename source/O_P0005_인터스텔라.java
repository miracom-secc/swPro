package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
3
6 6 0
1 2 3
5 1 2
5 6 5
3 4 3
6 4 6
2 3 3
1 4
6 6 1
1 2 3
5 1 2
5 6 5
3 4 3
6 4 6
2 3 3
1 4
6 6 2
1 2 3
5 1 2
5 6 5
3 4 3
6 4 6
2 3 3
1 4
*/

public class O_P0005_인터스텔라 {
	
	static class Node implements Comparable<Node>{
		int v; 
		long w;
		int k;
		
		Node(int v, long w, int k){
			this.v = v;
			this.w = w;
			this.k = k;
		}
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.w < o.w ? -1:1;
		}
	}
	static int n,m,k; // 행성의 갯수, 행성의 쌍의 갯수, 워프의 갯수
	static ArrayList<Node> arr[];
	static int start, end; // 여행 시작점, 여행 종료점 ==> 둘사이으 최단거리를 구하는 문제.
	static long dist[][]; // 
	static long INF = 999999900;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st= new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int tc = Integer.parseInt(st.nextToken()); // TC 갯수
		int de=1;
		for(int t=1; t<=tc ; t++) {
			st= new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());  // 행성의 갯수
			m = Integer.parseInt(st.nextToken());  // 행성의 쌍의 갯수
			k = Integer.parseInt(st.nextToken());  // 워프의 갯수
			
			// 초기화
			dist = new long [k+1][n+1]; // 워프의 수, 행성의수
			arr = new ArrayList[n+1];
			
			for(int i=0; i<k+1; i++) {
				Arrays.fill(dist[i], INF);
			}
			for(int i=0; i<n+1; i++) {
				arr[i] = new ArrayList<Node>();
			}			
			
			for(int i=0; i< m ; i++) {
				st= new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());  // 행성 1
				int v = Integer.parseInt(st.nextToken());  // 행성2
				long w = Long.parseLong(st.nextToken());  // 둘사이의 거리
				
				arr[u].add(new Node(v,w,k)); // 각지점에 워프를 2개 가지고 도착했을때를 세팅.
				arr[v].add(new Node(u,w,k));				
			}
			de=1;
			st= new StringTokenizer(br.readLine());
			start = Integer.parseInt(st.nextToken());  // 여행 시작점
			end = Integer.parseInt(st.nextToken());  // 여행 종료점 
			
			// 시작점 초기화.
			for(int i=0; i<k+1; i++) {
				dist[i][start] =0;
			}
			//탐색
			dijkstra(start, end, k);
			
			long ans= INF;
			for(int i=0; i<k+1; i++) {
				ans = Math.min(ans, dist[i][end]); // 워프 사용갯수별 어떤게 제일 먼저 도착했는지 확인 
			}
			//System.out.println(ans +"===================");
			sb.append("#"+t+" "+ans+"\n");
			de=1;
		}
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		br.close();
		
	}
	
	static void dijkstra(int start, int end, int k) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(start, 0, k)); // 탐색 시작지점, 0, 아직 워프 사용안했으니 갯수 그대로.
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int now_v = now.v; // 현위치
			long now_w = now.w; // 현재의 여행시간
			int now_k = now.k;//현재 워프의수!
			
			if(now_v == end) break; //원하는 지점에 도착했으니 break 하고 빠져나가기
						
			for(Node next: arr[now_v]) {
				int next_v = next.v;
				long next_w = next.w;

				if(next_v == start) continue;
				
				long cost = now_w + next_w;
				
				//System.out.println("now_v:"+now_v+" |now_k: "+now_k+" |now_k:"+now_k+"|next_v:"+next_v+" |next_w:"+next_w);
				
				//System.out.println("1: dist["+now_k+"]["+next_v+"]="+dist[now_k][next_v]+" / cost:"+ cost);
				//1. 워프 미적용
				if(dist[now_k][next_v] > cost) { 
					dist[now_k][next_v] = cost;
					pq.add(new Node(next_v, cost, now_k)); // 워프 사용 안했으니  k는 현재꺼 그대로 들고감.
				}
				
				
				// 2. 워프 적용
				if(now_k > 0 && dist[now_k-1][next_v] > now_w +1 ) { // 워프 사용시 이동시간이 1이기 때문에 cost가 아닌 현재의 여행시간에 +1을함
				//	System.out.println("2: dist["+(now_k-1)+"]["+next_v+"]="+dist[now_k-1][next_v]+" /now_w +1: "+ (now_w +1));
					dist[now_k-1][next_v] = now_w +1;
					pq.add(new Node(next_v, now_w+1, now_k-1 )); //워프 사용했으니 갯수 --
				}
			}
				
					
		}

	}
	
}
