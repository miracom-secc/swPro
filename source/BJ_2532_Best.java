package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class BJ_2532_Best{
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		int N = Integer.parseInt(st.nextToken());
		
		ArrayList<long[]> arr = new ArrayList<>();
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int num = Integer.parseInt(st.nextToken());
			long L = Long.parseLong(st.nextToken());
			long R = Long.parseLong(st.nextToken());
			
			arr.add(new long[] {L,R});
		}
		
		// L기준 오름차순 정렬, 같으면 R기준 내림차순
		Collections.sort(arr, new Comparator<long[]>() { 
			@Override
			public int compare(long[] o1, long[] o2) {
				// TODO Auto-generated method stub
				if(o1[0] == o2[0]) return Long.compare(o2[1], o1[1]);
				return Long.compare(o1[0], o2[0]);
			}
		});
		
		
		// 중복제거
		for(int i=1; i<arr.size(); i++) {
			long[] cmp1 = arr.get(i-1);
			long[] cmp2 = arr.get(i);
					
			if(cmp1[0] == cmp2[0] && cmp1[1] == cmp2[1]) {
				arr.remove(i--);
			}
		}
		
		ArrayList<Long> list = new ArrayList<>();
		list.add(arr.get(0)[1]);  
		
		int len = arr.size();
		for(int i=1; i<len; i++) {
			
			if(arr.get(i)[1]<=list.get(list.size()-1)) { // 완전 포함되는게 들어오면 마지막에 추가
				list.add(arr.get(i)[1]);
			}
			else {
				// 이분탐색으로 list에 넣어줘야함
				int left = 0;
				int right = list.size()-1;
				
				while(left<right) {
					int mid = (left+right)/2;
					
					if(arr.get(i)[1]<= list.get(mid) ) {
						left = mid+1;
					}
					else {
						right = mid;
					}
				}
				list.set(right, arr.get(i)[1]);
			}
		}
		System.out.println(list.size());
	}
}