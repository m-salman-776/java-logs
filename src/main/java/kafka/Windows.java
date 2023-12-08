package kafka;

import jdk.jfr.Description;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.apache.kafka.streams.kstream.SlidingWindows;
import org.apache.kafka.streams.kstream.TimeWindows;

import java.time.Duration;

public class Windows {
    // if size < advance works as hopping window
    // if size == advance works as tumbling window
    public static TimeWindows getWindow(int size , int advance){
        Duration sizeDuration = Duration.ofMinutes(size);
        Duration advanceBy = Duration.ofMinutes(advance);
        TimeWindows timeWindow = TimeWindows.ofSizeWithNoGrace(sizeDuration).advanceBy(advanceBy);
//        TimeWindows.ofSizeWithNoGrace(sizeDuration);
        return timeWindow;
    }
    public static SessionWindows getSessionWindow(int inActivityGap){
        // keeps on growing till events are coming if no see till inactivityGap window closes
        Duration inactivity = Duration.ofMinutes(inActivityGap);
        return SessionWindows.ofInactivityGapWithNoGrace(inactivity);
    }

    public static SlidingWindows getSlidingWindow(int size){
        Duration duration = Duration.ofMinutes(size);
        return SlidingWindows.ofTimeDifferenceWithNoGrace(duration);
    }
}
