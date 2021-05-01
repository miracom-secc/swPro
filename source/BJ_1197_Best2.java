package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
3 3
1 2 1
2 3 2
1 3 3
 */

public class BJ_1197_Best2 {

	static int U , E;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		U = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		List<Edge1197> node[] = new ArrayList[U+1];
		
		for (int i=1; i<=U; i++) {
			node[i] = new ArrayList<>();
		}
		
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			//prim은 양방향이어야 한다
			node[u].add(new Edge1197(v, w));
			node[v].add(new Edge1197(u, w));
		}
		
		PriorityQueue<Edge1197> pq = new PriorityQueue<Edge1197>();
		boolean visited[] = new boolean[U+1];
		
		pq.offer(new Edge1197(1,0));
		
		int sum = 0;
		while (!pq.isEmpty()) {
			Edge1197 now = pq.poll();
			
			if (visited[now.v]) continue;
			
			visited[now.v] = true;
			sum += now.w;
			
			for (int i=0; i<node[now.v].size(); i++) {
				Edge1197 there = node[now.v].get(i);
				int v = there.v;
				int w = there.w;
				
				pq.offer(new Edge1197(v,w));
			}
		}
		
		bw.write(sum+"");
		br.close();
		bw.close();
	}

}

class Edge1197 implements Comparable<Edge1197>{
	int v;
	int w;
	
	Edge1197(int v, int w) {
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Edge1197 right) {
		if(w < right.w) return -1;
		if(w > right.w) return 1;
		return 0;
	}
}
