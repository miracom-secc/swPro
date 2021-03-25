package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_2233_Best2 {

	static int N, M, apples[], depths[], parent[][];
	
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		apples = new int[N*2+1];
		depths = new int[N+1];
		parent = new int[N+1][21];
		
		String node = br.readLine();
		int apple = 0, depth = 0, start = 0, end = 0;
		for (int n=1; n<=N*2; n++) {
			char move = node.charAt(n-1);
			
			if (move == '0') {
				start = end;
				apples[n] = ++apple; //새로 찾은 사과
				depths[apples[n]] = ++depth; //내려가니 depth는 깊게
				parent[apples[n]][0] = start; //부모노드는 바로 전 사과나무값
				end = apples[n];
			} else if (move == '1') {
				apples[n] = end; //
				end = parent[end][0]; //올라가는 지금은 부모노드
				--depth;
			}
		}
		
		//dfs(1,0);
		fillParent(); // 부모찾기
		
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int cut = lca(apples[x], apples[y]); //공통 가지 찾기
		
		for (int i=1; i<=(N*2); i++) {
			if (apples[i] == cut)
				System.out.print(i+" ");
		}
		
	}
	
	/* 전처리  1 */
//	static void dfs(int here, int depth) {
//	    visited[here] = true;
//	    depths[here] = depth;
//	    
//	    for (int a=0; a<adjList[here].size(); a++) {
//	    	int there = adjList[here].get(a);
//	        if (visited[there]) continue;
//	        
//	        parent[there][0] = here; //뿌리에 달려있는 노드의 부모는 호출한 정점
//	        dfs(there, depth + 1);
//	    }
//	}
	
	/* 전처리  2 */
	static void fillParent() {
	    for (int j = 1; j < 21; j++) {
	        for (int i = 1; i <= N; i++) {
	        	// 2^j 노드 = 2^(J-1)노드의 부모(j-1)
	        	parent[i][j] = parent[parent[i][j - 1]][j - 1];
	        }
	    }
	}

	/* LCA */
	static int lca(int x, int y) {
	    // y를 더 깊은 depth에 있는 값으로 고정
		if (depths[x] > depths[y]) {
	    	// y를 더 큰값으로 치환
	    	int temp = y;
	    	y = x;
	    	x = temp;
	    }
	    
	    // 같은 depth에서 시작하기 위해 같은 depth로 올려준다
	    for (int i = 20; i >= 0; i--) {
	        // (1 << i) => 비트마스크 : 1을 왼쪽으로 쉬프트 2의 N승 표현
	    	if (depths[y] - depths[x] >= (1 << i)) 
	            y = parent[y][i];
	    }
	    
	    // 부모가 같을 경우 x가부 부모
	    if (x == y) return x;
	    
	    //공통부모 찾기
	    for (int i = 20; i >= 0; i--) {
	        if (parent[x][i] != parent[y][i]) {
	            x = parent[x][i];
	            y = parent[y][i];
	        }
	    }
	    
	    return parent[x][0];
	}

}
