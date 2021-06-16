package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/*
12 3
1 5 2 3 6 2 3 7 3 5 2 6
 */

public class BJ_11003_Best {
	
	static int N, L;
	static int A[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		
		A = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		//=============== L범위 내의 최소값을 담는다
		Deque<NumInfo> dq = new ArrayDeque<NumInfo>();
		
		for (int i=0; i<N; i++) {
			if (dq.isEmpty()) {
				dq.add(new NumInfo(i, A[i]));
				bw.write(A[i]+" ");
			} else {
				// 구간을 벗어난 최소값 제거(L사이즈 이하 만큼간 가지고 있다)
				if (dq.getFirst().idx < i-(L-1)) dq.removeFirst();
				
				// 최소값의 후보가 되지 않으면 삭제하고 후보값만 남겨두기
				while(!dq.isEmpty() && dq.getLast().val > A[i]) dq.removeLast();
				
				dq.addLast(new NumInfo(i, A[i]));
				bw.write(dq.getFirst().val+" ");
			}
		}
		
		br.close();
		bw.close();
	}
	
}

class NumInfo {
	int idx;
	int val;
	
	public NumInfo(int idx, int val) {
		this.idx = idx;
		this.val = val;
	}
}
