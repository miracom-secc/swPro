package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*

시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	128 MB	39615	12471	7657	29.115%

▶ 문제
초기에 {0}, {1}, {2}, ... {n} 이 각각 n+1개의 집합을 이루고 있다. 여기에 합집합 연산과, 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산을 수행하려고 한다.
집합을 표현하는 프로그램을 작성하시오.

▶ 입력
첫째 줄에 n(1 ≤ n ≤ 1,000,000), m(1 ≤ m ≤ 100,000)이 주어진다. m은 입력으로 주어지는 연산의 개수이다. 
다음 m개의 줄에는 각각의 연산이 주어진다. 합집합은 0 a b의 형태로 입력이 주어진다. 이는 a가 포함되어 있는 집합과, b가 포함되어 있는 집합을 합친다는 의미이다. 
두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산은 1 a b의 형태로 입력이 주어진다. 이는 a와 b가 같은 집합에 포함되어 있는지를 확인하는 연산이다. a와 b는 n 이하의 자연수 또는 0이며 같을 수도 있다.

▶ 출력
1로 시작하는 입력에 대해서 한 줄에 하나씩 YES/NO로 결과를 출력한다. (yes/no 를 출력해도 된다)

*/

public class BJ_1717_Best {

	static int[] parent; // 부모 배열
	
	public static void main(String[] args) throws Exception {
		
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        int N = Integer.parseInt(st.nextToken()); // 숫자
        int M = Integer.parseInt(st.nextToken()); // 연산갯수
 
        parent = new int[N+1]; 
        
        for (int i = 1; i <= N; i++) {
            parent[i] = i; 	// 처음 자기 자신으로 초기화
        }
        
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int chk = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
 
            if (chk == 0) {
                union(a, b);
            } else if (chk == 1) {
            	a = find(a);
                b = find(b);
                if(a == b){
                    bw.write("YES\n");
                } else {
                    bw.write("NO\n");
                }
            }
        }
 
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
 
   
    public static int find(int num) {
        if (num == parent[num]) {
            return num;
        } 
        return parent[num] = find(parent[num]);
    }
 
    public static void union(int a, int b) {
        a = find(a);
        b = find(b);
 
        if(a > b) {
            parent[b] = a;
        }else{
        	parent[a] = b;
        } 
    }
}
