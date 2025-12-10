//package Project101.BookingSystem.MoviesBooking.Services;
//
//import Project101.BookingSystem.MoviesBooking.Model.Booking;
//import Project101.BookingSystem.MoviesBooking.Repository.BookingRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookingService {
//
//    // --- Dependencies ---
//    private final ShowSeatRepository showSeatRepo;
//    private final BookingRepository bookingRepo;
//    private final OutboxEventRepository outboxRepo;
//
//    // Redis Lock Manager (The "Gatekeeper")
//    private final ILockProvider lockProvider;
//
//    // Pricing Engine (The Strategy/Decorator Pattern)
//    private final PricingService pricingService;
//
//    // Configuration
//    private static final int LOCK_TTL_SECONDS = 600; // 10 Minutes
//
//    public BookingService(ShowSeatRepository showSeatRepo,
//                          BookingRepository bookingRepo,
//                          OutboxEventRepository outboxRepo,
//                          ILockProvider lockProvider,
//                          PricingService pricingService) {
//        this.showSeatRepo = showSeatRepo;
//        this.bookingRepo = bookingRepo;
//        this.outboxRepo = outboxRepo;
//        this.lockProvider = lockProvider;
//        this.pricingService = pricingService;
//    }
//
//    // =========================================================================
//    // 🟢 PHASE 1: CREATE BOOKING (The "Happy Path" Reservation)
//    // =========================================================================
//
//    public Booking createBooking(String userId, String showId, List<String> seatIds) {
//
//        // 1. INPUT VALIDATION
//        if (seatIds == null || seatIds.isEmpty()) {
//            throw new IllegalArgumentException("No seats selected.");
//        }
//
//        // 2. CONSTRUCT REDIS LOCK KEYS
//        // We need a unique lock key for every single seat to prevent partial overlaps.
//        List<String> lockKeys = seatIds.stream()
//                .map(seatId -> "LOCK:SHOW:" + showId + ":SEAT:" + seatId)
//                .collect(Collectors.toList());
//
//        // 3. ACQUIRE DISTRIBUTED LOCK (Redis)
//        // "tryAcquireAll" is an atomic Lua script ensuring we get ALL locks or NONE.
//        boolean acquired = lockProvider.tryAcquireAll(lockKeys, userId, LOCK_TTL_SECONDS);
//
//        if (!acquired) {
//            // FAILURE PATH: Someone else is selecting these seats right now.
//            throw new SeatTemporaryUnavailableException("Seats are currently held by another user.");
//        }
//
//        try {
//            // 4. DB TRANSACTION START
//            return executeBookingTransaction(userId, showId, seatIds);
//
//        } catch (Exception e) {
//            // FAILURE PATH: DB Error? Logic Error?
//            // CRITICAL: Release the Redis locks immediately so others can try.
//            lockProvider.releaseAll(lockKeys);
//            throw e;
//        }
//    }
//
//    // Helper method to keep the transaction scope tight
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    protected Booking executeBookingTransaction(String userId, String showId, List<String> seatIds) {
//
//        // A. FETCH CURRENT STATE (Optimistic Locking Check)
//        // We fetch the seats to ensure they are actually AVAILABLE in the DB.
//        // Even if Redis said OK, the DB is the source of truth.
//        List<ShowSeat> seats = showSeatRepo.findAllById(seatIds);
//
//        // Validation: Do all seats exist?
//        if (seats.size() != seatIds.size()) {
//            throw new InvalidSeatException("Invalid seat IDs provided.");
//        }
//
//        // Validation: Are they all AVAILABLE?
//        boolean allAvailable = seats.stream()
//                .allMatch(seat -> seat.getStatus() == SeatStatus.AVAILABLE);
//
//        if (!allAvailable) {
//            throw new SeatPermanentlyBookedException("Some seats were already booked.");
//        }
//
//        // B. CALCULATE PRICING (Strategy Pattern)
//        double totalAmount = 0.0;
//        for (ShowSeat seat : seats) {
//            totalAmount += pricingService.calculatePrice(seat);
//        }
//
//        // C. CREATE BOOKING RECORD (PENDING)
//        Booking booking = new Booking();
//        booking.setUserId(userId);
//        booking.setShowId(showId);
//        booking.setTotalAmount(totalAmount);
//        booking.setStatus(BookingStatus.PENDING); // Waiting for Payment
//        booking.setCreatedAt(LocalDateTime.now());
//
//        Booking savedBooking = bookingRepo.save(booking);
//
//        // D. UPDATE INVENTORY (The "Visual" Lock)
//        // We mark them LOCKED so search queries exclude them.
//        for (ShowSeat seat : seats) {
//            seat.setStatus(SeatStatus.LOCKED);
//            seat.setBookingId(savedBooking.getId());
//            // seat.setVersion(seat.getVersion() + 1); // JPA handles this automatically
//        }
//        showSeatRepo.saveAll(seats);
//
//        return savedBooking;
//    }
//
//
//    // =========================================================================
//    // 🟡 PHASE 2: CONFIRM BOOKING (Payment Success Webhook)
//    // =========================================================================
//
//    @Transactional
//    public void confirmBooking(String bookingId, String transactionId) {
//
//        // 1. IDEMPOTENCY CHECK
//        // If Stripe sends the webhook twice, we must not process twice.
//        Booking booking = bookingRepo.findById(bookingId)
//                .orElseThrow(() -> new BookingNotFoundException(bookingId));
//
//        if (booking.getStatus() == BookingStatus.CONFIRMED) {
//            return; // Already processed, silent success.
//        }
//
//        // 2. UPDATE BOOKING STATUS
//        booking.setStatus(BookingStatus.CONFIRMED);
//        booking.setTransactionId(transactionId);
//        bookingRepo.save(booking);
//
//        // 3. PERMANENTLY BOOK SEATS
//        List<ShowSeat> seats = showSeatRepo.findByBookingId(bookingId);
//        for (ShowSeat seat : seats) {
//            seat.setStatus(SeatStatus.BOOKED);
//        }
//        showSeatRepo.saveAll(seats);
//
//        // 4. TRANSACTIONAL OUTBOX (Reliability Pattern)
//        // Instead of sending email here (which might fail), we insert an event.
//        OutboxEvent event = new OutboxEvent();
//        event.setAggregateId(bookingId);
//        event.setType("BOOKING_CONFIRMED");
//        event.setPayload(JsonUtils.toJson(booking)); // Serialize booking data
//        event.setStatus("PENDING");
//
//        outboxRepo.save(event);
//
//        // 5. RELEASE REDIS LOCKS
//        // We don't need the temporary lock anymore; the DB is now authoritative.
//        // (This can be done async, but good to clean up)
//        List<String> lockKeys = seats.stream()
//                .map(s -> "LOCK:SHOW:" + s.getShowId() + ":SEAT:" + s.getSeatId())
//                .collect(Collectors.toList());
//
//        lockProvider.releaseAll(lockKeys);
//    }
//
//
//    // =========================================================================
//    // 🔴 PHASE 3: HANDLE FAILURE (Payment Failed / Cancelled)
//    // =========================================================================
//
//    @Transactional
//    public void handleBookingFailure(String bookingId) {
//
//        Booking booking = bookingRepo.findById(bookingId).orElse(null);
//        if (booking == null || booking.getStatus() != BookingStatus.PENDING) {
//            return;
//        }
//
//        // 1. UPDATE BOOKING STATUS
//        booking.setStatus(BookingStatus.FAILED);
//        bookingRepo.save(booking);
//
//        // 2. REVERT INVENTORY
//        // Release the seats back to the pool
//        List<ShowSeat> seats = showSeatRepo.findByBookingId(bookingId);
//        for (ShowSeat seat : seats) {
//            seat.setStatus(SeatStatus.AVAILABLE);
//            seat.setBookingId(null);
//        }
//        showSeatRepo.saveAll(seats);
//
//        // 3. RELEASE REDIS LOCKS IMMEDIATELY
//        // Allow other users to buy these seats instantly
//        List<String> lockKeys = seats.stream()
//                .map(s -> "LOCK:SHOW:" + s.getShowId() + ":SEAT:" + s.getSeatId())
//                .collect(Collectors.toList());
//
//        lockProvider.releaseAll(lockKeys);
//    }
//}