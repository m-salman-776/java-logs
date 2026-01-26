package Project101.MeetingScheduler.repository;

import Project101.MeetingScheduler.model.Event;
import Project101.MeetingScheduler.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {
    List<Event> getEventsForUsers(User users, LocalDateTime start, LocalDateTime end);
}
