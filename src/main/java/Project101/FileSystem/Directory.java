package Project101.FileSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Directory extends FileSystemNode{
    Map<String,FileSystemNode> children;
    Directory parent;
    Directory(String name,Directory parent){
        super(name,true);
        children = new HashMap<>();
        this.parent = parent;
    }
    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemNode fileSystemNode : children.values()){
            totalSize += fileSystemNode.getSize();
        }
        return totalSize;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public void addFileSystemNode(FileSystemNode fileSystemNode){
        this.children.put(fileSystemNode.name,fileSystemNode);
    }
    public void removeFileSystemNode(String name){
        this.children.remove(name);
    }
    public List<FileSystemNode> getChildren(){
        return new ArrayList<>(this.children.values());
    }
    public FileSystemNode getChild(String name){
        return this.children.get(name);
    }
    @Override
    public Directory getParent(){
        return this.parent;
    }
}
