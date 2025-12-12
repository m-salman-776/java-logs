package Project101.Calendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetException {
    long eventId;
    String tittle;
    LocalDateTime originalStart;
    LocalDateTime newStart;
    boolean isCancelled;
}
