package Project101.MeetingScheduler.strategy;

import Project101.MeetingScheduler.model.RecurrenceRule;
import ch.qos.logback.core.joran.sanity.Pair;

import java.time.LocalDateTime;
import java.util.List;

public class DailyStrategy implements RecurrenceStrategyV2{
    @Override
    public List<Pair<Long, Long>> generateInterval(RecurrenceRule recurrenceRule){
    return null;
    }
}
