package swPro.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
문제 : 
기숙사에서 살고 있는 준규는 한 개의 멀티탭을 이용하고 있다. 준규는 키보드, 헤어드라이기, 핸드폰 충전기, 디지털 카메라 충전기 등 
여러 개의 전기용품을 사용하면서 어쩔 수 없이 각종 전기용품의 플러그를 뺐다 꽂았다 하는 불편함을 겪고 있다. 
그래서 준규는 자신의 생활 패턴을 분석하여, 자기가 사용하고 있는 전기용품의 사용순서를 알아내었고, 
이를 기반으로 플러그를 빼는 횟수를 최소화하는 방법을 고안하여 보다 쾌적한 생활환경을 만들려고 한다.
예를 들어 3 구(구멍이 세 개 달린) 멀티탭을 쓸 때, 전기용품의 사용 순서가 아래와 같이 주어진다면, 

1. 키보드
2. 헤어드라이기
3. 핸드폰 충전기
4. 디지털 카메라 충전기
5. 키보드
6. 헤어드라이기

키보드, 헤어드라이기, 핸드폰 충전기의 플러그를 순서대로 멀티탭에 꽂은 다음 
디지털 카메라 충전기 플러그를 꽂기 전에 핸드폰충전기 플러그를 빼는 것이 최적일 것이므로 플러그는 한 번만 빼면 된다. 

조건 : 
1. N개의 멀티탭 구멍개수
2. K개의 전기용품 총 사용횟수
3. 이미 꽂혀있는 전기용품은 안뽑을 수 있다.(이미 꽂혀있음 넘어간다.)
4. 다 쓴거부터 뽑는다.  

예제 입출력 :
첫 줄에는 멀티탭 구멍의 개수 N (1 ≤ N ≤ 100)과 전기 용품의 총 사용횟수 K (1 ≤ K ≤ 100)가 정수로 주어진다. 
두 번째 줄에는 전기용품의 이름이 K 이하의 자연수로 사용 순서대로 주어진다. 각 줄의 모든 정수 사이는 공백문자로 구분되어 있다. 

예제1)
2 7
2 3 2 3 1 2 7
: 2

예제2)
2 13
2 3 1 3 1 3 1 3 2 2 2 2 2
: 2

예제3)
2 15
3 2 1 2 1 2 1 2 1 3 3 3 3 3 3
: 2

예제4)
3 8
1 2 3 4 1 1 1 2
: 1

예제5)
1 3
1 2 1
: 2

예제6)
3 6
1 2 3 4 1 2
: 1

예제7)
6 7
1 1 1 1 1 1 2
: 0

예제8)
2 10
1 2 3 2 3 2 2 2 1 2
: 2

예제9)
5 20
1 2 3 4 1 1 1 3 3 2 5 7 20 1 3 4 2 1 9 19
: 4

예제10)
3 20
1 2 3 4 4 3 5 8 9 19 20 1 2 3 20 4 1 2 3 4
: 10

예제11)
3 10
2 3 1 4 2 3 2 4 1 4
: 2

 * */
