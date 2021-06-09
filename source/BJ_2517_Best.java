package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
8
2
8
10
7
1
9
4
15
 */

public class BJ_2517_Best {
	
	static int N, ans[];
	static int leafNode, tree[];
	static List<Player> rank;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());

		init();
		tree = new int[leafNode*2+1];
		ans = new int[N+1];
		rank = new ArrayList<Player>();
		
		for (int i=1; i<=N; i++) {
			int power = Integer.parseInt(br.readLine());
			rank.add(new Player(i, power));
		}
		
		//========== 능력치 내림차순으로 정렬
		Collections.sort(rank);
	    
		//========== 능력치 높은 순으로 순위 매기기
		for (Player player : rank) {
			ans[player.idx] = count(1, player.idx-1);
			update(player.idx);
		}
		
		for (int i=1; i<=N; i++) {
			bw.write(ans[i]+"\n");
		}
		
		br.close();
		bw.close();
	}
	
	static void init() {
		leafNode = 1;
		while(N > leafNode) {
			leafNode = leafNode << 1 ;
		}
	}
	
	static void update(int index) {
		index += leafNode - 1;
		tree[index] = 1;
		
		while(index > 1) {
			index = index  >> 1;
			tree[index] += 1;
		}
	}
	
	static int count(int s, int e) {
		s += leafNode - 1;
		e += leafNode - 1;
		
		int sum = 0;
		while(s <= e) {
			if(s%2 == 1) sum += tree[s];
			if (e%2 == 0) sum += tree[e];
			
			s = (s+1) >> 1;
			e = (e-1) >> 1;
		}
		
		return sum+1 ;
	}

}

class Player implements Comparable<Player>{
	int idx;
	int power;
	
	Player(int idx, int power) {
		this.idx = idx;
		this.power = power;
	}
	@Override
	public int compareTo(Player right) {
		if (power < right.power) return 1;
		if (power > right.power) return -1;
		return 0;
	}
	
	public String toString() {
		return "idx : "+idx+" power : "+power;
	}
	
}
