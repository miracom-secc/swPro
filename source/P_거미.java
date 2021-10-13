package dijkstra;

import java.io.*;
import java.util.*;
/*
2
3 3
90 60 98
97 50 96
85 40 90
6 4 
10 50 30 20
90 99 77 88
27 50 33 99
5 73 99 99
4 21 98 40
9 59 99 100
*/

public class P_거미 {
	
	static class Node implements Comparable<Node>{
		int x;
		int y;
		double w;
		
		Node(int x, int y, double w){
			this.x = x;
			this.y = y;	
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.w > o.w? -1:1; // 확률이 높을 수록 좋으니
		}
	}

	static int n, m;
	static double map[][];
	static double dist[][];
	static double INF = -1;
	static PriorityQueue<Node> pq;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st= null;
				
		int t = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <=t; tc++) {
			st= new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			
			map = new double[n+1][m+1];
			dist = new double[n+1][m+1];
			for(int i=0;i<=n; i++) {
				for(int j=0; j<=m; j++) {
					dist[i][j] = 0.000000;
				}
			}
			int de =1;
			for(int i=1; i<=n; i++) {
				st= new StringTokenizer(br.readLine());
				for(int j=1; j<=m; j++) {
					double temp =Double.parseDouble(st.nextToken());
					
					map[i][j] = (temp == 100)? 1: 0.9999+(temp/1000000); // 0.9999xx 의 형태로 만들기
				}				
			}
			dijkstra();
			de =1;
			//String.format("%.6f", dist[n][m]);
			double num = (double) Math.round(dist[n][m]*1000000)/1000000;
			System.out.println("#"+tc+" "+String.format("%.6f", num));
		}

	}

	static void dijkstra() {
		pq = new PriorityQueue<Node>();
		int dx[] = {0, 0 ,-1, 1}; // 상, 하, 좌, 우
		int dy[] = { 1,-1, 0, 0};
		
		pq.add(new Node(1,1, map[1][1]));
		dist[1][1] = map[1][1];
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			if(dist[now.x][now.y] > now.w) // 확률이 커야 하브로 더 작은 수가 들어오
				continue; 
			
			for(int i=0; i<4; i++) {
				//
					int next_x = now.x + dx[i];
					int next_y = now.y + dy[i];
					
					int de=1;
					// 범위를 벗어난 경우
					if(next_x<1 ||next_y<1 || next_x >n || next_y > m)
						continue;
					double num = dist[now.x][now.y]*map[next_x][next_y];
					
					if(dist[next_x][next_y] < num)  {						
						dist[next_x][next_y] = num; 
						pq.add(new Node(next_x, next_y, dist[next_x][next_y]));
					}
					
				//}
			}
		}
		
		
		
	}

}
