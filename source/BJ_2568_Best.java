package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

/* 이분탐색 풀이 
- 메모리 : 53564KB
- 시간 : 696ms

* 변수정의
- wire[N][2] : A전봇대와 B전봇대를 연결한 전선의 정보를 저장
 			   wire[n][0] : n번째 전선이 A전봇대와 연결되는 위치의 번호
 			   wire[n][1] : n번째 전선이 B전봇대와 연결되는 위치의 번호
- ArrayList<int[]> list : LIS 정보가 저장될 리스트 (= LIS의 최대길이는 list의 크기를 의미)
- checked[N] : LIS에서의 인덱스를 저장하는 배열, 제거할 전선을 찾아내는데 사용 
- Stack<Integer> remove : 제거해야하는 전깃줄의 A전봇대 번호를 순차적으로 출력하기 위한 자료구조

* 풀이방법
1) wire에 전선의 정보를 입력받는다.
2) A전봇대의 번호를 기준으로 오름차순 정렬한다.
3) list에 예외처리(Null Exception 발생방지)를 위해 더미값(0)을 넣어준다.
4) B전봇대의 번호를 비교하며 LIS를 구성 & checked배열의 각 전선에 LIS에서의 인덱스를 저장
  4-1) B전봇대의 번호가 list의 마지막 요소보다 크면 list의 마지막에 추가 / checked[i] = list.size()-1 (더미값(0))
  4-2) B전봇대의 번호가 list의 마지막 요소보다 작으면 list(=LIS)에서 해당값이 들어갈 위치를 찾아 바꿔준다. / checked[i] = idx (해당값이 들어갈 위치)
  		-> list(=LIS)에서 해당값이 들어갈 위치는 이분탐색으로 검색한다(list는 오름차순으로 정렬되어 있는 상태)
5) 제거해야할 전선의 개수 출력 (list의 크기는 연결할 수 있는 전선의 최대개수이므로 N - (list.size()-1)로 계산. -1은 더미값(0))
6) checked배열의 마지막부터 탐색하여 LIS의 인덱스값이 순차적이지 않으면 Stack에 제거대상으로 분류     
7) Stack에서 하나씩 빼낸 값을 출력 
 
* 테스트케이스
1) INPUT : N=8, (3,30), (6,10), (1,10), (4,5), (5,20), (8,40), (7,30), (2,20) 
2) A기준 정렬 : (1,10), (2,20), (3,30), (4,5), (5,20), (6,10), (7,30), (8,40)
3) list : {0}
4) LIS 구성
------------------------------------------------
i=0 (10)  list | {0, 10} 
		checked| 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
------------------------------------------------		
i=1 (20)  list | {0, 10, 20} 
		checked| 1 | 2 | 0 | 0 | 0 | 0 | 0 | 0 |
------------------------------------------------
i=2 (30)  list | {0, 10, 20, 30} 
		checked| 1 | 2 | 3 | 0 | 0 | 0 | 0 | 0 |
------------------------------------------------
i=3 ( 5)  list | {0, 5, 20, 30} 
		checked| 1 | 2 | 3 | 1 | 0 | 0 | 0 | 0 |
------------------------------------------------
i=4 (20)  list | {0, 5, 20, 30} 
		checked| 1 | 2 | 3 | 1 | 2 | 0 | 0 | 0 |
------------------------------------------------
i=5 (10)  list | {0, 5, 10, 30} 
		checked| 1 | 2 | 3 | 1 | 2 | 2 | 0 | 0 |
------------------------------------------------
i=6 (30)  list | {0, 5, 10, 30} 
		checked| 1 | 2 | 3 | 1 | 2 | 2 | 3 | 0 |
------------------------------------------------
i=7 (40)  list | {0, 5, 10, 30, 40} 
		checked| 1 | 2 | 3 | 1 | 2 | 2 | 3 | 4 |
------------------------------------------------
5) 제거해야할 전선의 개수 : N - (list.size()-1) = 8-4 = 4
6) checked배열의 마지막부터 탐색하여
7) index = list.size()-1, checked을 뒤에서부터 탐색
	checked| 1 | 2 | 3 | 1 | 2 | 2 | 3 | 4 | 
	                                 	 O   index--, index:3, stack : {}
	                              	 O       index--, index:2, stack : {}
	                             O           index--, index:1, stack : {}
	                         X								   stack : {4} 
	                     O                   index--, index:0
	                 X										   stack : {4, 2} 
	             X											   stack : {4, 2, 1} 
	         X												   stack : {4, 2, 1, 0} 
8) stack.pop : wire[0][0]=>wire[1][0]=>wire[2][0]=>wire[4][0] 

	
* INPUT 
8
3 30
6 10
1 10
4 5
5 20
8 40
7 30
2 20

* OUTPUT
4
1
3
5
	           
*/
public class BJ_2568_Best{ // 2568 전깃줄2
	public static void main(String[] args) throws Exception{
		BufferedReader br = new  BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= null;
		StringBuilder sb= new StringBuilder();
		
		int N = Integer.parseInt(br.readLine().trim());
		
		// 1)
		int[][] wire = new int[N][2];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			wire[i][0] = Integer.parseInt(st.nextToken());
			wire[i][1] = Integer.parseInt(st.nextToken());
		}

		// 2)
		Arrays.sort(wire, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1[0], o2[0]);
			}
		});
		
		// 3)
		ArrayList<int[]> list = new ArrayList<>();
		list.add(new int[] {0,0});
		
		int[] checked = new int[N];
		
		// 4)
		for(int i=0; i<N; i++) {
			if(list.get(list.size()-1)[1] < wire[i][1]) { // 4-1)
				list.add(wire[i]);
				checked[i] = list.size()-1;
			}
			else { // 4-2)
				int left = 1;
				int right = list.size()-1;
				
				while(left<right) {
					int mid = (left+right)/2;
					
					if(wire[i][1] > list.get(mid)[1]) {
						left = mid+1;
					}
					else {
						right = mid;
					}
				}
				checked[i] = right;
				list.set(right, wire[i]);
			}
		}
		
		int idx = list.size()-1;
		Stack<Integer> remove = new Stack<>();
		
		// 5)
		sb.append(N - (list.size()-1)).append("\n");
		
		// 6)
		for(int i=N-1; i>=0; i--) {
			if(checked[i] == idx) idx--;
			else remove.push(wire[i][0]); // A 출력
		}
		
		// 7)
		while(!remove.isEmpty()) {
			sb.append(remove.pop()).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}

