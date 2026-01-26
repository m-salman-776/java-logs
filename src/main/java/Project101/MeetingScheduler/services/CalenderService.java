package Project101.MeetingScheduler.services;

import Project101.MeetingScheduler.model.RecurrenceRule;
import Project101.MeetingScheduler.strategy.RecurrenceStrategyFactory;
import Project101.MeetingScheduler.model.Event;
import Project101.MeetingScheduler.model.EventInstance;
import Project101.MeetingScheduler.model.MeetException;
import Project101.MeetingScheduler.strategy.RecurrenceStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CalenderService {
    EventService eventService;
    ConflictService conflictService;
    UserService userService;

    public CalenderService(EventService eventService,ConflictService conflictService,UserService userService){
        this.conflictService = conflictService;
        this.eventService = eventService;
        this.userService = userService;
    }
    public Event createEvent(long organiserId,String title,String description,List<Long> attendee, LocalDateTime start, Duration duration){
        LocalDateTime endTime = start.plus(duration);
        for (long user_id : attendee){
            List<EventInstance> conflictList = conflictService.hasConflict(user_id, start, endTime);
            if (!conflictList.isEmpty()){
                System.out.println("Hash Conflict" + user_id + "  " + conflictList);
            }
        }
        Event event= eventService.createEvent(organiserId,title,description,start,duration);
        event.addAttendee(attendee);
        sendInvitationToAttendee(event.getId(),attendee);
        return event;
    }
    private void sendInvitationToAttendee(long eventId,List<Long> attendee){
        for (long userId : attendee){
            userService.addEventInvitation(eventId,userId);
        }
    }
    public Event addRecurrence(long eventId,RecurrenceRule recurrenceRule){
        Event event = eventService.eventMap.get(eventId);
        event.updateRecurrenceRule(recurrenceRule);
        return event;
    }
    public List<EventInstance> viewCalender(long userId,LocalDateTime start,LocalDateTime end){
        List<EventInstance> instances = new ArrayList<>();
//        for (Event event : eventService.getUserEvent())
        return instances;
    }
    public List<EventInstance> generateView(Event event, List<MeetException> meetExceptions) {
        List<EventInstance> finalView = new ArrayList<>();
        if (event.getRecurrenceRule() == null){
            System.out.println("Event is not recurring");
            return finalView;
        }

        // Map meet Exception for fast lookup: Key = OriginalStartTime
        Map<LocalDateTime, MeetException> MeetExceptionsMap = meetExceptions.stream()
                .collect(Collectors.toMap(MeetException::getOriginalStart, e -> e));
            // 1. Expand the Rule (e.g., Daily -> Nov 1, Nov 2... Nov 30)
        RecurrenceStrategy strategy = RecurrenceStrategyFactory.getStrategy(event.getRecurrenceRule().getPattern());
        assert strategy != null;
        List<EventInstance> rawInstances = strategy.getOccurrences(event, event.getStartTimeUtc(), event.getRecurrenceRule().getEndsOnUtc());

        for (EventInstance eventInstance : rawInstances) {

            // 2. CHECK: Is there an MeetExceptions for this specific date?
            if (MeetExceptionsMap.containsKey(eventInstance)) {
                MeetException ex = MeetExceptionsMap.get(eventInstance);

                if (ex.isCancelled()) {
                    continue; // SKIP this instance (It was deleted)
                }

                if (ex.getNewStart() != null) {
                    // ADD the moved instance instead
                    finalView.add(new EventInstance(ex.getEventId(),ex.getTittle(),ex.getNewStart(), ex.getNewStart().plus(event.getDuration())));
                    continue;
                }
            }

            // 3. No MeetExceptions? Add the regular instance.
            finalView.add(new EventInstance(eventInstance.getEventId(),eventInstance.getTitle(),eventInstance.getStart(), eventInstance.getEnd()));
        }
        return finalView;
    }
}