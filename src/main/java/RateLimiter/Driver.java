package RateLimiter;

import com.sun.source.tree.Tree;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Driver {
    public static void main(String []args){
        TreeSet<Integer> set = new TreeSet<>(Arrays.asList(1, 2, 4, 5, 6, 7, 8));
        int val = 4;
        while (!set.isEmpty() && set.first() < val ){
            set.pollFirst();
        }
        System.out.println(set);
        System.out.println("DONE");
    }
}
