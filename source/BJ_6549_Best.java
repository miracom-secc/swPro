package swPro.source;

import java.io.*;
import java.util.*;
 
/*

시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
1 초	256 MB	25440	6326	4062	25.456%

▶ 문제
히스토그램은 직사각형 여러 개가 아래쪽으로 정렬되어 있는 도형이다. 각 직사각형은 같은 너비를 가지고 있지만, 높이는 서로 다를 수도 있다. 
예를 들어, 왼쪽 그림은 높이가 2, 1, 4, 5, 1, 3, 3이고 너비가 1인 직사각형으로 이루어진 히스토그램이다.
히스토그램에서 가장 넓이가 큰 직사각형을 구하는 프로그램을 작성하시오.

▶ 입력
입력은 테스트 케이스 여러 개로 이루어져 있다. 각 테스트 케이스는 한 줄로 이루어져 있고, 직사각형의 수 n이 가장 처음으로 주어진다. (1 ≤ n ≤ 100,000) 그 다음 n개의 정수 h1, ..., hn (0 ≤ hi ≤ 1,000,000,000)가 주어진다. 
이 숫자들은 히스토그램에 있는 직사각형의 높이이며, 왼쪽부터 오른쪽까지 순서대로 주어진다. 모든 직사각형의 너비는 1이고, 입력의 마지막 줄에는 0이 하나 주어진다.

▶ 출력
각 테스트 케이스에 대해서, 히스토그램에서 가장 넓이가 큰 직사각형의 넓이를 출력한다.

*/

public class BJ_6549_Best {
	static int N;
	static long his[], sgtree[];
	static long maxArea = 0L;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        while(true) {
        	String readLine = br.readLine();
        	if ("0".equals(readLine)) break;
        	
        	StringTokenizer st = new StringTokenizer(readLine);
        	N = Integer.parseInt(st.nextToken());
        	
        	his = new long[N];
            sgtree = new long[N*4];
            sgtree[0] = Integer.MAX_VALUE;
            
            for (int i=0; i<N; i++) his[i] = Integer.parseInt(st.nextToken());
            
            // 구간의 최소 높이의 index를 보관하는 세그먼트 트리 생성
            maxArea = 0L;
            init(his, 0, N-1, 1);
            largestHis(0, N-1);
            
            bw.write(String.valueOf(maxArea));
            bw.newLine();
        }
        
        br.close();
        bw.close();
    }
    
    /* 세그먼트 트리 초기 생성 */
    static long init(long[] arr, long left, long right, long node) {
        // 시작과 끝이 같으면 배열 자신을 저장
    	if (left == right) return sgtree[(int) node] = left;
        	
    	long mid = (left + right) / 2;
    	long leftMinIdx = init(arr, left, mid, node * 2);
    	long rightMinIdx = init(arr, mid + 1, right, node * 2 + 1);
    	
        return sgtree[(int) node] = arr[(int) leftMinIdx] > arr[(int) rightMinIdx] ? rightMinIdx : leftMinIdx;
    }
    
    /* 세그먼트 트리 구간의 최소 index 질의연산*/
    static long query(long left, long right, long node, long nodeLeft, long nodeRight) {
        //두 구간이 겹치지 않으면 아주 작은 값을 반환한다. 무시됨.
    	if (right < nodeLeft || nodeRight < left) return -1L;
        
    	// sgtree가 표현하는 범위가 arr[left...right]에 완전히 포함되는 경우 , 이미 구간의 값을 담고 있는 노드 리턴
        if (left <= nodeLeft && nodeRight <= right)  return sgtree[(int) node];
        
        long mid = (nodeLeft + nodeRight) / 2;
        long leftMinIdx = query(left, right, node * 2, nodeLeft, mid);
        long rightMinIdx = query(left, right, node * 2 + 1, mid+1, nodeRight);
        
        if (leftMinIdx < 0) return rightMinIdx;
        if (rightMinIdx < 0) return leftMinIdx;
        
        return his[(int) leftMinIdx] > his[(int) rightMinIdx] ? rightMinIdx : leftMinIdx;
    }
    
    /* 부분을 나눠가면서 최대 넓이 찾기 */
    static void largestHis(long start, long end) {
    	//범위의 정당성 체크 (start가 end 보다 크면 안된다)
    	if (start > end) return;
    	
    	long size = end - start + 1;
    	long minIdx = query(start, end, 1, 0, N-1);
    	
    	// 구간의 최대 넓이 저장
    	maxArea = Math.max(maxArea, his[(int) minIdx] * size);
    	
    	largestHis(start, minIdx-1);
    	largestHis(minIdx+1, end);
    }
    
}

