package Project101.OrderInventory;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InventoryService {
    // product_id -> inventory
    private final Map<Integer, ProductInventory> inventoryMap = new HashMap<>();
    // userId -> List Reserved products
    private final Map<Integer, Set<ProductReservation>> productReservation;
    ScheduledThreadPoolExecutor threadPool;
    private long inventory_release_sec = 3;
    public InventoryService(){
        productReservation = new HashMap<>();
        threadPool = new ScheduledThreadPoolExecutor(2);
        threadPool.scheduleAtFixedRate(new Thread(this::periodicInventoryRelease),1,1, TimeUnit.SECONDS);
    }

    private boolean handleError(ProductInventory productInventory) {
        return false;
    }

    public int getAvailableQuantity(int productId){
        ProductInventory productInventory = this.inventoryMap.get(productId);
        if (productInventory == null){
            return 0;
        }
        return productInventory.quantity - productInventory.reservedQuantity;
    }

    public synchronized void addProduct(Product product,int quantity){
        if (this.inventoryMap.containsKey(product.id)){
            this.inventoryMap.get(product.id).quantity = quantity;
        }
        else {
            this.inventoryMap.putIfAbsent(product.id,new ProductInventory(product,quantity));
        }
    }
    public void reserveInventory(int productId,int quantity,int userId){

        ProductInventory productInventory = this.inventoryMap.get(productId);
        if (handleError(productInventory)) {
            System.out.println("Invalid Product");
        }else {
            int availableQuantity = productInventory.quantity;
            int reservedQuantity = productInventory.reservedQuantity;
            if (availableQuantity - reservedQuantity < quantity){
                System.out.println("Product not available");
                // todo handle this
                return;
            }
            synchronized (this){
                productInventory.reservedQuantity += quantity;
                this.productReservation.putIfAbsent(userId,new HashSet<>());
                this.productReservation.get(userId).add(new ProductReservation(userId,productId,quantity));
            }
            System.out.printf("Inventory reserved for product %s buy user  %s%n",productId,userId);
        }
    }
    public void releaseInventoryByProduct(int productId,int quantity){
        ProductInventory productInventory = this.inventoryMap.get(productId);
        if (handleError(productInventory)) {
            System.out.println("Invalid Product");
        }else{
            // todo input validation
            synchronized (this) {
                productInventory.reservedQuantity -= quantity;
            }
            System.out.printf("Inventory reserved for product %s",productId);

        }
    }
    public void releaseInventory(int productId,int quantity,int userId){
        Set<ProductReservation> remainingReservation = new HashSet<>();
        Set<ProductReservation> userProductReservations = this.productReservation.get(userId);

        for (ProductReservation productReservation1 : userProductReservations){
            if (productReservation1.productId == productId){
                synchronized (this.inventoryMap) {
                    this.inventoryMap.get(productId).reservedQuantity -= quantity;
                }
                System.out.printf("Inventory release for product %s buy user  %s",productId,userId);
            }else{
                remainingReservation.add(productReservation1);
            }
        }
        this.productReservation.put(userId,remainingReservation);
    }
    public void periodicInventoryRelease(){
        for (Integer userId : this.productReservation.keySet()) {
            Set<ProductReservation> productReservationsList = new HashSet<>();
            for (ProductReservation productReservation1 : this.productReservation.getOrDefault(userId,new HashSet<>())) {
                long now = Instant.now().getEpochSecond();
                long reservedAt = productReservation1.reservedAt;
                if (now - reservedAt > inventory_release_sec) {
                    releaseInventory(productReservation1.productId, productReservation1.quantity,userId);
//                    releaseInventoryByProduct(productReservation1.productId, productReservation1.quantity);
                    System.out.printf("Inventory reserved for product %s buy user  %s",productReservation1.productId,productReservation1.userId);
                }else {
                    productReservationsList.add(productReservation1);
                }
            }
            synchronized (this) {
                this.productReservation.put(userId,productReservationsList);
            }

        }
    }
    public void completeOrder(int userId, int productId){
        Set<ProductReservation> productReservations = this.productReservation.get(userId);
        Set<ProductReservation> updatedProductReservation = new HashSet<>();
        boolean order = false;
        for (ProductReservation productReservation1 : productReservations){
            if (productReservation1.productId == productId){
                synchronized (this) {
                    ProductInventory productInventory = this.inventoryMap.get(productId);
                    productInventory.reservedQuantity -= productReservation1.quantity;
                    productInventory.quantity -= productReservation1.quantity;
                    System.out.println("\nOrder completed for product " + productId );
                    order = true;
                }
            }else {
                updatedProductReservation.add(productReservation1);
            }
        }
        synchronized (this) {
            this.productReservation.put(userId,updatedProductReservation);

        }
        if (!order)
            System.out.println("Order failed for product " + productId );
    }
}
