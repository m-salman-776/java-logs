package Project101.Calendar.services;

import Project101.Calendar.strategy.RecurrenceStrategyFactory;
import Project101.Calendar.model.Event;
import Project101.Calendar.model.EventInstance;
import Project101.Calendar.model.User;
import Project101.Calendar.repository.EventRepository;
import Project101.Calendar.strategy.RecurrenceStrategy;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConflictService {
    EventRepository eventRepository;
    EventService eventService;

    public ConflictService(EventService eventService){
        this.eventService = eventService;
    }
    public List<EventInstance> hasConflict(long user_id, LocalDateTime start, LocalDateTime end){
        List<Event> userEvent = eventService.getUserEvent(user_id);
        List<EventInstance> conflictingEvent = new ArrayList<>();
        for (Event event : userEvent){
            if (event.getRecurrenceRule() == null) continue;
            RecurrenceStrategy recurrenceStrategy = RecurrenceStrategyFactory.getStrategy(event.getRecurrenceRule().getFrequency());
            for (EventInstance eventInstance :recurrenceStrategy.getOccurrences(event,start,end)){
                if (eventInstance.getStart().isBefore(end) && eventInstance.getEnd().isAfter(start)){
                    conflictingEvent.add(eventInstance);
                }
            }
        }
        return conflictingEvent;
    }
}
