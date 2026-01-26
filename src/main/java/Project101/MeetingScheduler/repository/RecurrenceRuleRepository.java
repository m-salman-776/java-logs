package Project101.MeetingScheduler.repository;

import Project101.MeetingScheduler.model.RecurrenceRule;

public interface RecurrenceRuleRepository {
    RecurrenceRule getRuleForEvent(String eventId);
}
