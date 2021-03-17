package swPro.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*

시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
1 초	128 MB	5539	2481	2203	45.357%

▶ 문제
학교에서 그래픽스 수업을 들은 동준이는 수업시간에 들은 내용을 바탕으로 스마트폰 게임을 만들었다. 게임에는 총 N개의 레벨이 있고, 각 레벨을 클리어할 때 마다 점수가 주어진다. 
플레이어의 점수는 레벨을 클리어하면서 얻은 점수의 합으로, 이 점수를 바탕으로 온라인 순위를 매긴다. 동준이는 레벨을 난이도 순으로 배치했다. 
하지만, 실수로 쉬운 레벨이 어려운 레벨보다 점수를 많이 받는 경우를 만들었다.이 문제를 해결하기 위해 동준이는 특정 레벨의 점수를 감소시키려고 한다. 
이렇게해서 각 레벨을 클리어할 때 주는 점수가 증가하게 만들려고 한다.각 레벨을 클리어할 때 얻는 점수가 주어졌을 때, 몇 번 감소시키면 되는지 구하는 프로그램을 작성하시오. 
점수는 항상 양수이어야 하고, 1만큼 감소시키는 것이 1번이다. 항상 답이 존재하는 경우만 주어진다. 정답이 여러 가지인 경우에는 점수를 내리는 것을 최소한으로 하는 방법을 찾아야 한다.

▶ 입력
첫째 줄에 레벨의 수 N이 주어진다. (1 ≤ N ≤ 100) 다음 N개 줄에는 각 레벨을 클리어하면 얻는 점수가 첫 번째 레벨부터 마지막 레벨까지 순서대로 주어진다. 
점수는 20,000보다 작은 양의 정수이다.

▶ 출력
첫째 줄에 점수를 몇 번 감소시키면 되는지 출력한다.

 */

public class BJ_2847_Best {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());	//레벨 수 입력
		
		int Level[] = new int[n];		// 각 레벨 점수 저장을 위한 배열
		
		for(int i=0 ; i<n ; i++) {
			Level[i] = Integer.parseInt(br.readLine());	// 각 레벨 점수 저장
		}
		
		int count = 0;	// 점수 감소시키는 수 카운팅
		
		for(int i=n-1 ; i>0 ; i--) {
			if(Level[i] <= Level[i-1]) {		//전 레벨 점수가 현재 레벨보다 점수가 큰 경우
				count += Level[i-1]-Level[i]+1;	//전 레벨 점수가 현재 레벨 점수보다 작아지는 시점만큼 카운팅
				Level[i-1] = Level[i]-1;		//전 레벨 점수를 현재레벨 점수보다 1작게 저장
			}
		}
		
		System.out.println(count);

	}

}