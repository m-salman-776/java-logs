package LLD.NotificationSystem.service;

import LLD.NotificationSystem.model.ChannelType;
import LLD.NotificationSystem.model.Message;
import LLD.NotificationSystem.model.NotificationTask;
import LLD.NotificationSystem.queue.NotificationQueue;

import java.util.Map;

public class NotificationService {
    private final TemplateService templateService;
    private final NotificationQueue queue = NotificationQueue.getInstance();

    public NotificationService(TemplateService templateService) {
        this.templateService = templateService;
    }

    public void sendNotification(ChannelType channelType, String recipient, String templateId, Map<String, String> params) {
        // 1. Check user preferences
        if (!checkUserPreferences(recipient, channelType)) {
            System.out.println("User " + recipient + " has disabled notifications for channel " + channelType);
            return;
        }

        // 2. Get and render the template
        String templateContent = templateService.getTemplate(templateId);
        String renderedContent = templateService.renderTemplate(templateContent, params);

        // 3. Create the message using the Builder
        Message.Builder messageBuilder = new Message.Builder(recipient, renderedContent);
        if (channelType == ChannelType.EMAIL) {
            // A more realistic subject line
            String subject = templateService.getTemplate(templateId + "-subject");
            messageBuilder.withSubject(subject);
        }
        Message message = messageBuilder.build();

        // 4. Create a task and add it to the queue
        NotificationTask task = new NotificationTask(message, channelType);
        queue.add(task);
        System.out.println("Queued notification for " + recipient + " via " + channelType);
    }

    private boolean checkUserPreferences(String userId, ChannelType channelType) {
        System.out.println("Checking preferences for user " + userId + " on channel " + channelType + "...");
        return true;
    }
}
