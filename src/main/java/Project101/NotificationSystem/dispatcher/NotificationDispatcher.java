package Project101.NotificationSystem.dispatcher;

import Project101.NotificationSystem.channel.NotificationChannel;
import Project101.NotificationSystem.factory.NotificationFactory;
import Project101.NotificationSystem.model.NotificationTask;
import Project101.NotificationSystem.queue.NotificationQueue;

public class NotificationDispatcher implements Runnable {
    private final NotificationQueue queue = NotificationQueue.getInstance();

    @Override
    public void run() {
        System.out.println("Notification dispatcher started...");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                NotificationTask task = queue.take();
                NotificationChannel channel = NotificationFactory.getChanel(task.getChannelType());
                channel.send(task.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                // Log the error but continue processing
                System.err.println("Error processing notification: " + e.getMessage());
            }
        }
        System.out.println("Notification dispatcher stopped.");
    }
}
