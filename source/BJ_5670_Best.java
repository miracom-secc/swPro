package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
 

/*
 * 입력
 * 	N : 사전에 속한 단어수(1 ≤ N ≤ 105)
 *      단어는 영어 소문자로만 이루어진 1~80글자
 * 
 * 조건
 *  1. 첫번째 글자는 무조건 사용자가 버튼 입력
 *  2. 길이가 1 이상인 문자열 c1c2...cn이 지금까지 입력되었을 때,
 *     사전 안의 모든 c1c2...cn으로 시작하는 단어가 c1c2...cnc로도 시작하는 글자 c가 존재한다면 모듈은 사용자의 버튼 입력 없이도 자동으로 c를 입력
 *     ex) "hello", "hell", "heaven", "goodbye"
 *        -> 사용자가 첫글자 h를 입력하면 사전에서 h로 시작하는 단어의 다음 글자는 모두 e 이므로 e는 자동 입력
 * 
 */


public class BJ_5670_Best {
	
	/* 트라이 노드 표현 */
    static class TrieNode{
        HashMap<Character, TrieNode> childNodes = new HashMap<>();
        boolean last;
    }
    
    /* 트라이 구성 객체 */
    static class Trie{
        TrieNode rootNode;
        Trie(){
            rootNode=new TrieNode();
        }
 
        /* 노드에 단어 입력 */
        void insert(String word) {
            TrieNode currentNode = rootNode;
            
            for(int i=0; i<word.length(); i++) {
            	currentNode = currentNode.childNodes.computeIfAbsent(word.charAt(i), c-> new TrieNode());
            }
            currentNode.last = true;
        }
 
        /*버튼 클릭 횟수 체크 */
        double buttonClinkCount(String word) {
            TrieNode currentNode = rootNode.childNodes.get(word.charAt(0));
            int answer = 1;	//첫글자는 무조건 입력하므로 버튼클릭 1회
            
            for (int i = 1; i < word.length(); i++) {            	
                if (currentNode.childNodes.size() != 1||currentNode.last) {
                	// 자식노드가 1개 이상이거나(다음글자가 다른글자가 있는것이므로 자동완성이 안됨) 마지막글자이면 직접 입력
                    answer++;
                }
                currentNode = currentNode.childNodes.get(word.charAt(i));
            }
            return answer;
        }
    }
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input="";
        while ((input = br.readLine()) != null) {
            int N = Integer.parseInt(input);	//사전에 속한 단어 수
            Trie trie = new Trie();
            String[] words = new String[N];
            double answer = 0;
 
            for (int i = 0; i < N; i++) {
                words[i] = br.readLine();
                trie.insert(words[i]);	//입력 단어 trie노드 입력
            }
 
            for (int i = 0; i < N; i++) {
                answer += trie.buttonClinkCount(words[i]);
            }
 
            System.out.println(String.format("%.2f", answer / N));
        }
    }
}