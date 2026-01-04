package Project103;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DistanceQueries {
    static int LOG = 18;
    static List<Integer> graph[];
    static int tin[] , tout[] , dest[];
    static int timer = 0;
    static int up [][];
    static void dfs(int src, int parent){
        tin[src] = timer ++ ;
        if (src != parent)
        dest[src] = dest[parent] + 1;
        up[src][0] = parent;
        for (int child : graph[src]){
            if (parent == child) continue;
            dfs(child,src);
        }
        tout[src] = timer ++;
    }
    static boolean is_ancestor(int u,int v){
        return tin[u] <= tin[v] && tout[u] >= tout[v];
    }
    static int lcs(int u, int v){
        if (is_ancestor(u,v)) return u;
        if (is_ancestor(v,u)) return v;
        for (int l = LOG-1 ; l>= 0 ; l--){
            if (!is_ancestor(up[u][l],v)){
                u = up[u][l];
            }
        }
        return up[u][0];
    }
    public static void main(String []args)throws Exception{
//        String input = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        InputStream inputStream =  System.in;
//        inputStream = new FileInputStream(input);
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();
        int q = fs.nextInt();
        tin = new int[n + 1];
        tout = new int[n + 1];
        dest = new int[n + 1];
        dest[0] = -1;
        up = new int[n + 1][LOG];
        timer = 0;
        graph = new ArrayList[n+1];
        for (int i=0;i<=n;i++){
            graph[i] = new ArrayList<>();
        }
        for (int i = 1 ; i < n ; i++){
            int src = fs.nextInt();
            int des = fs.nextInt();
            graph[src].add(des);
            graph[des].add(src);
        }
        dfs(1,1);
        for (int j=1; j < LOG;j++){
            for (int i=1; i <= n; i++){
                up[i][j] = up[ up[i][j-1] ][j-1];
            }
        }
        StringBuilder sb = new StringBuilder();
        while (q-- > 0){
            int a = fs.nextInt();
            int b = fs.nextInt();
            int lca_a_b = lcs(a,b);
            int ans = dest[a] + dest[b] - 2 * dest[lca_a_b];
            sb.append(ans).append("\n");
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
