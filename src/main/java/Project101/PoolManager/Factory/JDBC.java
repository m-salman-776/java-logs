package Project101.PoolManager.Factory;
import java.util.UUID;
public class JDBC implements UnderlyingConnection{
    private final String id;
    private boolean closed = false;

    public JDBC() {
        this.id = "UnderlyingConn-"+UUID.randomUUID().toString().substring(0,8);
        System.out.println("Created " + id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isValid() {
        return !this.closed;
    }

    @Override
    public void closeConnection() {
        this.closed = true;
        System.out.println("Closed " + this.id);
    }
    void executeQuery(String query){
        if (!isValid()) {
            throw new IllegalStateException("Connection is closed.");
        }
        System.out.println(id + " executing query: " + query);
        try {
            Thread.sleep(50); // Simulate network latency/work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
