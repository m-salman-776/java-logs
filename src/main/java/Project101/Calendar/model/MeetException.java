package Project101.Calendar.model;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class MeetException {
    String eventId;
    String tittle;
    LocalDateTime originalStart;
    LocalDateTime newStart;
    boolean isCancelled;
}
