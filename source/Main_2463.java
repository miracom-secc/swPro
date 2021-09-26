package STUDY_26;

import java.util.*;
import java.io.*;

/*
[문제풀이]
- 서로 다른 두 정점 u,v에 대하여, Cost(u,v)는 다음에서 제거되는 간선들의 가중치 합
- u와 v사이의 경로가 있으면 이 그래프의 최소 가중치 간선을 그래프에서 제거
- 이 과정을 u와 v사이의 경로가 없을 때까지 반복 

- u < v인 모든 두 정점 u, v에 대한 Cost(u,v)들의 총 합을 구하기
- 총 합이 10^9보다 크거나 같으면 이를 10^9으로 나눈 나머지를 출력

=> 가중치가 작은 간선을 제거할 때마다 경로가 있는지 탐색하면 시간초과
=> 가중치가 큰 간선을 추가할 때마다 생기는 경로를 파악

1. 간선을 내림차순 정렬
2. 그룹화(union-find)하면서 cost의 합 += cost(u,v)구하기
*/
public class Main_2463 {
	static int N,M;
	static int MOD = 1000000000;
	static int[] parent, groupSize;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		ArrayList<int[]> list = new ArrayList<>();
		long sum = 0;
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			if(x>y) {
				int tmp = x;
				x = y;
				y = tmp;
			}
			
			list.add(new int[] {x,y,w});
			sum += w;
		}
		// 내림차순 정렬 
		Collections.sort(list, new Comparator<int[]>() { 

			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o2[2], o1[2]);
			}
		});
		
		parent = new int[N+1];
		groupSize = new int[N+1];
		
		for(int i=1; i<N+1; i++) {
			parent[i] = i;
			groupSize[i] = 1;
		}
		
		long cost = 0;
		
		for(int i=0; i<list.size(); i++) {
			int[] edge = list.get(i);
			int pX = findParent(edge[0]);
			int pY = findParent(edge[1]);
			
			if(pX!=pY) {
				cost += (((((long)groupSize[pX]) * ((long)groupSize[pY]))%MOD) * sum) % MOD;
				cost %= MOD;
				
				union(pX,pY);
			}
			sum -= edge[2];
		}
		System.out.println(cost);
	}
	
	static int findParent(int x) {
		if(parent[x] == x) return x;
		return parent[x] = findParent(parent[x]); 
	}
	
	static void union(int x, int y) {
		if(groupSize[x]>groupSize[y]) {
			groupSize[x] += groupSize[y];
			groupSize[y] = 1;
			parent[y] = x;
		}
		else {
			groupSize[y] += groupSize[x];
			groupSize[x] = 1;
			parent[x] = y;
		}
	}
}