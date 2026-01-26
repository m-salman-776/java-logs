//package Project101.Calendar.repository;
//
//import Project101.Calendar.model.RecurrenceRule;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MockRecurrenceRuleRepository implements RecurrenceRuleRepository {
//    private final Map<String, RecurrenceRule> rules = new HashMap<>();
//    public void addRule(RecurrenceRule rule) { this.rules.put(rule.getEventId(), rule); }
//    @Override
//    public RecurrenceRule getRuleForEvent(String eventId) {
//        return rules.get(eventId);
//    }
//}