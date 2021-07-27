package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 입력
 * 	N : 전체 용액의 수(3 ~ 5000)
 *  용액의 특성값 : -1,000,000,000 ~ 1,000,000,000
 * 
 * 출력
 * 첫째 줄에 특성값이 0에 가장 가까운 용액을 만들어내는 세 용액의 특성값을 출력
 * 출력해야하는 세 용액은 특성값의 오름차순으로 출력
 * 특성값이 0에 가까운 용액을 만들어내는 경우가 두 개 이상일 경우에는 그 중 아무거나 하나 출력
 * 
 * 
 * 풀이법
 * 하나의 용액을 지정하고, 나머지 용액에서 left, right를 지정하여 세용액의 합이 0에 가까운 경우를 출력
 * 세 용액의 합이 3,000,000,000 일수도 있으므로 long타입으로 풀이
 */

public class BJ_2473_Best {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		long[] solutions = new long[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < N; i++) {
			solutions[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(solutions);	//오름차순 정렬
		
		long[] res = new long[3];
		
		long diff = Long.MAX_VALUE;

		// 가장 왼쪽부터 시작해서 가장 오른쪽 끝까지
		for(int i = 0; i < N; i++) {
			int left = i+1; // 가장 왼쪽을 제외하고 그 다음부터 순회
			int right = N-1;
			
			while(left < right) {
				// 세 용액의 합 계산
				long sum = solutions[i] + solutions[left] + solutions[right];
				
				// 차이 계산
				long cur_diff = Math.abs(sum);
				
				// 차이가 더 작다면 원소 저장
				if(cur_diff < diff) {
					diff = cur_diff;
					res[0] = solutions[i];
					res[1] = solutions[left];
					res[2] = solutions[right];
				}
				
				if(sum > 0) { // 세용액의 합이 0보다 크면 right를 용액을 한 칸 앞으로
					right--;
				} else { // 세용액의 합이 0보다 작으면 왼쪽 용액을 한 칸 앞으로
					left++;
				}
			}
		}
		System.out.println(res[0] + " " + res[1] + " " + res[2]);
	}
}
