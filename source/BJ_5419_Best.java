package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

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
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine().trim());
				
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				list.add(new Node (x,y));
			}
			
			Collections.sort(list, new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					// x기준 오름차순 정렬
					return Integer.compare(o1.x, o2.x);
				}
			});
			
			int idx = 1;
			list.get(0).idx = idx;
			for(int i=1; i<list.size(); i++) {
				if(list.get(i).x == list.get(i-1).x) list.get(i).idx = idx;
				else {
					list.get(i).idx = ++idx;
				}
			}
			Collections.sort(list);
			
			init(idx);
			
			long cnt = 0;
			for(Node island: list) {
				cnt += query(island.idx);
				update(island.idx);
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
		int idx;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			this.idx = 0;
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