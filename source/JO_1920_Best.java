package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
 
/*
1000 100 6
 
1234257123 123 100000
*/
 
public class JO_1920_Best {
 
    static int M, C, N;
    static long X[];
    static int leafNode, tree[];
    static final int MOD = (1 << 31) - 1;
 
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
 
    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
 
        X = new long[N+1];
        X[1] = 1;
        for (int k=2; k<=N; k++) {
            X[k] = (M * X[k-1] + C) % MOD;
        }
 
        init();
        tree = new int[leafNode * 2 + 1];
 
        // ===================== 좌표 압축
        int idx = 0;
        long temp[] = X.clone();
        Map<Long, Integer> map = new HashMap<>();
 
        // 작은 순으로 정렬
        Arrays.sort(temp);
        for (int t=1; t<=N; t++) {
            if (!map.containsKey(temp[t])) map.put(temp[t], idx++);
        }
 
        // ===================== 역순으로 되어 있는 쌍 찾기
        long sum = 0;
        for (int x=1; x<=N; x++) {
            int i = map.get(X[x]);
            sum += sum(i+1, N-1); // 더 큰 수가 몇개 있는지 찾기
            update(i, 1); // 해당 위치에 두기
        }
 
        bw.write(sum+"");
 
        br.close();
        bw.close();
 
    }
 
    static void init() {
        leafNode = 1;
        while(leafNode < N){
            leafNode *= 2;
        }
    }
 
    static void update(int idx, int diff) {
        idx += leafNode;
 
        tree[idx] += diff;
        while(idx > 1) {
            idx /= 2;
            tree[idx] += diff;
        }
    }
 
    static long sum(int s, int e) {
        s += leafNode;
        e += leafNode;
 
        long sum = 0;
        while (s <= e) {
            if (s%2 == 1) sum += tree[s];
            if (e%2 == 0) sum += tree[e];
 
            s = (s+1) / 2;
            e = (e-1) / 2;
        }
 
        return sum;
    }
 
}
