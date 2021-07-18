package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
3
3 3 3
3
1 2 3
 */

public class BJ_10816_Best {
	
	static int N, M, nlist[];

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		N = Integer.parseInt(br.readLine());
		nlist = new int[N+1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			nlist[i] = Integer.parseInt(st.nextToken());
		}
		
		nlist[N] = Integer.MAX_VALUE;
		Arrays.sort(nlist);
		
		M = Integer.parseInt(br.readLine()); 
		
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<M; i++) {
			int find = Integer.parseInt(st.nextToken());
			int upper = upper_bs(find);
			int lower = lower_bs(find);
			
			bw.write((upper - lower)+" ");
		}
		
		br.close();
		bw.close();
	}
	
	// 찾고자 하는 값이 처음 나오는 인덱스 반환(큰쪽)
	static int upper_bs(int find) throws Exception {
		int s = 0;
		int e = N;
		
		while (s < e) {
			int mid = (s + e) / 2;
			
			if (nlist[mid] > find) {
				e = mid;
			} else {
				s = mid + 1;
			} 
		}
		
		return e;
	}
	
	// 찾고자 하는 값이 처음 나오는 인덱스 반환 (작은쪽)
	static int lower_bs(int find) throws Exception {
		int s = 0;
		int e = N;
		
		while (s < e) {
			int mid = (s + e) / 2;
			
			if (nlist[mid] >= find) {
				e = mid;
			} else {
				s = mid + 1;
			} 
		}
		
		return e;
	}

}
