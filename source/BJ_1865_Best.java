package swPro.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1865_Best {
	static final int INF = 1000000000;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(st.nextToken());

		for(int t=0; t<TC; t++) {
			st = new StringTokenizer(br.readLine().trim());
			int N = Integer.parseInt(st.nextToken());	//지점의 수
			int M = Integer.parseInt(st.nextToken());	//도로의 개수
			int W = Integer.parseInt(st.nextToken());	//웜홀의 개수

			ArrayList<Edge> edgeList = new ArrayList<>();
			/* 양방향 양수 가중치 */
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine().trim());
				int S = Integer.parseInt(st.nextToken());	//S,E 연결된 지점
				int E = Integer.parseInt(st.nextToken());
				int T = Integer.parseInt(st.nextToken());	//걸리는 시간
				edgeList.add(new Edge(S, E, T));
				edgeList.add(new Edge(E, S, T));
			}
			
			/* 단방향 음수 가중치 */
			for (int i = 0; i < W; i++) {
				st = new StringTokenizer(br.readLine());
				int S = Integer.parseInt(st.nextToken());	//시작지점
				int E = Integer.parseInt(st.nextToken());	//도착지점
				int T = Integer.parseInt(st.nextToken());	//줄어드는 시간
				edgeList.add(new Edge(S, E, (-1) * T));
			}

			int[] nodes = new int[N+1];
			Arrays.fill(nodes, INF);
			nodes[1] = 0;
			boolean isUpdated = false;

			// N-1 Edge Relaxation(N-1번 수행했을때가 최단 경로)
			for(int i = 1; i <= N; i++) {
				isUpdated = false;
				for(Edge edge : edgeList) {
					if(nodes[edge.end] > nodes[edge.start] + edge.time) {
						nodes[edge.end] = nodes[edge.start] + edge.time;
						isUpdated = true;
						// N번 수행시에도 갱신되는 값이 있으면 사이클 존재
						if (i == N) {
							isUpdated = true;
						}
					}
				}
				if(!isUpdated) break;
			}
			sb.append((isUpdated ? "YES" : "NO") + "\n");
		}
		System.out.println(sb);
	}
}

class Edge {
	int start, end, time;

	Edge(int start, int end, int time) {
		this.start = start;
		this.end = end;
		this.time = time;
	}
}