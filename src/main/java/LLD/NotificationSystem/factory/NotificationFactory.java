package LLD.NotificationSystem.factory;

import LLD.NotificationSystem.channel.EmailChannel;
import LLD.NotificationSystem.channel.NotificationChannel;
import LLD.NotificationSystem.channel.PushChannel;
import LLD.NotificationSystem.channel.SmsChannel;
import LLD.NotificationSystem.model.ChannelType;

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
