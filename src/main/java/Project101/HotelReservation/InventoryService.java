package Project101.HotelReservation;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryService {
    // Key : RoomTypeId , value : Inventory
    Map<Integer,RoomInventory> inventoryMap;
    public InventoryService(){
        inventoryMap = new ConcurrentHashMap<>();
    }
    public synchronized void updateRooms(int roomType,int maxCapacity){
        this.inventoryMap.put(roomType,new RoomInventory(maxCapacity));
    }
    public boolean reserveRoom(LocalDate start_date,LocalDate end_date,int roomType,int quantity){
        RoomInventory inventory = inventoryMap.get(roomType);
        if (inventory == null){
            System.out.printf("Inventory for %d not found",roomType);
            return false;
        }
        return inventory.reserveRoom(start_date,end_date,quantity);
    }
    public void releaseRoom(LocalDate start_date,LocalDate end_date,int roomType,int quantity){
        RoomInventory inventory = inventoryMap.get(roomType);
        if (inventory == null){
            System.out.printf("Inventory for %d not found",roomType);
            return ;
        }
        inventory.releaseRoom(start_date,end_date,quantity);
    }
    public int getAvailableInventory(LocalDate date,int roomType){
        if (!inventoryMap.containsKey(roomType)){
            return 0;
        }
        return inventoryMap.get(roomType).getAvailableRooms(date);
    }
}
