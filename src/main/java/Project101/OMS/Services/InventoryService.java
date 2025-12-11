package Project101.OMS.Services;

import Project101.OMS.Inventory;
import Project101.OMS.Order;
import Project101.OMS.OrderItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {
    Map<Integer, Inventory> inventoryMap;
    public InventoryService(){
        inventoryMap = new HashMap<>();
    }
    public void addStock(int productId,int quantity){
        Inventory inventory = new Inventory(productId,quantity);
        inventoryMap.put(productId,inventory);
    }
    public boolean reserveStock(List<OrderItem> orderItemList){
        for (OrderItem orderItem : orderItemList) {
            int productIt = orderItem.getProductId();
            int quantity = orderItem.getQuantity();

            Inventory inventory = inventoryMap.get(productIt);
            synchronized (inventory) {
                if (inventory.getAvailableStock() < quantity) {
                    return  false;
                }
                inventory.setAvailableStock(inventory.getAvailableStock() - quantity);
                inventory.setReservedStock(inventory.getReservedStock() + quantity);
            }
        }
        return true;
    }
    public boolean releaseStock(List<OrderItem> orderItemList){
        for (OrderItem orderItem: orderItemList) {
            int productIt = orderItem.getProductId();
            int quantity = orderItem.getQuantity();
            Inventory inventory = inventoryMap.get(productIt);
            synchronized (inventory) {
                inventory.setReservedStock(inventory.getReservedStock() - quantity);
                inventory.setAvailableStock(inventory.getAvailableStock() + quantity);
            }
        }
        return true;
    }
    public boolean commitStock(List<OrderItem> orderItemList){
        for (OrderItem orderItem: orderItemList) {
            int productIt = orderItem.getProductId();
            int quantity = orderItem.getQuantity();

            Inventory inventory = inventoryMap.get(productIt);
            synchronized (inventory) {
                inventory.setAvailableStock(inventory.getAvailableStock() - quantity);
                inventory.setReservedStock(inventory.getReservedStock() - quantity);
            }
        }
        return true;
    }
}
