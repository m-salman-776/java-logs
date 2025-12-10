package Project101.NotificationSystem.channel;

import Project101.NotificationSystem.model.Message;

public class SmsChannel implements NotificationChannel {
    @Override
    public void send(Message message) {
        System.out.println("====================================");
        System.out.println("Sending SMS...");
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Body: " + message.getContent());
        System.out.println("====================================");
        // Here you would integrate with an actual SMS gateway (e.g., Twilio)
    }
}
