package Project101.OrderInventory;

public class ProductInventory {
    public int productId;
    public int availableQuantity;
    public int reservedQuantity;
    public ProductInventory(Product product, int quantity){
        this.productId = product.id;
        this.availableQuantity = quantity;
        this.reservedQuantity = 0;
    }
    @Override
    public String toString(){
        return String.format("ID : %s , availableQuantity : %s , reservedQuantity: %s",productId,availableQuantity,reservedQuantity);
    }

    public synchronized void updateAvailableQuantity(int quantity){
        this.availableQuantity -= quantity;
    }
    public synchronized void releaseProduct(int quantity){
        System.out.printf("Releasing the inventory for product %s with quantity %s\n",productId,quantity);
        this.reservedQuantity -= quantity;
    }
    public synchronized boolean reserveProduct(int quantity){
        if (this.availableQuantity - this.reservedQuantity  < quantity){
            System.out.printf("Failed to reserve the inventory for product %s with quantity %s\n",productId,quantity);
            return false; // Added return false here, was missing
        }
        this.reservedQuantity += quantity;
        System.out.printf("Reserved the inventory for product %s with quantity %s\n",productId,quantity);
        return true;
    }
    public synchronized int getReservedQuantity(){
        return this.reservedQuantity; // Fixed: was returning availableQuantity
    }
    public synchronized int getAvailableQuantity(){
        return this.availableQuantity - this.reservedQuantity;
    }

}
