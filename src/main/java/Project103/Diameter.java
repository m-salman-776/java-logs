package Project103;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Diameter {
    static int []dist;
    static int []head, next , to;
    static int ptr = 0;
    static void dfs(int root){
        dist[root] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()){
            root = q.poll();
            for (int e=head[root];e != -1 ; e = next[e]){
                int child = to[e];
                if (dist[child] == -1){
                    dist[child] = dist[root] + 1;
                    q.add(child);
                }
            }
        }
    }
    static int findMax(int n){
        int first = 1;
        int max_ = 0;
        for (int i=1; i<=n ; i++){
            if (dist[i] > max_){
                max_ = dist[i];
                first = i;
            }
        }
        return first;
    }
    static void addEdge(int u, int v){
        to[ptr] = v;
        next[ptr] = head[u];
        head[u] = ptr ++;
    }
    static int result = 0;
    public static void main(String []args) throws Exception{
        String path = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        InputStream inputStream = System.in;
        inputStream = new FileInputStream(path);
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();

        head = new int[n+1];
        next = new int[2*n+1];
        to = new int[2*n+1];
        Arrays.fill(head,-1);

        dist = new int[n+1];

        for (int i=0;i<n-1;i++){
            int a = fs.nextInt();
            int b = fs.nextInt();
            addEdge(a,b);
            addEdge(b,a);
        }
        Arrays.fill(dist,-1);
        dfs(1);
        int l = findMax(n);
        Arrays.fill(dist,-1);
        dfs(l);
        int ans = 0;
        for (int i  : dist){
            if (ans < i) {
                ans = i;
            }
        }
        System.out.println(ans);
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
