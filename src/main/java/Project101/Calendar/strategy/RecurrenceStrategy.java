package Project101.Calendar.strategy;

import Project101.Calendar.model.Event;
import Project101.Calendar.model.EventInstance;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Defines the contract for generating event occurrences based on a recurrence rule.
 */
public interface RecurrenceStrategy {
    List<EventInstance> getOccurrences(Event event, LocalDateTime start, LocalDateTime end);
}
