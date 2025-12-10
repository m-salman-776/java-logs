package Project101.Calendar.model;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Event {
    String id;
    String title;
    String description;
    String organizerId;

    LocalDateTime startTimeUtc;
    Duration duration;

    RecurrenceRule recurrenceRule;

    List<String> attendee;

    ZoneId zoneId;

    // Constructor, getters...
    public Event(String title, String description, String organizerId, LocalDateTime startTimeUtc, Duration duration) {
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.startTimeUtc = startTimeUtc;
        this.duration = duration;
        this.attendee = new ArrayList<>();
    }
    public Event(String title, String description, String organizerId, LocalDateTime startTimeUtc, Duration duration,RecurrenceRule recurrenceRule) {
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.startTimeUtc = startTimeUtc;
        this.duration = duration;
        this.recurrenceRule=recurrenceRule;

    }
}
