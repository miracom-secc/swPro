package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
	맥세권 : 맥세권인 집은 맥도날드와 집 사이의 최단거리가 x이하인 집이다.
	스세권 : 스세권인 집은 스타벅스와 집 사이의 최단거리가 y이하인 집이다.
	맥세권과 스세권을 만족하는 집 중 최단거리의 합이 최소인 집
*/
/*
 첫줄: 정점의 개수 V(3 ≤ V ≤ 10,000)와 도로의 개수 E(0 ≤ E ≤ 300,000) 
 그 다음 1~1+E줄 걸쳐 각 도로를 나타내는 세 개의 정수 (u,v,w)가 순서대로 주어진다. 
 		- 정점 u와 v(1 ≤ u,v ≤ V), 가중치가 w(1 ≤ w < 10,000)인 도로
 		- u와 v는 서로 다르며 다른 두 정점 사이에는 여러 개의 간선이 존재할 수도 있음
 E+2번째 줄: 맥도날드의 수 M(1 ≤ M ≤ V-2) 맥세권일 조건 x(1 ≤ x ≤ 100,000,000)
 E+3:M개의 맥도날드 정점 번호가 주어진다. 
 E+4:스타벅스의 수 S(1 ≤ S ≤ V-2), 스세권일 조건 y(1 ≤ y ≤ 100,000,000)
 E+5: S개의 스타벅스 정점 번호가 주어진다. 

 .맥도날드나 스타벅스가 위치한 정점에는 집이 없다.
 .한 정점에 맥도날드와 스타벅스가 같이 위치할 수 있다.
 .집이 있는(= 맥도날드나 스타벅스가 위치하지 않은) 정점이 하나 이상 존재한다.
*/

class Ed implements Comparable<Ed> {
    int v;
    int w;

    public Ed(int v, int w) {
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Ed o) {
        // TODO Auto-generated method stub
        return Integer.compare(this.w, o.w); // x==y : 0 ,  x < y : -1, x > y :  1
        //return this.w < o.w ? -1 : 1;
    }
}

public class BJ_113911_Best {
	static ArrayList<Ed>[] arr;
	static boolean visit[];
	static int V,E;
	static int M, Mx, S ,Sy;
	static int[] mdist;
	static int[] sdist;
	static PriorityQueue<Ed> queue = new PriorityQueue<>();


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());		
		
		V = Integer.parseInt(st.nextToken()); // 정점의 갯수
		E = Integer.parseInt(st.nextToken()); // 도로의 갯수
		
		arr = new ArrayList[V+1];
		for(int i=1; i<=V; i++) { //맥세권, 스세권 부모노드 생성하기위해 +2 
			arr[i] = new ArrayList<>();
		}
		mdist = new int[V+1]; //맥도날드용
		sdist = new int[V+1]; //스타벅스용
		
		for(int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()); //점1
			int v = Integer.parseInt(st.nextToken()); //점2
			int w = Integer.parseInt(st.nextToken()); //가중치
			arr[u].add(new Ed(v,w));	
			arr[v].add(new Ed(u,w));	
		}		
		
		// 맥세권
		Arrays.fill(mdist,Integer.MAX_VALUE);
		st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken()); // 맥도날드수 
		Mx = Integer.parseInt(st.nextToken()); // 맥세권 조건		

		st = new StringTokenizer(br.readLine());
		for(int i=0; i<M; i++) {
			int mm =  Integer.parseInt(st.nextToken()); // 맥도날드 위치
			mdist[mm] = 0;
			queue.add(new Ed(mm, 0));	// 맥도날드의 위치를 시작점. 가중치 0
			
		}
		dijkstra(mdist);

		// 스세권
		Arrays.fill(sdist,Integer.MAX_VALUE);
		st = new StringTokenizer(br.readLine());
		
		S = Integer.parseInt(st.nextToken()); // 스타벅스 수 
		Sy = Integer.parseInt(st.nextToken()); // 스세권 조건
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<S; i++) {
			int ss =  Integer.parseInt(st.nextToken()); // 스타벅스 위치
			sdist[ss] = 0;
			queue.add(new Ed(ss, 0)); // 스타벅스의 위치를 시작점. 가중치 0
		}
		dijkstra(sdist);
		
		int ans = Integer.MAX_VALUE;
        for(int i=1; i<=V; i++) {
            if((mdist[i]>0 && mdist[i]<=Mx) && (sdist[i]>0 && sdist[i]<=Sy)) // dist수행항 결과가 스세권, 맥세권 조건보다는 작아야함.
                ans = Math.min(ans, mdist[i]+sdist[i]);
        }

      

        if(ans==Integer.MAX_VALUE)
        	ans = -1;

        bw.write(String.valueOf(ans));
        bw.flush();
		bw.close();
		br.close();

	}
	

	public static void dijkstra(int[] dist) {

        while(!queue.isEmpty()) {
            Ed eg = queue.poll();
            
            int des = eg.v;
            for (Ed next : arr[des]) {
                if (dist[next.v] >= dist[des] + next.w) {
                    dist[next.v] = dist[des] + next.w;
                    queue.add(new Ed(next.v,dist[next.v]));
                }
            }
        }
    }		
}
