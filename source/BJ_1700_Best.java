package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
 
시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	128 MB	11090	2743	2087	25.845%

▶ 문제
기숙사에서 살고 있는 준규는 한 개의 멀티탭을 이용하고 있다. 준규는 키보드, 헤어드라이기, 핸드폰 충전기, 디지털 카메라 충전기 등 여러 개의 전기용품을 사용하면서 어쩔 수 없이 각종 전기용품의 플러그를 뺐다 꽂았다 하는 불편함을 겪고 있다. 그래서 준규는 자신의 생활 패턴을 분석하여, 자기가 사용하고 있는 전기용품의 사용순서를 알아내었고, 이를 기반으로 플러그를 빼는 횟수를 최소화하는 방법을 고안하여 보다 쾌적한 생활환경을 만들려고 한다.
예를 들어 3 구(구멍이 세 개 달린) 멀티탭을 쓸 때, 전기용품의 사용 순서가 아래와 같이 주어진다면, 
키보드
헤어드라이기
핸드폰 충전기
디지털 카메라 충전기
키보드
헤어드라이기
키보드, 헤어드라이기, 핸드폰 충전기의 플러그를 순서대로 멀티탭에 꽂은 다음 디지털 카메라 충전기 플러그를 꽂기 전에 핸드폰충전기 플러그를 빼는 것이 최적일 것이므로 플러그는 한 번만 빼면 된다. 

▶ 입력
첫 줄에는 멀티탭 구멍의 개수 N (1 ≤ N ≤ 100)과 전기 용품의 총 사용횟수 K (1 ≤ K ≤ 100)가 정수로 주어진다. 
두 번째 줄에는 전기용품의 이름이 K 이하의 자연수로 사용 순서대로 주어진다. 각 줄의 모든 정수 사이는 공백문자로 구분되어 있다. 

▶ 출력
하나씩 플러그를 빼는 최소의 횟수를 출력하시오.

 */

public class BJ_1700_Best {

	static int N, K;
	static int[] usethings, multitab;

	public static void main(String[] args) throws Exception {
		BJ_1700_Best t = new BJ_1700_Best();
		long startTime = System.currentTimeMillis();
		/* ========================================= */
		// 알고리즘을 해당 쿼리 안에 넣어 작성한 알고리즘의 성능을 계산할 수 있다.

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
	    StringTokenizer st = new StringTokenizer(br.readLine());
	    
    	N = Integer.parseInt(st.nextToken());
    	K = Integer.parseInt(st.nextToken());
    	
    	usethings = new int[101];
    	multitab = new int[101];
    	
    	st = new StringTokenizer(br.readLine());
    	for (int i=1; i<=K; i++) {
    		usethings[i] = Integer.parseInt(st.nextToken());
    	}
    	
    	/** 
    	 * 1. 멀티탭에 비어있으면 꽂기
    	 * 2. 꽂혀있는 제품이 다음에도 써야되면 냅두기
    	 * 3. 꽉 차있을 경우 가장 나중에 쓸 제품 뽑기
    	 */
    	int unplugCnt = 0; //플러그 빼는 횟수
    	boolean fullPlug = false;
    	for (int k=1; k<=K; k++) {
    		for (int n=1; n<=N; n++) {
    			// 1. 멀티탭에 비어있으면 꽂기
    			if (multitab[n] == 0) {
    				multitab[n] = usethings[k];
    				fullPlug = false;
    				break;
    			}
    			
    			// 2. 이미 꽂혀있는 제품이면 냅두기
    			if (multitab[n] == usethings[k]) {
    				fullPlug = false;
    				break;
    			}
    			
    			fullPlug = true;
    		}
    		
    		// 3. 꽉 차있을 경우 가장 나중에 쓸 제품 뽑기
    		if (fullPlug) {
    			int lastOrder = 0; //가장 마지막에 쓰는 제품
    	    	int unplugIdx = 0;
    	    	
    			for (int n=1; n<=N; n++) {
    				boolean use = false;
    				for (int a=k+1; a<=K; a++) {
    					
    					// 가장 마지막에 사용되는 제품 순번 기록
    					if (multitab[n] == usethings[a]) {
    						if (lastOrder < a) {
    							lastOrder = a;
    							unplugIdx = n;
    						}
    						
    						use = true;
    						break;
    					}
    				}
    				
    				// 앞으로 사용안하는 제품이면 뽑기
    				if (!use) {
    					unplugIdx = n;
    					break;
    				}
    			}
    			
    			multitab[unplugIdx] = usethings[k];
    			unplugCnt++;
    		}
    	}
    	
	    System.out.println(unplugCnt);
		
		/* ========================================= */
		long endTime = System.currentTimeMillis();
		//System.out.println("수행속도 : "+(endTime - startTime));
	    
	}
}
