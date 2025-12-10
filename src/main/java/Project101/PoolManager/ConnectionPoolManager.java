package Project101.PoolManager;

import Project101.PoolManager.Factory.ConnectionFactory;
import Project101.PoolManager.Factory.UnderlyingConnection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolManager {
    int minPoolSize;
    int maxPoolSize;
    long defaultTimeOutMs;
    ConnectionFactory connectionFactory;
    BlockingQueue<PooledConnection> availableConnections;
    ConcurrentHashMap<String,PooledConnection> allConnections;
    AtomicInteger connectionCounter ;
    ConnectionPoolManager(int minPoolSize, int maxPoolSize, long defaultTimeOutMs, ConnectionFactory connectionFactory){
        if (maxPoolSize <= 0 || maxPoolSize < minPoolSize){
            throw  new IllegalArgumentException("Invalid pool size");
        }
        if (defaultTimeOutMs <= 0){
            throw  new IllegalArgumentException("Invalid timeout");
        }
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;

        this.connectionFactory = connectionFactory;

        this.connectionCounter = new AtomicInteger(0);

        this.allConnections = new ConcurrentHashMap<>();
        this.availableConnections = new LinkedBlockingQueue<>(this.maxPoolSize);

    }
    public void init(){
        for (int i=0;i < minPoolSize;i++){
            this.createConnection();
        }
    }
    private void createConnection(){
        try {
            UnderlyingConnection underlyingConnection = connectionFactory.createConnection();
            PooledConnection pooledConnection = new PooledConnection(underlyingConnection, this);
            allConnections.put(underlyingConnection.getId(), pooledConnection);
            availableConnections.add(pooledConnection);
        }catch (Exception e) {
            System.err.println("Failed to create new underlying connection: " + e.getMessage());
        }
    }
    public PooledConnection getConnection() throws ConnectionPoolExhaustedException {

        try {
            // 1. First, try to get an existing connection without waiting long.
            PooledConnection connection = availableConnections.poll();

            if (connection == null) {
                // 2. No connections are available. Can we create a new one?
                if (connectionCounter.get() < maxPoolSize) {
                    // 3. Attempt to create a new connection. This must be synchronized.
                    // We use double-checked locking to ensure efficiency and thread safety.
                    synchronized (this) {
                        if (connectionCounter.get() < maxPoolSize) {
                            // This new method creates a connection and returns it directly, already acquired.
                            return createAndAcquireNewConnection();
                        }
                    }
                }

                // 4. If we're here, it means the pool is at max size. Now we must wait.
                System.out.println(Thread.currentThread().getName() + " is waiting for a connection as pool is at max size (" + maxPoolSize + ")");
                connection = availableConnections.poll(defaultTimeOutMs, TimeUnit.MILLISECONDS);
                if (connection == null) {
                    throw new ConnectionPoolExhaustedException("No connection available in the pool within " + defaultTimeOutMs + "ms.");
                }
            }

            // Standard validation and acquisition logic
            if (!connection.getConnection().isValid()) {
                System.err.println("Stale connection detected: " + connection.getId() + ". Replacing it.");
                closeConnection(connection); // Close and replace
                return getConnection(); // Recursively try again
            }
            connection.acquire();
            return connection;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConnectionPoolExhaustedException("Interrupted while waiting for a connection: " + e.getMessage());
        }
    }
    public void closeConnection(PooledConnection connection){
        if (allConnections.remove(connection.getId()) != null){
            connectionFactory.destroyConnection(connection.getConnection());
            connectionCounter.decrementAndGet();
            availableConnections.remove(connection);
            createConnection();
        }
    }
    private void releaseConnection(PooledConnection connection){
        try{
            availableConnections.put(connection);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            connectionFactory.destroyConnection(connection.getConnection());
            allConnections.remove(connection.getId());
            connectionCounter.decrementAndGet();
        }
    }
    // New helper method for the growth logic
    private PooledConnection createAndAcquireNewConnection() {
        try {
            UnderlyingConnection uConn = connectionFactory.createConnection();
            PooledConnection pConn = new PooledConnection(uConn, this);
            pConn.acquire(); // Mark as BLOCKED immediately
            allConnections.put(pConn.getId(), pConn);
            connectionCounter.incrementAndGet();
            System.out.println("Created new connection on-demand: " + pConn.getId() + ". Pool size now: " + connectionCounter.get());
            return pConn;
        } catch (Exception e) {
            System.err.println("Failed to create new on-demand connection: " + e.getMessage());
            // If creation fails, we should throw an exception so the client knows.
            throw new RuntimeException("Failed to create new connection", e);
        }
    }
}
