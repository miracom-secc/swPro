package algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

public class BJ_14621_Kru{

	static int E , V, parent[];
	static char coll[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		E = Integer.parseInt(st.nextToken());
		V = Integer.parseInt(st.nextToken());
		parent = new int[E+1];
		coll = new char[E+1];
		
		List<Edge14621k> node = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i=1; i<=E; i++) {
			parent[i] = i;
			coll[i] = st.nextToken().charAt(0);
		}
		
		for (int i=0; i<V; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			node.add(new Edge14621k(u, v, w));
		}
		
		Collections.sort(node);
		
		int sum = 0;
		boolean visited[] = new boolean[E+1];
		for (int i=0; i<node.size(); i++) {
			Edge14621k edge = node.get(i);
			
			if ((coll[edge.u] != coll[edge.v]) && find(edge.u) != find(edge.v)) {
				sum += edge.w;
				visited[edge.u] = visited[edge.v] = true;
				union(edge.u,edge.v);
			}
		}
		
		boolean checked = true;
		for (int i=1; i<=E; i++) {
			if (!visited[i]) {
				checked = false;
				break;
			}
		}
		
		sum = checked ? sum : -1;
		
		bw.write(sum+"");
		br.close();
		bw.close();
	}

	static int find(int x) {
		if (x == parent[x]) return x;
		return parent[x] = find(parent[x]);
	}
	
	static void union(int u, int v) {
		u = find(u);
		v = find(v);
		
		if(find(u) != find(v)) {
			parent[u] = v;
		}
	}
}

class Edge14621k implements Comparable<Edge14621k>{
	int u;
	int v;
	int w;
	
	Edge14621k(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Edge14621k right) {
		if(w < right.w) return -1;
		if(w > right.w) return 1;
		return 0;
	}
}
