package Project101.NotificationSystem.channel;

import Project101.NotificationSystem.model.Message;

public interface NotificationChannel {
    void send(Message message);
}
