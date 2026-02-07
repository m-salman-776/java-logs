package Project101.HotelReservation;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomInventory {
    private final int totalCapacity;
    // Tracks how many rooms are booked for each specific date
    // Key: Date, Value: Number of rooms reserved on that date
    private final Map<LocalDate, Integer> dailyReservations;

    public RoomInventory(int totalCapacity) {
        this.totalCapacity = totalCapacity;
        this.dailyReservations = new ConcurrentHashMap<>();
    }

    /**
     * Checks availability and reserves rooms for the given date range.
     * Synchronized to ensure atomicity (check-then-act).
     */
    public synchronized boolean reserveRoom(LocalDate startDate, LocalDate endDate, int quantity) {
        // 1. Check availability for every night in the range
        // Note: Hotel bookings are typically [startDate, endDate) - exclusive of checkout day
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            int currentReserved = dailyReservations.getOrDefault(date, 0);
            if (currentReserved + quantity > totalCapacity) {
                System.out.printf("Failed to reserve. Unavailable on %s\n", date);
                return false;
            }
        }

        // 2. Update reservations
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            dailyReservations.merge(date, quantity, Integer::sum);
        }
        
        System.out.printf("Reserved room from %s to %s for %d rooms\n", startDate, endDate, quantity);
        return true;
    }

    public synchronized void releaseRoom(LocalDate startDate, LocalDate endDate, int quantity) {
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            dailyReservations.computeIfPresent(date, (k, v) -> Math.max(0, v - quantity));
        }
        System.out.printf("Released room from %s to %s for %d rooms\n", startDate, endDate, quantity);
    }

    public synchronized int getAvailableRooms(LocalDate date) {
        return totalCapacity - dailyReservations.getOrDefault(date, 0);
    }

    public synchronized void removeExpiredReservations() {
        LocalDate today = LocalDate.now();
        dailyReservations.keySet().removeIf(date -> date.isBefore(today));
    }

    @Override
    public String toString() {
        return String.format("RoomType , Total Capacity: %d", totalCapacity);
    }
}