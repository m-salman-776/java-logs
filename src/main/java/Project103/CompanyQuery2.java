package Project103;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class CompanyQuery2 {
    static int log = 18;
    static int timer = 0;
    static int[][] up;
    static int[] tin, tout;
    static int[] head,to,next;
    static int ptr = 0;
    static void dfs(int root, int parent){
        tin[root] = ++timer;
        up[root][0] = parent;
        for (int e = head[root]; e != -1; e = next[e]){
            int child = to[e];
            dfs(child,root);
        }
        tout[root] = ++timer;
    }
    static  int lca(int u,int v){
        if (is_ancestor(u,v)) return u;
        if (is_ancestor(v,u)) return v;
        for (int l = log-1; l >= 0; l --){
            if (!is_ancestor(up[u][l],v)){
                u = up[u][l];
            }
        }
        return up[u][0];
    }
    static boolean is_ancestor(int u,int v){
        return tin[u] <= tin[v] && tout[u] >= tout[v];
    }
    static void addEdge(int u, int v){
        to[ptr] = v;
        next[ptr] = head[u];
        head[u] = ptr ++;
    }
    public static void main(String[]args) throws Exception{
//        String input = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        InputStream inputStream = System.in;
//        inputStream = new FileInputStream(input);
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();
        int q = fs.nextInt();
        timer = 0;
        tout = new int[n];
        tin = new int[n];
        up = new int[n][log];
        head = new int[n];
        Arrays.fill(head,-1);
        next = new int[n];
        to = new int[n];
        for (int i = 0 ; i < n-1 ; i++){
            int val = fs.nextInt()-1;
            addEdge(val,i+1);
        }

        dfs(0,0);
        for (int j=1;j<log;j++){
            for (int i=0;i<n;i++){
                up[i][j] = up[ up[i][j-1] ][j-1];
            }
        }
        StringBuilder sb = new StringBuilder();
        while (q -- > 0){
            int a = fs.nextInt();
            int b = fs.nextInt();
            int lcA = lca(a-1,b-1) + 1;
            sb.append(lcA).append("\n");
        }
        System.out.println(sb.toString());

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
