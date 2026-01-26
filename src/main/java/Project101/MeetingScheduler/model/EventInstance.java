package Project101.MeetingScheduler.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Getter
public class EventInstance {
    long eventId;
    String title;
    LocalDateTime start;
    LocalDateTime end;
    boolean isCancelled;
    ZoneId zoneId;
    Set<Long> conflictingEvents;

    public EventInstance(long eventId, String title,LocalDateTime start, LocalDateTime end) {
        this.eventId =eventId;
        this.title = title;
        this.start =start;
        this.end = end;
        this.conflictingEvents = new HashSet<>();
    }
}
