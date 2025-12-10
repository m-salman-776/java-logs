package Project101.PoolManager.Factory;

public class JDBCFactory implements ConnectionFactory{
    @Override
    public UnderlyingConnection createConnection() {
        return new JDBC();
    }

    @Override
    public void destroyConnection(UnderlyingConnection connection) {
        ((JDBC) connection).closeConnection();
    }
}
