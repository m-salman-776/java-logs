package Project101.MeetingScheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MeetingSchedulerTest {

    private MeetingScheduler scheduler;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        scheduler = new MeetingScheduler();
        now = LocalDateTime.now();

        // Setup Users
        scheduler.addUser(1, "Organizer");
        scheduler.addUser(2, "Alice");
        scheduler.addUser(3, "Bob");
        scheduler.addUser(4, "Charlie");
    }

    @Test
    void testRoomManagement() {
        // 1. Add Rooms
        scheduler.addMeetingRoom("Room A", 10);
        scheduler.addMeetingRoom("Room B", 5);
        assertEquals(2, scheduler.meetingRoomMap.size(), "Should have 2 rooms");

        // 2. Remove Room
        scheduler.removeMeetingRoom(1); // IDs start at 1
        assertEquals(1, scheduler.meetingRoomMap.size(), "Should have 1 room after removal");
        assertNull(scheduler.meetingRoomMap.get(1), "Room 1 should be gone");
        assertNotNull(scheduler.meetingRoomMap.get(2), "Room 2 should exist");
    }

    @Test
    void testCreateMeetingSuccess() {
        scheduler.addMeetingRoom("Conference Hall", 20);
        List<Integer> participants = Arrays.asList(2, 3);

        Meeting meeting = scheduler.createMeeting(1, now, now.plusHours(1), participants, "Q1 Planning", 1);

        assertNotNull(meeting);
        assertEquals(1, meeting.id);
        
        // Verify Calendars
        assertTrue(scheduler.viewCalender(1).meetings.contains(meeting.id), "Organizer should have meeting");
        assertTrue(scheduler.viewCalender(2).meetings.contains(meeting.id), "Alice should have meeting");
        assertTrue(scheduler.viewCalender(3).meetings.contains(meeting.id), "Bob should have meeting");
        assertFalse(scheduler.viewCalender(4).meetings.contains(meeting.id), "Charlie should NOT have meeting");
    }

    @Test
    void testAddParticipantsToExistingMeeting() {
        scheduler.addMeetingRoom("Room A", 10);
        Meeting meeting = scheduler.createMeeting(1, now, now.plusHours(1), Arrays.asList(2), "Sync", 1);

        // Add Bob (3) and Charlie (4)
        boolean result = scheduler.addParticipants(meeting.id, Arrays.asList(3, 4));
        
        assertTrue(result);
        
        // Verify Meeting Object
        Map<Integer, RSVPStatus> statusMap = scheduler.getParticipationStatus(meeting.id);
        assertTrue(statusMap.containsKey(3));
        assertTrue(statusMap.containsKey(4));

        // Verify Calendars
        assertTrue(scheduler.viewCalender(3).meetings.contains(meeting.id), "Bob's calendar updated");
        assertTrue(scheduler.viewCalender(4).meetings.contains(meeting.id), "Charlie's calendar updated");
    }

    @Test
    void testRemoveParticipantsFromMeeting() {
        scheduler.addMeetingRoom("Room A", 10);
        List<Integer> participants = Arrays.asList(2, 3);
        Meeting meeting = scheduler.createMeeting(1, now, now.plusHours(1), participants, "Sync", 1);

        // Remove Alice (2)
        boolean result = scheduler.removeParticipants(meeting.id, Arrays.asList(2));
        
        assertTrue(result);

        // Verify Meeting Object
        Map<Integer, RSVPStatus> statusMap = scheduler.getParticipationStatus(meeting.id);
        assertFalse(statusMap.containsKey(2), "Alice removed from meeting object");
        assertTrue(statusMap.containsKey(3), "Bob still in meeting");

        // Verify Calendars
        assertFalse(scheduler.viewCalender(2).meetings.contains(meeting.id), "Alice's calendar cleared");
        assertTrue(scheduler.viewCalender(3).meetings.contains(meeting.id), "Bob's calendar unchanged");
    }

    @Test
    void testCancelMeeting() {
        scheduler.addMeetingRoom("Room A", 10);
        Meeting meeting = scheduler.createMeeting(1, now, now.plusHours(1), Arrays.asList(2, 3), "Cancel Me", 1);

        boolean result = scheduler.cancelMeeting(meeting.id, 1);
        assertTrue(result);

        // Verify Meeting Removed from Map
        assertNull(scheduler.meetingMap.get(meeting.id));

        // Verify Calendars Cleared
        assertFalse(scheduler.viewCalender(2).meetings.contains(meeting.id));
        assertFalse(scheduler.viewCalender(3).meetings.contains(meeting.id));
        
        // Note: Organizer calendar cleanup depends on implementation. 
        // Current implementation in cancelMeeting iterates over participants to remove meeting.
        // It does NOT explicitly remove from organizer unless organizer is in participant list or handled separately.
        // (Assuming the fix I provided in previous turns regarding organizer cleanup is applied, otherwise this assertion might fail for user 1)
    }

    @Test
    void testCapacityAndAvailability() {
        scheduler.addMeetingRoom("Tiny Room", 1); // Capacity 1
        
        // 1. Fail Capacity
        Meeting m1 = scheduler.createMeeting(1, now, now.plusHours(1), Arrays.asList(2, 3), "Too Many", 1);
        assertNull(m1, "Should fail due to capacity");

        // 2. Success
        Meeting m2 = scheduler.createMeeting(1, now, now.plusHours(1), Arrays.asList(2), "Just Right", 1);
        assertNotNull(m2);

        // 3. Fail Overlap
        Meeting m3 = scheduler.createMeeting(1, now.plusMinutes(30), now.plusMinutes(90), Arrays.asList(3), "Overlap", 1);
        assertNull(m3, "Should fail due to room unavailability");
    }

    @Test
    void testInvalidParticipant_NPE() {
        scheduler.addMeetingRoom("Room A", 10);
        int roomId = 1;

        // User 99 does not exist in userMap
        List<Integer> participants = Arrays.asList(99);

        try {
            scheduler.createMeeting(1, now, now.plusHours(1), participants, "Ghost Meeting", roomId);
        } catch (NullPointerException e) {
            fail("Should handle non-existent participants gracefully, but threw NPE");
        }
    }

    @Test
    void testCancelInvalidMeeting() {
        // Try to cancel a meeting ID that doesn't exist
        try {
            boolean result = scheduler.cancelMeeting(999, 1);
            assertFalse(result, "Should return false for non-existent meeting");
        } catch (NullPointerException e) {
            fail("Should handle non-existent meeting ID gracefully, but threw NPE");
        }
    }

    @Test
    void testOrganizerCalendarUpdate() {
        scheduler.addMeetingRoom("Room A", 10);
        Meeting meeting = scheduler.createMeeting(1, now, now.plusHours(1), Arrays.asList(2), "Sync", 1);

        Calender organizerCal = scheduler.viewCalender(1);
        assertTrue(organizerCal.meetings.contains(meeting.id), "Organizer should have the meeting in their calendar");
    }

    @Test
    void testParticipantStatusAndDecline() {
        scheduler.addMeetingRoom("Room A", 10);
        Meeting meeting = scheduler.createMeeting(1, now, now.plusHours(1), Arrays.asList(2), "Sync", 1);

        // Check status
        Map<Integer, RSVPStatus> status = scheduler.getParticipationStatus(meeting.id);
        assertEquals(RSVPStatus.PENDING, status.get(2));

        // Decline
        scheduler.updateParticipation(meeting.id, 2, RSVPStatus.REJECTED);

        // Check removal from calendar
        Calender userCal = scheduler.viewCalender(2);
        assertFalse(userCal.meetings.contains(meeting.id), "Declined meeting should be removed from user calendar");
    }

    @Test
    void testDoubleBookingAllowed() {
        scheduler.addMeetingRoom("Room A", 10);
        scheduler.addMeetingRoom("Room B", 10);

        Meeting m1 = scheduler.createMeeting(1, now, now.plusHours(1), Arrays.asList(2), "M1", 1);
        Meeting m2 = scheduler.createMeeting(3, now, now.plusHours(1), Arrays.asList(2), "M2", 2);

        assertNotNull(m2, "Double booking for participants should be allowed");
    }

    @Test
    void testConcurrency_MeetingId() throws InterruptedException {
        scheduler.addMeetingRoom("Room A", 1000);
        int numberOfThreads = 100;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final int index = i;
            service.submit(() -> {
                try {
                    // Create meeting with no participants to avoid locking on userMap
                    // Use unique time slots to avoid room availability conflicts
                    scheduler.createMeeting(1, now.plusHours(index * 2), now.plusHours(index * 2 + 1), new ArrayList<>(), "Concurrent", 1);
                } catch (Exception e) {
                    // ignore
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        service.shutdown();

        // If race condition occurs, size will be less than numberOfThreads because IDs were overwritten
        assertEquals(numberOfThreads, scheduler.meetingMap.size(), "Race condition detected in meeting ID generation");
    }
}