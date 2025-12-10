package Project101.PoolManager;

import Project101.PoolManager.Factory.UnderlyingConnection;
import lombok.Getter;

import java.util.UUID;
@Getter
public class PooledConnection {
    private final String id;
    private final UnderlyingConnection connection;
    private volatile ConnectionStatus status;
    private final ConnectionPoolManager poolManager;
    PooledConnection(UnderlyingConnection connection,ConnectionPoolManager poolManager){
        this.id = "PooledConnection-" + UUID.randomUUID().toString().substring(0,8);
        this.connection = connection;
        this.poolManager = poolManager;
        this.status = ConnectionStatus.FREE;
    }
    public void acquire(){
        this.status= ConnectionStatus.BLOCKED;
    }
    void release(){
        this.status = ConnectionStatus.FREE;
//        this.poolManager.release(this);
    }
    void close(){
        this.status = ConnectionStatus.CLOSED;
    }
}
