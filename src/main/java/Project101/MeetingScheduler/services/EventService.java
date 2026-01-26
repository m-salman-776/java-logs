package Project101.MeetingScheduler.services;

import Project101.MeetingScheduler.model.Event;
import Project101.MeetingScheduler.model.RecurrenceRule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class EventService {
    Map<Long, Event> eventMap;
    Map<Long, Set<Long>> userEvent;
    long eventId;
    public EventService(){
        eventMap = new HashMap<>();
        eventId = 0;
        userEvent = new HashMap<>();
    }
    public Event createEvent(long organiserId, String title, String description, LocalDateTime startTime, Duration duration){
        this.eventId += 1;
        Event event = new Event(this.eventId,organiserId,title,description,startTime,duration);
        eventMap.put(this.eventId,event);
        userEvent.computeIfAbsent(organiserId,k -> new HashSet<>()).add(eventId);
        return event;
    }
    public Event createEvent(long organiserId, String title, String description, LocalDateTime startTime, Duration duration, RecurrenceRule recurrenceRule){
        Event event = this.createEvent(organiserId,title,description,startTime,duration);
        event.updateRecurrenceRule(recurrenceRule);
        return event;
    }
    public boolean addAttendee(long eventId,List<Long> attendeeList){
        Event event = eventMap.get(eventId);
        for (long attendee : attendeeList){
            userEvent.computeIfAbsent(attendee,k-> new HashSet<>()).add(eventId);
        }
        return this.eventMap.get(eventId).addAttendee(attendeeList);
    }
    public boolean removeAttendee(long eventId,long attendee){
        userEvent.get(attendee).remove(eventId);
        return this.eventMap.get(eventId).removeAttendee(attendee);
    }
    public List<Event> getUserEvent(long userId){
        List<Event> userEventList = new ArrayList<>();
        for (long eventId : userEvent.getOrDefault(userId,new HashSet<>())) {
            Event event = eventMap.get(eventId);
            if (event != null)
                userEventList.add(event);
        }
        return userEventList;
    }
}
