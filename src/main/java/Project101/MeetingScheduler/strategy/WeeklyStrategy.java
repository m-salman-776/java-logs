package Project101.MeetingScheduler.strategy;

import Project101.MeetingScheduler.model.Event;
import Project101.MeetingScheduler.model.EventInstance;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class WeeklyStrategy implements RecurrenceStrategy {
    @Override
    public List<EventInstance> getOccurrences(Event event, LocalDateTime windowStart, LocalDateTime windowEnd) {
        List<EventInstance> eventInstances = new ArrayList<>();
        ZonedDateTime zonedStart = event.getStartTimeUtc().atZone(event.getZoneId());

        long weeksToSkip = ChronoUnit.WEEKS.between(zonedStart,windowStart.atZone(event.getZoneId()));
        Instant instant = Instant.now();
        LocalDate d1 = LocalDate.now();
        LocalDate d2 = LocalDate.now();
        d1.plusDays(1);

        LocalDate d  = LocalDate.now();
        LocalDateTime dt = LocalDateTime.now();
        long a = Duration.between(d1,d2).get(ChronoUnit.HOURS);



        if (weeksToSkip < 0) weeksToSkip = 0; // consider view point
        zonedStart = zonedStart.plusMonths(weeksToSkip);

        while (zonedStart.isBefore(windowEnd.atZone(event.getZoneId()))) {
            zonedStart = zonedStart.plusWeeks(1);
            eventInstances.add(new EventInstance(event.getId(),
                    event.getTitle(),zonedStart.toLocalDateTime(),zonedStart.plus(event.getDuration()).toLocalDateTime()));
        }
        return eventInstances;
    }
}