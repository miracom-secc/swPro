package swPro.source;

import java.io.*;
import java.util.*;

// 프림알고리즘 사용
/* 풀이 방법
 * 
 * 1. 한번에 모두 입력을 받음
 * 2. 입력 받은 값들을 오름차순으로 정렬
 * 3. 트리 생성
 *  1) 오른 차순으로 정렬 되었으므로 이미 방문한 경우는 제외
 *  2) 이미 방문했을 때 저장된 값보다 큰 경우는 제외
 *  3) 트리를 생성
 *    3-1) 현재보다 큰경우에만 이동.
 *    3-2) 이미 방문한 경우는 제외
 *    3-3) 다음 지점의 가중치를 answer 변수에 추가 
 *        , 가중치 배열에 저장된값이 아니라면 현재의 answer 배열에서 해당값을 제외하고 다음 가중치를 더함.
 *    3-4) 다음 지점을 큐에 추가함 
 * 
 * */
public class P0102_최소증가스패닝트리 {

	static class Node implements Comparable<Node>{
	//	int u;
		int v; 
		int w;
		
		Node( int v, int w){
			//this.u = u;
			this.v = v;
			this.w = w;
		} 
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.w < o.w ? -1:1;
		}
	}
	static int n,m;
	static ArrayList<Node> arr[]; // 간성정보 저장용
	static long cost[];
	static boolean visit[];
	static long INF = Long.MAX_VALUE;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		PriorityQueue<Node> pq = new PriorityQueue<>();
		StringTokenizer st;
		int t= Integer.parseInt(br.readLine());
		
		for(int tc =1; tc <= t; tc++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			cost = new long[n+1];
			visit = new boolean[n+1];
			arr = new ArrayList[n+1];
			
			for(int i=0; i<n+1; i++) {
				cost[i]=INF;
				arr[i] = new ArrayList<Node>();
			}	
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());

				arr[u].add(new Node(v, w));
				arr[v].add(new Node( u, w));
				
			}

			// 시작점 지정
			pq.add(new Node(1,0));
			cost[1] =0; 

			long ans=0;
			int cnt=0;
			while(!pq.isEmpty()) {
				Node now = pq.poll();
				
				// 오름차순으로 정렬되므로 이미 방문한 경우 pass
				if(visit[now.v]) continue;
				// 이미 저장된 값보다 큰경우 올필요 없으므로 X
				if(cost[now.v]  < now.w) continue;
				visit[now.v] = true;
				cnt++;
				for(Node next: arr[now.v]) {
					
					// 현재보다 큰경우에만 이동해야하므로.
					if(now.w  > next.w) continue;
					// 오름차순으로 정렬되므로 이미 방문한 경우 pass
					if(visit[next.v]) continue;

					if(cost[next.v] > next.w) 
					{
						if(cost[next.v] != INF) {
							ans -= cost[next.v];
						}
						ans +=  next.w;
						cost[next.v] = next.w;
						pq.add(new Node(next.v, next.w));
					}
				}
			}
			bw.write(cnt==n? "#"+tc+" "+ ans+"\n":"#"+tc+" -1\n");
		}
		bw.flush();
		bw.close();
		br.close();
		
	}

}
