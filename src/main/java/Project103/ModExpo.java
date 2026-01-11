package Project103;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ModExpo {
    static long MOD = (long)1e9 + 7;
    static long modExpo(long a, long n, long mod){
        long ans = 1L;
        while (n > 0){
            if ((n & 1) == 1){
                ans = (ans * a ) % mod;
            }
            a = (a * a) % mod;
            n >>= 1;
        }
        return  ans;
    }
    public static void main(String []args) throws IOException {
        InputStream inputStream = System.in;
        inputStream = new FileInputStream("/Users/salman/Downloads/code/java-logs/src/main/java/input.txt");
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();
        StringBuilder sb = new StringBuilder();

        for (int i=1; i<= n;i++){
            long a = fs.nextInt();
            long b = fs.nextInt();
            long c = fs.nextInt();
            long ans = modExpo(a,modExpo(b,c,MOD-1),MOD);
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
