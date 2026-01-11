package Project103;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class TreeFlattening {
    static int []head,next,to;
    static int ptr = 0;
    static int[] tin,tout;
    static int timer = 0;
    static void dfs(int root,int parent){
        tin[root] = ++timer;
        for (int e = head[root]; e != -1 ; e = next[e]){
            int child  = to[e];
            if (child == parent) continue;
            dfs(child,root);
        }
        tout[root] = timer;
    }
    static void addEdge(int u,int v){
        to[ptr] = v;
        next[ptr] = head[u];
        head[u] = ptr ++;
    }
    public static void main(String[]args) throws Exception {
        InputStream inputStream = new FileInputStream("/Users/salman/Downloads/code/java-logs/src/main/java/input.txt");
        Scanner sc = new Scanner(inputStream);
        int n = sc.nextInt();
        head = new int[n+1];
        Arrays.fill(head,-1);
        next = new int[2*n+1];
        to = new int[2*n+1];
        tin = new int[n+1];
        tout = new int[n+1];
        for (int i=1;i<n;i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            addEdge(a,b);
            addEdge(b,a);
        }
        dfs(1,1);
        System.out.println("Node 1 Range: [" + tin[1] + ", " + tout[1] + "]");
        System.out.println("DONE");
    }
}
