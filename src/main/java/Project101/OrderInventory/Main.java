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

        System.out.println(inventoryService.getAvailableQuantity(1));
        inventoryService.reserveInventory(1,1,1);
        System.out.println(inventoryService.getAvailableQuantity(1));

        inventoryService.completeOrder(1,1);
        Thread.sleep(5000);

        System.out.println(inventoryService.getAvailableQuantity(1));

        System.out.println(inventoryService.getAvailableQuantity(1));
        System.out.println("DEbug");
    }
}