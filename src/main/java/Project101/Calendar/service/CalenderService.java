package Project101.Calendar.service;

import Project101.Calendar.factory.RecurrenceStrategyFactory;
import Project101.Calendar.model.Event;
import Project101.Calendar.model.EventInstance;
import Project101.Calendar.model.MeetException;
import Project101.Calendar.repository.MockEventRepository;
import Project101.Calendar.strategy.RecurrenceStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CalenderService {
    MockEventRepository repository;
    ConflictService conflictService;
    public CalenderService(){
        conflictService = new ConflictService(new MockEventRepository());
    }
    public Event createEvent(List<String> userIds, LocalDateTime start, LocalDateTime end){
        for (String user_id : userIds){
            if (conflictService.hasConflict(user_id,start,end)){

            }
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        Event newEvenr = new Event("title","somed","me",start,null);
        userIds.forEach(user -> repository.addEvent(user,newEvenr));
        return newEvenr;
    }
    public List<EventInstance> generateView(List<Event> events, List<MeetException> meetExceptions) {
        List<EventInstance> finalView = new ArrayList<>();

        // Map MeetExceptionss for fast lookup: Key = OriginalStartTime
        Map<LocalDateTime, MeetException> MeetExceptionsMap = meetExceptions.stream()
                .collect(Collectors.toMap(MeetException::getOriginalStart, e -> e));

        for (Event event : events) {
            // 1. Expand the Rule (e.g., Daily -> Nov 1, Nov 2... Nov 30)
            RecurrenceStrategy strategy = RecurrenceStrategyFactory.getStrategy(event.getRecurrenceRule().getFrequency());
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
        }
        return finalView;
    }
}