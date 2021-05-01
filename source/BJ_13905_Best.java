package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
7 9
1 5
1 2 2
1 7 4
2 3 5
3 7 5
4 6 1
6 7 4
5 6 3
5 7 1
3 5 2
 */

public class BJ_13905_Best {

	static int N, M, S, E;
	static ArrayList<Edge13905> node[], maxNode[];
	static boolean visited[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		visited = new boolean[N+1];
		node = new ArrayList[N+1];
		
		for (int i=1; i<=N; i++) {
			node[i] = new ArrayList<Edge13905>();
		}
		
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			//프림은 양방향  노드이다
			node[u].add(new Edge13905(v, cost));
			node[v].add(new Edge13905(u, cost));
		}
		
		//====================== 최대 스패닝트리 구하기
		PriorityQueue<Edge13905> pq = new PriorityQueue<Edge13905>(Comparator.reverseOrder());
		
		int dist[] = new int[N+1];
		dist[S] = Integer.MAX_VALUE;
		pq.offer(new Edge13905(S, dist[S]));
		
		while(!pq.isEmpty()) {
			Edge13905 here = pq.poll();
			
			if (visited[here.v]) continue;
			
			visited[here.v] = true;
			
			for (int i=0; i<node[here.v].size(); i++) {
				Edge13905 there = node[here.v].get(i);
				
				dist[there.v] = Math.max(dist[there.v], Math.min(dist[here.v], there.cost));
				pq.offer(new Edge13905(there.v, there.cost));
			}
		}
		
//		System.out.println(Arrays.toString(dist));
		bw.write(dist[E]+"");
		
		br.close();
		bw.close();
	}

}

class Edge13905 implements Comparable<Edge13905> {
	int v;
	int cost;
	
	Edge13905(int v, int cost){
		this.v = v;
		this.cost = cost;
	}

	@Override
	public int compareTo(Edge13905 right) {
		if (cost < right.cost) return -1;
		if (cost > right.cost) return 1;
		return 0;
	}
	
	public String toSting() {
		return "v : "+v+" cost : "+cost;
	}
}
