package Project103;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LCS {
    static int [][]dp ;
//    static void lcs(int i,int j){
//        if (i < 0 || j < 0) return;
//        if (dp[i][])
//    }
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        inputStream = new FileInputStream("/Users/salman/Downloads/code/java-logs/src/main/java/input.txt");
        FastScanner fs = new FastScanner(inputStream);
        int n = fs.nextInt();
        int m = fs.nextInt();
        dp = new int[n+1][m+1];
        int []arr1 = new int[n];
        int []arr2 = new int[m];
        for (int i=0;i<n;i++){
            arr1[i] = fs.nextInt();
        }
        for (int i=0;i<m;i++){
            arr2[i] = fs.nextInt();
        }
        for (int i=1 ; i<= n;i++){
            for (int j=1; j<= m;j++){
                if (arr1[i-1] == arr2[j-1]){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i][j-1],dp[i-1][j]);
                }
            }
        }
        for (int []a:dp){
            for (int b : a){
                System.out.print(b);
            }
            System.out.println();
        }

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
