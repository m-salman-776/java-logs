package Project103;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class CountingPath {
    static int [] head,next,to,tin,tout,diff,ans;
    static int[][]up;
    static int ptr = 0 ,timer = 0 , log = 18;
    static void addEdge(int u,int v){
        to[ptr] = v;
        next[ptr] = head[u];
        head[u] = ptr ++;
    }
    static void dfs(int root,int parent){
        up[root][0] = parent;
        tin[root] = ++timer;
        for (int e = head[root];e != -1 ; e = next[e]){
            if (to[e] != parent){
                dfs(to[e],root);
            }
        }
        tout[root] = ++timer;
    }
    static boolean is_ancestor(int u,int v){
        return tin[u] <= tin[v] && tout[u] >= tout[v];
    }
    static int getLca(int u,int v){
        if (is_ancestor(u,v)) return u;
        if (is_ancestor(v,u)) return v;
        for (int i=log-1; i>=0;i--){
            if (!is_ancestor(up[u][i],v)){
                u = up[u][i];
            }
        }
        return up[u][0];
    }
    static void dfs2(int root,int parent){
        ans[root] += diff[root];
        for (int e=head[root];e != -1; e=next[e]){
            int child = to[e];
            if (child == parent) continue;
            dfs2(child,root);
            ans[root]  += ans[child];
        }
    }
    public static void main(String []args) throws Exception {
        InputStream inputStream = System.in;
        inputStream = new FileInputStream("/Users/salman/Downloads/code/java-logs/src/main/java/input.txt");
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();
        int m = fs.nextInt();
        head = new int[n+1];
        Arrays.fill(head,-1);
        next = new int[2*n+1];
        to = new int[2*n+1];
        up = new int[n+1][log];
        tin = new int[n+1];
        tout = new int[n+1];
        diff = new int[n+1];
        ans = new int[n+1];
        for (int i=1; i< n;i++){
            int a = fs.nextInt()-1;
            int b = fs.nextInt()-1;
            addEdge(a,b);
            addEdge(b,a);
        }
        dfs(0,0);
        for (int j=1; j < log; j++){
            for (int i=1; i<= n;i++){
                up[i][j] = up[ up[i][j-1] ][j-1];
            }
        }
        for (int i=1;i <= m;i++){
            int a = fs.nextInt()-1;
            int b = fs.nextInt()-1;
            int lca = getLca(a,b);
            diff[a] ++;
            diff[b] ++;
            diff[lca] -= 1;
            if (up[lca][0] != lca){
                diff[up[lca][0]] -= 1;
            }
        }
        dfs2(0,0);
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<n;i++){
            sb.append(ans[i]).append(" ");
        }
        System.out.println(sb);
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
