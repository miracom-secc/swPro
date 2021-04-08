package swPro.source;

import java.io.*;
import java.util.*;

/*

시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	256 MB	46923	9166	4718	23.498%

▶ 문제
어떤 N개의 수가 주어져 있다. 그런데 중간에 수의 변경이 빈번히 일어나고 그 중간에 어떤 부분의 합을 구하려 한다. 
만약에 1,2,3,4,5 라는 수가 있고, 3번째 수를 6으로 바꾸고 2번째부터 5번째까지 합을 구하라고 한다면 17을 출력하면 되는 것이다. 
그리고 그 상태에서 다섯 번째 수를 2로 바꾸고 3번째부터 5번째까지 합을 구하라고 한다면 12가 될 것이다.

▶ 입력
첫째 줄에 수의 개수 N(1 ≤ N ≤ 1,000,000)과 M(1 ≤ M ≤ 10,000), K(1 ≤ K ≤ 10,000) 가 주어진다. 
M은 수의 변경이 일어나는 횟수이고, K는 구간의 합을 구하는 횟수이다. 그리고 둘째 줄부터 N+1번째 줄까지 N개의 수가 주어진다. 
그리고 N+2번째 줄부터 N+M+K+1번째 줄까지 세 개의 정수 a, b, c가 주어지는데, a가 1인 경우 b(1 ≤ b ≤ N)번째 수를 c로 바꾸고 a가 2인 경우에는 b(1 ≤ b ≤ N)번째 수부터 c(b ≤ c ≤ N)번째 수까지의 합을 구하여 출력하면 된다.

입력으로 주어지는 모든 수는 -263보다 크거나 같고, 263-1보다 작거나 같은 정수이다.

▶ 출력
첫째 줄부터 K줄에 걸쳐 구한 구간의 합을 출력한다. 단, 정답은 -263보다 크거나 같고, 263-1보다 작거나 같은 정수이다.

*/

public class BJ_2042_Best {
	
	static int N, M, K;
	static long fwTree[], arr[];
	
    public static void main(String[] args) throws IOException {
        // StringTokenizer 이용
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        fwTree = new long[N+1];
        arr = new long[N+1];
        
        M += K;
        for (int i=1; i<=N; i++) {
        	arr[i] = Long.parseLong(br.readLine());
        	update(i, arr[i]);
        }
        
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());

            if (a == 1) {
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());
                
                long diff = c - arr[b];
                arr[b] = c;
                update(b, diff);
                
            } else {
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                long sum = sum(c) - sum(b-1);
                System.out.println(sum);
            }
        }
    }
    
	// 111,  110, 100 ..
	public static long sum(int pos) {
		// 인덱스가 1부터 시작한다고 생각하자.
		//++pos;
		
		long ret = 0;
		while(pos > 0) {
			ret += fwTree[pos];
			pos &= (pos - 1); // 오른쪽 끝 위치의 이진수에서 마지막 비트를 지움.
//			pos -= (pos & -pos);
		}
		
		return ret;
	}

	// 101, 110, 1000, 10000 ..
	public static void update(int pos, long val) {
		//++pos;
		while (pos < fwTree.length) { 
			fwTree[pos] += val;
			pos += (pos & -pos); // 맨 마지막 비트를 스스로에게 더해 준다.
		}
	}
}
