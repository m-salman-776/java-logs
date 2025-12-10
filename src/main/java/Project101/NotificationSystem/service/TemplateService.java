package Project101.NotificationSystem.service;

import java.util.Map;

public class TemplateService {

    // In a real system, this would fetch the template from the database (notification_templates table)
    public String getTemplate(String templateId) {
        // Mocking template fetching
        switch (templateId) {
            case "welcome-email":
            return "Hi {{username}}, welcome to our platform!";
            case "otp-sms":
            return "Your verification code is {{otp_code}}.";
            case "welcome-email-subject":
            return "Welcome to Our Platform!";
            default:
                // fall through to method's default return
                break;
        }
        return "A New Notification"; // Default template/subject
    }

    public String renderTemplate(String templateContent, Map<String, String> params) {
        String renderedContent = templateContent;
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                renderedContent = renderedContent.replace(placeholder, entry.getValue());
            }
        }
        return renderedContent;
    }
}
