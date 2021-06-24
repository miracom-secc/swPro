package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
10 15
5 1 3 5 10 7 4 9 2 8

5 5
1 1 1 1 1

5 6
1 1 1 1 1
 */

public class BJ_1806_Best {
	
	static int N, S;
	static int nList[];
	static final int INF = 987654321;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		nList = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			nList[i] = Integer.parseInt(st.nextToken());
		}
		
		int left = 0, right = 0;
		int ans = INF;
		long sum = 0;
		
		while(true) {
			//System.out.println("left= "+left+" right= "+right+ " sum= "+sum);
			
			if(sum >= S) {
				sum -= nList[left++];
				ans = Math.min(ans, (right - left) + 1);
			} else if (right == N) {
				break;
			} else {
				sum += nList[right++];
			}
		}
		
		bw.write((ans == INF ? 0 : ans)+"");
		
		br.close();
		bw.close();
	}

}
