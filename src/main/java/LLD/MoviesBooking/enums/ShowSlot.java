package LLD.MoviesBooking.enums;

import java.time.LocalTime;

public enum ShowSlot {
    MORNING("Morning", LocalTime.of(9, 0), LocalTime.of(12, 0)),
    AFTERNOON("Afternoon", LocalTime.of(12, 0), LocalTime.of(3, 0)),
    EVENING("Evening", LocalTime.of(3, 0), LocalTime.of(6, 0)),
    NIGHT("Night", LocalTime.of(6, 0), LocalTime.of(9, 0)),
    LATE_NIGHT("Late Night", LocalTime.of(9, 0), LocalTime.of(2, 50));
    private final String displayName;
    private final LocalTime startTime;
    private final LocalTime endTime;

    ShowSlot(String displayName, LocalTime startTime, LocalTime endTime) {
        this.displayName = displayName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
