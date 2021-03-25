package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
3 초	256 MB	10048	4547	2634	43.783%

▶ 문제
N(2 ≤ N ≤ 50,000)개의 정점으로 이루어진 트리가 주어진다. 트리의 각 정점은 1번부터 N번까지 번호가 매겨져 있으며, 루트는 1번이다.
두 노드의 쌍 M(1 ≤ M ≤ 10,000)개가 주어졌을 때, 두 노드의 가장 가까운 공통 조상이 몇 번인지 출력한다.

▶ 입력
첫째 줄에 노드의 개수 N이 주어지고, 다음 N-1개 줄에는 트리 상에서 연결된 두 정점이 주어진다. 
그 다음 줄에는 가장 가까운 공통 조상을 알고싶은 쌍의 개수 M이 주어지고, 다음 M개 줄에는 정점 쌍이 주어진다.

▶ 출력
M개의 줄에 차례대로 입력받은 두 정점의 가장 가까운 공통 조상을 출력한다.

*/

public class BJ_11437_Best {

	static int N, M, depths[], parent[][];
	static boolean[] visited;
	static ArrayList<Integer>[] adjList;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		visited = new boolean[N+1];
		depths = new int[N+1];
		parent = new int[N+1][21];
		adjList = new ArrayList[N+1];
		
		for(int a=1; a<=N; a++) adjList[a] = new ArrayList<Integer>();
		
		for (int n=1; n<N; n++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			//양방향
			adjList[start].add(end);
			adjList[end].add(start);
		}
		
		dfs(1,0);
		fillParent();
		
		M = Integer.parseInt(br.readLine());
		for (int m=0; m<M; m++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			System.out.println(lca(x,y));
		}
		
	}
	
	/* 전처리  1 */
	static void dfs(int here, int depth) {
	    visited[here] = true;
	    depths[here] = depth;
	    
	    for (int a=0; a<adjList[here].size(); a++) {
	    	int there = adjList[here].get(a);
	        if (visited[there]) continue;
	        
	        parent[there][0] = here; //뿌리에 달려있는 노드의 부모는 호출한 정점
	        dfs(there, depth + 1);
	    }
	}
	
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
