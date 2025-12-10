package Project101.NotificationSystem.queue;

import Project101.NotificationSystem.model.NotificationTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NotificationQueue {
    private static NotificationQueue instance;
    private final BlockingQueue<NotificationTask> queue;

    private NotificationQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    public static synchronized NotificationQueue getInstance() {
        if (instance == null) {
            instance = new NotificationQueue();
        }
        return instance;
    }

    public void add(NotificationTask task) {
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public NotificationTask take() throws InterruptedException {
        return queue.take();
    }
}