public class BJ_1700_Best2 {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());	// 멀티탭 구멍개수
		int K = Integer.parseInt(st.nextToken());	// 전기용품 총 사용횟수

		int[] orderArr = new int[K+1]; 				// 전기용품 사용순서
		int[] useArr = new int[101]; 				// 각 전기용품 사용가능 횟수 {0, 1, 3, 2, 0, 0, 0, 1}
		int maxNum = 0; 							// 전기용품 최대 크기 확인 
		int cnt = 0; 								// 전기용품를  뽑은 횟수(결과값)
		
		st = new StringTokenizer(br.readLine());
		
		// 사용순서, 사용횟수, 최대크기를 세팅
		for(int i=1; i<=K; i++){
			int num = Integer.parseInt(st.nextToken());
			orderArr[i] = num;
			useArr[num] += 1;
			if(maxNum < num) maxNum = num;
		}		
		//System.out.println(Arrays.toString(order));
		//System.out.println(Arrays.toString(useCnt));
		
		int[] multitap = new int[maxNum+1]; // 각 전기용품의 멀티탭 사용유무 {0, 0, 0, 0, 0, 0, 0, 0}, 사용중:1, 사용안함:0
		int useCnt = 0; 					// 사용중인 전기용품 수

		// 순서별로 확인
		for(int i=1; i<=K; i++) {
			
			int num = orderArr[i]; // 현재 순서의 전기용품 번호
			
			/**
			 1. 사용중인 전기용품은 안꽂는다.
			 **/
			if(multitap[num] == 1) {
				useArr[num] -= 1;  // 사용가능 횟수 차감
				continue;
			}
			
			/**
			 2. 멀티탭 구멍이 남아있다면 꽂는다. 
			 **/
			if(useCnt < N) {
				multitap[num] = 1; // 멀티탭 사용중 표기
				useArr[num] -= 1;  // 사용가능 횟수 차감
				useCnt++;		   // 사용중인 전기용품 수 증가
				continue;
			}
			
			/**
			 3. 다 쓴 전기용품 확인하여 뽑고 꽂는다.
			 **/
			int outNum = 0;
			// 3-1. 반복문 이용 전기용품별  확인
			for(int j=1; j<=maxNum; j++) {
				// 3-2 전자제품이 사용중 & 남은 사용휫수 0 (다 쓴 전기용품  확인) 
				if(multitap[j] == 1 && useArr[j] == 0) {
					outNum = j;	// 다 쓴 전기용품 확인
					break;
				}
			}
			
			// 3-3. 다 쓴 전기용품 존재시 뽑고 다음 전기용품 꽂는다. 
			if(outNum > 0) {
				// 다 쓴걸 뽑는다.
				multitap[outNum] = 0; 	// 사용표기
				useCnt--;		  		// 사용중인 전기용품수 감소
				cnt++;				 	// 뽑은횟수 증가

				// 다음 전기용품을 꽂는다.
				multitap[num] = 1; 		// 사용표기
				useArr[num] -= 1;  		// 남은 사용횟수 차감
				useCnt++;		   		// 사용중인 전기용품수 증가
				continue;
			}
			
			/**
			 4. 기존 전기용품을 뽑는다.(다음 사용할 순서가 멀리 있는것부터 뽑는다.)
			 **/
			int lastNum = 0;		// 늦게 사용할 전기용품 
			int lastNumOrder = 0;	// 늦게 사용할 전기용품 순서
				
			// 4-1 반복문 이용 전기용품별  확인
			for(int j=1; j<=maxNum; j++) {
				// 4-2 사용중인 전기용품인지 확인
				if(multitap[j] == 1) {
					// 4-3 반복문 이용 전기용품별 사용순서 확인(가장 나중에 사용할 용품 확인)
					for(int y=i; y<=K; y++) {						
						// 4-4 사용중인 전기용품의 차례 확인
						if(j == orderArr[y]) {
							// 4-5 각 전기용품별 차례 비교해서 가장 늦게 쓰는 전기용품 확인
							if(lastNumOrder < y) {
								lastNum = j;	  // 이 전기용품을
								lastNumOrder = y; // 몇번째에 쓸 예정이다.
							}
							break;
						}	
					}
				}
			}
			
			// 4-6 뽑는다.
			multitap[lastNum] = 0; // 사용표기
			cnt++;		 // 뽑은횟수 증가
			useCnt--;		 // 사용중인 전기용품수 감소
			
			// 4-7 다음 전기용품을 꽂는다.
			multitap[num] = 1; // 사용표기
			useArr[num] -= 1;  // 남은 사용횟수 차감
			useCnt++;		   // 사용중인 전기용품수 증가
		}
		
		System.out.println(cnt);
	}

}
