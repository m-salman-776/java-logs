package Project101.Calendar.repository;

import Project101.Calendar.model.Event;
import Project101.Calendar.model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {
    List<Event> getEventsForUsers(User users, LocalDateTime start, LocalDateTime end);
}
