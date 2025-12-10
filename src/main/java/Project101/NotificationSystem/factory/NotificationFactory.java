package Project101.NotificationSystem.factory;

import Project101.NotificationSystem.channel.EmailChannel;
import Project101.NotificationSystem.channel.NotificationChannel;
import Project101.NotificationSystem.channel.PushChannel;
import Project101.NotificationSystem.channel.SmsChannel;
import Project101.NotificationSystem.model.ChannelType;

public class NotificationFactory {
    public static NotificationChannel createChannel(ChannelType channelType) {
        switch (channelType) {
            case EMAIL:
                return new EmailChannel();
            case SMS:
                return new SmsChannel();
            case PUSH:
                return new PushChannel();
            default:
                throw new IllegalArgumentException("Unknown channel type: " + channelType);
        }
    }
}
