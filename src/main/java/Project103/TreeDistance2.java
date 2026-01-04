//package Project103;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class TreeDistance2 {
    static int []head,next,to;
    static long[] size,ans;
    static int eptr = 0;
    static void addEdge(int u,int v){
        to[eptr] = v;
        next[eptr] = head[u];
        head[u] = eptr ++;
    }
    static void dfs1(int root,int parent){
        size[root] = 1;
        for (int e = head[root];e != -1 ; e = next[e]){
            int child = to[e];
            if (child == parent) continue;
            dfs1(child,root);
            size[root] += size[child];
            ans[root] += ans[child] + size[child];
        }
    }
    static void dfs2(int root,int parent){
        for (int e = head[root];e != -1;e = next[e]){
            int child = to[e];
            if (child == parent) continue;
            ans[child] = ans[root] - size[child] + (head.length-1 - size[child]);
            dfs2(child,root);
        }
    }
    public static void main(String []args)throws Exception{
        InputStream inputStream = System.in;
//        inputStream = new FileInputStream("/Users/salman/Downloads/code/java-logs/src/main/java/input.txt");
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();
        head = new int[n+1];
        Arrays.fill(head,-1);
        next = new int[2*n + 1];
        to = new int[2*n + 1];
        ans = new long[n+1];
        size = new long[n+1];
        for (int i=1; i<n ; i++){
            int a = fs.nextInt();
            int b = fs.nextInt();
            addEdge(a,b);
            addEdge(b,a);
        }
        dfs1(1,1);
        dfs2(1,1);
        StringBuilder sb = new StringBuilder();
        for (int i=1;i <= n; i++){
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
