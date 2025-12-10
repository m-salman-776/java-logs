package Project101.NotificationSystem.channel;

import Project101.NotificationSystem.model.Message;

public class PushChannel implements NotificationChannel {
    @Override
    public void send(Message message) {
        System.out.println("====================================");
        System.out.println("Sending Push Notification...");
        System.out.println("Device Token: " + message.getRecipient());
        System.out.println("Body: " + message.getContent());
        System.out.println("====================================");
        // Here you would integrate with a push notification service (e.g., FCM, APNS)
    }
}
