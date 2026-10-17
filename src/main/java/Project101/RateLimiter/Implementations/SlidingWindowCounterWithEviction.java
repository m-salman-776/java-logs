package Project101.RateLimiter.Implementations;

import Project101.RateLimiter.RateLimitAlgorithm;
import com.amazonaws.services.glue.model.transform.TableInputMarshaller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowCounterWithEviction implements RateLimitAlgorithm {
    private final long maxAllowedRequest;
    private final long windowSizeMs;
    private final long ttlMs;
    private final Node head = new Node(null,null);
    private final Node tail = new Node(null,null);

    // Protects list modifications
    private final ReentrantLock listLock = new ReentrantLock();

    private final Map<String,Node> map = new ConcurrentHashMap<>();

    public SlidingWindowCounterWithEviction(long maxAllowedRequest,long windowSizeMs, long ttlMs){
        this.maxAllowedRequest = maxAllowedRequest;
        this.windowSizeMs = windowSizeMs;
        this.ttlMs = ttlMs;
        this.head.next = this.tail;
        this.tail.prev = this.head;

        ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r,"O1-Eviction-Thread");
            t.setDaemon(true);
            return t;
        });

        cleaner.scheduleAtFixedRate(this::purseOldEntries, 1, 1, TimeUnit.SECONDS);
    }



    private void addToEnd(Node node){
        node.prev = tail.prev;
        tail.prev.next = node;

        node.next = tail;
        tail.prev = node;
    }
    private void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    private void moveToEnd(Node node){
        removeNode(node);
        addToEnd(node);
    }

    private void purseOldEntries(){
        long now = System.currentTimeMillis();
        listLock.lock();
        try{
            while (head.next != tail){
                Node oldestNode = head.next;
                if (now - oldestNode.lastAccessed > ttlMs){
                    removeNode(oldestNode);
                    map.remove(oldestNode.key);
                    System.out.println("Evicted in O(1) time: " + oldestNode.key);
                }else{
                    break;
                }
            }
        }finally {
            listLock.unlock();
        }
    }

    @Override
    public boolean allowRequest(String key, long tokenRequested) {
        long currentTime = System.currentTimeMillis();
        Node node = map.get(key);
        if (node == null){
            listLock.lock();
            try {
                // Double-check inside lock
                node = map.get(key);
                if (node == null){
                    Window window = new Window(windowSizeMs,currentTime,maxAllowedRequest);
                    node = new Node(key,window);
                    map.put(key,node);
                    addToEnd(node);
                }
            } finally {
                listLock.unlock();
            }
        }else{
            listLock.lock();
            moveToEnd(node);
        }
        node.lastAccessed = currentTime;
        return node.userWindow.tryConsume(currentTime,windowSizeMs,tokenRequested);
    }
    private static class Node {
        String key;
        Window userWindow;
        Node next;
        Node prev;
        long lastAccessed ;
        Node(String key,Window userWindow){
            this.key = key;
            this.userWindow = userWindow;
            this.lastAccessed = System.currentTimeMillis();
        }
    }
    private static class Window {
        long currentWindowStart;
        long previousWindowCount;
        long currentWindowCount;
        long maxAllowedRequest;
        private Window(long windowSizeMs,long currentTime,long maxAllowedRequest){
            this.currentWindowCount = 0;
            this.previousWindowCount = 0;
            this.maxAllowedRequest = maxAllowedRequest;
            this.currentWindowStart = (currentTime / windowSizeMs) * windowSizeMs;
        }
        private synchronized boolean tryConsume(long currentTime,long windowSizeMs,long requestCount){
            long elapsedTime = this.currentWindowStart - currentTime;
            if (elapsedTime > windowSizeMs){
                advanceWindow(windowSizeMs,currentTime);
                elapsedTime = currentTime - this.currentWindowStart;
            }
            double weight = (double) ((windowSizeMs - elapsedTime) / windowSizeMs) / windowSizeMs;
            long estimatedRequest = (long) (this.previousWindowCount * weight + this.currentWindowCount);
            if (estimatedRequest + requestCount > this.maxAllowedRequest){
                return false;
            }
            this.currentWindowCount += requestCount;
            return true;
        }
        private void advanceWindow(long windowSizeMs,long currentTime){
            long newStart = (currentTime / windowSizeMs ) * windowSizeMs;
            long windowToSkip = newStart - this.currentWindowStart;
            if (windowToSkip == 1){
                this.previousWindowCount = this.currentWindowCount;
            }else{
                this.previousWindowCount = 0;
            }
            this.currentWindowCount = 0;
            this.currentWindowStart = newStart;
        }
    }
}
