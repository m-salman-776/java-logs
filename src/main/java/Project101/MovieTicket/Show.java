package Project101.MovieTicket;

import lombok.Getter;

import java.util.Date;
@Getter
public class Show {
    String showId;
    Movie movie;
    Date startTime;
    Date endTime;
}
