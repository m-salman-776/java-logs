package Project101.Calendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class EventInstance {
    String eventId;
    String title;

    LocalDateTime start;
    LocalDateTime end;

    boolean isCancelled;

    ZoneId zoneId;

    public EventInstance(String eventId, String title,LocalDateTime start, LocalDateTime end) {
        this.eventId =eventId;
        this.title = title;
        this.start =start;
        this.end = end;
    }
}
