package Project101.OrderInventory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryService {
    // product_id -> inventory
    private final Map<Integer, ProductInventory> inventoryMap = new ConcurrentHashMap<>();
    // userId -> List Reserved products
    private final Map<Integer, Set<ProductReservation>> productReservation = new ConcurrentHashMap<>();
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private long inventory_release_sec = 3;
    private AtomicInteger orderId = new AtomicInteger(0);

    public Map<Integer,Order> reservedOrder = new ConcurrentHashMap<>();

    public InventoryService(){
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
    }

    public int getAvailableQuantity(int productId){
        ProductInventory productInventory = this.inventoryMap.get(productId);
        if (productInventory == null){
            return 0;
        }
        System.out.println(productInventory);
        return productInventory.getAvailableQuantity();
    }

    public void addProduct(Product product,int quantity){
        // this is excessive locking. ConcurrentHashMap is already thread-safe. Synchronizing the entire method locks the entire service just to add a product, creating a bottleneck
        this.inventoryMap.computeIfAbsent(product.id,k -> new ProductInventory(product,quantity));
    }
    
    private boolean reserveInventory(int productId, int quantity){
        ProductInventory productInventory = this.inventoryMap.get(productId);
        if (productInventory == null) {
            return false;
        }
        return productInventory.reserveProduct(quantity);
    }

    public Order createOrder(int userId,int productId, int quantity){
        int currentOrderId = orderId.incrementAndGet();
        boolean isSuccess = reserveInventory(productId,quantity);
        if (!isSuccess) return null;
        Order order = new Order(currentOrderId,userId,productId,quantity);
        this.reservedOrder.put(currentOrderId,order);
        this.scheduledThreadPoolExecutor.schedule(() -> releaseInventory(currentOrderId),inventory_release_sec, TimeUnit.SECONDS);
        return order;
    }
    
    public void completeOrder(int orderId){
        // Atomically remove the order. If it returns null, the timer thread already released it.
        Order order = this.reservedOrder.remove(orderId);
        if (order == null) {
            System.out.printf("Order : %s could not be completed (expired or invalid)\n",orderId);
            return;
        }
        // 1. Update available quantity (permanently remove items from stock)
        this.inventoryMap.get(order.productId).updateAvailableQuantity(-order.quantity);
        
        // 2. Release the reservation (since we've now deducted from main stock, we no longer need the reservation hold)
        this.inventoryMap.get(order.productId).releaseProduct(-order.quantity);

        System.out.printf("Order : %s completed successfully\n",orderId);
    }
    public void releaseInventory(int orderId){
        // Atomically remove the order. If it returns null, another thread (completeOrder) already handled it.
        Order order = this.reservedOrder.remove(orderId);
        if (order == null) {
            System.out.printf("Order : %s Not be available for release (already completed or released)\n",orderId);
            return;
        }
        // Release the reserved quantity. We pass negative because ProductInventory adds the value.
        this.inventoryMap.get(order.productId).releaseProduct(order.quantity);
    }
}
