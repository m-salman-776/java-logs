package Assignment;

import java.util.*;

public class Questions {
    static  boolean solve(int []arr,int k,int p){
        int n = arr.length;
        if (n % k != 0) return false;

        TreeMap<Integer,Integer> map = new TreeMap<>();
        for (int c : arr) {
            map.put(c,map.getOrDefault(c,0)+1);
        }
        while (--n > 0){
            int minKey = map.firstKey();
            for (int i=0;i<k;i++){
                if (!map.containsKey(minKey)) return false;
                int val = map.get(minKey);
                if (val >= 1) {
                    map.put(minKey, val - 1);
                }else{
                    return false;
                }
                minKey += 1;
            }
        }
        return true;
    }
    public static void main(String[] args) {
//        boolean ans = solve(new int[]{1,2,3,3,4,4,5,6},4,1);
        Queue<Integer> q= new LinkedList<>();
        q.add(1);
        Integer val = q.poll();
        List<String> a = new ArrayList<>();
//        int a = val + 1;
        System.out.println(val);
    }
}
