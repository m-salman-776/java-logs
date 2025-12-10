package Project101.NotificationSystem.model;

/**
 * Represents an immutable message to be sent.
 * Use the nested Builder class to construct an instance.
 */
public final class Message {

    private final String recipient;
    private final String content;
    private final String subject; // Optional, for email

    // Private constructor to be called by the Builder
    private Message(Builder builder) {
        this.recipient = builder.recipient;
        this.content = builder.content;
        this.subject = builder.subject;
    }

    // Only getters, making the class immutable
    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Message{" +
                "recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    /**
     * A fluent builder for creating Message instances.
     */
    public static class Builder {
        // Required parameters
        private final String recipient;
        private final String content;

        // Optional parameters
        private String subject;

        public Builder(String recipient, String content) {
            if (recipient == null || recipient.trim().isEmpty()) {
                throw new IllegalArgumentException("Recipient cannot be null or empty.");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("Content cannot be null or empty.");
            }
            this.recipient = recipient;
            this.content = content;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
