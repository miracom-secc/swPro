package swPro.source;

import java.io.*;
import java.util.*;

/*
 * list : 섬의 정보를 저장(x,y좌표 : [-10^9 : 10^9])
 * 		- 탐색을 위해 ArrayList사용
 * xPos : 좌표압축을 위한 리스트, x좌표만 저장
 * 		- 데이터 삭제를 위해 LinkedList사용
 * map : 좌표압축을 위한 hashmap, 중복제거 및 키값으로 탐색
 * 
 * 문제 풀이
 * 1. 섬의 좌표를 입력받고, x좌표를 기준으로 좌표압축 (섬은 최대 75000개)
 * 2. 섬의 y좌표 기준 내림차순정렬, y같으면 x기준 오름차순 정렬
 * 		- y좌표가 증가하는 방향은 북쪽, x좌표가 증가하는 방향은 동쪽
 * y
 * |-------------------------------
 * | (-10,10)		(10,10)         
 * | 
 * | (-10,-10)		(10,-10)
 * |-------------------------------x
 * 정렬 후: (-10,10) (10,10) (-10,-10) (10,-10)
 * 	압축 좌표:   1          2          1           2 
 * 3. 인덱스트리를 이용하여 x좌표 기준으로 더 작거나 같은 값 탐색 (자신에게 올 수 있는 섬들을 카운트)
 * 		(-10,10) (10,10) (-10,-10) (10,-10)
 * 		0 -> 1 -> 1 -> 3 = 총 5개의 쌍
 */
public class BJ_5419_Best {
	static int leaf;
	static int[] tree;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st  = null;
		
		int TC = Integer.parseInt(br.readLine().trim());
		for(int tc = 1; tc <= TC ; tc++) {
			int N = Integer.parseInt(br.readLine().trim());
			
			ArrayList<Node> list = new ArrayList<>();
			LinkedList<Integer> xPos = new LinkedList<>();
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine().trim());
				
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				list.add(new Node (x,y));
				xPos.add(x);
			}
			// x좌표 기준 좌표압축
			Collections.sort(xPos);
			
			HashMap<Integer, Integer> map = new HashMap<>();
			int idx = 0;
			
			while(!xPos.isEmpty()) {
				int getX = xPos.poll();
				if(!map.containsKey(getX)) map.put(getX, ++idx);
			}
			
			// 섬의 좌표 정렬
			Collections.sort(list);
			
			init(idx);
			
			long cnt = 0;
			for(Node island: list) {
				int key = map.get(island.x);
				cnt += query(key);
				update(key);
			}
			
			bw.write(cnt+"\n");
			
		}
		bw.flush();
	}
	
	static void init(int idx) {
		leaf = 1;

		while(leaf < idx) {
			leaf *= 2;
		}
		
		tree = new int[(leaf--)*2];
	}
	
	static int query(int pos) {
		int start = leaf+1;
		int end = leaf+pos;
		int sum = 0;
		
		while(start<=end) {
			if(start%2 == 1) {
				sum+=tree[start];
			}
			if(end%2 == 0) {
				sum+=tree[end];
			}
			
			start = (start+1)/2;
			end = (end-1)/2;
		}
		return sum;
	}
	
	static void update(int pos) {
		int idx = leaf+pos;
		
		while(idx>=1) {
			tree[idx] += 1;
			idx /= 2;
		}
	}
	
	static class Node implements Comparable<Node> {
		int x;
		int y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Node o) {
			// y기준 내림차순정렬, y같으면 x기준 오름차순 정렬
			if(this.y == o.y) {  
				return Integer.compare(this.x, o.x); 
			}
			return Integer.compare(o.y, this.y);
		}
	}
}