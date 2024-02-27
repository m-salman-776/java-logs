package DSDesign;

import java.util.*;

public class GetDeleteRandom {
    Map<Integer, Integer> location;
    List<Integer> list ;
    GetDeleteRandom(){
        list = new ArrayList<>();
        location = new HashMap<>();
    }
    public boolean insert(int val) {
        if (location.containsKey(val)) return false;
        list.add(val);
        location.put(val,location.size());
        return true;
    }

    public boolean remove(int val) {
        if (!location.containsKey(val)) return false;
        int currentIdx = location.get(val);
        int lastVal = list.get(list.size()-1);
        int lastIdx = location.get(lastVal);

        list.remove(lastIdx);
        location.remove(lastVal);

        list.set(currentIdx,lastVal);
        location.put(lastVal,currentIdx);

        location.remove(val);
        return true;
    }
//    public int getRandom() {
//        int size = location.size();
////        int idx = Math.random();
//    }
}
