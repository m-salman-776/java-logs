package Project101.MeetingScheduler;
import Project101.MeetingScheduler.model.RecurrenceRule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MeetingScheduler {
    Map<Integer,MeetingRoom> meetingRoomMap;
    Map<Integer,Meeting> meetingMap;
    Map<Integer,Calender> userMap ;
    AtomicInteger meetingIdGenerator = new AtomicInteger(0);
    int meetingRoomId=0;
    public MeetingScheduler(){
        meetingRoomMap = new ConcurrentHashMap<>();
        userMap = new ConcurrentHashMap<>();
        meetingMap = new ConcurrentHashMap<>();
    }
    public synchronized void addMeetingRoom(String name,int capacity){
        this.meetingRoomId += 1;
        this.meetingRoomMap.put(meetingRoomId,new MeetingRoom(meetingRoomId,capacity,name));
    }
    public void addUser(int id,String name){
        this.userMap.put(id,new Calender());
    }
    public synchronized void removeMeetingRoom(int id){
        meetingRoomMap.remove(id);
    }
    public Meeting createMeeting(int organiser, LocalDateTime start, LocalDateTime end, List<Integer> participants, String title, int roomId){
        MeetingRoom meetingRoom = meetingRoomMap.get(roomId);
        if (meetingRoom == null){
            System.out.println("Meeting Room Not available");
            return null;
        }
        if (!meetingRoom.book(start,end, participants.size())){
            System.out.println("Meeting Room Not available");
            return null;
        }

        Meeting meeting = new Meeting(meetingIdGenerator.incrementAndGet(),organiser,roomId,title,start,end);
        for (Integer participant: participants){
            if (!userMap.containsKey(participant)) {
                System.out.printf("User %d not available\n",participant);
                continue;
            }
            userMap.get(participant).addMeeting(meeting.id);
        }
        userMap.get(organiser).addMeeting(meeting.id);
        meeting.addParticipants(participants);
        this.meetingMap.put(meeting.id,meeting);
        return meeting;
    }
    public void createMeeting(int organiser, LocalDateTime start, LocalDateTime end, List<Integer> participants, String title, int roomId, RecurrenceRule recurrenceRule){
//        if (meetingRoomMap.get(meetingRoomId) == null || meetingRoomMap.get(meetingRoomId))
    }
    public boolean cancelMeeting(int meetingId,int organiser){
        Meeting meeting = meetingMap.get(meetingId);
        if (meeting == null) {
            System.out.printf("Meeting with %d is not available\n",meetingId);
            return false;
        }
        if (meeting.organiser != organiser){
            return false;
        }
        for (int participant : meeting.participants.keySet()){
            userMap.get(participant).removeMeeting(meetingId);
        }
        userMap.get(meeting.organiser).removeMeeting(meetingId);

        meetingRoomMap.get(meeting.meetingRoomId).releaseRoom(meeting.start,meeting.end);
        meetingMap.remove(meetingId);
        return true;
    }
    public boolean addParticipants(int meetingId,List<Integer>participants){
        Meeting meeting = meetingMap.get(meetingId);
        meeting.addParticipants(participants);
        for (int participant: participants){
            userMap.get(participant).addMeeting(meetingId);
        }
        return true;
    }
    public boolean removeParticipants(int meetingId,List<Integer> participants){
        Meeting meeting = meetingMap.get(meetingId);
        meeting.removeParticipants(participants);
        for (int participant: participants){
            userMap.get(participant).removeMeeting(meetingId);
        }
        return true;
    }
    public void updateParticipation(int meetingId, int userId, RSVPStatus status){
        Meeting meeting = meetingMap.get(meetingId);
        if (meeting == null){
            return;
        }
        meeting.updateParticipationStatus(userId,status);
        if (status == RSVPStatus.REJECTED){
            userMap.get(userId).removeMeeting(meetingId);
        }
    }
    public Map<Integer,RSVPStatus> getParticipationStatus(int meetingId){
        return meetingMap.get(meetingId).participants;
    }
    public Calender viewCalender(int userId){
        return userMap.get(userId);
    }
}
