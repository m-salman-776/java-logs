package Project101.MeetingScheduler.strategy;

import Project101.MeetingScheduler.model.Event;
import Project101.MeetingScheduler.model.EventInstance;
import Project101.MeetingScheduler.model.RecurrenceRule;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DailyRecurrenceStrategy implements RecurrenceStrategy {
    @Override
    public List<EventInstance> getOccurrences(Event event, LocalDateTime queryStart, LocalDateTime queryEnd) {
        List<EventInstance> instances = new ArrayList<>();
        RecurrenceRule rule = event.getRecurrenceRule();
        
        LocalDateTime start = event.getStartTimeUtc();
        LocalDateTime ruleEnd = rule.getEndsOnUtc();
        int interval = rule.getInterval(); // e.g., every 2 days

        if (interval <= 0) interval = 1;

        /*
         * Optimized Logic:
         * 1. Calculate how many 'interval' blocks we can skip to get close to the queryStart date.
         *    This avoids iterating from a very old start date.
         * 2. Start iterating from the calculated date.
         * 3. Stop if we exceed the queryEnd or the rule's endsOnUtc.
         */

        // 1. Calculate days to skip
        long daysToSkip = 0;
        if (queryStart.isAfter(start)) {
            long daysBetween = ChronoUnit.DAYS.between(start, queryStart);
            // Align to the interval (e.g., if interval is 2, skip 0, 2, 4...)
            // We use integer division to find the number of full intervals passed
            // Then multiply by interval to get the exact number of days to add
            daysToSkip = (daysBetween / interval) * interval;
            
            // If the calculated start is still before queryStart (due to integer division floor),
            // we might need to add one more interval if we want to start strictly >= queryStart.
            // However, it's safer to start slightly before or exactly at the aligned interval 
            // and let the loop condition handle the exact check.
        }

        // 2. Start iterating from the calculated date
        LocalDateTime current = start.plusDays(daysToSkip);

        while (current.isBefore(queryEnd) || current.isEqual(queryEnd)) {
            // Check if we've passed the rule's end date
            if (ruleEnd != null && current.isAfter(ruleEnd)) {
                break;
            }

            // Check if the current occurrence is within the query window
            // (It might be slightly before queryStart due to the jump logic, so we check)
            if (!current.isBefore(queryStart)) {
                LocalDateTime instanceEnd = current.plus(event.getDuration());
                instances.add(new EventInstance(event.getId(), event.getTitle(), current, instanceEnd));
            }

            // Move to the next occurrence
            current = current.plusDays(interval);
        }
        
        return instances;
    }
}
