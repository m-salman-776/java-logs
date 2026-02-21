package Project101.FileSystem;

public abstract class FileSystemNode {
    String name;
    boolean isDirectory;
    FileSystemNode parent;
    FileSystemNode(String name,boolean isDirectory){
        this.name = name;
        this.isDirectory = isDirectory;
    }
    public abstract int getSize();
    public abstract boolean isDirectory();
    public abstract FileSystemNode getParent();
}
