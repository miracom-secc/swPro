package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
첫 줄에 나무 그루터기의 개수와 오솔길의 개수를 의미하는 정수 N, M(2 ≤ N ≤ 4,000, 1 ≤ M ≤ 100,000)이 주어진다.

두 번째 줄부터 M개의 줄에 걸쳐 각 줄에 세 개의 정수 a, b, d(1 ≤ a, b ≤ N, a ≠ b, 1 ≤ d ≤ 100,000)가 주어진다. 
이는 a번 그루터기와 b번 그루터기 사이에 길이가 d인 오솔길이 나 있음을 의미한다.

출력
첫 줄에 달빛 여우가 달빛 늑대보다 먼저 도착할 수 있는 나무 그루터기의 개수를 출력한다.


5 6
1 2 3
1 3 2
2 3 2
2 4 4
3 5 4
4 5 3


1

첫 줄에 달빛 여우가 달빛 늑대보다 먼저 도착할 수 있는 나무 그루터기의 개수를 출력한다.
*/
public class BJ_16118_Best {
	
	static class Node implements Comparable<Node>{
		int v;
		long w;
		int st;
		
		Node(int v, long w){ // 토끼용
			this.v = v;
			this.w = w;
		}
		Node(int v, long w,int st){ // 늑대용 위치, 가중치, 상태
			this.v = v;
			this.w = w;
			this.st = st;
		}
		@Override
		public int compareTo(Node o) {
			return this.w < o.w ? -1 :1;
		}
	}
	
	static int N, M ;
	static ArrayList<Node>[] arr ;
	static long dist_f[] ; // 여우꺼
	static long dist_w[][]; // 늑대꺼
	static long INF = Long.MAX_VALUE;
		

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		dist_f = new long[N+1];
		dist_w = new long[2][N+1]; // 0 : 해당 지점에 느리게 달려왔을 때의 최소 시간. 1 : 해당 지점에 빠르게 달려왔을 때의 최소 시간. 현재 값이 0이면 이번에는 /2 속도로 빠르게 갈 차례
		
		Arrays.fill(dist_f, INF);
		for(int i=0; i<N+1; i++){
			dist_w[0][i] = INF;
			dist_w[1][i] = INF;
		}
		arr = new ArrayList[N+1];
		
		for(int i=0; i<N+1; i++) {
			arr[i] = new ArrayList<Node>();
		}
		
		for(int i=0; i< M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			long w = Long.parseLong(st.nextToken());
			
			// 양방향
			arr[u].add(new Node(v,2*w));  // /2 가 있으니 계산하기 편하게 처음부터 *2 하고 시작
			arr[v].add(new Node(u,2*w));
		}
		
		// 토끼용 다익스트라
		dist_f[1] =0;
		dijsktra_f(1);

		dijsktra_w(1);

		int ans =0; // 그루터기 카운트용
		for(int i=2; i<N+1; i++) {
			if(dist_f[i] <  Math.min(dist_w[0][i], dist_w[1][i]) )  // 각 지점별 여우의 최단거리 < 각 지점별 *2 의 속도로 도착했을 경우의 최단거리, /2 의 속도로 도착했을 경우의 최단거리 중 최소값
				ans++;
		}
		
		bw.write(ans +"\n");
		bw.flush();
		bw.close();
		br.close();
		
	}


	static void dijsktra_w(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		
		pq.offer(new Node(1,0,0)); // 시작은 빠르게 할꺼니깐 
		// 늑대용 다익스트라
		dist_w[0][1] =0;  // 다익스트라 시작지점 초기화 (처음은 무조건 빠르게 시작(/2 속도)하니 [0][1] 지점을 시작으로 세팅. 시작 지점에 느리게 도착했다고 가정)
		//boolean speed = true;
		
		while(!pq.isEmpty()) {
			
			Node now = pq.poll();
			int now_v= now.v;			
			//System.out.println("dist_w["+now.st+"]["+now_v+"] :"+dist_w[now.st][now_v]+" | now.w: "+ now.w);
			if(dist_w[now.st][now_v] < now.w) {	// 해당 지점의 최소시간보다 현재 해당 지점에 오기까지 걸린 시간이 크면 계산 X
				continue;
			}
			
			for(Node next: arr[now_v]) {
				//long next_w =0;
				int st  = 1 - now.st;	// 0 이면 1로, 1이면 0으로 스위치 전환 가능 
				long cost = dist_w[now.st][now_v] + ((now.st == 0) ? next.w / 2 : next.w * 2);	// 상태가 0이면 /2 의 속도, 1이면 *2 의속도 

				if(dist_w[st][next.v] > cost) {
					dist_w[st][next.v] = cost;
					pq.offer(new Node(next.v, cost, st));
				}
			}	
		}
		
	}
	



	static void dijsktra_f(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(start,0));
		
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int now_v= now.v;
			
			if(dist_f[now_v] < now.w) continue; 	// 해당 지점의 최소시간보다 현재 해당 지점에 오기까지 걸린 시간이 크면 계산 X
			
			for(Node next: arr[now_v]) {
				if(dist_f[next.v] > dist_f[now_v]+ next.w) {
					dist_f[next.v] = dist_f[now_v]+ next.w;
					pq.add(new Node(next.v, dist_f[next.v]));
					
				}
			}				
		}
		
	}

}