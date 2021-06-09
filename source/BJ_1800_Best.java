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
5 7 1
1 2 5
3 1 4
2 4 8
3 2 3
5 2 9
3 4 7
4 5 6
 */

public class BJ_1800_Best {
	
	static int N, P, K, maxCost;
	static List<Edge1800> adj[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList[N+1];
		
		for (int i=1; i<=N; i++) {
			adj[i] = new ArrayList<Edge1800>();
		}
		
		maxCost = 0;
		for (int i=0; i<P; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			adj[u].add(new Edge1800(v, cost));
			adj[v].add(new Edge1800(u, cost));
			
			maxCost = Math.max(maxCost, cost);
		}
		
		//============== x비용으로 k개이하로 N까지 갈 수 있는지 확인
		int s = 0;
		int e = maxCost;
		int ret = -1;
		
		while(s <= e) {
			int mid = (s+e) / 2;
			
			if (dijkstra(mid)) {
				ret = mid;
				e = mid - 1;
			} else {
				s = mid + 1;
			}
		}

		bw.write(ret+"");
		
		br.close();
		bw.close();
	}
	
	static boolean dijkstra(int x) {
		//============ 1. 다익스트라
		PriorityQueue<Edge1800> pq = new PriorityQueue<Edge1800>();
		int dist[] = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		dist[1] = 0;
		pq.offer(new Edge1800(1, 0));
		
		while(!pq.isEmpty()) {
			Edge1800 here = pq.poll();
			
			 if (dist[here.v] < here.cost) continue;
			
			for (Edge1800 there : adj[here.v]) {
				int thereCost = there.cost > x ? 1 : 0;
				int cost = dist[here.v] + thereCost;
				
				if (dist[there.v] > cost) {
					dist[there.v] = cost;
					pq.offer(new Edge1800(there.v, cost));
				}
			}
		}
		
//		System.out.println(Arrays.toString(dist));
		
		// K 개만 지불하고 갈 수 있는지 확인
		boolean ret = false;
		if (dist[N] <= K) ret = true;
		
		return ret;
	}
	
}

class Edge1800 implements Comparable<Edge1800>{
	int v;
	int cost;
	
	Edge1800(int v, int cost) {
		this.v = v;
		this.cost = cost;
	}

	@Override
	public int compareTo(Edge1800 right) {
		if (cost < right.cost) return -1;
		if (cost > right.cost) return 1;
		return 0;
	}
}
