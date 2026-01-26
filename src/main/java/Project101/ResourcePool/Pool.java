package Project101.ResourcePool;

public interface Pool<T> {
    T acquire() throws Exception;
    void release(T object);
    void shutdown();
}
