package Project101.NotificationSystem.model;

public class NotificationTask {
    private final Message message;
    private final ChannelType channelType;

    public NotificationTask(Message message, ChannelType channelType) {
        this.message = message;
        this.channelType = channelType;
    }

    public Message getMessage() {
        return message;
    }

    public ChannelType getChannelType() {
        return channelType;
    }
}
