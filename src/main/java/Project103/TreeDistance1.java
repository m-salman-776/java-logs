package Project103;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TreeDistance1 {
    static int []head ,next , to;
    static int ptr;
    static void  addEdge(int u,int v){
        to[ptr] = v;
        next[ptr]  = head[u];
        head[u] = ptr ++;
    }
    static int[] findDistance(int src, int n){
        int []distance = new int[n+1];
        Arrays.fill(distance,-1);
        distance[src] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        while (!q.isEmpty()){
            int root = q.poll();
            for (int e = head[root]; e != -1 ; e = next[e]){
                int child = to[e];
                if (distance[child] == -1){
                    distance[child] = distance[root] + 1;
                    q.add(child);
                }
            }
        }
        return distance;
    }
    public static void main(String []args) throws  Exception {
//        String input = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        InputStream inputStream = System.in;
//        inputStream = new FileInputStream(input);
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();
        head = new int[n+1];
        Arrays.fill(head,-1);
        next = new int[2*n + 1];
        to = new int[2*n + 1];
        for (int i=1;i < n;i++){
            int a = fs.nextInt();
            int b = fs.nextInt();
            addEdge(a,b);
            addEdge(b,a);
        }
        int []arb = findDistance(1,n);
        int a = 0 , b = 0;
        int max_ = 0;
        for (int i = 1 ; i <= n; i++){
            if (arb[i] > max_) {
                max_  = arb[i];
                a = i;
            }
        }
        int [] distA = findDistance(a,n);
        max_ = 0;
        for (int i = 1 ; i <= n; i++){
            if (distA[i] > max_) {
                max_  = distA[i];
                b = i;
            }
        }
        int []distB = findDistance(b,n);
        StringBuilder sb = new StringBuilder();
        for (int i=1; i<= n; i++){
            int t = Math.max(distA[i],distB[i]);
            if (t < 0) t = 0;
            sb.append(t).append(" ");
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
