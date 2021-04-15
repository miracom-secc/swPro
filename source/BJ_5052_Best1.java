package swPro.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_5052_Best1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int i = 0; i < T; i++) {
			int n = Integer.parseInt(br.readLine());
			Trie trie = new Trie();
			for (int j = 0; j < n; j++) {
				String word = br.readLine();
				trie.insert(word);
			}
			if (Trie.isConsistency)
				System.out.println("YES");
			else
				System.out.println("NO");
			Trie.isConsistency = true;
		}
	}

}

// 트라이
class Trie { 
 
	static boolean isConsistency = true;
	boolean isWord = false;
	boolean existChild = false;

	// 디폴트 속성
	Trie[] childTrie = new Trie[10];

	void insert(String word) {
		int len = word.length();
		Trie nowTrie = this;
		
		for (int i = 0; i < len; i++) {
			int nextNum = word.charAt(i) - '0';
			if (nowTrie.childTrie[nextNum] == null) {
				nowTrie.childTrie[nextNum] = new Trie();
			}

			nowTrie = nowTrie.childTrie[nextNum];

			// 자식 그래프로 이동
			if (i == len - 1) {
				nowTrie.isWord = true;
			} else {
				nowTrie.existChild = true;
			}

			if (nowTrie.isWord && nowTrie.existChild)
				isConsistency = false;
		}
	}
}