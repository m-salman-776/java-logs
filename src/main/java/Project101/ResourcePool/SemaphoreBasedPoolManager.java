package Project101.ResourcePool;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SemaphoreBasedPoolManager<T> implements Pool<T>{
    Resource<T> factory;
    Semaphore semaphore;
    ArrayBlockingQueue<T> idleResource;
    int maxSize;
    int maxWaitTime;
    AtomicBoolean isShutdown = new AtomicBoolean(false);
    public SemaphoreBasedPoolManager(int maxSize, int maxWaitTime, Resource<T> factory){
        this.maxSize = maxSize;
        this.factory = factory;
        this.maxWaitTime = maxWaitTime;
        this.idleResource = new ArrayBlockingQueue<>(maxSize);
        this.semaphore = new Semaphore(maxSize);
    }
    @Override
    public T acquire() throws Exception {
        if (isShutdown.get()){
            throw new RuntimeException("Pool is Shutting is in down");
        }
        boolean acquired = semaphore.tryAcquire(maxWaitTime, TimeUnit.SECONDS);
        if (!acquired){
            throw  new Exception("Unable to acquire Resource");
        }
        T resource = null;
        try{
            while (true){
                if (isShutdown.get()){
                    throw new RuntimeException("Pool is Shutting is in down");
                }
                resource = idleResource.poll();
                if (resource == null) { // queue is empty create one since we have permit
                    resource =  factory.create();
                    return resource;
                }
                if (factory.validate(resource)){ // valid resource return it
                    return resource;
                }
                // invalid or corrupted resource
                factory.destroy(resource);
                resource = null;
            }
        }catch (Exception e){
            semaphore.release(); // permit release
            throw e;
        }
    }

    @Override
    public void release(T object) {
        if (object == null){
         semaphore.release();
         return;
        }
        boolean added = idleResource.offer(object);
        if (added){
            semaphore.release();
        }else{
            factory.destroy(object);
            semaphore.release();
        }
    }

    @Override
    public synchronized void shutdown() {
        isShutdown.set(true);
        while (!idleResource.isEmpty()){
            T resource = idleResource.poll();
            factory.destroy(resource);
//            semaphore.release(); can release the permit
        }
    }
}
