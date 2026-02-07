package Project101.HotelReservation;

public class Main {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        inventoryService.updateRooms(1,123);
        ReservationService reservationService = new ReservationService(inventoryService);
        System.out.println("DONE");
    }
}
