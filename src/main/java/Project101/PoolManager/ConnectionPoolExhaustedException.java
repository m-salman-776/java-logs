package Project101.PoolManager;

public class ConnectionPoolExhaustedException extends RuntimeException {
    public ConnectionPoolExhaustedException(String s) {
        super(s);
    }
}
