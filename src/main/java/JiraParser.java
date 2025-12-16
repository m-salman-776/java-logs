import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JiraParser {

    // The structure provided in your prompt
    static class Entity {
        public String id;
        public String parentId;
        public String name;
        public String type;

        @Override
        public String toString() {
            return String.format("[%s] %s (Type: %s, Parent: %s)", id, name, type, parentId);
        }
    }

    public static void main(String[] args) {
        String filePath = "/Users/salman/Downloads/code/java-logs/src/main/java/project.txt"; // Ensure this matches your file location
        List<Entity> entities = parseJiraFile(filePath);

        // Print results to verify
        for (Entity e : entities) {
            System.out.println(e);
        }
    }

    /**
     * Parses a text file with format: ID, Name, ParentID, Duration
     * Returns a list of Entity objects.
     */
    public static List<Entity> parseJiraFile(String filePath) {
        List<Entity> entityList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                // Split by comma
                String[] parts = line.split(",");

                // Basic validation to ensure line has enough columns
                if (parts.length < 4) continue;

                Entity entity = new Entity();
                entity.id = parts[0].trim();
                entity.name = parts[1].trim();

                // Handle "null" string for parent
                String parent = parts[2].trim();
                entity.parentId = parent.equalsIgnoreCase("null") ? null : parent;

                // We ignore parts[3] (Duration) here because the Entity class
                // provided doesn't have a duration field, but the file required it.

                // Logic to determine 'type' based on ID prefix since file didn't include type column
                entity.type = determineType(entity.id);

                entityList.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entityList;
    }

    private static String determineType(String id) {
        if (id.startsWith("PROJ")) return "Project";
        if (id.startsWith("EPIC")) return "Epic";
        if (id.startsWith("STORY")) return "Story";
        if (id.startsWith("TASK")) return "Task";
        if (id.startsWith("BUG")) return "Bug";
        return "Unknown";
    }
}
