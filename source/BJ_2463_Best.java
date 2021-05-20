package swPro.source;

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
6 7
1 2 10
2 3 2
4 3 5
6 3 15
3 5 4
4 5 3
2 6 6
 */

public class BJ_2463_Best {

	static int N, M;
	static int parents[], size[];
	static List<Edge2463> adj;
	static long total = 0;
	static final int MOD = (int) 1e9;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));	
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		parents = new int[N+1];
		size = new int[N+1];
		adj = new ArrayList<Edge2463>();
		
		for (int i=1; i<=N; i++) {
			parents[i] = i;
			size[i] = 1;
		}
		
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			adj.add(new Edge2463(u, v, w));
			total += w;
		}
		
		Collections.sort(adj, Collections.reverseOrder());
		long ans = 0;
		for (Edge2463 edge : adj) {
			int u = find(edge.u);
			int v = find(edge.v);
			if (u != v) {
//				System.out.println(Arrays.toString(size));
//				System.out.println("total= "+total);
				ans +=  (((1L * size[u] * size[v]) %MOD ) * total) % MOD;
				ans %= MOD;
				
				union(edge.u, edge.v);
			}
			
			total -= edge.w;
		}
		
		bw.write(ans+"");
		
		br.close();
		bw.close();
		
	}
	
	static int find(int x) {
		if (x == parents[x]) return x;
		return parents[x] = find(parents[x]);
	}
	
	static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		if (x != y) {
			parents[x] = y;
			size[y] += size[x];
			size[x] = 1;
		}
	}
}

class Edge2463 implements Comparable<Edge2463>{
	int u;
	int v;
	int w;
	
	Edge2463(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	@Override
	public int compareTo(Edge2463 right) {
		if (w < right.w) return -1;
		if (w > right.w) return 1;
		return 0;
	}
	
}
