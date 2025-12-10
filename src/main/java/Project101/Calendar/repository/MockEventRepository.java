package Project101.Calendar.repository;

import Project101.Calendar.model.Event;
import Project101.Calendar.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockEventRepository implements EventRepository {
    Map<String,List<Event>> userEvents;
    public void addEvent(String userId, Event event) {
        this.userEvents.computeIfAbsent(userId,k-> new ArrayList<>()).add(event);
    }
    @Override
    public List<Event> getEventsForUsers(User users, LocalDateTime start, LocalDateTime end) {
        // For this demo, we ignore the users/time and return all known events.
        // A real implementation would have a SQL query with WHERE user_id IN (...)
        return this.userEvents.getOrDefault(users.getId(),new ArrayList<>());
    }
}
