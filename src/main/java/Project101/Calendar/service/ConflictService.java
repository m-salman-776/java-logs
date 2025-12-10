package Project101.Calendar.service;

import Project101.Calendar.factory.RecurrenceStrategyFactory;
import Project101.Calendar.model.Event;
import Project101.Calendar.model.EventInstance;
import Project101.Calendar.model.User;
import Project101.Calendar.repository.EventRepository;
import Project101.Calendar.strategy.RecurrenceStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class ConflictService {
    EventRepository eventRepository;

    public ConflictService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }
    public boolean hasConflict(String user_id, LocalDateTime start,LocalDateTime end){
        List<Event> userEvent = eventRepository.getEventsForUsers(new User(user_id),start,end);
        for (Event event : userEvent){
            RecurrenceStrategy recurrenceStrategy = RecurrenceStrategyFactory.getStrategy(event.getRecurrenceRule().getFrequency());
            for (EventInstance eventInstance :recurrenceStrategy.getOccurrences(event,start,end)){
                if (eventInstance.getStart().isBefore(end) && eventInstance.getEnd().isAfter(start)){
                    return true;
                }
            }
        }
        return false;
    }
}
