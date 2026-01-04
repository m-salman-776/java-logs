package Project101.Calendar.strategy;

import Project101.Calendar.model.RecurrenceRule;
// Import other strategies as they are created

public class RecurrenceStrategyFactory {
    public static RecurrenceStrategy getStrategy(RecurrenceRule.Frequency frequency) {

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
