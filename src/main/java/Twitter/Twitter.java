package Twitter;

import java.util.*;

public class Twitter {
    int timer;
    int CELEBRiTY_USER_THRESHOLD = 10;
    // user -> followeeId
    Map<Integer, Set<Integer>> following;

    // user -> (followerId)
    Map<Integer,Set<Integer>> followers;
    Map<Integer,List<Tweet>> tweetDb;

    // user personal cache
    Map<Integer,List<Tweet>> pushCache ;

    Set<Integer> celebrityUser;
    Twitter(){
        following = new HashMap<>();
        tweetDb = new HashMap<>();
        followers = new HashMap<>();

        celebrityUser = new HashSet<>();
        pushCache = new HashMap<>();
        timer = 0;
    }
    private void ensureUserExists(int userId){
        following.putIfAbsent(userId,new HashSet<>());
        tweetDb.putIfAbsent(userId,new ArrayList<>());
        followers.putIfAbsent(userId,new HashSet<>());
        pushCache.putIfAbsent(userId,new ArrayList<>());
    }
    void post(int userId,int tweetId){
        ensureUserExists(userId);

        Tweet tweet = new Tweet(tweetId,timer++);
        tweetDb.get(userId).add(tweet);

        if (celebrityUser.contains(userId)){
            return;
        }
        pushCache.get(userId).add(tweet);
        for (int follower : followers.get(userId)){
            ensureUserExists(follower);
            pushCache.get(follower).add(tweet);
        }
    }
    List<Tweet> getFeed(int userId, int topK){
        ensureUserExists(userId);
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a,b) -> Integer.compare(a.timeStamp,b.timeStamp));

        List<Tweet> feed = new ArrayList<>();
        int topKi = topK;

        for (int idx = pushCache.get(userId).size()-1 ; idx >= 0 && topKi-- > 0; idx--){
            minHeap.add(pushCache.get(userId).get(idx));
        }
        // pulling celebrity posts
        for (int followeeId : following.get(userId)){
            if (!celebrityUser.contains(followeeId)) continue;
            ensureUserExists(followeeId);
            topKi = topK;
            for (int idx = tweetDb.get(followeeId).size()-1 ; idx >= 0 && topKi-- >= 0; idx--){
                minHeap.add(tweetDb.get(followeeId).get(idx));
                if (minHeap.size() > topK){
                    minHeap.poll();
                }
            }
        }
        while (!minHeap.isEmpty()){
            feed.add(minHeap.poll());
        }
        Collections.reverse(feed);
        return feed;
    }
    void follow(int followeeId,int followerId){
        Map<Integer,LinkedList<Integer>> map;
        ensureUserExists(followeeId);
        ensureUserExists(followerId);

        followers.get(followeeId).add(followerId);
        following.get(followerId).add(followeeId);

        if (followers.get(followeeId).size() > CELEBRiTY_USER_THRESHOLD){
            celebrityUser.add(followeeId);
        }
    }
    void unfollow(int followeeId,int followerId){
        followers.get(followeeId).remove(followerId);
        following.get(followerId).remove(followeeId);
    }
}
