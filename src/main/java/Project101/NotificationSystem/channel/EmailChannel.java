package Project101.NotificationSystem.channel;

import Project101.NotificationSystem.model.Message;

public class EmailChannel implements NotificationChannel {
    @Override
    public void send(Message message) {
        System.out.println("====================================");
        System.out.println("Sending Email...");
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Subject: " + message.getSubject());
        System.out.println("Body: " + message.getContent());
        System.out.println("====================================");
        // Here you would integrate with an actual email sending service (e.g., SendGrid, AWS SES)
    }
}
