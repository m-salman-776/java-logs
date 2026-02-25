package Project101.MeetingScheduler.strategy;

import Project101.MeetingScheduler.model.RecurrenceRule;

public class RecurrenceStrategyFactory {
    public static RecurrenceStrategy getStrategy(RecurrenceRule.Pattern pattern) {
        switch (pattern) {
            case DAILY:
                return new DailyRecurrenceStrategy();
            case WEEKLY:
                return new WeeklyRecurrenceStrategy();
            // Add cases for MONTHLY, YEARLY as they are implemented
            default:
                throw new IllegalArgumentException("Unsupported recurrence pattern: " + pattern);
        }
    }
}
