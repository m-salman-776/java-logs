package LLD.NotificationSystem.channel;

import LLD.NotificationSystem.model.Message;

public interface NotificationChannel {
    void send(Message message);
}
