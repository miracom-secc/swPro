 package union;

import java.io.*;
import java.util.*;

public class P_악성코드 {

	// 감염은  k대, 치료는 3대만 가능.
	// 장비치료가 끝났을때 감염되지 않은 장비의 최대수를 구하는 문제
	// 1. 입력
	//		1) 간선정보 하나씩 입력 받으면서, union-find 수행
	//		2) 감염 노드 정보 입력받으면서 해당 부모에 ++ 해주기
	// 2. 모든 입력이 끝나고 난뒤 그룹의 크기순으로 정렬
	// 3. 해당그룹에 바이러스에 감염된 노드수를 기준으로 치료되는 최대노드수를 구함  
	// 		경우의수 
	//		0) 감염노드수 > 3	: X  -
	//		1) 한그룹에서 감염노드수가 3	: child가 최대인 것
	//		2) 한그룹 감염노드 2+1 		: child가 최대인 것
	//		3) 감염노드 1+1+1			: child가 최대인 것 세개
	// 1~3) 의 경우의 최대값을 구함
	// 4. avail_cnt = 1~3의 최대값 + 한대도 감염안된 그룹의 노드수
	// 5. avail_cnt 출력
	
	static class Node implements Comparable<Node> {
		int idx;   // 현재 인덱스
		int t_cnt; // 전체 자식수
		int v_cnt; // 감염된 자식수

		Node(int idx, int t_cnt, int v_cnt){
			this.idx = idx;
			this.t_cnt = t_cnt;
			this.v_cnt = v_cnt;
		}		
			@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return Integer.compare(o.t_cnt, this.t_cnt);
		}	
	}
	
	
	static int n, m, k;
	static int parent[]; //parent 부모배열
	static Node child[]; //전체자식수, 감염된 자식수	
	static int INF = (int) (Math.pow(10, 6)+1);
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		int d1=1;
		int t = Integer.parseInt(br.readLine().trim());
		
		for(int tc=1; tc<=t ; tc++) {
		//	System.out.println("INF:"+ INF);
			// 0. 초기화
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			
			parent = new int[n+1];
			child = new Node[n+1];
			
			for(int i=1; i<=n; i++) {
				parent[i] = i;
				child[i] = new Node(i, 1, 0);//인덱스, 해당 노드수는 자기자신 1, 감염수 0
			}
			child[0] = new Node(0,10000001,0);
			// 1-1). 간선 정보 하나씩 입력 받으면서, union-find 수행
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				union(a,b);// 부모정보
			}
			
			// 1-2) 감염된 노드의 부모노드에 ++ 해주기
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<k; i++) {
				int temp = Integer.parseInt(st.nextToken());
				// 감염된 노드의 부모노드찾아서 ++ 해주기
				int p_num = find(temp); 
				child[p_num].v_cnt +=1;
			}
			d1=1;
			Arrays.sort(child); // 크기순으로 소트하기
			//d1=1;
			long ans=0; // 전체 답
			long z_cnt=0; // 감염 바이러스가 한개도 없는 경우++
			long max_cnt3 = 0; 	// 3개 짜리 맥스값 구하기
			long max_cnt2 = 0; 	// 2개 짜리
			long max_cnt1[] = new long[3];	// 1개 짜리
			int cnt1_idx=0;
			
			//n번1: 3개짜기 최대값, 2개짜리 최대값, 1개짜리 최대값, 0개인거 더해해주기
			for(int i=1; i<=n; i++){
				
				// 3개짜리중 최대
				if(child[i].v_cnt ==3) 
					max_cnt3 = Math.max(child[i].t_cnt, max_cnt3);
				// 2개짜리중 최대
				if(child[i].v_cnt ==2) 
					max_cnt2 = Math.max(child[i].t_cnt, max_cnt2);
				
				// 1개짜리
				if(cnt1_idx<3 && child[i].v_cnt ==1) {
					max_cnt1[cnt1_idx] = child[i].t_cnt;
					cnt1_idx++;
				} 
				// 바이러스 감염안된 노드들끼리 뭉친것
				if(child[i].v_cnt ==0 &&  child[i].t_cnt!= 0) {
					z_cnt+= child[i].t_cnt;
				}
				
			}
			ans = Math.max(max_cnt3, max_cnt2+max_cnt1[0]);
			ans = Math.max(ans, max_cnt1[0]+max_cnt1[1]+max_cnt1[2]);
			
			ans+= z_cnt;
			System.out.println("#"+tc+" "+ans);								
		}
	}
	
	
	static void union(int a, int b) {
		a= find(a);
		b= find(b);
		if(a>b) {
			parent[a] = b;
			child[b].t_cnt += child[a].t_cnt; // 자식의 자식수를 부모에 더해주기
			child[a].t_cnt = 0;		
		}
		else if(a<b){
			parent[b] = a;
			child[a].t_cnt += child[b].t_cnt; // 자식의 자식수를 부모에 더해주기
			child[b].t_cnt = 0;				
		}
	}
	
	static int find(int a) {
		if(parent[a] == a) return a;
		return parent[a] = find(parent[a]);
	}

}
