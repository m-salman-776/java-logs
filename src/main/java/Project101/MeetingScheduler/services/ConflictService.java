package Project101.MeetingScheduler.services;

import Project101.MeetingScheduler.strategy.RecurrenceStrategyFactory;
import Project101.MeetingScheduler.model.Event;
import Project101.MeetingScheduler.model.EventInstance;
import Project101.MeetingScheduler.repository.EventRepository;
import Project101.MeetingScheduler.strategy.RecurrenceStrategy;

import java.time.LocalDateTime;
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
            RecurrenceStrategy recurrenceStrategy = RecurrenceStrategyFactory.getStrategy(event.getRecurrenceRule().getPattern());
            for (EventInstance eventInstance :recurrenceStrategy.getOccurrences(event,start,end)){
                if (eventInstance.getStart().isBefore(end) && eventInstance.getEnd().isAfter(start)){
                    conflictingEvent.add(eventInstance);
                }
            }
        }
        return conflictingEvent;
    }
}
