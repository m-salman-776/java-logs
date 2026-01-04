package Project103;

import com.amazonaws.services.dynamodbv2.xspec.S;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class ExactlyKSteps {
    public static void main(String []args) throws Exception{
        int LOG = 18;
        String path = "/Users/salman/Downloads/code/java-logs/src/main/java/input.txt";
        InputStream inputStream = System.in;
        inputStream = new FileInputStream(path);
        Scanner scanner = new Scanner(inputStream);
        int n = scanner.nextInt();
        int up[][] = new int[n+1][LOG];
        for (int i = 0 ; i < n-1; i++){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            up[b][0] = a;
        }
        up[1][0] = 0;
        for (int j=1; j < LOG; j++){
            for (int i=2;i <= n ; i++){
                up[i][j] = up[ up[i][j-1] ][j-1];
            }
        }
        int q = scanner.nextInt();
        int node , k ;
        int j = 0;
        while (q -- > 0){
            node = scanner.nextInt();
            k = scanner.nextInt();
            while (k > 0){
                if ((k & 1) == 1){
                    node = up[node][j];
                }
                if (node < 0){
                    break;
                }
                j += 1;
                k = k >> 1;
            }
            if (node <= 0) {
                node = -1;
            }
            System.out.println(node);
        }
    }
}
