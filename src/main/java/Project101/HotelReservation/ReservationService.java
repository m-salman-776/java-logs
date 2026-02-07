package Project101.HotelReservation;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationService {
    private final InventoryService inventoryService;
    private final Map<Integer, Reservation> reservationMap;
    private final AtomicInteger idGenerator;
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor ;
    public ReservationService(InventoryService inventoryService){
        this.inventoryService = inventoryService;
        this.reservationMap = new ConcurrentHashMap<>();
        this.idGenerator = new AtomicInteger(0);
        this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
//        this.scheduledThreadPoolExecutor.schedule(this.inventoryService::cleanupAllInventories,1,TimeUnit.DAYS);
    }

    public Reservation createReservation(LocalDate startDate, LocalDate endDate, int roomType, int quantity){
        // 1. Attempt to reserve inventory
        boolean reserved = inventoryService.reserveRoom(startDate, endDate, roomType, quantity);

        if (!reserved) {
            return null;
        }

        // 2. Create Reservation Record
        int reservationId = idGenerator.incrementAndGet();
        Reservation reservation = new Reservation(reservationId, startDate, endDate, roomType, quantity);

        reservationMap.put(reservationId, reservation);

        // Fix: Only auto-cancel if the status is still CREATED (Pending)
        this.scheduledThreadPoolExecutor.schedule(() -> {
            synchronized (reservation) {
                if (reservation.getReservationStatus() == ReservationStatus.CREATED) {
                    cancelReservation(reservation.id);
                }
            }
        }, 10, TimeUnit.SECONDS);

        return reservation;
    }

    public void confirmReservation(int reservationId){
        Reservation reservation = reservationMap.get(reservationId);
        if (reservation == null) {
            System.out.printf("Reservation %d could not be found", reservationId);
            return;
        }

        synchronized (reservation) {
            if (reservation.getReservationStatus() == ReservationStatus.CREATED) {
                reservation.setReservationStatus(ReservationStatus.CONFIRMED);
            } else {
                System.out.printf("Reservation %d Either completed or expired", reservationId);
            }
        }
    }

    public void cancelReservation(int reservationId){
        Reservation reservation = reservationMap.get(reservationId);
        if (reservation == null){
            System.out.printf("Reservation %d could not be found", reservationId);
            return;
        }
        synchronized (reservation) {
            if (reservation.getReservationStatus() != ReservationStatus.CANCELLED) {
                // Release inventory
                inventoryService.releaseRoom(reservation.getStartDate(), reservation.getEndDate(),
                        reservation.getRoomType(), reservation.getQuantity());

                reservation.setReservationStatus(ReservationStatus.CANCELLED);
            }
        }
    }

    // Added: Essential for viewing details
    public Reservation getReservation(int ReservationId) {
        return reservationMap.get(ReservationId);
    }
}
