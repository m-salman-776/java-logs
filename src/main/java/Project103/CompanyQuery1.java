package Project103;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CompanyQuery1 {
    static int up[][];
    static int lca(int a, int b){
        if (a == b) return a;

        return b;
    }
    public static void main(String[]args) throws Exception{
        int LOG = 18;
        String input = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        InputStream inputStream = System.in;
//        inputStream = new FileInputStream(input);
        FastScanner fs = new FastScanner(inputStream);
        StringBuilder sb = new StringBuilder();
        int n = fs.nextInt();
        int q = fs.nextInt();
        up = new int[n + 1][LOG];
        up[1][0] = 0;
        for (int i=2; i<= n ; i++){
            up[i][0] = fs.nextInt();
        }
        for (int j=1; j < LOG; j++){
            for (int i=2 ; i <= n; i++){
                up[i][j] = up[up[i][j-1]][j-1];
            }
        }
        while (q -- > 0){
            int x = fs.nextInt();
            int k = fs.nextInt();
            int j = 0;
            while (k > 0){
                if ((k & 1) == 1){
                    x = up[x][j];
                }
                k >>= 1;
                j++;
            }
            if (x == 0){
                sb.append("-1");
            }else{
                sb.append(x);
            }
            sb.append("\n");
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
