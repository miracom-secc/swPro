package tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


//구간합


/*
7 4 
1 50 70 10 15 25 60
7 4 4 5
4 7 2 90
2 6 3 1
1 7 4 5
*/
public class O_BOJ_1275_커피숍2 {

	static int N, Q; // 수의개수, 턴의 개수
	static long tree[];
	static long input[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st= new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); 
		Q = Integer.parseInt(st.nextToken());
		
		input= new long[N+1];
		tree= new long[N+1];
		
		// 1) 정보입력
		st= new StringTokenizer(br.readLine());
		for(int i=1; i<N+1; i++) {
			input[i] = Long.parseLong(st.nextToken()); 
			update(i, input[i]);
			
		}
		
		for(int i=0; i<Q ; i++) {						
			st= new StringTokenizer(br.readLine());
			// x~y 까지의 구간합 -> sum
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			long ans =0; 
			
			if(y> x) {
				ans = sum(y) - sum(x-1);
			} else {
				ans = sum(x) - sum(y-1);
			}
			
			bw.write(String.valueOf(ans)+"\n");
			// a 의 값을 b로 변경 -> update
			int a = Integer.parseInt(st.nextToken());
			long b = Long.parseLong(st.nextToken());
			long diff = b - input[a];
			input[a] = b;
			update(a, diff);
				
		}

		bw.flush();
		bw.close();
		br.close();
		

	}
	
	static long sum(int idx) {
		long ret =0;
		while(idx >0) {
			ret += tree[idx];
			idx -= (idx & -idx);
		}
		
		return ret;
	}
	
	static void update(int idx, long val) {
		
		while(idx <tree.length) {
			tree[idx] += val;
			idx += (idx & -idx);
		}
		
	}

}
