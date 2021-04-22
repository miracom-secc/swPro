package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
 * 해결 조건 
 * 1. 건물을 모두 방문하는 이동경로
 * 2. 무조건 입구(0번)과 1번 건물은 연결
 * 3. 오르막길 K번 -> K^2 피로도 발생 
 * 
 * - 최악의 코스 : 피로도가 최대 = 오르막이 최대 횟수 발생
 * - 최적의 코스 : 피로도가 최소 = 오르막이 최소 횟수 발생 
 * => 각각의 경로 찾기
 * 
 *  필요 변수 
 *  minP, maxP : 이동경로가 사이클을 형성하는지 판단할 배열
 *  cntMin, cntMax : 피로도가 각각 최대, 최소일 때 오르막길 횟수저장 
 */

public class BJ_13418_Best {
	static int N,M;
	
	static class Node implements Comparable<Node>{
		int from;
		int to;
		int dir;
		
		public Node(int from, int to, int dir) {
			this.from = from;
			this.to = to;
			this.dir = dir;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.dir, o.dir);
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		int[] minP = new int[N+1];
		int[] maxP = new int[N+1];
		
		for(int i=0; i<N+1; i++) {
			minP[i] = maxP[i] = i;
		}
		
		int cntMin = 0; // 오르막길 카운트 저장
		int cntMax = 0;
		
		ArrayList<Node> list = new ArrayList<>();
		
		for(int i=0; i<M+1; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int dir = (Integer.parseInt(st.nextToken())+1)%2; // 0(오르막길) 또는 1(내리막길) -> 1(오르막길), 0(내리막길)로 진행
			
			list.add(new Node(A,B,dir));
		}
		
		Collections.sort(list);
		
		for(int i=0; i<list.size(); i++) {
			// 최소 피로도 경로
			Node cur = list.get(i);
			int pA = findParents(minP, cur.from);
			int pB = findParents(minP, cur.to);
			
			if(pA != pB) {
				union(minP, pA, pB);
				cntMin += cur.dir;
			}
			
			// 최대 피로도 경로 
			cur = list.get(list.size()-1-i);
			pA = findParents(maxP, cur.from);
			pB = findParents(maxP, cur.to);
			
			if(pA != pB) {
				union(maxP, pA, pB);
				cntMax += cur.dir;
			}
		}
		
		// 최대, 최소 피로도의 차이 계산
		System.out.println((int)(Math.pow(cntMax, 2)- Math.pow(cntMin, 2)));
	}
	
	static int findParents(int[] parents, int x) {
		if(parents[x] == x) return x;
		return parents[x] = findParents(parents, parents[x]);
	}
	
	static void union(int[] parents, int x, int y) {
		parents[y] = x;
	}
}

