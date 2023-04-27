package Assignment;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.HashMap;
import java.util.Scanner;

public class Assignment {
    public static void main(String []args){
        Solution solution = new Solution();
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        String t = scanner.next();
        String ans = solution.solve(s,t);
        System.out.println(ans);
    }
}

class Solution {
    String solve(String s, String t){
        HashMap<Character,Integer> need = new HashMap();
        HashMap<Character,Integer> window = new HashMap();
        for (Character c : t.toCharArray()){
            need.put(c,need.getOrDefault(c,0)+1);
        }
        String res = "";
        int left = 0 , match = 0 , len = s.length()+1 , start = 0;
        for (int right = 0 ;right < s.length();){
            char c = s.charAt(right);
            if (need.containsKey(c)){
                window.put(c,window.getOrDefault(c,0)+1);
                if (window.get(c) == need.get(c)){
                    match += 1;
                }
            }
            right += 1;
            while (match == need.size()){
                if (right - left < len){
                    len = right - left;
                    start = left;
                }
                c = s.charAt(left);
                if (need.containsKey(c)){
                    window.put(c,window.get(c)-1);
                    if (window.get(c) < need.get(c)){
                        match -= 1;
                    }
                }
                left += 1;
            }
        }
        return s.substring(start,start+len);
    }
    public String minWindow(String s, String t) {
        return solve(s,t);
    }
}
