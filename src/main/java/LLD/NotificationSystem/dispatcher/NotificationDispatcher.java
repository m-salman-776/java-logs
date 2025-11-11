package LLD.NotificationSystem.dispatcher;

import LLD.NotificationSystem.channel.NotificationChannel;
import LLD.NotificationSystem.factory.NotificationFactory;
import LLD.NotificationSystem.model.NotificationTask;
import LLD.NotificationSystem.queue.NotificationQueue;

public class NotificationDispatcher implements Runnable {
    private final NotificationQueue queue = NotificationQueue.getInstance();

    @Override
    public void run() {
        System.out.println("Notification dispatcher started...");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                NotificationTask task = queue.take();
                NotificationChannel channel = NotificationFactory.createChannel(task.getChannelType());
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
