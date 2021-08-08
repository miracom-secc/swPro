package index_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


/*
 *  =============풀이 순서
 * 1. 직원수를 입력받음
 *     - 입력받은 직원수, 누적직원수 배열에 넣음
 *  2. 최대공약수(팀으로 구성할 수 있는 최대 인원수) 를  트리 생성
 *  3. 층별로 개최한 대회에 참가하는 팀의 수 구하기
 *    0) 층별 초대 가능한 층의 수를 입력
 *    1) 층별 초대 가능 층의 수를 기준으로 참가하는 층의 구간을 구함
 *    2) 해당구간의 최대공약수(팀으로 구성할 수 있는 최대 인원수) 를 구함
 *    3) 1번에서 구했던 누적직원수 배열을 이용해서 해당 구간의 누적직원 수를 구하고
 *        3-2) 에서 구한 최대 공약수로 나눠주면 
 *        해당 층에서 추최하는 대회에 참가하는 팀의 수가 나옴
 * */       

public class O_P0084_알고리즘경진대회 {
	
	static int n ; // 층수
	static int input[];	// 입력받은 직원수
	static long sum[];	// 직원수 합계용
	static int tree[];  // 최소공배수(팀으로 구성할 수 있는 최대 인원수)용 트리
	static int leaf;
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int t = Integer.parseInt(br.readLine()); // 테스트 케이스
		 
		for(int tc = 1; tc <=t ; tc++) {
			n = Integer.parseInt(br.readLine()); // 건물의 층 수
			
			leaf =1;
			while(leaf<n) {
				leaf*=2;
			}

			input  = new int[n+1];
			sum =  new long[n+1];
			tree = new int[leaf*2+1];

			//1. 직원수를 입력받음
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				input[i] =Integer.parseInt(st.nextToken()); // 현재 층의 직원 수
				sum[i] = sum[i-1]+input[i]; // 누적직원수 : 전 층까지의 직원의 합 + 현재 층의 직원수
			}
			
			// 2. 최대공약수(팀으로 구성할 수 있는 최대 인원수) 를  트리 생성
			initTree(1, n, 1); // start , end , node						
			
			long ans=0;
			// 3. 층별로 개최한 대회에 참가하는 팀의 수 구하기
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				// 0) 층별 초대 가능한 층의 수를 입력
				int game =Integer.parseInt(st.nextToken());
				
				// 1) 층별 초대 가능 층의 수를 기준으로 참가하는 층의 구간을 구함
				int leftnode = (i-game) < 1 ? 1 : i-game; // 아랫층
				int rightnode = (i+game) > n ? n : i+game; // 윗층
				
				// 2) 해당구간의 최대공약수(팀으로 구성할 수 있는 최대 인원수) 를 구함
				int gcd = findGcd(1, n, leftnode, rightnode,1); 
				
				// 3) 1번에서 구했던 누적직원수 배열을 이용해서 해당 구간의 누적직원 수
				long count = sum[rightnode] - sum[leftnode-1]; 
				
				// 4) 해당층의 참가팀의 수 = 누적 직원수 / 최대공약수 
				ans += count/gcd;
				
			}
			sb.append("#"+tc+" "+ans +"\n");
			
		}
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		br.close();
	}

	// 구간의 gcd 찾기
	static int findGcd(int start, int end, int leftnode, int rightnode, int node) {
		// 구간을 완전히 벗어났을때
		if(rightnode < start || end < leftnode) return 0;
		
		// 구간에 완전히 포함됐을때
		if(leftnode <= start && end <= rightnode) return tree[node];
		
		// 걸쳤을때
		int mid = (start+end)/2;
		int num1 = findGcd(start, mid, leftnode,  rightnode, node*2);
		int num2 = findGcd(mid+1, end, leftnode,  rightnode, node*2+1);
		return initGcd(Math.max(num1, num2), Math.min(num1, num2));
	}

	// 최대공약수 트리 만들기.
	static int initTree(int start, int end, int node) {
		if(start == end) return tree[node] = input[start];
		
		int mid = (start+end)/2;
		int num1 = initTree(start, mid,node*2);
		int num2 = initTree(mid+1, end, node*2+1);
		
		return tree[node] = initGcd(Math.max(num1, num2), Math.min(num1, num2));
	}
	
	// 최대 공약수 찾기
	static int initGcd(int num1, int num2) {
		
		while(num2>0) {
			int rest = num1%num2;
			num1 = num2;
			num2 = rest;
		}
		
		return num1;
	}
	
	//** 참고! 최소 공배수 찾기
	static int initLcm(int num1, int num2) {
		// 두수의 곱/최대굥약수
		 return num1*num2/initGcd(Math.max(num1, num2), Math.min(num1, num2));
	 }

}
