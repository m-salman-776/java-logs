package Concurrency;

public class UserBucket {
    private final int maxCapacity;
    private final long refillIntervalMs;
    private long currentTokens;
    private long lastRefillTime;

    public UserBucket(int maxCapacity, long refillIntervalMs) {
        this.maxCapacity = maxCapacity;
        this.refillIntervalMs = refillIntervalMs;
        this.currentTokens = maxCapacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized void consume() throws InterruptedException {
        refill();
        while (currentTokens == 0) {
            long timeSinceLastRefill = System.currentTimeMillis() - lastRefillTime;
            long timeToWait = refillIntervalMs - timeSinceLastRefill;

            if (timeToWait > 0) {
                System.out.printf("[%s] Thread %s waiting for %d ms...\n", this.hashCode(), Thread.currentThread().getName(), timeToWait);
                this.wait(timeToWait);
            }
            refill();
        }
        currentTokens--;
        System.out.printf("Granting token to %s. Remaining tokens: %d\n", Thread.currentThread().getName(), currentTokens);
    }

    private void refill() {
        long elapsedTime = System.currentTimeMillis() - lastRefillTime;
        if (elapsedTime < refillIntervalMs) return;

        long tokensToAdd = elapsedTime / refillIntervalMs;
        if (tokensToAdd > 0) {
            currentTokens = Math.min(maxCapacity, currentTokens + tokensToAdd);
            lastRefillTime += tokensToAdd * refillIntervalMs;
        }
    }
}