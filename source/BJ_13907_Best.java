package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
3 3 2
1 3
1 3 5
1 2 1
2 3 2
1
2
 */
public class BJ_13907_Best {
	 
	static int N, M, K, S, E, dist[][], upper[];
	static List<Edge13907> adj[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		dist = new int[N+1][N+1];
		upper = new int[K+1];
		adj = new ArrayList[N+1];

		for (int i=1; i<=N; i++) {
			adj[i] = new ArrayList<Edge13907>();
		}
		
		 st = new StringTokenizer(br.readLine());
		 S = Integer.parseInt(st.nextToken());
		 E = Integer.parseInt(st.nextToken());
		 
		 for (int i=0; i<M; i++) {
			 st = new StringTokenizer(br.readLine());
			 int u = Integer.parseInt(st.nextToken());
			 int v = Integer.parseInt(st.nextToken());
			 int cost = Integer.parseInt(st.nextToken());
			 
			 adj[u].add(new Edge13907(v, cost, 0));
			 adj[v].add(new Edge13907(u, cost, 0));
		 }
		 
		 for (int i=1; i<=K; i++) {
			 upper[i] = Integer.parseInt(br.readLine());
		 }
		 
		 dijkstra();
		 
		 int sum = 0;
		 for (int up : upper) {
			 sum += up;
			 
			 int ret = Integer.MAX_VALUE;
			 for (int i=1; i<N; i++) {
				 if (dist[E][i] == Integer.MAX_VALUE) continue;
				 ret = Math.min(ret, dist[E][i] + (i*sum));
			 }
			 bw.write(ret+"\n");
		 }
		 
		 br.close();
		 bw.close();
		 
	}
	
	static void dijkstra() {
		for (int i=0; i<=N; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		 
		 PriorityQueue<Edge13907> pq = new PriorityQueue<Edge13907>();
		 dist[S][0] = 0;
		 pq.offer(new Edge13907(S, 0, 0));
		 
		 while(!pq.isEmpty()) {
			 Edge13907 here = pq.poll();
			 
			 if(here.cnt >= (N-1)) continue; //
			 if(dist[here.v][here.cnt] < here.cost) continue; //현재 최소값으로 갱신되었으면 skip
			 
			 for (Edge13907 there : adj[here.v]) {
				 int cost = dist[here.v][here.cnt] + there.cost;
				 
				 if (dist[there.v][here.cnt+1] > cost) {
					 dist[there.v][here.cnt+1] = cost;
					 pq.offer(new Edge13907(there.v, cost, here.cnt+1));
				 }
			 }
		 }
	}

}

class Edge13907 implements Comparable<Edge13907> {
	int v;
	int cost;
	int cnt;
	
	Edge13907(int v, int cost, int cnt) {
		this.v = v;
		this.cost = cost;
		this.cnt = cnt;
	}

	@Override
	public int compareTo(Edge13907 right) {
		if (cost < right.cost) return -1;
		if (cost > right.cost) return 1;
		return 0;
	}
	
}
