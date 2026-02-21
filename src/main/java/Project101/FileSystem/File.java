package Project101.FileSystem;

import java.io.FileNotFoundException;

public class File extends FileSystemNode{
    private byte[] content;
    FileSystemNode fileSystemNode;
    File(String name, FileSystemNode fileSystemNode){
        super(name,false);
        this.fileSystemNode = fileSystemNode;
    }
    @Override
    public int getSize() {
        return content.length;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
    public byte[] read (){
        return this.content;
    }
    public void write(byte[] newContent){
        this.content = newContent;
    }
    @Override
    public FileSystemNode getParent(){
        return this.parent;
    }
}
