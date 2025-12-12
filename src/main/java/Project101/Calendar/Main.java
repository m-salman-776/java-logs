package Project101.Calendar;

import Project101.Calendar.model.Event;
import Project101.Calendar.services.CalenderService;
import Project101.Calendar.services.ConflictService;
import Project101.Calendar.services.EventService;
import Project101.Calendar.services.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String []args){

        EventService eventService = new EventService();
        UserService userService = new UserService();
        userService.addUser("alice");
        userService.addUser("bob");
        userService.addUser("kane");
        ConflictService conflictService = new ConflictService(eventService);
        CalenderService calenderService = new CalenderService(eventService,conflictService,userService);
        LocalDateTime localDateTime = LocalDateTime.now();
        Duration duration = Duration.ofHours(1);
        Event event1 = calenderService.createEvent(1,"test event 01","this is for test", List.of(1L,2L,3L), localDateTime,duration);
        Event event2 = calenderService.createEvent(1,"test event 02","this is for test", List.of(1L,2L,3L), LocalDateTime.now().plusMinutes(30),duration);
//        Event event1 = calenderService.createEvent(1,"test event 02","this is for test", List.of(1L,2L,3L), localDateTime,duration);
//        eventService.addAttendee()
        System.out.println("done");
    }
}