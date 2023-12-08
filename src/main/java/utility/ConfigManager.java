package utility;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    static final Properties properties;

    static {
        properties = new Properties();
        // ../../configs/dev.properties
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("dev.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find dev.properties");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read the properties file", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    public static Properties getAllProperties(){
        return properties;
    }
}
// /Users/salman/Downloads/me/ws-observer/src/configs
///Users/salman/Downloads/me/ws-observer/src/main/java/utility/Config.java
