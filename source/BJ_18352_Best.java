package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_18352_Best { // 18352
	static class Node implements Comparable<Node> {
		int v;
		int dist;
		
		public Node(int v, int dist) {
			this.v = v;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.dist, o.dist);
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(st.nextToken()); // 도시의 개수
		int M = Integer.parseInt(st.nextToken()); // 도로의 개수
		int K = Integer.parseInt(st.nextToken()); // 최단거리
		int X = Integer.parseInt(st.nextToken()); // 출발위치
		
		ArrayList<Node>[] adj = new ArrayList[N+1];
		int[] W = new int[N+1];
		
		for(int i=1; i<N+1; i++){
			adj[i] = new ArrayList<>();
			W[i] = Integer.MAX_VALUE;
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			adj[Integer.parseInt(st.nextToken())].add(new Node(Integer.parseInt(st.nextToken()), 1));
		}
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(X,0));
		W[X] = 0;
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			for(Node next : adj[n.v]) {
				if(W[next.v] > W[n.v]+next.dist) {
					W[next.v] = W[n.v]+next.dist;
					pq.add(new Node(next.v, W[next.v]));
				}
			}
		}
		
		for(int i=1; i<N+1; i++) {
			if(W[i] == K)
				sb.append(i).append("\n");
		}
		
		String ANS = sb.toString();
		if(ANS.length()<1) ANS = "-1";
		System.out.println(ANS);
	}
}