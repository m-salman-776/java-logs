-- DB Schema for a Senior-Level Calendar Application

-- Stores user information
CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    -- User's primary timezone, helps in displaying times in their preferred zone.
    primary_timezone VARCHAR(50) DEFAULT 'UTC'
);

-- A user can have multiple calendars (e.g., "Work", "Personal")
CREATE TABLE calendars (
    calendar_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    calendar_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Stores the master information for an event. If it's recurring, this is the "parent" or "template" event.
CREATE TABLE events (
    event_id VARCHAR(255) PRIMARY KEY,
    calendar_id VARCHAR(255) NOT NULL,
    organizer_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    -- The start and end time of the *first* occurrence, always in UTC.
    start_time_utc TIMESTAMP NOT NULL,
    end_time_utc TIMESTAMP NOT NULL,
    -- The original timezone the event was created in (e.g., 'America/New_York'). Crucial for interpreting recurrence rules correctly.
    creation_timezone VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (calendar_id) REFERENCES calendars(calendar_id),
    FOREIGN KEY (organizer_id) REFERENCES users(user_id)
);

-- Models the many-to-many relationship between events and attendees (users)
CREATE TABLE event_attendees (
    attendee_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    status ENUM('PENDING', 'ACCEPTED', 'DECLINED', 'TENTATIVE') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE(event_id, user_id)
);

-- THE CORE OF RECURRENCE: Stores the rule for a recurring event, inspired by iCalendar/RFC 5545.
CREATE TABLE recurrence_rules (
    rule_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id VARCHAR(255) NOT NULL,
    -- Frequency of the event.
    frequency ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY') NOT NULL,
    -- e.g., every 2 weeks (frequency=WEEKLY, interval_val=2)
    interval_val INT DEFAULT 1,
    -- The rule stops after this date (inclusive).
    ends_on_utc DATE,
    -- The rule stops after this many occurrences.
    count INT,
    -- For weekly recurrence, e.g., "every Monday and Wednesday". Stored as a comma-separated string.
    by_day VARCHAR(20), -- e.g., 'MO,WE'
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);

-- Stores exceptions to a recurrence rule. This is CRITICAL for a real-world system.
-- e.g., "The recurring meeting on Dec 25th is canceled" or "The meeting on Jan 5th is moved to 3 PM"
CREATE TABLE event_exceptions (
    exception_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id VARCHAR(255) NOT NULL,
    -- The original start date of the occurrence that is being modified or deleted.
    original_occurrence_date_utc DATE NOT NULL,
    -- If true, this occurrence is simply canceled.
    is_canceled BOOLEAN DEFAULT FALSE,
    -- If not null, this occurrence is rescheduled to this new time.
    new_start_time_utc TIMESTAMP,
    new_end_time_utc TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);
