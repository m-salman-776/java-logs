package Project101.OrderInventory;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Product product1 = new Product(1,"p1");
        Product product2 = new Product(2,"p2");
        Product product3 = new Product(3,"p3");


        InventoryService inventoryService = new InventoryService();

        inventoryService.addProduct(product1,3);
        inventoryService.addProduct(product2,4);
        inventoryService.addProduct(product3,4);

        inventoryService.getAvailableQuantity(1);
        Order order1 = inventoryService.createOrder(1,1,1);
        inventoryService.getAvailableQuantity(1);

        inventoryService.completeOrder(1);
        inventoryService.releaseInventory(order1.id);
        inventoryService.getAvailableQuantity(1);


        inventoryService.createOrder(2,2,2);
        inventoryService.getAvailableQuantity(2);

        Thread.sleep(5300);
        inventoryService.getAvailableQuantity(1);

        inventoryService.getAvailableQuantity(2);
        System.out.println("Debug");
    }
}