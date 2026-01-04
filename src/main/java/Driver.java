import java.io.*;

public class Driver {

    static final int LOG = 30; // since 2^30 > 1e9

    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        String filePath = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        inputStream = new FileInputStream(filePath);
        FastScanner fs = new FastScanner(inputStream);
        StringBuilder out = new StringBuilder();

        int n = fs.nextInt();
        int q = fs.nextInt();

        int[][] up = new int[n + 1][LOG];

        for (int i = 1; i <= n; i++) {
            up[i][0] = fs.nextInt();
        }

        // Build binary lifting table
        for (int j = 1; j < LOG; j++) {
            for (int i = 1; i <= n; i++) {
                up[i][j] = up[ up[i][j - 1] ][j - 1];
            }
        }

        // Process queries
        while (q-- > 0) {
            int x = fs.nextInt();
            int k = fs.nextInt();

            int j = 0;
            while (k > 0) {
                if ((k & 1) == 1) {
                    x = up[x][j];
                }
                k >>= 1;
                j++;
            }

            out.append(x).append('\n');
        }

        System.out.print(out.toString());
    }

    // Fast I/O (critical for CSES)
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
