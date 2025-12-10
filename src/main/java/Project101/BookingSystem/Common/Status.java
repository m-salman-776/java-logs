package Project101.BookingSystem.Common;

public enum Status {
    REQUESTED, // temporarily held
    CONFIRMED, // Payment Completed
    CANCELLED, // User Cancelled
    FAILED // Payment failure or timed out
}
