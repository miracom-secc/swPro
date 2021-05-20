package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
5
132 392 311 351 231
392 351 132 311 231
 */

public class BJ_7578_Best {

	static int N, A[], B[];
	static int leafNode, tree[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		
		init();
		tree = new int[leafNode*2+1];
		A = new int[N+1];
		B = new int[1000001];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i=1; i<=N; i++) {
			int num = Integer.parseInt(st.nextToken());
			A[i] = num;
		}
		
		st = new StringTokenizer(br.readLine());
		for (int i=1; i<=N; i++) {
			int num = Integer.parseInt(st.nextToken());
			B[num] = i;
		}
		
		//============== 케이블 연결
		long sum = 0;
		for (int i=1; i<=N; i++) {
			sum += query(B[A[i]], N);
			update(B[A[i]], 1);
		} // NlogN
		
		// 총 시간 복잡도 : NlogN
		bw.write(sum+"");
		
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
		idx += leafNode - 1;
		tree[idx] = diff;
		
		while(idx > 1) {
			idx /= 2;
			tree[idx] += diff;
		}
	}
	
	static int query(int s, int e) {
		s += leafNode - 1;
		e += leafNode - 1;
		
		int sum = 0;
		while(s <= e) {
			if (s%2 == 1) sum += tree[s];
			if (e%2 == 0) sum += tree[e];
			
			s = (s+1) / 2;
			e = (e-1) / 2;
		}
		
		return sum;
	}

}
