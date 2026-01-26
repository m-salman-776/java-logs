package Project101.MeetingScheduler;

import Project101.MeetingScheduler.model.RecurrenceRule;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MeetingRoom {
    int id;
    TreeMap<Long,Long> bookedSlots;
    List<RecurrenceRule> recurrenceRuleList;
    int capacity;
    String name;
    public MeetingRoom(int id,int capacity,String name){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        bookedSlots = new TreeMap<>();
    }
    private synchronized boolean isAvailable(LocalDateTime start,LocalDateTime end){
        Long startEpoc = start.toEpochSecond(ZoneOffset.UTC);
        Long endEpoc = end.toEpochSecond(ZoneOffset.UTC);
        Map.Entry<Long,Long> prev = bookedSlots.floorEntry(startEpoc);
        if (prev != null && prev.getValue() > startEpoc) {
            return false;
        }
        Map.Entry<Long,Long> next = bookedSlots.ceilingEntry(startEpoc);
        if (next != null && endEpoc > next.getKey()){
            return false;
        }
        return true;
    }
    public synchronized boolean book(LocalDateTime start,LocalDateTime end,int participantsSize){
        if (!isAvailable(start,end) || this.capacity < participantsSize){
            return false;
        }

        long startEpoc = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoc = end.toEpochSecond(ZoneOffset.UTC);
        bookedSlots.put(startEpoc,endEpoc);
        return true;
    }
    public synchronized void releaseRoom(LocalDateTime start, LocalDateTime end){
        long startEpoc = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoc = end.toEpochSecond(ZoneOffset.UTC);
        if (bookedSlots.containsKey(startEpoc) && bookedSlots.get(startEpoc) == endEpoc) {
            bookedSlots.remove(startEpoc);
        }
    }
}
