import java.util.Arrays;

public class Practice {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        String s = "hello";
        s = s + " world";
        System.out.println(s);
        char []arr = s.toCharArray();
        int [] numArr = {3, 1, 4, 2};
//        Arrays.sort(numArr,(a,b) -> Integer.compare((Integer) a,b));
        Arrays.sort(arr);
        String sorted = String.valueOf(arr);
        System.out.println(sorted);
    }
}
