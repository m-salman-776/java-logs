package Project101.PoolManager.Factory;

public interface ConnectionFactory {
    UnderlyingConnection createConnection();
    void destroyConnection(UnderlyingConnection connection);
}
