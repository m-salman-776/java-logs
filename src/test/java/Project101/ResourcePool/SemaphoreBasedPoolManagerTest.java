package Project101.ResourcePool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SemaphoreBasedPoolManagerTest {

    private MockResourceFactory factory;
    private Pool<MockResource> pool;

    // A mock resource for testing purposes
    static class MockResource {
        private static final AtomicInteger idCounter = new AtomicInteger(0);
        private final int id;
        private boolean isValid = true;

        public MockResource() {
            this.id = idCounter.incrementAndGet();
        }

        public int getId() {
            return id;
        }

        public void invalidate() {
            this.isValid = false;
        }

        public boolean isValid() {
            return isValid;
        }
    }

    // A mock factory to control resource creation, validation, and destruction for tests
    static class MockResourceFactory implements Resource<MockResource> {
        private final AtomicInteger createCount = new AtomicInteger(0);
        private final AtomicInteger destroyCount = new AtomicInteger(0);
        private final AtomicInteger validateCount = new AtomicInteger(0);

        @Override
        public MockResource create() {
            createCount.incrementAndGet();
            return new MockResource();
        }

        @Override
        public boolean validate(MockResource object) {
            validateCount.incrementAndGet();
            return object.isValid();
        }

        @Override
        public void destroy(MockResource object) {
            destroyCount.incrementAndGet();
        }

        public int getCreateCount() { return createCount.get(); }
        public int getDestroyCount() { return destroyCount.get(); }
    }

    @BeforeEach
    void setUp() {
        factory = new MockResourceFactory();
    }

    @Test
    void testAcquireAndRelease_Success() throws Exception {
        pool = new SemaphoreBasedPoolManager<>(5, 1, factory);

        MockResource resource = pool.acquire();
        assertNotNull(resource);
        assertEquals(1, factory.getCreateCount());

        pool.release(resource);

        MockResource resource2 = pool.acquire(); // Should reuse the same resource
        assertEquals(resource.getId(), resource2.getId());
        assertEquals(1, factory.getCreateCount(), "Should not create a new resource");
    }

    @Test
    void testPoolExhaustion_AndTimeout() throws Exception {
        pool = new SemaphoreBasedPoolManager<>(1, 1, factory); // Pool of size 1, 1 second timeout

        pool.acquire(); // Acquire the only available resource

        // This next acquire should block and then throw an exception
        assertThrows(Exception.class, () -> {
            pool.acquire();
        }, "Should throw exception when pool is exhausted and timeout is reached");
    }

    @Test
    void testConcurrentAcquireAndRelease() throws InterruptedException {
        int poolSize = 10;
        int numThreads = 50;
        pool = new SemaphoreBasedPoolManager<>(poolSize, 2, factory);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    latch.await();
                    MockResource resource = pool.acquire();
                    Thread.sleep(50); // Simulate work
                    pool.release(resource);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    // Fail the test if any thread fails to acquire/release
                    fail("Thread failed with exception: " + e.getMessage());
                }
            });
        }

        latch.countDown(); // Start all threads at once
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS), "Test timed out");

        assertEquals(numThreads, successCount.get(), "All threads should complete successfully");
        assertTrue(factory.getCreateCount() <= poolSize, "Should not create more resources than the pool size");
    }

    @Test
    void testInvalidResource_IsDestroyedAndReplaced() throws Exception {
        pool = new SemaphoreBasedPoolManager<>(1, 1, factory);

        // 1. Acquire and release a valid resource
        MockResource resource1 = pool.acquire();
        pool.release(resource1);
        assertEquals(1, factory.getCreateCount());

        // 2. Invalidate the resource while it's in the pool
        resource1.invalidate();

        // 3. Acquire again. The pool should detect the invalid resource, destroy it, and create a new one.
        MockResource resource2 = pool.acquire();

        assertEquals(1, factory.getDestroyCount(), "The invalid resource should have been destroyed");
        assertEquals(2, factory.getCreateCount(), "A new resource should have been created to replace the invalid one");
        assertNotEquals(resource1.getId(), resource2.getId(), "A new resource should be returned");
    }

    @Test
    void testRelease_WhenQueueIsFull() throws Exception {
        pool = new SemaphoreBasedPoolManager<>(1, 1, factory);

        // Fill the pool's idle queue
        MockResource resourceInPool = pool.acquire();
        pool.release(resourceInPool);

        // Create an extra resource and try to release it to the full pool
        MockResource extraResource = factory.create();
        pool.release(extraResource);

        // The extra resource should be destroyed because the idle queue is full
        assertEquals(1, factory.getDestroyCount(), "Extra resource should be destroyed when released to a full pool");
    }

    @Test
    void testShutdown_ClearsIdleResources() throws Exception {
        pool = new SemaphoreBasedPoolManager<>(5, 1, factory);
        List<MockResource> acquiredResources = new ArrayList<>();

        // Acquire 5 resources to ensure 5 are created
        for (int i = 0; i < 5; i++) {
            acquiredResources.add(pool.acquire());
        }
        assertEquals(5, factory.getCreateCount());

        // Release 3 resources back to the pool (making them idle)
        for (int i = 0; i < 3; i++) {
            pool.release(acquiredResources.get(i));
        }

        // Shutdown the pool
        pool.shutdown();

        // The shutdown should destroy the 3 idle resources
        assertEquals(3, factory.getDestroyCount(), "Shutdown should destroy only the idle resources");
    }
}
