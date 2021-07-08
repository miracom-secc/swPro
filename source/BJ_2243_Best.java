package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
사탕의 맛 :  1부터 1,000,000 
첫째 줄:  수정이가 사탕상자에 손을 댄 횟수 n(1≤n≤100,000)
다음 n개의 줄: 두 정수 A, B, 혹은 세 정수 A, B, C가 주어진다. 
            > A가 1인 경우: 사탕상자에서 사탕을 꺼내는 경우. B만 주어짐. B는 꺼낼 사탕의 순위, 사탕 한개 꺼냄.
            > A가 2인 경우: 사탕을 넣는 경우. B는 넣을 사탕의 맛. C는 그러한 사탕의 개수. C가 양수일 경우에는 사탕을 넣는 경우이고, 음수일 경우에는 뺌. 
            
맨 처음에는 빈 사탕상자에서 시작한다고 가정하며, 사탕의 총 개수는 2,000,000,000을 넘지 않는다. 또한 없는 사탕을 꺼내는 경우와 같은 잘못된 입력은 주어지지 않는다.

출력
A가 1인 모든 입력에 대해서, 꺼낼 사탕의 맛의 번호를 출력한다. 물론, A=2 이면서 C<0 일 때는 출력하지 않는다.
*/

public class BJ_2243_Best {
static int n, leafCnt; // 손을 댄 횟수
	
	static int INF = 1000001; //사탕의 맛 :  1 ~ 1,000,000 
	//static long[] arr;
	static long[] tree;

	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		//arr = new long[INF];
		tree = new long[INF];
		
		// 입력
		n = Integer.parseInt(st.nextToken()); // - 손을 댄 횟수
		
		for(int i=0; i<n ; i++) {
			
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			
			// 사탕을 꺼내는 경우
			if(a==1) {
				int b = Integer.parseInt(st.nextToken()); // 사탕의 순위
				int ans = find(b); // 빼는 위치 찾기
				bw.write(String.valueOf(ans) +"\n");
				update(ans,-1);
			}
			// 사탕을 넣는 경우
			else {
				int b = Integer.parseInt(st.nextToken()); // 사탕의 맛
				long c = Long.parseLong(st.nextToken()); // c 가 양수: 그 갯수만큼 넣고 / 음수: 그 갯수만큼 뺌
				update(b, c);				
			}			
		}
		bw.flush();
		bw.close();
		br.close();
				
	}
	static void update(int idx, long val) {
		while (idx <tree.length) {
			tree[idx] += val;
			idx += (idx & -idx); // 상위 구간 인덱스로 갱신, 맨 마지막 비트를 스스로에게 더해줌
		}
		
	}
	
	static int find(int num) {
		int idx=-1;
		int left =1, right= INF-1;
		
		while(left < right) {
			int mid = (left + right) /2 ;
			if (sum(mid) >= num) {				
				right = mid;
			}else {
				left = mid+1;
			}	
			int de=1;
		}	
		idx = right;
		return idx;
	}
	
	static long sum(int pos) {
		// 인덱스가 1 시작
		long ret = 0;
		while(pos > 0) {
			ret += tree[pos];
			//pos &= (pos - 1); 
			pos -= (pos & -pos); // 이진수에서 마지막 비트를 지움.
		}
		return ret;
	}
}
