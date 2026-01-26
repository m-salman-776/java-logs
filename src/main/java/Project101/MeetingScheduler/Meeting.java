package Project101.MeetingScheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Meeting {
    int id;
    int organiser;
    int meetingRoomId;
    String title;
    LocalDateTime start;
    LocalDateTime end;
    // user -> status
    Map<Integer,RSVPStatus> participants;
    public Meeting(int id,int organiser,int roomId,String title,LocalDateTime start,LocalDateTime end){
        this.id = id;
        this.organiser = organiser;
        this.title = title;
        this.start = start;
        this.end = end;
        this.meetingRoomId = roomId;
        this.participants = new ConcurrentHashMap<>();
        this.participants.put(organiser,RSVPStatus.ACCEPTED);
    }
    public synchronized void addParticipants(List<Integer> participants){
        for (int participant: participants){
            this.participants.put(participant,RSVPStatus.PENDING);
        }
    }
    public synchronized void removeParticipants(List<Integer> participants){
        for (int participant: participants){
            this.participants.remove(participant);
        }
    }
    public synchronized void updateParticipationStatus(int participant,RSVPStatus status){
        this.participants.put(participant,status);
    }
}
