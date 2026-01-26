package Project101.MeetingScheduler.model;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Event {
    long id;
    String title;
    String description;
    long organizerId;
    LocalDateTime startTimeUtc;
    Duration duration;
    RecurrenceRule recurrenceRule;
    Set<Long> attendee;
    ZoneId zoneId;
    public Event(long id,long organizerId,String title, String description, LocalDateTime startTimeUtc, Duration duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.startTimeUtc = startTimeUtc;
        this.duration = duration;
        this.attendee = new HashSet<>();
    }
    public Event(long id,long organizerId,String title, String description, LocalDateTime startTimeUtc, Duration duration,RecurrenceRule recurrenceRule) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.startTimeUtc = startTimeUtc;
        this.duration = duration;
        this.recurrenceRule=recurrenceRule;
        this.attendee = Set.of(organizerId);
    }
    public boolean addAttendee(List<Long> attendee){
        this.attendee.addAll(attendee);
        return true;
    }
    public boolean removeAttendee(long attendeeId){
        this.attendee.remove(attendeeId);
        return true;
    }
    public boolean updateRecurrenceRule(RecurrenceRule recurrenceRule){
        this.recurrenceRule = recurrenceRule;
        return true;
    }
}
