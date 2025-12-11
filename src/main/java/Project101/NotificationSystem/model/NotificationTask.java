package Project101.NotificationSystem.model;

import lombok.Getter;

@Getter
public class NotificationTask {
    private final Message message;
    private final ChannelType channelType;

    public NotificationTask(Message message, ChannelType channelType) {
        this.message = message;
        this.channelType = channelType;
    }

}
