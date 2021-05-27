package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*
4 5
1 2
2 3
4 2
4 3
3 1
 */

public class BJ_14866_Best {
	
	static int N, M;
	static int counter, discovered[], component[], low[];
	static List<Integer> adj[];
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		discovered = new int[N+1];
		low = new int[N+1];
		component = new int[N+1];
		adj = new ArrayList[N+1];	
		
		for (int i=1; i<=N; i++) {
			adj[i] = new ArrayList<>();
			discovered[i] = -1;
			low[i] = Integer.MAX_VALUE;
		}
		
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		
		counter = 0;
		findCutEdge(1, 0);
		
//		System.out.println(Arrays.toString(low));
//		System.out.println(Arrays.toString(component));
		long ans = 0;
		for(int i=1;i <=N; i++){
			if(N - component[i] == M-adj[i].size()) ans+=i;
		}
		
		bw.write(ans+"");
		
		br.close();
		bw.close();

	}
	
	static void findCutEdge(int here, int parent) {
		discovered[here] = ++counter;
		//인접 정점 탐색
		for (int there : adj[here]) {
			if (there == parent) continue;
			
			//미발견 정점
			if (discovered[there] == -1) {
				findCutEdge(there, here);
				low[here] = Math.min(low[here], low[there]);
				//here점을 제거했을 때 component하나 추가(조상과 연결되어 있으면 컴포넌트가 나뉘지 않음)
				if(low[there] >= discovered[here]) component[here]++;
				
			} else {
				low[here] = Math.min(low[here],discovered[there]);
			}
		}
		
		if(here == 1) component[here]+=1;
		else component[here]+=2;
	}

}
