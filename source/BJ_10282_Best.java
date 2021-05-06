package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_10282_Best { // 10282
	static class Node implements Comparable<Node> {
		int v;
		int s;
		
		public Node(int v, int s) {
			this.v = v;
			this.s = s;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.s, o.s);
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(st.nextToken());
		for(int tc=1 ; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine().trim());
			int N = Integer.parseInt(st.nextToken());
			int D = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			ArrayList<Node>[] adj = new ArrayList[N+1];
			int[] W = new int[N+1];
			
			for(int i=1; i<N+1; i++) {
				adj[i] = new ArrayList<>();
				W[i] = Integer.MAX_VALUE;
			}
			
			for(int d=0; d<D; d++) {
				st = new StringTokenizer(br.readLine().trim());
				
				// b°¨¿° sÃÊ ÈÄ a°¨¿°µÊ(´Ü¹æÇâ) 
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int s = Integer.parseInt(st.nextToken());
				
				adj[b].add(new Node (a,s));
			}
			
			PriorityQueue<Node> pq = new PriorityQueue<>();
			pq.add(new Node(C,0));
			W[C] = 0;
			boolean[] visited = new boolean[N+1];

			while(!pq.isEmpty()) {
				Node n = pq.poll();
				for(Node next : adj[n.v]) {
					if(W[next.v] > W[n.v]+next.s) {
						W[next.v] = W[n.v]+next.s;
						pq.add(new Node (next.v, W[next.v]));
					}
				}
				visited[n.v] = true;
			}
			
			int count = 0;
			int time = 0;
			
			for(int i=1; i<N+1; i++) {
				if(visited[i]) {
					count++;
					if(time<W[i])
						time = W[i];
				}
			}
			sb.append(count).append(" ").append(time).append("\n");
		}
		System.out.println(sb.toString());
	}
}