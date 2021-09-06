package tree;

import java.io.*;
import java.util.*;
/*
3
10 3
175 182 178 179 170 179 171 185 185 181
3 7 175
1 10 180
1 10 179
7 5
183 176 175 183 174 182 186
1 4 176 
2 6 177
1 7 180
1 7 160
5 7 180
2 2
161 168
1 2 175
1 2 188 
* */


/* a ~ b 번째 사이에 x 보다 큰사람이 몇 명? => 구간합
 * 
 * 풀이 순서
 *  => 사람정보와 질문 정보를 나눠서 입력 받은 후 정렬하여 질문 정보 출력
 * 
 * 1. 입력
 *  1) 서있는 사람의 정보와 질문의 정보를 타입을 나눠서 입력받음
 *     - 사람정보: Type 1
 *     - 질문정보: Type 2
 *  2) 객체 생성
 *     - 타입, 키, 인덱스(사람정보/질문용), 탐색시작구간, 탐색종료구간
 * 2. 정렬
 *  1) 키의 역순으로 정렬 
 * 3. 입력받은 정보에 따리 진행
 *  1) 사람의 경우 트리에 insert
 *  2) 질문의 경우 구간합
 * 4. 질문 출력
 * 
 * */

public class P_키컸으면 {
	
	static class Info implements Comparable<Info>{
		int type;	//타입 - 1: 사람, 2: 질문
		long h;		//키
		int idx;	//인덱스
		int start; // 탐색시작구간
		int end;	// 탐색종료구간
		
		// 사람정보용
		Info(int type, long h, int idx){
			this.type = type;
			this.h = h;
			this.idx = idx;
		}
		// 질문정보용
		Info(int type, long h, int idx, int start, int end){
			this.type = type;
			this.h = h;
			this.idx = idx;
			this.start = start;
			this.end= end;
		}
		
		@Override
		public int compareTo(Info o) {
			if(this.h == o.h) {
				if(this.type > o.type) return -1;
				else return 1;
			}else {
				return (this.h >o.h)? -1: 1;
			}
		}
	}
	
	static int n,q; // 사람의 수, 질문의 수
	static Info input[];
	static long tree[]; 
	static int leaf;
	static long ans[];
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		int t = Integer.parseInt(br.readLine().trim());
		int de;
		for(int tc=1; tc <=t ; tc++) {
			
			st= new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			q = Integer.parseInt(st.nextToken());
			
			input = new Info[n+q+1];	// 정보 입력용
			leaf=1;
			while(leaf <n+q+1){
				leaf *=2;
			}
			tree = new long[leaf*2]; 	// 트리 생성용
			ans = new long[q+1]; 			//질문저장용						
			
			/****************** 1. 입력 ******************/
			// 1) 서있는 사람의 정보
			st= new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				long h = Long.parseLong(st.nextToken());
				input[i] = new Info(1,h,i); // 타입1, 키
			}
			input[0] = new Info(1,0,0); 
			
			// 2) 질문 정보
			for(int i=1; i<= q; i++) {
				st= new StringTokenizer(br.readLine());
				int left = Integer.parseInt(st.nextToken());
				int right = Integer.parseInt(st.nextToken());
				long h = Long.parseLong(st.nextToken());
				input[i+n] = new Info(2, h, i, left, right); // 타입2, 키, 질문인덱스, 탐색시작구간, 탐색 종료구간		
			}
			
			/****************** 2. 정렬 ******************/
			Arrays.sort(input); // 1. 특정 키보다 큰 사람의 수를 구하는 문제이므로 키의 역순으로 sort, 2. 큰 사람의 수이므로 같은 경우는 제외되어야 하니 키가 같은경우에는 질문이 먼저오도록 sort
			
			de=1;
			/****************** 3. 진행 ******************/			
			for(int i=0; i<= n+q; i++ ) { 
				Info info = input[i];
				
				// 1) 사람정보인 경우
				if(info.type == 1) {
					update(info.idx,1);									
				}
				// 2) 질문 정보인 경우
				else {
					ans[info.idx] = sum(info.start, info.end); // 입력받을 때 저장한 인덱스에 해당 구간의 합을 저장.
					
				}				
			}
			StringBuilder sb = new StringBuilder();
			sb.append("#"+tc);
			for(int i=1; i<=q; i++) {
				sb.append(" "+ans[i]);
			}
			bw.write(sb.toString() +"\n");
			bw.flush();
		}
		
		bw.close();
		br.close();
	}
	
	static long sum(int start, int end) {
		long ret=0; 
		
		start = start+leaf-1;
		end= end+leaf-1;
		
		while(start < end) {
			if(start % 2 ==1) {
				ret += tree[start];
				start++;
			} 
			if(end % 2 == 0) {
				ret += tree[end];
				end--;
			}
			
			start /= 2;
			end /= 2;
		}
		if(start== end)
			ret += tree[end];
		
		return ret;
	}
	
	static void update(int idx, int val) {
		idx = idx+leaf-1;
		tree[idx] += val;
		
		while(idx > 1) {
			idx /=2 ;
			tree[idx] = tree[idx*2]+ tree[idx*2+1];
		}
		
	}

}
