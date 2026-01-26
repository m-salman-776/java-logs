package Project101.MeetingScheduler.strategy;

import Project101.MeetingScheduler.model.RecurrenceRule;
import ch.qos.logback.core.joran.sanity.Pair;

import java.util.List;

public interface RecurrenceStrategyV2 {
    List<Pair<Long,Long>> generateInterval(RecurrenceRule recurrenceRule);
}
