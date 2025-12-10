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
    public List<EventInstance> getOccurrences(Event event, LocalDateTime start, LocalDateTime end) {
        List<EventInstance> eventInstances = new ArrayList<>();
        ZonedDateTime zonedStart = event.getStartTimeUtc().atZone(event.getZoneId());

        long monthToSkip = ChronoUnit.MONTHS.between(zonedStart,start.atZone(event.getZoneId()));

        if (monthToSkip < 0) monthToSkip = 0; // consider view point
        zonedStart = zonedStart.plusMonths(monthToSkip);


        while (zonedStart.isBefore(ZonedDateTime.of(end,event.getZoneId()))) {
            zonedStart = zonedStart.plusWeeks(1);
            eventInstances.add(new EventInstance(event.getId(),
                    event.getTitle(),zonedStart.toLocalDateTime(),zonedStart.plus(event.getDuration()).toLocalDateTime()));
        }
        return eventInstances;
    }
}