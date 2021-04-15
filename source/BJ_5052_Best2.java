package swPro.source;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

/*

시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
1 초	256 MB	22132	6844	3948	29.447%

▶ 문제
전화번호 목록이 주어진다. 이때, 이 목록이 일관성이 있는지 없는지를 구하는 프로그램을 작성하시오.
전화번호 목록이 일관성을 유지하려면, 한 번호가 다른 번호의 접두어인 경우가 없어야 한다.
예를 들어, 전화번호 목록이 아래와 같은 경우를 생각해보자

긴급전화: 911
상근: 97 625 999
선영: 91 12 54 26
이 경우에 선영이에게 전화를 걸 수 있는 방법이 없다. 전화기를 들고 선영이 번호의 처음 세 자리를 누르는 순간 바로 긴급전화가 걸리기 때문이다. 따라서, 이 목록은 일관성이 없는 목록이다. 

▶ 입력
첫째 줄에 테스트 케이스의 개수 t가 주어진다. (1 ≤ t ≤ 50) 각 테스트 케이스의 첫째 줄에는 전화번호의 수 n이 주어진다. (1 ≤ n ≤ 10000) 다음 n개의 줄에는 목록에 포함되어 있는 전화번호가 하나씩 주어진다. 전화번호의 길이는 길어야 10자리이며, 목록에 있는 두 전화번호가 같은 경우는 없다.

▶ 출력
각 테스트 케이스에 대해서, 일관성 있는 목록인 경우에는 YES, 아닌 경우에는 NO를 출력한다.

 */

public class BJ_5052_Best2 {
	
	public static int T, N;
	public static TrieNode rootNode; 
	public static String phoneNumber[];

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		
		while(T-- > 0) {
			N = Integer.parseInt(br.readLine());
			rootNode = new TrieNode(); 
			phoneNumber = new String[N];
			
			int count = 0;
			for (int n=0; n<N; n++) {
				phoneNumber[n] = br.readLine();
				insert(phoneNumber[n]);
			}
			
			for (int n=0; n<N; n++) {
				//System.out.println(phoneNumber[n]);
				if (!contains(phoneNumber[n])) count++;
			}
			
			System.out.println(count > 0 ? "NO" : "YES");
		}
		
	}
	
	static void insert(String word) {
		TrieNode thisNode = rootNode;
		for (int i=0; i<word.length(); i++) {
			thisNode = thisNode.childNodes.computeIfAbsent(word.charAt(i), c -> new TrieNode());
		}
		thisNode.isLastChar = true;
	}
	
	static boolean contains(String word) {
		TrieNode thisNode = rootNode;
		for (int i=0; i<word.length(); i++) { 
			char character = word.charAt(i); 
			TrieNode node = thisNode.childNodes.get(character); 
			//if (node == null) return false; 
			if (node != null) {
				if (i != (word.length()-1) && node.isLastChar)
					return false;
			}
			
			thisNode = node; 
		} 
		
		return thisNode.isLastChar; 
	}

}

class TrieNode {
	HashMap <Character, TrieNode> childNodes = new HashMap<>();
	boolean isLastChar;
}
