package Project101.FileSystem;

public class Main {
    public static void main(String[] args) {
        FileSystemService fileSystemService = new FileSystemService();
        System.out.println(fileSystemService.pwd());
        fileSystemService.makeDirectory("/user");
        fileSystemService.makeDirectory("user/temp");
        fileSystemService.changeDirectory("user");
        System.out.println(fileSystemService.pwd());
        System.out.println("DONE");
    }
}
