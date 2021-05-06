package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*
5
8
1 2 2
1 3 3
1 4 1
1 5 10
2 4 2
3 4 1
3 5 1
4 5 3
1 5
 */ 

public class BJ_11779_Best {
	
	static int N, M, S, E, dist[], root[];
	static List<Edge11779> node[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		node = new ArrayList[N+1];
		dist = new int[N+1];
		root = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		for (int i=1; i<=N; i++) {
			node[i] = new ArrayList<Edge11779>();
		}
		
		for (int i=0; i<M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			node[u].add(new Edge11779(v, cost));
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		PriorityQueue<Edge11779> pq = new PriorityQueue<Edge11779>();
		dist[S] = 0;
		pq.offer(new Edge11779(S, 0));
		
		while(!pq.isEmpty()) {
			Edge11779 here = pq.poll();
			
			for (Edge11779 there : node[here.v]) {
				int cost = dist[here.v] + there.cost;
				
				if (dist[there.v] > cost) {
					dist[there.v] = cost;
					pq.offer(new Edge11779(there.v, cost));
					
					root[there.v] = here.v;
				}
			}
		}
		
		bw.write(dist[E]+"\n");
		
		Stack<Integer> sk = new Stack<Integer>();
		sk.add(E);
		int index = E;
		while (index != S) {
			sk.add(root[index]);
			index = root[index];
		}
		
		bw.write(sk.size()+"\n");
		
		while(!sk.isEmpty()) {
			bw.write(sk.pop()+" ");
		}
		
		br.close();
		bw.close();

	}

}

class Edge11779 implements Comparable<Edge11779>{
	int v;
	int cost;
	
	Edge11779(int v, int cost) {
		this.v = v;
		this.cost = cost;
	}

	@Override
	public int compareTo(Edge11779 right) {
		if (cost < right.cost) return -1;
		if (cost > right.cost) return 1;
		return 0;
	}
}
