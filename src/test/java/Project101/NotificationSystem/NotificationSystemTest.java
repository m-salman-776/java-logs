package Project101.NotificationSystem;

import Project101.NotificationSystem.channel.NotificationChannel;
import Project101.NotificationSystem.factory.NotificationFactory;
import Project101.NotificationSystem.model.ChannelType;
import Project101.NotificationSystem.model.Message;
import Project101.NotificationSystem.model.NotificationTask;
import Project101.NotificationSystem.queue.NotificationQueue;
import Project101.NotificationSystem.service.NotificationService;
import Project101.NotificationSystem.service.TemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationSystemTest {

    private NotificationService notificationService;
    private TemplateService templateService;
    private NotificationQueue queue;

    @BeforeEach
    public void setUp() {
        templateService = new TemplateService();
        notificationService = new NotificationService(templateService);
        queue = NotificationQueue.getInstance();
    }

    @Test
    public void testMessageBuilder() {
        Message message = new Message.Builder("user@example.com", "Hello World")
                .withSubject("Welcome")
                .build();

        assertEquals("user@example.com", message.getRecipient());
        assertEquals("Hello World", message.getContent());
        assertEquals("Welcome", message.getSubject());
    }

    @Test
    public void testMessageBuilderInvalidRecipient() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Message.Builder(null, "Content").build();
        });
    }

    @Test
    public void testMessageBuilderInvalidContent() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Message.Builder("user@example.com", "").build();
        });
    }

    @Test
    public void testTemplateRendering() {
        String template = "Hello {{name}}, your code is {{code}}";
        Map<String, String> params = new HashMap<>();
        params.put("name", "Alice");
        params.put("code", "1234");

        String result = templateService.renderTemplate(template, params);
        assertEquals("Hello Alice, your code is 1234", result);
    }

    @Test
    public void testNotificationFactory() {
        NotificationChannel emailChannel = NotificationFactory.getChanel(ChannelType.EMAIL);
        assertNotNull(emailChannel);
        assertTrue(emailChannel instanceof Project101.NotificationSystem.channel.EmailChannel);

        NotificationChannel smsChannel = NotificationFactory.getChanel(ChannelType.SMS);
        assertNotNull(smsChannel);
        assertTrue(smsChannel instanceof Project101.NotificationSystem.channel.SmsChannel);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    public void testNotificationServiceQueuesTask() throws InterruptedException {
        String recipient = "test-queue-" + System.currentTimeMillis() + "@example.com";
        Map<String, String> params = new HashMap<>();
        params.put("username", "TestUser");
        
        notificationService.sendNotification(ChannelType.EMAIL, recipient, "welcome-email", params);
        
        // Loop to find our specific task, discarding any old ones
        NotificationTask task;
        while (true) {
            task = queue.take(); // Blocks until something is available
            if (task.getMessage().getRecipient().equals(recipient)) {
                break;
            }
            // If we picked up an old task, loop again.
            // The timeout on the test method prevents infinite loops.
        }
        
        assertNotNull(task);
        assertEquals(ChannelType.EMAIL, task.getChannelType());
        assertEquals(recipient, task.getMessage().getRecipient());
        assertTrue(task.getMessage().getContent().contains("TestUser"));
    }
}
