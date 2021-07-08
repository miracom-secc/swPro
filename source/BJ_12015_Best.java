package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
6
10 20 10 30 20 50

6
10 20 1 2 3 2

5
5 1 4 2 3
 */

public class BJ_12015_Best {
	
	static int N;
	static List<Integer> list = new ArrayList();

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		list.add(0);
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			int temp = Integer.parseInt(st.nextToken());
			if (list.get(list.size()-1) < temp) {
				list.add(temp);
			} else if (list.get(list.size()-1) == temp) {
				continue;
			} else {
				bs(temp);
			}
			
//			System.out.println(list);
		}
		
		bw.write((list.size()-1)+"");
		
		br.close();
		bw.close();
	}
	
	static void bs(int num) {
		int s = 1;
		int e = list.size()-1;
		
		while(s < e) {
			int mid = (s + e) / 2;
			if (list.get(mid) >= num) {
				e = mid;
			} else if (list.get(mid) < num) {
				s = mid + 1;
			}
		}
		
//		System.out.println("num= "+num+" s= "+s+" e= "+e);
		
		list.set(s, num);
	}

}
