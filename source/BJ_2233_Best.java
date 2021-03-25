package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 사과나무
 * 1) 정점의 개수와 벌레이동경로를 입력받는다.
 * 2) 3개의 배열을 정의한다.
 * 	  depths  : 트리의 깊이(depth)를 저장한다.
 * 	  parents : 각각의 사과 위치로부터 부모 노드들(사과)의 정보를 저장한다.
 *    visits  : 벌레가 사과를 방문한 경로를 저장한다.
 * 3) 입력받은 벌레 이동 경로를 통해 트리를 생성하고, 벌레가 방문한 사과들을 visits배열에 저장해준다.   
			|	0 	|	0	|	0	|	1	|	0	|	1	|	1	|	0	|	1	|	1	|
 	----------------------------------------------------------------------------------------
  	prev	|	0	|	1	|	2	|	1	|	2	|	1	|	0	|	1	|	0	|	0	|
   	cur		|	1	|	2	|	3	|	2	|	4	|	2	|	1	|	5	|	1	|	0	|
 	apple	|	2	|	3	|	4	|	5	|	5	|	5	|	6	|	6	|	6	|	6	|
 	----------------------------------------------------------------------------------------
 	visit	|	1	|	2	|	3	|	3	|	4	|	4	|	2	|	5	|	5	|	1	|
 * 4) 썩은 사과 정보를 입력받아 visits배열에서 썩은 사과 번호를 알아낸다.
 * 5) 두 번호가 같지 않다면 최소 공통 조상을 찾아준다.
*/
public class BJ_2233_Best {  // 백준 2233
	static int N;
	static String visit;
	static int[] depths, visits;
	static int[][] parents;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		
		// 정점의 개수
		N = Integer.parseInt(st.nextToken());
		
		depths = new int[N+1];
		parents = new int[N+1][12];  // 모든 내부 정점(자식이 있는 정점)이 최소 두 개의 자식을 갖는 나무
		
		// 벌레 이동 경로
		visit = br.readLine().trim();
		visits = new int[visit.length()];
		
		/*********depth, parent 정의 START ********/
		int cur = 1;
		int apple = 2;
		depths[cur] = 1;
		visits[0] = 1;
		
		for(int i=1; i<visit.length(); i++) {
			if(visit.charAt(i) == '0' ) { // 새로운 노드 방문
				parents[apple][0] = cur;
				depths[apple] = depths[cur]+1;
				cur = apple++;
				visits[i] = cur;
			}
			else if(visit.charAt(i) == '1') { // 되돌아가기
				visits[i] = cur;
				cur = parents[cur][0];
			}
		}
		
		// 루트노드까지 조상정보 저장
		for(int j=1; j<12; j++ ) {
			for(int i=1; i<N+1; i++) {
				parents[i][j] = parents[parents[i][j-1]][j-1];
			}
		}
		/*********depth, parent 정의  END ********/
		
		
		// 썩은 사과 정보
		st = new StringTokenizer(br.readLine().trim());
		int X = visits[Integer.parseInt(st.nextToken())-1]; 
		int Y = visits[Integer.parseInt(st.nextToken())-1];
		
		int pos = X;
		
		if( X != Y )  {
			// 최소 공통 조상
			pos = LCA(X,Y);
		}
		
		for(int i=0; i<visit.length(); i++) {
			if(visits[i] == pos) {
				sb.append(i+1).append(" ");
			}
		}
		
		System.out.println(sb.toString().trim());
	}

	static int LCA(int vtx1, int vtx2) {
		if(depths[vtx1] > depths[vtx2]) { // 깊이가 더 깊은 노드를 vtx2로 설정
			int tmp = vtx1;
			vtx1 = vtx2;
			vtx2 = tmp;
		}
		
		for(int i=11; i>=0; i--) { // 높이를 맞추기
			int jump = 1 << i;
			
			if(depths[vtx2] - depths[vtx1] >= jump) {
				vtx2 = parents[vtx2][i];
			}
		}
		if(vtx1 == vtx2) return vtx1;
		
		for(int i=11; i>=0; i--) {
			if(parents[vtx1][i]==parents[vtx2][i]) continue;
			
			vtx1 = parents[vtx1][i];
			vtx2 = parents[vtx2][i];
		}
		
		return parents[vtx1][0];
	}
}
