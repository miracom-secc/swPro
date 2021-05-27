package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
12
120
167
163
172
145
134
182
155
167
120
119
156
0 1 0 0 3 2 6 7 4 6 9 4
 */

public class BJ_2465_Best {

	static int N, S[], ans[], order[];
	static int leafNode, tree[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		
		init();
		tree = new int[leafNode*2+1];
		
		S = new int[N+1];
		order = new int[N+1];
		ans = new int[N+1];
		
		for (int i=1; i<=N; i++) {
			order[i] = Integer.parseInt(br.readLine());
			update(i, 1);
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i=1; i<=N; i++) {
			S[i] = Integer.parseInt(st.nextToken());
		}
		
		//================== 키순으로 오름차순 정렬
		Arrays.sort(order);
//		System.out.println(Arrays.toString(order));
		
		//================== 키 정하기
		for (int i=N; i>0; i--) {
			int node = find(S[i]+1); //앞에 인원수 + 1이 나의 위치
			ans[i] = order[node]; 
			update(node, -1); //배열에서 제거
		}
		
		for (int i=1; i<=N; i++)
			bw.write(ans[i]+"\n");
		
		br.close();
		bw.close();
	}

	static void init() {
		leafNode = 1;
		while(N > leafNode) {
			leafNode *= 2;
		}
		
	}
	
	static void update(int idx, int diff) {
		idx += leafNode  - 1;
		tree[idx] = diff;
		
		while(idx > 1) {
			idx /= 2;
			tree[idx] += diff;
		}
	}
	
	static int query(int s, int e) {
		s += leafNode  - 1;
		e += leafNode  - 1;
		
		int sum = 0;
		while(s <= e) {
			if (s%2 == 1) sum += tree[s];
			if (e%2 == 0) sum += tree[e];
			
			s = (s+1) / 2;
			e = (e-1) / 2;
		}
		
		return sum;
	}
	
	static int find(int findnum) {
		int root = 1;
		
		while (root < leafNode) {
//			System.out.println("root= "+root);
			if (tree[root*2] >= findnum) {
				root *= 2 ;
			} else {
				findnum -= tree[root*2];
				root = root *2 +1;
			}
		}
		
		return root - leafNode + 1;
	}
}
