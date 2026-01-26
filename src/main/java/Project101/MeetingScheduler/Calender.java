package Project101.MeetingScheduler;

import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.HashSet;
import java.util.Set;

public class Calender {
    // meeting id;
    Set<Integer> meetings;
    public Calender(){
        meetings = new HashSet<>();
    }
    public synchronized void addMeeting(int id){
        this.meetings.add(id);
    }
    public synchronized void removeMeeting(int id){
        this.meetings.remove(id);
    }
    public Set<Integer> getMeetingSet(){
        return this.meetings;
    }
}
