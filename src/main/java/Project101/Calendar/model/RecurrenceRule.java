package Project101.Calendar.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class RecurrenceRule {
    public enum Frequency { DAILY, WEEKLY, MONTHLY, YEARLY }
    int interval; // 2  (every two week)
    List<DayOfWeek> byDay ; // [monday,friday]
    LocalDateTime endsOnUtc; // repeat thill here only
    Frequency frequency;

    // Constructor, getters...
    public RecurrenceRule(int interval, List<DayOfWeek> byDay, LocalDateTime endsOnUtc,Frequency frequency) {
        //A 1-hour meeting every 2 weeks on Monday and Wednesday, for 10 occurrences.
        this.frequency=frequency;
        this.interval = interval;
        this.byDay = byDay;
        this.endsOnUtc = endsOnUtc;
    }

}
