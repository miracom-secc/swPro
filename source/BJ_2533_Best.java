package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/*
 1. 자신이 얼리어답터 X : 친구 모두 얼리어답터
 2. 자신이 얼리어답터 O : 친구 상관X
 
 input[v][0] : 정점 v가 얼리 아답터가 아닌 경우, 친구들의 수의 합
 input[v][1] : 정점 v가 얼리 아답터인 경우, 각 자식들이 얼리 아답터이거나 아닌 때의 최소값들의 합
 */
public class BJ_2533_Best {
	public static int input[][];
	//public static LinkedList<Integer>[] list;
	public static List<Integer>[] list;
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		int N = Integer.parseInt(st.nextToken());
		list = new ArrayList[N+1];
		input= new int[N+1][2];
		
		// 정점 N개의 인접 리스트 생성 
		for (int i = 1; i <= N; i++) 
			list[i] = new ArrayList<Integer>();
		
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			list[a].add(b);
			list[b].add(a);
		}
		
		ea(1, -1); // 1부터 시작, 부모는 없는 상태 -1 

		//System.out.println(Math.min(input[1][0], input[1][1]));
		bw.write(String.valueOf(Math.min(input[1][0], input[1][1])));
		bw.flush();
		bw.close();
		br.close();
	}
	
	public static void ea(int v, int parent) {
		
		
		input[v][0] = 0; 
		input[v][1] = 1;
		
		for(int next : list[v]) {
			if(next != parent) {  // 부모노드가 같은 경우는 이미 확인을 한 경우 
				// parent에 현재 노드 값을 넣어, 다음 분기에서 판단 
				ea(next, v); 
				input[v][0] += input[next][1];	//부모가 포함X -> 자식 포함
				input[v][1] += Math.min(input[next][0], input[next][1]); //부모가 포함 -> 자식은 포함된 건,포함 안된 것 중에 작은 값
			}
		}
	}

}
