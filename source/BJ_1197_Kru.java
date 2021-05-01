package algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
3 3
1 2 1
2 3 2
1 3 3
 */

public class BJ_1197_Kru {

	static int U , E, parent[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		U = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		List<Edge1197k> node = new ArrayList<>();
		parent = new int[U+1];
		
		for (int i=1; i<=U; i++) {
			parent[i] = i;
		}
		
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			node.add(new Edge1197k(u, v, w));
		}
		
		Collections.sort(node);
		
		int sum = 0;
		for (int i=0; i<node.size(); i++) {
			Edge1197k temp = node.get(i);
			int u = temp.u;
			int v = temp.v;
			int w = temp.w;
			
			if (find(u) != find(v)) {
				sum += w;
				union(u,v);
			}
		}
		
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
		
		if(u != v) {
			parent[u] = v;
		}
	}

}

class Edge1197k implements Comparable<Edge1197k>{
	int u;
	int v;
	int w;
	
	Edge1197k(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Edge1197k right) {
		if(w < right.w) return -1;
		if(w > right.w) return 1;
		return 0;
	}
}
