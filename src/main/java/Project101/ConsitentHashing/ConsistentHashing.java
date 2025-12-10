package Project101.ConsitentHashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ConsistentHashing {
    private MessageDigest messageDigest;
    private final TreeMap<Integer,String> ring;
    private final int replicaCount;
    ConsistentHashing(int replicaCount){
        this.ring = new TreeMap<>();
        List<Integer> s = new ArrayList<>();
        this.replicaCount = replicaCount;
        try {
            this.messageDigest =  MessageDigest.getInstance("MD5");

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }
    private int hash(String input){
        byte [] bytes = this.messageDigest.digest(input.getBytes());
        return (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | bytes[3] & 0xFF;
    }

    public void addServer(String node){
        for (int i = 0; i < this.replicaCount; i++) {
            String virtualNode = node + "-replica-" + i;
            int hashValue = hash(virtualNode);
            ring.put(hashValue, virtualNode);
        }
    }
    public void removeServer(String node){
        for (int i = 0; i < this.replicaCount; i++) {
            String virtualNode = node + "-replica-" + i;
            int hashValue = hash(virtualNode);
            ring.remove(hashValue);
        }
    }
    public String getServer(String key){
        if (ring.isEmpty()) {
            return null; // No nodes available
        }
        int hashValue = hash(key);
        if (!ring.containsKey(hashValue)) {
            // Find the next node in the circle

            SortedMap<Integer, String> tailMap = ring.tailMap(hashValue);
            hashValue = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }
        return ring.get(hashValue);
    }
}
