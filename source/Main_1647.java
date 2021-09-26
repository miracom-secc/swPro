package STUDY_26;

import java.util.*;
import java.io.*;

public class Main_1647 {
	static int N,M;
	static int[] parent, rank;
	static PriorityQueue<int[]> pq;
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		init(); 
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			pq.add(new int[] {A,B,C});
		}
		
		int homeCnt = 1;  // 연결된 집의 개수세기
		int maxCost = -1; // MST를 구성하는 길 중 가장 큰 유지비를 저장 
		int totalCost = 0;  // 집을 연결하는 길들의 유지비의 총 합
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			
			int pX = findParent(cur[0]);
			int pY = findParent(cur[1]);
			
			if(pX!=pY) {
				union(pX,pY);
				
				totalCost += cur[2];
				if(maxCost<cur[2]) maxCost = cur[2]; 
				
				homeCnt++;

				if(homeCnt == N) break;
			}
		}
		
		System.out.println(totalCost - maxCost);
		
	}
	
	static void init() {
		parent = new int[N+1];
		rank = new int[N+1];
		
		for(int i=1; i<N+1; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
		
		pq = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1[2], o2[2]); // 유지비 오름차순 정렬
			}
		});
	}
	
	static int findParent(int x) {
		if(parent[x] == x) return x;
		return parent[x] = findParent(parent[x]);
	}
	
	static void union(int x, int y) {
		if(rank[x]<rank[y]) {
			rank[y]++;
			rank[x] = 1;
			parent[x] = y;
		}
		else {
			rank[x]++;
			rank[y] = 1;
			parent[y] = x;
		}
	}
}
