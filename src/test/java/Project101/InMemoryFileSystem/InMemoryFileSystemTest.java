package Project101.InMemoryFileSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryFileSystemTest {
    FileSystem fileSystem;
    @BeforeEach
    void setUp(){
        fileSystem = new FileSystem();
    }
    @Test
    void testMkdir(){
        fileSystem.mkdir("/user");
        List<String> result = fileSystem.ls("/");
        assertTrue(result.contains("user"));
    }

    @Test
    void testLsThrowsNPEForInvalidPath() {
        // Demonstrates lack of error handling for non-existent paths
        assertThrows(NullPointerException.class, () -> fileSystem.ls("/nonexistent/path"));
    }

    @Test
    void testReadContentThrowsNPEForInvalidPath() {
        // Demonstrates lack of error handling for non-existent files
        assertThrows(NullPointerException.class, () -> fileSystem.readContent("/nonexistent/file"));
    }

    @Test
    void testDirectoryHidesChildrenIfContentAdded() {
        // Demonstrates Design Flaw: Ambiguity between File and Directory
        fileSystem.mkdir("/a/b"); // /a is a directory containing b
        fileSystem.addContent("/a", "some content"); // /a is now treated as a file too

        List<String> listing = fileSystem.ls("/a");

        // The ls command logic returns the name of the node if content is present, ignoring children
        assertFalse(listing.contains("b"), "Design Flaw: Directory children are hidden if content is added to the directory node");
        assertTrue(listing.contains("a"), "Design Flaw: ls returns the directory name itself instead of its contents");
    }

    @Test
    void testMkdirThrowsExceptionWhenPathContainsFile() {
        fileSystem.addContent("/file", "content");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileSystem.mkdir("/file/child");
        });

        assertEquals("Path contains a file: file", exception.getMessage());
    }
}
