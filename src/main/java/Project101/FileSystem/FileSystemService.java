package Project101.FileSystem;
import java.util.*;

public class FileSystemService {
    Directory root;
    private Directory currentDirectory;
    FileSystemService(){
        root = new Directory("/",null);
        this.currentDirectory = root;
    }
    private FileSystemNode resolvePath(String path){
        if (path == null || path.isEmpty()){
            throw new IllegalArgumentException("Path can't be empty");
        }
        FileSystemNode current = path.startsWith("/") ? this.root : this.currentDirectory;
        String [] components = path.split("/");
        for (String component : components){
            if (component.isEmpty() || component.equals(".")) continue;
            if (component.equals("..") && current.getParent() != null){
                current = current.getParent();
                continue;
            }
            if (!current.isDirectory) {
                throw new IllegalArgumentException(current.name + " is a file, not a directory");
            }

            Directory currentDir = (Directory) current;
            FileSystemNode child = currentDir.getChild(component);
            if (child == null) {
                throw new IllegalArgumentException("No such file or directory: " + component);
            }
            current = child;
        }
        return current;
    }

    public List<String> listContents(String path,ListOptions options){
        if (path == null){
            return filter(this.currentDirectory,options);
        }
        FileSystemNode target = resolvePath(path);
        Directory directory = (Directory) target;
        return filter(directory,options);
    }

    private List<String> filter(Directory directory,ListOptions options){
        List<String> ls = new ArrayList<>();
        for (FileSystemNode fileSystemNode : directory.getChildren()){
            ls.add(fileSystemNode.name);
        }
        return ls;
    }

    public void changeDirectory(String path){
        FileSystemNode target = resolvePath(path);
        // 2. Ensure it's actually a directory (you can't 'cd' into a text file!)
        if (!target.isDirectory()) {
            throw new IllegalArgumentException(target.name+ " is not a directory.");
        }
        this.currentDirectory = (Directory) target;
    }

    public void makeDirectory(String path) {
        // 1. Get the parsed context
        PathTarget target = resolveParentAndName(path, "mkdir");

        // 2. Check for duplicates
        if (target.parentDir.getChildren().stream().anyMatch(c -> c.name.equals(target.targetName))) {
            throw new IllegalArgumentException("mkdir: cannot create directory '" + path + "': File exists");
        }

        // 3. Create and attach
        Directory newDirectory = new Directory(target.targetName, target.parentDir);
        target.parentDir.addFileSystemNode(newDirectory);
    }

    private PathTarget resolveParentAndName(String path, String commandName) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException(commandName + ": missing operand");
        }

        // Sanitize trailing slashes
        while (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        if (path.equals("/")) {
            throw new IllegalArgumentException(commandName + ": cannot perform operation on root '/'");
        }

        // Safely split the path
        int lastSlashIndex = path.lastIndexOf("/");
        String parentPath;
        String targetName;

        if (lastSlashIndex == -1) {
            parentPath = ".";
            targetName = path;
        } else if (lastSlashIndex == 0) {
            parentPath = "/";
            targetName = path.substring(1);
        } else {
            parentPath = path.substring(0, lastSlashIndex);
            targetName = path.substring(lastSlashIndex + 1);
        }

        // Gracefully handle parent resolution
        FileSystemNode parentEntry;
        try {
            parentEntry = resolvePath(parentPath);
        } catch (Exception e) {
            throw new IllegalArgumentException(commandName + ": cannot access '" + parentPath + "': No such file or directory");
        }

        // Type checking
        if (!parentEntry.isDirectory()) {
            throw new IllegalArgumentException(commandName + ": '" + parentPath + "' is not a directory");
        }

        return new PathTarget((Directory) parentEntry, targetName);
    }

    public void touch(String path) {
        // 1. Get the parsed context
        PathTarget target = resolveParentAndName(path, "touch");

        // 2. Check if file already exists
        FileSystemNode existingNode = target.parentDir.getChildren().stream()
                .filter(c -> c.name.equals(target.targetName))
                .findFirst()
                .orElse(null);

        // 3. Update timestamp if exists, otherwise create new file
        if (existingNode != null) {
            ((File) existingNode).setSetLastModifiedTime(System.currentTimeMillis());;
        } else {
            File newFile = new File(target.targetName, target.parentDir);
            target.parentDir.addFileSystemNode(newFile);
        }
    }

    public void writeFile(String path, byte[] content) {
        // 1. Get the parsed context using our beautiful helper
        PathTarget target = resolveParentAndName(path, "writeFile");

        // 2. Look for an existing item with this name in the parent directory
        FileSystemNode existingNode = target.parentDir.getChildren().stream()
                .filter(c -> c.name.equals(target.targetName))
                .findFirst()
                .orElse(null);

        if (existingNode != null) {
            // 3a. Handle existing item

            // Edge Case: What if the user tries to write to "/home/user/docs", but "docs" is a folder?
            if (existingNode.isDirectory()) {
                throw new IllegalArgumentException("writeFile: cannot overwrite directory '" + path + "' with file content");
            }

            // It's safe to cast to a File and overwrite the content
            File file = (File) existingNode;
            file.write(content);

        } else {
            // 3b. Handle completely new file
            File newFile = new File(target.targetName, target.parentDir);
            newFile.write(content);

            // Don't forget to attach it to the parent tree!
            target.parentDir.addFileSystemNode(newFile);
        }
    }

    public String pwd(){
        List<String> pathList = new ArrayList<>();
        FileSystemNode fileSystemNode = this.currentDirectory;
        while (fileSystemNode != null){
            pathList.add(fileSystemNode.name);
            fileSystemNode = fileSystemNode.getParent();
        }
        Collections.reverse(pathList);
        String dir = String.join("/",pathList);
        dir = dir.substring(1);
        return dir.isEmpty() ? "/" : dir;
    }

    public byte[] readFile(String path) {
        // 1. Basic Sanity Check
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("cat: missing operand");
        }

        // 2. Find the exact target node in the file system
        FileSystemNode targetNode;
        try {
            targetNode = resolvePath(path);
        } catch (Exception e) {
            // Translate the internal exception to a standard terminal error
            throw new IllegalArgumentException("cat: " + path + ": No such file or directory");
        }

        // 3. Prevent reading a Directory
        // Edge Case: What if the user types "cat /home/user/docs"?
        if (targetNode.isDirectory()) {
            throw new IllegalArgumentException("cat: " + path + ": Is a directory");
        }

        // 4. Safely cast and read the content
        File file = (File) targetNode;
        return file.read();
    }

    // A simple container to hold the results of our path parsing
    private static class PathTarget {
        Directory parentDir;
        String targetName;

        public PathTarget(Directory parentDir, String targetName) {
            this.parentDir = parentDir;
            this.targetName = targetName;
        }
    }

}
