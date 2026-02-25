package Project101.MeetingScheduler.strategy;

import Project101.MeetingScheduler.model.Event;
import Project101.MeetingScheduler.model.EventInstance;
import Project101.MeetingScheduler.model.RecurrenceRule;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WeeklyRecurrenceStrategy implements RecurrenceStrategy {
    @Override
    public List<EventInstance> getOccurrences(Event event, LocalDateTime queryStart, LocalDateTime queryEnd) {
        List<EventInstance> instances = new ArrayList<>();
        RecurrenceRule rule = event.getRecurrenceRule();
        
        LocalDateTime start = event.getStartTimeUtc();
        LocalDateTime ruleEnd = rule.getEndsOnUtc();
        int interval = rule.getInterval(); // e.g., every 2 weeks
        List<DayOfWeek> byDay = rule.getByDay(); // e.g., Mon, Wed

        if (interval <= 0) interval = 1;

        // If byDay is empty, default to the day of the start time
        if (byDay == null || byDay.isEmpty()) {
            byDay = List.of(start.getDayOfWeek());
        }

        /*
         * Logic for Weekly Recurrence (e.g., "Every 2 weeks on Mon, Wed"):
         * 1. Anchor to a fixed point: Find the Monday of the week where the event starts ("Week 0").
         * 2. Optimize: Calculate how many 'interval' blocks we can skip to get close to the queryStart date.
         *    This avoids iterating from a very old start date.
         * 3. Iterate week by week: Start from the calculated week and jump by 'interval' weeks in each loop.
         * 4. Generate instances: For each valid week, iterate through the specified days (Mon, Wed) and create instances.
         * 5. Validate each instance:
         *    - Must not be before the original event start time.
         *    - Must not be after the rule's end date.
         *    - Must fall within the [queryStart, queryEnd] window.
         */

        // 1. Find the Monday of the week where the event starts.
        /*
            DayOfWeek.MONDAY.getValue()    = 1
            DayOfWeek.TUESDAY.getValue()   = 2
            ...
            DayOfWeek.SUNDAY.getValue()    = 7
        *
        * */
        LocalDateTime startWeekMonday = start.minusDays(start.getDayOfWeek().getValue() - 1);

        // 2. Calculate how many weeks we need to skip to get close to queryStart.
        long weeksToSkip = 0;
        if (queryStart.isAfter(start)) {
            long weeksBetween = ChronoUnit.WEEKS.between(startWeekMonday, queryStart);
            weeksToSkip = (weeksBetween / interval) * interval;
        }

        // 3. Start iterating from the calculated week
        LocalDateTime currentWeekMonday = startWeekMonday.plusWeeks(weeksToSkip);

        while (currentWeekMonday.isBefore(queryEnd) || currentWeekMonday.isEqual(queryEnd)) {
            // Stop if we've passed the rule's end date
            if (ruleEnd != null && currentWeekMonday.isAfter(ruleEnd)) {
                break;
            }

            // 4. Generate instances for the specified days in this week
            for (DayOfWeek day : byDay) {
                // Calculate the date for this day and set the time from the original event
                LocalDateTime instanceDate = currentWeekMonday.plusDays(day.getValue() - 1)
                        .withHour(start.getHour())
                        .withMinute(start.getMinute())
                        .withSecond(start.getSecond())
                        .withNano(start.getNano());
                
                // 5. Validate the generated instance
                if (instanceDate.isBefore(start)) continue;
                if (ruleEnd != null && instanceDate.isAfter(ruleEnd)) continue;
                
                if (!instanceDate.isBefore(queryStart) && !instanceDate.isAfter(queryEnd)) {
                     LocalDateTime instanceEnd = instanceDate.plus(event.getDuration());
                     instances.add(new EventInstance(event.getId(), event.getTitle(), instanceDate, instanceEnd));
                }
            }

            // Jump to the next interval
            currentWeekMonday = currentWeekMonday.plusWeeks(interval);
        }
        
        return instances;
    }
}
