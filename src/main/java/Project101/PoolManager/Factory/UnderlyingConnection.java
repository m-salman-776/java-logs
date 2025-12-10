package Project101.PoolManager.Factory;

public interface UnderlyingConnection {
    String getId();
    boolean isValid();
    void closeConnection();
}
