package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.StringCharacterIterator;
import java.util.StringTokenizer;

public class BJ_1256_Best{
	
	static int skip;
	static int N, M, K, bino[][];
	static final int INF = 1000000000 + 100; //K의  최대값 + 100, 오버플로를 막기 위해 이보다 큰 값은 구하지 않는다.
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        bino = new int[201][201];
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        skip = K-1; //건너 뛸 갯수
        calcBino(); //이항계수 구하기
        
        if (bino[N+M][N] <= skip) System.out.println("-1");
        else generate(N, M, ""); //1번 풀이
        //else System.out.println(kth(N, M, skip)); //2번 풀이
	}
	
	//필요한 이항계수를 미리 계산해 둔다.
	static void calcBino() {
		for (int n=0; n<=200; n++) {
			bino[n][0] = bino[n][n] = 1;
			for (int r=1; r<n; r++)
				bino[n][r] = Math.min(INF, bino[n-1][r-1]+bino[n-1][r]);
		}
	}
	
	//n개의 a, m개의 z로 구성된 신호 중 skip개를 건너뛰고 만들어지는 신호를 반환한다.
	static String kth(int n, int m, int skip2) {
		//n == 0인 경우 나머지 부분은 전부 'z'일 수 밖에 없다
		if (n == 0) {
			String s = "";
			for(int i=0; i<m; i++)
				s += "z";

			return s;
		}
		
		if (skip2 < bino[n+m-1][n-1]) return "a" + kth(n-1, m, skip2);
		
		return "z" + kth(n, m-1, skip2 - bino[n+m-1][n-1]);
	}
	
	// n : 더 필요한 a의 갯수
	// m : 더 필요한 z의 갯수
	// s : 지금까지 만든 문자열
	static void generate(int n, int m, String s) {
		//기저사례 skip<0
		if (skip < 0) return;
		
		//기저사례 n==m==0
		if (n==0 && m==0) {
			// k-1개 건너 뛰었으면 출력
			if (skip == 0) System.out.println(s);
			
			--skip; //건너뛰기
			return;
		}
		
		if (bino[n+m][n] <= skip) {
			skip -= bino[n+m][n];
			return;
		}
		
		//사전 순으로 문자열 만들기
		if(n > 0) generate(n-1, m, s+"a"); 
		if(m > 0) generate(n, m-1, s+"z");
	}
}
