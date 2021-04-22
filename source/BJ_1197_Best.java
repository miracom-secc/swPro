package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

//간선정보용 Edge 객체
class Edge implements Comparable<Edge> {
    int v1; 	// 정점1
    int v2;		// 정점2
    int cost; 	// 가중치
    Edge(int v1, int v2, int cost) {
        this.v1 = v1;
        this.v2 = v2;
        this.cost = cost;
    }
    @Override
    public int compareTo(Edge o) {
        if(this.cost < o.cost)
            return -1;
        else if(this.cost == o.cost)
            return 0;
        else
            return 1;
    }
}

public class BJ_1197_Best {
	
    public static int[] parent;
    public static ArrayList<Edge> edgeList;


    public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      //  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	 
    	StringTokenizer st = new StringTokenizer(br.readLine());
 	
    	
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        
        // 간선정보를 담는 리스트를 만들어 데이터 담음
        edgeList = new ArrayList<Edge>();
        
        for(int i = 0; i < e; i++) {
        	st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edgeList.add(new Edge(v1,v2,cost));
        }
		
        parent = new int[v+1];
        for(int i = 1; i <= v; i++) {
            parent[i] = i;
        }
		
        /* 1) 간선을 오름차순으로 정렬*/
        Collections.sort(edgeList);
     					      
        int sum = 0;	// 가중치
        //(2) 정렬된 간선을 순서대로 선택		
        for(int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
         	//(3) 선택한 간선의 두 정점이 연결되어 있지 않으면, 해당 두 정점을 연결	
            if(!chkParent(edge.v1, edge.v2)) {
                sum += edge.cost;
                union(edge.v1, edge.v2);
            }
        }
		
        System.out.println(sum);
    }	
    
    public static void union(int x, int y) {
        x = find(x);
        y = find(y);
        if(x != y)
            parent[y] = x;
    }
	
    public static int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }
    public static boolean chkParent(int x, int y) {
        x = find(x);
        y = find(y);
        if(x == y) return true;
        else return false;
    }
}
