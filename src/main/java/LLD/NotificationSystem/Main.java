package LLD.NotificationSystem;

import LLD.NotificationSystem.dispatcher.NotificationDispatcher;
import LLD.NotificationSystem.model.ChannelType;
import LLD.NotificationSystem.service.NotificationService;
import LLD.NotificationSystem.service.TemplateService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Initialize services
        TemplateService templateService = new TemplateService();
        NotificationService notificationService = new NotificationService(templateService);

        // Start the dispatcher in a background thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new NotificationDispatcher());

        System.out.println("Application started. Sending notifications asynchronously.");
        System.out.println("----------------------------------------------------");


        // --- Send a Welcome Email ---
        Map<String, String> emailParams = new HashMap<>();
        emailParams.put("username", "John Doe");
        notificationService.sendNotification(
            ChannelType.EMAIL,
            "john.doe@example.com",
            "welcome-email",
            emailParams
        );

        // --- Send an OTP SMS ---
        Map<String, String> smsParams = new HashMap<>();
        smsParams.put("otp_code", "123456");
        notificationService.sendNotification(
            ChannelType.SMS,
            "+15551234567",
            "otp-sms",
            smsParams
        );

        // --- Send a Generic Push Notification ---
        notificationService.sendNotification(
            ChannelType.PUSH,
            "device-token-abc-123",
            "generic-push",
            null
        );

        System.out.println("----------------------------------------------------");
        System.out.println("All notification requests have been queued. The application continues to run.");
        System.out.println("The dispatcher will process them in the background.");

        // Wait a moment to see the dispatcher work
        Thread.sleep(1000);

        // Shutdown the dispatcher thread gracefully
        executor.shutdownNow();
    }
}
