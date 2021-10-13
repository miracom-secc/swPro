package ex;

import java.io.*;
import java.util.*;

public class P_핀볼_LCA {
	
	static class Node{
		int v;
		int dep;
		
		Node(int v, int dep) {
			this.v = v;
			this.dep = dep;
		}
	}
	
	static int n, q ;// 지점수, 게임수
	//static ArrayList<Node> arr[]; // 지점정보
	static ArrayList<Integer> arr[]; // 지점간 연결정보
	static long score[]; // 각 지점의 점수
	static long sum[];	 // 각 지점까지의 누적함
	static int depth[];  // 깊이
	static int parent[][]; // 부모정보 입력용
	static int max_depth; // 최대 깊이
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= null;

		int t = Integer.parseInt(br.readLine().trim());
		
		for(int tc =1; tc<=t ; tc++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken()); // 지점수
			q = Integer.parseInt(st.nextToken()); // 게임수
			
			arr = new ArrayList[n+1];
			score = new long[n+1];
			sum = new long[n+1];
			depth = new int[n+1];
			int tmp =1;
			while(tmp<n) {
				tmp <<= 1;
				max_depth++;
			}
			parent = new int[n+1][max_depth];
			
			for(int i=0; i<=n; i++) 
				arr[i] = new ArrayList<Integer>();
			
			
			/*************** 1. 입력 ***************/
			// 1-1) 각 지점의 점수
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++)
				score[i] = Long.parseLong(st.nextToken());
			
			// 1-2) 지점간 연결정보
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				int pp = Integer.parseInt(st.nextToken());
				
				if(pp == 0) // 0 일때는 도착점이니 패스
					continue;
				
				arr[pp].add(i);  // 상위 노드에 하위노드 붙이고,
				parent[i][0] = pp; //바로 위의 부모 정보 입력.
			}
			
			
			/********* 2. 누적점수 및 깊이 계산 *********/
			Queue<Node> qq = new LinkedList<>();
			qq.add(new Node(1,1)); // 탐색시작.(도작점, 깊이)
			
			while (!qq.isEmpty()){
                Node now = qq.poll();
                int now_depth = now.dep;
                int pp = parent[now.v][0]; // 바로위의 부모.
                
                depth[now.v] = now_depth;	// 해당 지점의 깊이.
                sum[now.v] = score[now.v]+ sum[pp]; // 누적 점수 저장
                
                for(int next : arr[now.v]){
                	qq.add(new Node(next, now_depth+1));                  
                }
            }
			
			
			/********* 3. 조상을 가지는 트리 생성 *********/
			//===> 기본소스: fillParent() 에 해당, 각 노드들의 조상정보를 저장		
		    for (int j = 1; j < max_depth; j++) { // depth 가 작은 것부터 순차적으로 채움 
		        for (int i = 1; i <= n; i++) {
		        	parent[i][j] = parent[parent[i][j - 1]][j - 1];
		        }
		    }
		    
		    /************* 4. 게임정보입력 *************/
		    long a_sum=0;
		    long b_sum=0;
		    for(int i = 0; i < q; i++) { 
		    	st = new StringTokenizer(br.readLine());
		    	int a = Integer.parseInt(st.nextToken());
		    	int b = Integer.parseInt(st.nextToken());
		    	// 4-1) 두 지점의 최소공통조상 찾기 
		    	int p_node = (depth[a]>depth[b]? findLCA(a,b):findLCA(b,a));
		    	
		    	// 4-2) 점수계산
		    	long temp1=0;
		    	long temp2=0;
		    	
		    	// a) a의 깊이가 더 작은 경우 
		    	if(depth[a]< depth[b]) {  
		    		temp1 = sum[a]; // a 는 도착점까지의 모든 점수를 다 획득
		    		temp2 = sum[b] - sum[p_node]; // b는 공통조상전까지의 점수만 획득
		    	} 
		    	// b) b의 깊이가 더 작은 경우  
		    	else if(depth[a]> depth[b]) {
		    		temp1 = sum[a] - sum[p_node]; // a는 공통조상전까지의 점수만 획득
		    		temp2 = sum[b]; // b는 도착점까지의 모든 점수를 다 획득
		    	}
		    	// c) 높이가 같은경우
		    	else{
		    		// 공통조상에서 튕기기 때문에 둘다 공통조상전까지의 점수만 획득
		    		temp1 = sum[a] - sum[p_node];
		    		temp2 = sum[b] - sum[p_node];
		    	}
		    	a_sum += temp1;
		    	b_sum += temp2;
		    
		    
		    }
		    
		    /*************** 5. 출력 ***************/
		    System.out.println("#"+tc+" "+ a_sum+" "+b_sum);	
		}
	}

	// 4-1) 두 지점의 최소공통조상 찾기 
	static int findLCA(int a, int b) { 
		//===> 기본소스: lca() 에 해당, 두 노드의 공통조상을 찾음
		//a) 같은 높이 만들기
		for(int j=max_depth-1; j>=0; j--) { // 최대 깊이부터 순자적으로 탐색하며 높이를 맞춰감
			if(depth[a] - depth[b] >= (1 << j))
				a = parent[a][j];
		}
		
		//b) 같은 부모를 찾은 경우 
		if(a == b)
			return a;
		
		//c) 공통부모찾기
		for(int j = max_depth-1; j>=0; j--) {
			
			if(parent[a][j] != parent[b][j]) {
				a = parent[a][j];
				b = parent[b][j];
			}				
		}				
		return parent[a][0];
	}


}
