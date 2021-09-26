package STUDY_26;

import java.util.*;
import java.io.*;

public class Main_13418 {
	static int[] maxParent, minParent, maxRank, minRank;
	static ArrayList<int[]> list;
	static int N, M;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		init();
		
		for(int i=0; i<M+1; i++) {
			st = new StringTokenizer(br.readLine().trim());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = (Integer.parseInt(st.nextToken())+1)%2; // 0 오르막길, 1 내리막길 => 1 오르막길, 0 내리막길로 생각하기
			
			list.add(new int[] {A,B,C});
		}
		
		Collections.sort(list, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1[2], o2[2]);
			}
		});
		
		int size = list.size();
		int minAns = 0;
		int maxAns = 0;
		
		for(int i=0; i<size; i++) {
			int[] minCur = list.get(i);
			int[] maxCur = list.get(size-i-1);
			
			int minPx = findParent(minCur[0], minParent);
			int minPy = findParent(minCur[1], minParent);
			
			if(minPx != minPy) {
				union(minParent, minRank, minPx, minPy);
				minAns += minCur[2];
			}
			
			int maxPx = findParent(maxCur[0], maxParent);
			int maxPy = findParent(maxCur[1], maxParent);
			
			if(maxPx != maxPy) {
				union(maxParent, maxRank, maxPx, maxPy);
				maxAns += maxCur[2];
			}
			
		}
		System.out.println((int)(Math.pow(maxAns, 2)- Math.pow(minAns, 2)));
	}
	
	static int findParent(int x, int[] parent) {
		if(parent[x] == x) return x;
		return parent[x] = findParent(parent[x], parent);
	}
	
	static void union(int[] parent, int[] rank, int x, int y) {
		if(rank[x]<rank[y]) {
			parent[x] = y;
			rank[y]++;
			rank[x] = 1;
		}
		else {
			parent[y] = x;
			rank[x]++;
			rank[y] = 1;
		}
	}
	
	static void init() {
		maxParent = new int[N+1];
		minParent = new int[N+1];
		maxRank = new int[N+1];
		minRank = new int[N+1];
		
		for(int i=0; i<N+1; i++) {
			maxParent[i] = minParent[i] = i;
			maxRank[i] = minRank[i] = 1;
		}
		
		list = new ArrayList<>();
	}
}
