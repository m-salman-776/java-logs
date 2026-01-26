package Project101.MeetingScheduler.strategy;

import Project101.MeetingScheduler.model.RecurrenceRule;
// Import other strategies as they are created

public class RecurrenceStrategyFactory {
    public static RecurrenceStrategy getStrategy(RecurrenceRule.Pattern frequency) {

        switch (frequency) {
            case WEEKLY:
                return new WeeklyStrategy();
            // case DAILY:
            //     return new DailyStrategy();
            // case MONTHLY:
            //     return new MonthlyStrategy();
            // case YEARLY:
            //     return new YearlyStrategy();
            default:
                return null;
        }
    }
}
