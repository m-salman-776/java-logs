//package Project103;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class RoundTrip {
    static List<Integer> graph[];
    static boolean vis[];
    static int parent[];
    static int start_ = -1 , end_ = -1;
    static int []color;
    static boolean solve(int src , int parent_){
        vis[src] = true;
        for (int child:graph[src]){
            if (parent_ == child) continue;
            if (vis[child]){
                start_ = child;
                end_ = src;
                return true;
            }
            parent[child] = src;
            if (solve(child,src)){
                return true;
            }
        }
        return false;
    }
    static boolean solve2(int src){
        color[src] = 1;
        for (int child : graph[src]){
            if (color[child] == 1){
                start_ = child;
                end_ = src;
                return true;
            }
            if (color[child] == 0){
                parent[child] = src;
                if (solve2(child)) return true;
            }
        }
        color[src] = 2;
        return false;
    }
    static void solve(List<Integer>[]graph){
        int n = graph.length;
        int []color = new int[n];
        Arrays.fill(color,-1);
        boolean possible = true;
        Queue<Integer> q = new LinkedList<>();
        for (int i=0;i<n;i++){
            if (color[i] != -1) continue;
            q.add(i);
            color[i] = 1;
            while (!q.isEmpty()){
                int parent = q.poll();
                for (int child : graph[parent]){
                    if (color[child] == -1){
                        q.add(child);
                        color[child] = 1 ^ color[parent];
                    }else{
                        if (color[parent] == color[child]){
                            possible = false;
                            break;
                        }
                    }
                }
            }
            if (!possible) break;
        }
        if (!possible){
            System.out.println("IMPOSSIBLE");
        }else{
            for (int i=1;i<n;i++) System.out.print(color[i]+1+" ");
        }
    }
    public static void main(String[] args) throws Exception {
        String input = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        InputStream inputStream = System.in;
//        inputStream = new FileInputStream(input);
        FastScanner fs = new FastScanner(inputStream);
        int sx = 10;
        Map<Integer,Integer> map = new LinkedHashMap<>(100,.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > sx;
            }
        };

        int n = fs.nextInt();
        int m = fs.nextInt();
        List<Integer>[]g = new ArrayList[n+1];

        Arrays.setAll(g,i -> new ArrayList<>());
        for (int i=0;i<m;i++){
            int u = fs.nextInt();
            int v = fs.nextInt();
            g[v].add(u);
            g[u].add(v);
        }
        solve(g);
    }
    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c, sign = 1, res = 0;
            do c = read(); while (c <= ' ');
            if (c == '-') {
                sign = -1;
                c = read();
            }
            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return res * sign;
        }
    }
}
