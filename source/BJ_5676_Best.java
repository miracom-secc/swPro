package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 음주 코딩
 * 1) 총 N개의 정수로 이루어진 수열 X1, X2, ... XN 입력
 * 2) 게임은 총 K번 라운드, 매 라운드마다 명령 수행
 *    2-1) 변경 : 수열의 특정한 값 변경
 *         덧,뺄셈의 경우 변경할 값과 기존의 값 간의 차이(diff)를 이용하여 트리를 내려가면서 해당 노드까지 값을 업데이트 하지만
 *         곱셈의 경우 중간값이 0인 경우 이러한 방법을 적용할 수 없어 해당 노드에서부터 올라오면서 값을 업데이트해준다.
 *    2-2) 곱셈 : 수열의 i~j범위의 값을 모두 곱한 결과가 양수/음수/0인지 출력
 *         연산과정에 영향을 끼치지 않기 위헤  덧,뺄셈은 0을 리턴하지만 곱셈의 경우 1을 리턴
 * 
 *  이슈 발생
 * 1) EOF 처리할 때 'br.readLine().trim() != null'로 처리하면 런타임 에러 (NullPointer) 발생
 *    --> trim()제거하고 'br.readLine() != null'로 처리하니 해결
 * 2) Overflow 발생
 *    - 자료형 long이면 충분하다고 생각했지만 overflow 발생 (큰 수의 테스트케이스에서 +,- 결과값이 서로 뒤바뀌는 경우 발견)
 *    - 곱셈 명령에서 i=1,j=10^5(1≤i≤j≤10^5),수열의 모든 값이 100(-100≤Xi≤100)일 경우 결과는 100^10^5(=100^100000)
 *    --> 입력받은 값을 저장하지 않고 부호만 저장(양수는 1, 음수는 -1, 0은 0)
 *    
 * # 테스트케이스 
 *   IN) http://maratona.ime.usp.br/hist/2012/resultados12/data/contest/I.in
 *   OUT) http://maratona.ime.usp.br/hist/2012/resultados12/data/contest/I.sol
 */

public class BJ_5676_Best { // 백존 5676
	static int[] arr, tree;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		while (true) { 
			String input = br.readLine();
			if( input == null) break;
			st = new StringTokenizer(input);

			int N = Integer.parseInt(st.nextToken()); // 수열의 크기
			int K = Integer.parseInt(st.nextToken()); // 게임 라운드수 

			arr = new int[N+1];
			tree = new int[N*4];
			Arrays.fill(tree, 1);

			st = new StringTokenizer(br.readLine().trim());
			for(int i=0; i<N; i++) {
				int num = Integer.parseInt(st.nextToken());
				// overflow를 방지하기 위해 부호만 저장
				arr[i] = num > 0 ? 1 : num < 0 ? -1 : 0;
			}

			init(0, N-1, 1);

			for(int i=0; i<K; i++) {
				st = new StringTokenizer(br.readLine().trim());
				// C(변경) i, V -> Xi를 V로 변경 (1 ≤ i ≤ N, -100 ≤ V ≤ 100)
				// P(곱셈) i, j -> Xi~Xj 구간의 곱 (1 ≤ i ≤ j ≤ N)
				char cmd = st.nextToken().charAt(0);
				int val1 = Integer.parseInt(st.nextToken());
				int val2 = Integer.parseInt(st.nextToken());

				if(cmd=='C') {
					val2 = val2 > 0 ? 1 : val2 < 0 ? -1 : 0;
					cmdC_update(0, N-1, 1, val1-1, val2); // start, end, node, position, value
					arr[val1-1] = val2;
				}
				else if(cmd == 'P') {
					int rst = cmdP_mulp(0, N-1, 1, val1-1, val2-1);
					sb.append(rst > 0 ? '+' : rst < 0 ? '-' : '0');
				}
			}
			sb.append("\n");
		}
		bw.write(sb.toString());
		bw.close();
		br.close();
	}

	static int init(int start, int end, int node ) {
		if(start == end) {
			return tree[node] = arr[start];
		}

		int mid = (start+end)/2;  

		return tree[node] = init(start, mid, node*2) * init(mid+1, end, node*2+1);
	}

	static int cmdC_update(int start, int end, int node, int idx, int newVal) {
		// 수정된 노드와 관련 없으면 그대로 리턴
		if(idx < start  || idx > end)  {
			return tree[node];
		}
		// 중간 노드가 0일 경우 곱셈 결과를 수정할 수 없기 때문에 리프노드부터 위로 업데이트
		if(start == end ) return tree[node] = newVal; 

		int mid = (start+end)/2;
		return tree[node] = cmdC_update(start, mid, node*2, idx, newVal) * cmdC_update(mid+1, end, node*2+1, idx, newVal);

	}

	static int cmdP_mulp(int start, int end, int node, int left, int right) {
		// 연산결과에 영향을 미치지 않기 위해 덧,뺄셈은  0을 리턴하지만 곱셈은 1을 리턴
		if(left>end || right < start) return 1; 

		if(left <= start && end <= right)
			return tree[node];

		int mid = (start+end)/2;
		return cmdP_mulp(start, mid, node*2, left, right) * cmdP_mulp(mid+1, end, node*2+1, left, right);
	}
}