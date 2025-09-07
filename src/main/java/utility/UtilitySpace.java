package utility;

import com.amazonaws.services.dynamodbv2.xspec.S;

import java.util.Arrays;

public class UtilitySpace {
    public static void main(String []args){
        int arr[][] = new int[5][];
        Arrays.sort(arr);
        String str = "";
//        StringBuilder sb = new
//        char [] s = new StringBuilder(str).reverse().toString().toCharArray()
//        str = sb.reverse().toString();
        char ch = '1';
        int val = ch-'0';
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(1);
        stringBuilder.append(2);
        System.out.println(val + "  " + stringBuilder.toString());
    }
}
