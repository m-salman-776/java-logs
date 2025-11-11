-- DB Schema for Notification System

-- Stores user information and their contact details
CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20) UNIQUE
);

-- Stores notification message templates
-- e.g., "Welcome, {{username}}!"
CREATE TABLE notification_templates (
    template_id VARCHAR(255) PRIMARY KEY,
    template_name VARCHAR(255) NOT NULL,
    channel_type ENUM('EMAIL', 'SMS', 'PUSH') NOT NULL,
    content_body TEXT NOT NULL
);

-- Stores user-specific preferences for different types of notifications
-- A user can enable/disable notifications for a specific channel and type
CREATE TABLE user_notification_preferences (
    preference_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    notification_type VARCHAR(255) NOT NULL, -- e.g., 'marketing_promo', 'security_alert'
    channel_type ENUM('EMAIL', 'SMS', 'PUSH') NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE (user_id, notification_type, channel_type)
);

-- Logs every notification sent by the system for history and debugging
CREATE TABLE notifications_log (
    notification_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    channel_type ENUM('EMAIL', 'SMS', 'PUSH') NOT NULL,
    content TEXT NOT NULL,
    status ENUM('PENDING', 'SENT', 'FAILED') NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    provider_message_id VARCHAR(255), -- ID from the external service (e.g., Twilio, SendGrid)
    error_message TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Example Data

INSERT INTO users (user_id, username, email, phone_number) VALUES
('user-123', 'John Doe', 'john.doe@example.com', '+15551234567');

INSERT INTO notification_templates (template_id, template_name, channel_type, content_body) VALUES
('welcome-email', 'Welcome Email', 'EMAIL', 'Hi {{username}}, welcome to our platform!'),
('otp-sms', 'OTP SMS', 'SMS', 'Your verification code is {{otp_code}}.');

INSERT INTO user_notification_preferences (user_id, notification_type, channel_type, is_enabled) VALUES
('user-123', 'marketing_promo', 'EMAIL', TRUE),
('user-123', 'marketing_promo', 'SMS', FALSE), -- Prefers not to get marketing via SMS
('user-123', 'security_alert', 'SMS', TRUE);
