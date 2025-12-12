package Project101.Calendar.strategy;

import Project101.Calendar.model.Event;
import Project101.Calendar.model.EventInstance;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WeeklyStrategy implements RecurrenceStrategy {
    @Override
    public List<EventInstance> getOccurrences(Event event, LocalDateTime windowStart, LocalDateTime windowEnd) {
        List<EventInstance> eventInstances = new ArrayList<>();
        ZonedDateTime zonedStart = event.getStartTimeUtc().atZone(event.getZoneId());

        long weeksToSkip = ChronoUnit.WEEKS.between(zonedStart,windowStart.atZone(event.getZoneId()));

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