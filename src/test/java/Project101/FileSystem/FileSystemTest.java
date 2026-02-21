package Project101.FileSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileSystemTest {
    FileSystemService fileSystemService;
    @BeforeEach
    void setup(){
        fileSystemService = new FileSystemService();
    }
    @Test
    void testMakeDirectory(){
        fileSystemService.makeDirectory("d1");
        assertEquals(List.of("d1"),fileSystemService.listContents(null,null));
    }

    @Test
    void testWriteAndReadFile(){
        fileSystemService.makeDirectory("user");
        fileSystemService.writeFile("/user/name.txt",new byte[]{'d','d'});
        byte[] content = fileSystemService.readFile("/user/name.txt");
        assertEquals("dd", new String(content));
    }

    @Test
    void testPwdRoot() {
        assertEquals("/", fileSystemService.pwd());
    }

    @Test
    void testPwdNested() {
        // This case is expected to FAIL due to double slash generation (e.g., //user)
        fileSystemService.makeDirectory("user");
        fileSystemService.changeDirectory("user");
        assertEquals("/user", fileSystemService.pwd());
    }

    @Test
    void testTouchCreatesEmptyFile() {
        // This case is expected to FAIL with NullPointerException because File content is not initialized
        fileSystemService.touch("empty.txt");
        byte[] content = fileSystemService.readFile("empty.txt");
        assertNotNull(content, "Touched file content should not be null");
        assertEquals(0, content.length, "Touched file should be empty");
    }

    @Test
    void testOverwriteExistingFile(){
        fileSystemService.writeFile("file.txt", "Old".getBytes());
        fileSystemService.writeFile("file.txt", "New".getBytes());
        assertEquals("New", new String(fileSystemService.readFile("file.txt")));
    }

    @Test
    void testOverwriteDirectoryWithFileThrowsException() {
        fileSystemService.makeDirectory("myDir");
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            fileSystemService.writeFile("myDir", "data".getBytes());
        });
        assertEquals("writeFile: cannot overwrite directory 'myDir' with file content", e.getMessage());
    }

    @Test
    void testReadDirectoryAsFileThrowsException() {
        fileSystemService.makeDirectory("myDir");
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            fileSystemService.readFile("myDir");
        });
        assertEquals("cat: myDir: Is a directory", e.getMessage());
    }

    @Test
    void testChangeDirectoryParent() {
        fileSystemService.makeDirectory("a");
        fileSystemService.changeDirectory("a");
        assertEquals("/a", fileSystemService.pwd());
        fileSystemService.changeDirectory("..");
        assertEquals("/", fileSystemService.pwd());
    }

    @Test
    void testChangeDirectoryToNonExistent() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            fileSystemService.changeDirectory("nonexistent");
        });
        assertTrue(e.getMessage().contains("No such file or directory"));
    }

    @Test
    void testListContents() {
        fileSystemService.makeDirectory("a");
        fileSystemService.makeDirectory("b");
        fileSystemService.touch("c.txt");
        List<String> contents = fileSystemService.listContents(null, null);
        assertTrue(contents.contains("a"));
        assertTrue(contents.contains("b"));
        assertTrue(contents.contains("c.txt"));
        assertEquals(3, contents.size());
    }

    @Test
    void testMakeDirectoryDuplicate() {
        fileSystemService.makeDirectory("a");
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            fileSystemService.makeDirectory("a");
        });
        assertTrue(e.getMessage().contains("File exists"));
    }

    @Test
    void testComplexPathResolution() {
        fileSystemService.makeDirectory("a");
        fileSystemService.makeDirectory("a/b");
        fileSystemService.touch("a/b/c.txt");

        byte[] content = fileSystemService.readFile("a/b/../b/c.txt");
        assertEquals(0, content.length);
    }

    @Test
    void testWriteFileToNonExistentParent() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            fileSystemService.writeFile("nonexistent/file.txt", "data".getBytes());
        });
        assertTrue(e.getMessage().contains("No such file or directory"));
    }

    @Test
    void testTouchExistingFilePreservesContent() {
        fileSystemService.touch("file.txt");
        fileSystemService.writeFile("file.txt", "content".getBytes());
        fileSystemService.touch("file.txt");
        assertEquals("content", new String(fileSystemService.readFile("file.txt")));
    }
}
