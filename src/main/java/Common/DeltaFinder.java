package Common;

import org.apache.kafka.common.protocol.types.Field;

public class DeltaFinder {

    public static String findDelta(String original, String modified) {
        int m = original.length();
        int n = modified.length();

        int[][] dp = new int[m + 1][n + 1];

        // Build the dp table in a bottom-up manner
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (original.charAt(i - 1) == modified.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Reconstruct and print the delta
        int i = m, j = n;
        StringBuilder delta = new StringBuilder();

        while (i > 0 && j > 0) {
            if (original.charAt(i - 1) == modified.charAt(j - 1)) {
                delta.insert(0, original.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                delta.insert(0, "(-" + original.charAt(i - 1) + ")");
                i--;
            } else {
                delta.insert(0, "(+" + modified.charAt(j - 1) + ")");
                j--;
            }
        }

        while (i > 0) {
            delta.insert(0, "(-" + original.charAt(i - 1) + ")");
            i--;
        }

        while (j > 0) {
            delta.insert(0, "(+" + modified.charAt(j - 1) + ")");
            j--;
        }

        System.out.println("Delta: " + delta.toString());
        return delta.toString();
    }

    public static void main(String[] args) {
        String original = "my name is xyx";
        String modified = "my NAME is abc";

        String delta = findDelta(original, modified);
        System.out.println(delta);
    }
}
