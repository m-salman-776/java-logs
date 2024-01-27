package Cache;

import Cache.Implementations.LFU;
import Cache.Interface.Cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Driver {

    static void print(Iterator<Integer>itr){
        System.out.println(itr.next());
    }
    public static void main(String []args){

        Cache<Integer,Integer> cache = new LFU<Integer,Integer>(10);
        cache.get(10);
        cache.put(10,24);
//        List<Integer> ls = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
//        Iterator<Integer> itr = ls.listIterator();
//        print(itr);
//        System.out.println("DONE");
    }
}
