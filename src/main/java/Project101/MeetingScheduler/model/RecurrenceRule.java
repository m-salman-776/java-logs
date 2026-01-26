package Project101.MeetingScheduler.model;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RecurrenceRule {
    public enum Pattern { DAILY, WEEKLY, MONTHLY, YEARLY }
    int interval; // 2  (every two week)
    List<DayOfWeek> byDay ; // [monday,friday]
    LocalDateTime start;
    long duration;
    LocalDateTime endsOnUtc; // repeat thill here only
    Pattern pattern;

    // Constructor, getters...
    public RecurrenceRule(int interval, List<DayOfWeek> byDay, LocalDateTime endsOnUtc, Pattern frequency) {
        //A 1-hour meeting every 2 weeks on Monday and Wednesday, for 10 occurrences.
        this.pattern =frequency;
        this.interval = interval;
        this.byDay = byDay;
        this.endsOnUtc = endsOnUtc;
    }

}
