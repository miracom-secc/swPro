package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_13905_Best2 {
    static int N, M;
    static int s,e;
    static ArrayList<Edge> [] adjList;
    static boolean [] visit;

    static int [] weight;
    public static void main(String [] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken()); //¼þÀÌ
        e = Integer.parseInt(st.nextToken()); //Çýºó
        adjList = new ArrayList[N+1];
        for (int i = 0 ; i <= N ; i ++) {
            adjList[i] = new ArrayList<>();
        }
        visit = new boolean[N+1];
        for (int i = 0 ; i < M ; i ++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            adjList[from].add(new Edge(to, cost));
            adjList[to].add(new Edge(from, cost));
        }
        System.out.print(prim());
    }
    private static int prim() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(s, 1000000));

        weight = new int[N+1];
        weight[s] = 1000000;

        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            if (visit[now.to]) continue;
            visit[now.to] = true;
            if (now.to == e) {
                return weight[e];
            }

            for (Edge next : adjList[now.to]) {
                weight[next.to] = Math.max(weight[next.to], Math.min(weight[now.to], next.cost));

                pq.add(new Edge(next.to, weight[next.to]));
            }
        }
        return 0;
    }
    private static class Edge implements Comparable<Edge> {
        int to;
        int cost;
        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(o.cost, cost);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "to=" + to +
                    ", cost=" + cost +
                    '}';
        }
    }
}