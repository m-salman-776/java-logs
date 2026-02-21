package Project101.FileSystem;

import DesignPatterns.wsobserver.repository.dbInterfaces.DataInterface;

import java.util.*;

public class FileSystemService {
    Directory root;
    private Directory currentDirectory;
    FileSystemService(){
        root = new Directory("/",null);
        this.currentDirectory = root;
    }
    public FileSystemNode resolvePath(String path){
        if (path == null || path.isEmpty()){
            path = "/";
//            throw new IllegalArgumentException("Path can't be empty");
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

    public void makeDirectory(String path){
        // /user/salman/test
        if (path == null || path.isEmpty() || path.equals("/")) {
            throw new IllegalArgumentException("Command");
        }
        String components [] = path.split("/");
        int idx = path.lastIndexOf('/');
        String directoryName = components[components.length-1];
        String parentDirName = path.substring(0,idx);


        try {
            Directory parentDirectory =  (Directory) resolvePath(parentDirName);
            Directory newDirectory = new Directory(directoryName,parentDirectory);
            parentDirectory.addFileSystemNode(newDirectory);
        } catch (Exception e) {
            throw new IllegalArgumentException("mkdir : Invalid file path");
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
        return dir.isEmpty() ? "/" : dir;
    }

}
