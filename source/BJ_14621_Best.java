package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
5 7
M W W W M
1 2 12
1 3 10
4 2 5
5 2 5
2 5 10
3 4 3
5 4 7

3 3
M W M
1 2 1 
2 3 1
1 3 1

4 4
M W M W
1 2 1
2 3 1
3 4 1
1 4 2
 */

public class BJ_14621_Best {
	
	static int E , V;
	static char coll[];

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		E = Integer.parseInt(st.nextToken());
		V = Integer.parseInt(st.nextToken());
		coll = new char[E+1];
		
		List<Edge14621> node[] = new ArrayList[E+1];
		
		st = new StringTokenizer(br.readLine());
		for (int i=1; i<=E; i++) {
			node[i] = new ArrayList<>();
			coll[i] = st.nextToken().charAt(0);
		}
		
		for (int i=0; i<V; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			if (coll[u] == coll[v]) continue;
			node[u].add(new Edge14621(v,w));
			node[v].add(new Edge14621(u,w));
		}
		
		PriorityQueue<Edge14621> pq = new PriorityQueue<Edge14621>();
		boolean visited[] = new boolean[E+1];
		pq.offer(new Edge14621(1, 0));
		
		int sum = 0;
		while(!pq.isEmpty()) {
			Edge14621 now = pq.poll();
			
			if(visited[now.v]) continue;
			
			visited[now.v] = true;
			sum += now.w;
			
			for (int i=0; i<node[now.v].size(); i++) {
				Edge14621 there = node[now.v].get(i);
				pq.offer(new Edge14621(there.v, there.w));
			}
		}
		
		boolean check = true;
		for (int i=1; i<=E; i++) {
			if (!visited[i]) {
				check = false;
				break;
			}
		}
		
		sum = check ? sum : -1;
		
		bw.write(sum+"");
		br.close();
		bw.close();

	}

}

class Edge14621 implements Comparable<Edge14621>{
	int v;
	int w;
	
	Edge14621(int v, int w) {
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Edge14621 right) {
		if(w < right.w) return -1;
		if(w > right.w) return 1;
		return 0;
	}
}
