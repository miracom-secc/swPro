package swPro.source;

/*
3
12 3 
0 3
3 4
5 5 
3 6
2 8
4 8
5 0
2 1
4 1
6 2
5 3
7 7
4 10 12
5 5
4 5
16 14
7 17
6 15
11 13
1 2 5 3 4
10 1
19 8
9 10
6 3
4 17
4 16
10 4
13 0
20 20
4 3
14 9
6
 * */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/**
문제 풀이 순서
 1. 입력
    - 도시의 좌효정보를 저장받을때 사용할 객체 필요 (===> City 객체)
 2. 입력받은 좌표끼리 전부 연결시키는것처럼 해서 모든좌표끼리 간의 거리를 계산하여 저장함 
    - 두지점간 거리 정보를 저장할 객체 생성필요. (===>Edge 객체)
    - 두지점간 비용은 (x1-x2)^2 _+ (y1-y2)^2
 3. 수원지 정보를 pq 에 넣어주고, dijkstra 수행.
     => ** 놓친부분 : 수원지로부터 최단거리가 아니라 수원지에서 최단거리로 연결관 각지점들과의 최단거리를 구해야 하므로
                    변수 하나를 선언해서 이미 최단거리가 구해진 경우 해당 변수에 값을 추가하고 거기까지의 dist 값을 0오로 초기화.
 4. 각 좌표들의 합을 구함
*/
public class O_P0079_도시계획 {
	
	static class City{
		int idx; // 순번
		int x; // x좌표
		int y; // y좌표
		
		City(int idx, int x, int y){
			this.idx = idx;
			this.x = x;
			this.y = y;
		}
	}
	
	static class Node implements Comparable<Node>{
		int v; //2번 도시의 번호
		long w;  //두 도시간의 거리 
		
		Node(int v, long w){
			this.v = v;
			this.w = w; 
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.w < o.w ?-1 : 1;
		}
	}
	
	static int n,m; // 도시갯수, 수원지 갯수
	static City city[];
	static ArrayList<Node>[] arr;
	static long dist[];
	static long dist_w[];
	static long INF =Long.MAX_VALUE;
	static int suwon[];
	static PriorityQueue<Node> pq;
	static long ans;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		//tc 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		int tc = Integer.parseInt(st.nextToken());
		
		for(int t=1; t<=tc; t++) {
			//******* 1. 입력 *******//
			//  1) n (도시의 수),m (수원지의 수) 
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			
			pq = new PriorityQueue<Node>();
			city = new City[n+1];
			dist = new long[n+1];
			dist_w = new long[n+1];
			arr = new ArrayList[n+1];
			Arrays.fill(dist, INF);
			
			for(int i=1; i<n+1; i++) {
				arr[i]= new ArrayList<Node>();
			}
			
			// 2) 좌표를 순서대로
			for(int i=1; i<n+1; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				city[i] = new City(i,x,y);			
			}
			
			// 3) 수원지 정보를 순서대로 큐에 담음.
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<m; i++) {
				int start = Integer.parseInt(st.nextToken());
				dist[start] =0;
				pq.add(new Node(start,0));
			}
			
			
			//******* 2. 모든 좌표간 거리를 계산 *******//
			calc_distance();

			//******* 3. 담긴 수원지를 기준으로 다익스트라 수행 *******//
			ans = dijkstra();
			bw.write(ans+"\n");
		}
		bw.flush();
		bw.close();
		br.close();

	}

	static long dijkstra() {
		long ret=0;
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int now_v= now.v;

			if(dist[now_v] < now.w) continue;

			//=============== 놓친부분 ================//
			ret += dist[now_v]; // 이미 최소로 저장된 부분이니깐 리턴값이 추가 
			dist[now_v]=0; // 이미 계산이 완료되었고, 수원지 기준이 아니라 그냥 제일 가까운 지점과 연결해야하니 0으로 세팅(앞에서부터 연결되어 누적된 값들은 다 쓸모X)
			//==========================================//
			for(Node next : arr[now_v]) {
				if(dist[next.v] > next.w) {
					dist[next.v] = next.w;
					pq.add(new Node(next.v, dist[next.v]));	
				}
			}						
		}	
		return ret;
	}

	//2. 모든 좌표간 거리를 계산
	static void calc_distance() {
		
		for(int i=1; i<n+1; i++) {
			City idx1 = city[i];
			for(int j=i+1; j<n+1; j++) {
				City idx2 = city[j];
				long dis = (long) (Math.pow((idx1.x-idx2.x), 2) + Math.pow((idx1.y - idx2.y), 2));
				arr[idx1.idx].add(new Node(idx2.idx, dis));
				arr[idx2.idx].add(new Node(idx1.idx, dis));
				
			}
		}
		
	}

}

