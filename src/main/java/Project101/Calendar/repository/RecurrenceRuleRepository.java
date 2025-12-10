package Project101.Calendar.repository;

import Project101.Calendar.model.RecurrenceRule;

public interface RecurrenceRuleRepository {
    RecurrenceRule getRuleForEvent(String eventId);
}
