package RateLimiter.Implementations;

import RateLimiter.Interface.RateLimiter;

import java.util.Set;
import java.util.TreeSet;

public class SlidingWindow implements RateLimiter {

    TreeSet<Long> sortedSet;
    Long windowSize ;
    int max_limit ;
    SlidingWindow(Long windowSize, int  max_limit){
        this.sortedSet = new TreeSet<Long>();
        this.windowSize = windowSize;
        this.max_limit = max_limit;
    }
    @Override
    public boolean allowRequest(Long epoch) {
        while (!sortedSet.isEmpty() && epoch-windowSize >sortedSet.first()){
            sortedSet.pollFirst();
        }
        int count = sortedSet.size();
        if (count >= this.max_limit) return false;
        sortedSet.add(epoch);
        return true;
    }
}

//
//1. Remove all the timestamps from the Sorted Set that are older than “CurrentTime - 1 minute”.
//        2. Count the total number of elements in the sorted set. Reject the request if this count is greater than our throttling limit of “3”.
//        3. Insert the current time in the sorted set and accept the request.