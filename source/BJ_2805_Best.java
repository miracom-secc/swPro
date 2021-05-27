package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
4 7
20 15 10 17

6 25
20 15 10 16 41 44

5 2000000000
900000000 900000000 900000000 900000000 900000000

 */

public class BJ_2805_Best {

	static long N, M, tree[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Long.parseLong(st.nextToken());
		M = Long.parseLong(st.nextToken());
		
		tree = new long[(int) N];
		
		long H = 0;
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			tree[i] = Long.parseLong(st.nextToken());
			H = Math.max(H, tree[i]);
		}
		
		Arrays.sort(tree);
		bw.write(bs(H)+"");
		
		br.close();
		bw.close();
	}
	
	static long bs(long H) {
		long ret = -1;
		long s = 0;
		long e = H;
		
		while (s <= e) {
			long mid = (s + e) / 2;
			
			long sum = 0;
			// 잘라낸 나무의 길이 더하기
			for (int i=0; i<N; i++) {
				if (tree[i] > mid) sum += (tree[i] - mid);
			}
			
			if (sum >= M) {
				ret = Math.max(ret, mid);
				s = mid + 1;
			} else if (sum < M) {
				e = mid - 1;
			}
		}
		
		return ret;
	}

}
