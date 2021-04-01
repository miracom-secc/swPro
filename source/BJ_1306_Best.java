package swPro.source;

import java.io.*;
import java.util.*;
 
/*

시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	256 MB	1056	455	350	44.473%

▶ 문제
홍준이는 러너이다. 그런데 어쩌다 보니 아무리 뛰어도 뛰어도 속도가 변하지 않는다. 1초에 딱 1칸을 움직인다.
그런데 홍준이가 뛰는 코스는 광고판으로 가득하다. 광고판은 빛의 세기가 다른데, 홍준이는 자신이 볼 수 있는 광고판들 중에서 가장 강렬한 빛의 광고판만이 눈에 들어온다.
홍준이는 눈이 안좋기 때문에 빛의 세기가 강한 지점에서는 안경을 쓰고 뛰려한다. 그래서 알아야 할 것이, 뛰어가면서 보이는 광고판의 빛의 세기를 알고 싶다.
홍준이가 뛰어갈 때, 1초마다 보이는 광고판의 빛의 세기를 출력하여라.

▶ 입력
첫째 줄에는 뛰는 코스의 길이, 즉 칸수 N과 홍준이의 시야의 범위 M이 주어진다. 시야가 M이라고 하면 현재 위치에서 앞뒤로 M-1칸까지 광고판이 보이는 것이다. (1 ≤ M ≤ N ≤ 1,000,000) 
두 번째 줄에는 각각 칸에 있는 광고판들의 빛의 세기가 주어진다. 빛의 세기는 1,000,000을 넘지 않는 자연수이다.
홍준이는 언제나 광고판을 2M-1개 보면서 뛰고 싶기 때문에(중심으로) M번째 칸에서 뛰기 시작해서 N-M+1번째 칸에서 멈춘다고 가정하자.

▶ 출력
뛰면서 보이는 광고판의 세기를 출력한다.

 */

public class BJ_1306_Best {
	
	static void init(int[] a, int[] tree, int node, int start, int end) {
        if (start == end) {
            tree[node] = a[start];
        } else {
            int mid = (start + end) / 2;
            int nextNode = node * 2;

            init(a, tree, nextNode, start, mid);
            init(a, tree, nextNode + 1, mid + 1, end);
            tree[node] = Math.max(tree[nextNode], tree[nextNode + 1]);
        }
    }

    static int query(int[] tree, int node, int start, int end, int i, int j) {
        if (i > end || j < start) return -1;
        if (i <= start && end <= j) return tree[node];
        int mid = (start + end) / 2;
        int nextNode = node * 2;
        int m1 = query(tree, nextNode, start, mid, i, j);
        int m2 = query(tree, nextNode+ 1, mid + 1, end, i, j);
        return Math.max(m1, m2);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] line = bf.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int[] a = new int[n];
        String temp = bf.readLine();
        String[] tempArr = temp.split(" ");
        for (int i = 0; i < tempArr.length; i++) {
            a[i] = Integer.parseInt(tempArr[i]);
        }


        int h = (int) Math.ceil(Math.log(n) / Math.log(2));
        int tree_size = (1 << (h + 1));
        int[] tree = new int[tree_size];
        init(a, tree, 1, 0, n - 1);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = m - 1; i < n - m + 1; i++) {
            int start = i - m + 1;
            int end = i + m - 1;
            bw.write(query(tree, 1, 0, n - 1, start, end) + " ");
        }
        bw.flush();
    }
    
}

